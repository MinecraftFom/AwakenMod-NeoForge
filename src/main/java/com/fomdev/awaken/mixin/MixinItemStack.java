package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.ClientDifficultyManager;
import com.fomdev.awaken.entries.raw.affix.AwakenPrefix;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.util.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mixin(ItemStack.class)
public abstract class MixinItemStack implements DataComponentHolder
{
    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract boolean is(Item p_150931_);

    @Shadow
    @Final
    @Deprecated
    @Nullable
    private Item item;

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

        AwakenPrefix.PrefixInstance prefix = NBTUtil.deserializeAffix$Prefix(self);
        MutableComponent component = Component.empty();

        if (prefix != null && !prefix.isEmpty())
        {
            Component result = cir.getReturnValue();
            component
                    .append(LocaleUtil.localizePrefix(prefix))
                    .append(" ")
                    .append(result)
                    .withStyle(result.getStyle());
        }
        else
            component.append(cir.getReturnValue());

        cir.setReturnValue(component);
    }

    @Inject(method = "getComponents", at = @At("RETURN"), cancellable = true)
    private void getComponents(
            CallbackInfoReturnable<DataComponentMap> cir
    )
    {
        if (!AwakenDataComponents.AWAKEN_AFFIX_PREFIX_STORAGE.isBound())
            return;

        DataComponentMap original = cir.getReturnValue();
        AwakenPrefix.PrefixInstance prefix = original.getOrDefault(AwakenDataComponents.AWAKEN_AFFIX_PREFIX_STORAGE.get(), AwakenPrefix.PrefixInstance.EMPTY);
        ItemEnchantments ie = original.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        ItemEnchantments.Mutable mie = new ItemEnchantments.Mutable(ie);
        prefix.getValue().getBaseEnchantments()
                .stream()
                .map(Records.EnchantmentHolder::toInstance)
                .filter(Objects::nonNull)
                .filter(e -> {
                    if (item == Items.BOOK)
                        return true;

                    Optional<HolderSet<Item>> primaryItems = e.enchantment.value().definition().primaryItems();
                    return (primaryItems.isEmpty() || primaryItems.get().contains(item.builtInRegistryHolder()));
                })
                .forEach(e -> mie.set(e.enchantment, e.level));

        PatchedDataComponentMap map = new PatchedDataComponentMap(original);
        map.set(DataComponents.ENCHANTMENTS, mie.toImmutable());
        cir.setReturnValue(map);
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