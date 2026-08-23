package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.ClientDifficultyManager;
import com.fomdev.awaken.enchant.EnchantManager;
import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.util.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Mixin(ItemStack.class)
public abstract class MixinItemStack implements DataComponentHolder
{
    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract boolean is(Item p_150931_);

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
                                    "item.awaken.unawaken_item"
                            )
                            .withStyle(
                                    ChatFormatting.OBFUSCATED
                            )
                            .withStyle(
                                    ChatFormatting.RED
                            )
            );

            return;
        }

        AwakenQuality quality = NBTUtil.deserializeQuality(self);
        AwakenInfix.InfixInstance infix = NBTUtil.deserializeInfix(self);
        AwakenPrefix.PrefixInstance prefix = NBTUtil.deserializePrefix(self);
        AwakenSuffix.SuffixInstance suffix = NBTUtil.deserializeSuffix(self);
        MutableComponent component = Component.empty();

        if (infix != null && prefix != null && suffix != null)
            component.append(LocaleUtil.localizePrefix(prefix)).append(" ").append(LocaleUtil.localizeInfix(infix)).append("-").append(cir.getReturnValue()).append(" (").append(LocaleUtil.localizeSuffix(suffix)).append(")");
        else
            component.append(cir.getReturnValue());

        if (quality != null)
            component.withStyle(ColorUtil.colorStyle(ColorUtil.render(quality.getColors(), quality.getPattern()).backEnd()));

        cir.setReturnValue(component);
    }

    @Inject(method = "getComponents", at = @At("RETURN"), cancellable = true)
    private void getComponents(
            CallbackInfoReturnable<DataComponentMap> cir
    )
    {
        DataComponentMap original = cir.getReturnValue();
        ItemStack self = (ItemStack) (Object) this;
        if (!original.has(AwakenDataComponents.AWAKEN_DESCRIBER_STORAGE.get()))
            return;

        Records.AwakenDescriberComponent desc = original.get(AwakenDataComponents.AWAKEN_DESCRIBER_STORAGE.get());
        AwakenPrefix prefix;

        assert desc != null;
        if (desc.prefix() == null || (prefix = NBTUtil.deserializePrefix(desc.prefix())) == null)
            return;

        ItemEnchantments ie = original.getOrDefault(EnchantmentHelper.getComponentType(self), ItemEnchantments.EMPTY);
        ItemEnchantments.Mutable mie = new ItemEnchantments.Mutable(ie);
        prefix.getBaseEnchantments()
                .stream()
                .map(Records.EnchantmentHolder::toInstance)
                .filter(Objects::nonNull)
                .filter(e -> EnchantManager.isPrimaryItemFor(self, e.enchantment))
                .forEach(e -> mie.set(e.enchantment, e.level));

        PatchedDataComponentMap map = new PatchedDataComponentMap(original);
        map.set(EnchantmentHelper.getComponentType(self), mie.toImmutable());
        cir.setReturnValue(map);
    }

    @Inject(method = "getEnchantments", at = @At("RETURN"), cancellable = true)
    private void getEnchantments(
            CallbackInfoReturnable<ItemEnchantments> cir
    )
    {
        ItemStack stack = (ItemStack) (Object) this;
        AwakenPrefix prefix = NBTUtil.deserializePrefix(stack);
        if (prefix == null)
            return;

        ImmutableList<Records.EnchantmentHolder> enchs = prefix.getBaseEnchantments();
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(cir.getReturnValue());
        enchs
                .stream()
                .map(Records.EnchantmentHolder::toInstance)
                .filter(Objects::nonNull)
                .forEach(ench -> mutable.set(ench.enchantment, ench.level));
        cir.setReturnValue(mutable.toImmutable());
    }

    @Inject(method = "getTooltipLines", at = @At("HEAD"), cancellable = true)
    private void fancyTooltip(Item.TooltipContext context, Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir)
    {
        ItemStack self = (ItemStack) (Object) this;

        if (player == null || player.isCreative() || !player.getInventory().contains(self::equals))
            return;

        Records.AwakenEpochComponent epoch = NBTUtil.deserializeEpoch(self);
        if (epoch == null)
            return;

        BigDecimal difficulty = ClientDifficultyManager.getDifficulty();
        BigDecimal level = NBTUtil.deserializeAwakenLevel(player);
        if (difficulty.compareTo(epoch.requiredMinDifficulty()) < 0 || level.compareTo(epoch.requiredAwakenLevel()) < 0)
            cir.setReturnValue(
                    TooltipUtil.castEpochTooltip(
                            flag,
                            epoch,
                            NBTUtil.deserializeAwakenLevel(player),
                            ClientDifficultyManager.getDifficulty()
                    )
            );
    }

    @Inject(method = "getItem", at = @At("RETURN"), cancellable = true)
    private void restrictedItem(CallbackInfoReturnable<Item> cir)
    {
        ItemStack self = (ItemStack) (Object) this;
        if (!AwakenDataComponents.AWAKEN_EPOCH_STORAGE.isBound())
            return;

        Player player = Minecraft.getInstance().player;
        if (player == null || player.isCreative() || player.level() instanceof ServerLevel)
            return;

        if (!player.getInventory().contains(self::equals))
            return;

        Records.AwakenEpochComponent epoch = NBTUtil.deserializeEpoch(self);
        if (epoch == null)
            return;

        BigDecimal difficulty = ClientDifficultyManager.getDifficulty();
        BigDecimal level = NBTUtil.deserializeAwakenLevel(player);

        if (difficulty.compareTo(epoch.requiredMinDifficulty()) < 0 || level.compareTo(epoch.requiredAwakenLevel()) < 0)
             cir.setReturnValue(AwakenItems.UNKNOWN_ITEM.asItem());
    }
}