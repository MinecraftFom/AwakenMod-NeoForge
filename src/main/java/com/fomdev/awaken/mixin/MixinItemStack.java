package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.entries.*;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.util.ColorUtil;
import com.fomdev.awaken.util.LocaleUtil;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.TooltipUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class MixinItemStack implements DataComponentHolder
{
    @Shadow
    public abstract Component getHoverName();

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract boolean is(Item p_150931_);

    @Shadow
    public abstract int getMaxDamage();

    @Shadow
    public abstract int getDamageValue();

    @Shadow
    @Final
    PatchedDataComponentMap components;

    @Shadow
    @Final
    private static Component DISABLED_ITEM_TOOLTIP;

    @Shadow
    @Nullable
    public abstract Entity getEntityRepresentation();

    @Shadow
    @Nullable
    private Entity entityRepresentation;

    @Inject(method = "getMaxDamage", at = @At("RETURN"), cancellable = true)
    private void customDurability(CallbackInfoReturnable<Integer> cir)
    {
        ItemStack self = (ItemStack) (Object) this;
        cir.setReturnValue(self.getItem().getMaxDamage(self));
    }

    @Inject(method = "getHoverName", at = @At("RETURN"), cancellable = true)
    private void fancyName(CallbackInfoReturnable<Component> cir)
    {
        ItemStack self = (ItemStack) (Object) this;
        if (self.is(AwakenItems.UNKNOWN_ITEM))
        {
            cir.setReturnValue(
                    Component
                            .translatable(
                                    "item.unawaken.name"
                            )
                            .withStyle(
                                    ChatFormatting.OBFUSCATED
                            )
                            .withStyle(
                                    ChatFormatting.RED
                            )
            );
        }

        AwakenQuality quality = NBTUtil.deserializeQuality(self);
        AwakenInfix infix = NBTUtil.deserializeInfix(self);
        AwakenPrefix prefix = NBTUtil.deserializePrefix(self);
        AwakenSuffix suffix = NBTUtil.deserializeSuffix(self);
        MutableComponent component = Component.empty();

        if (infix != null && prefix != null && suffix != null)
            component.append(LocaleUtil.localizePrefix(prefix)).append(" ").append(LocaleUtil.localizeInfix(infix)).append("-").append(cir.getReturnValue()).append(" (").append(LocaleUtil.localizeSuffix(suffix)).append(")");
        else
            component.append(cir.getReturnValue());

        if (quality != null)
            component.withStyle(ColorUtil.colorStyle(ColorUtil.render(quality.getColors(), quality.getPattern()).backEnd()));

        cir.setReturnValue(component);
    }

    @Inject(method = "getTooltipLines", at = @At("RETURN"), cancellable = true)
    private void fancyTooltip(Item.TooltipContext context, Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir)
    {
        ItemStack self = (ItemStack) (Object) this;
        if (self.is(AwakenItems.UNKNOWN_ITEM))
        {
            AwakenEpoch epoch = NBTUtil.deserializeEpoch(self);
            if (epoch == null)
                return;

            if (!(player.level() instanceof ServerLevel level))
                return;

            cir.setReturnValue(
                    TooltipUtil.castEpochTooltip(
                            flag,
                            epoch,
                            DifficultyManager.getLevelDifficulty(
                                    level
                            ),
                            NBTUtil.deserializeAwakenLevel(player)
                    )
            );
        }
    }

    @Inject(method = "getItem", at = @At("RETURN"), cancellable = true)
    private void restrictedItem(CallbackInfoReturnable<Item> cir)
    {
        ItemStack self = (ItemStack) (Object) this;
        Entity entity = getEntityRepresentation();
        if (!(entity instanceof ServerPlayer player))
            return;

        AwakenEpoch epoch = NBTUtil.deserializeEpoch(self);
        if (epoch == null)
            return;

        float difficulty = DifficultyManager.getLevelDifficulty(player.serverLevel());
        float level = NBTUtil.deserializeAwakenLevel(player);

        if (difficulty < epoch.getRequiredDifficulty() || level < epoch.getRequiredLevel().getMin())
            cir.setReturnValue(AwakenItems.UNKNOWN_ITEM.asItem());
    }
}