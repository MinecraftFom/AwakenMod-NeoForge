package com.fomdev.awaken.mixin;

import com.fomdev.awaken.entries.raw.AwakenPrefix;
import com.fomdev.awaken.entries.raw.AwakenQuality;
import com.fomdev.awaken.entries.raw.AwakenSuffix;
import com.fomdev.awaken.util.NBTUtil;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IItemExtension.class)
public interface MixinIItemExtension
{
    @Inject(method = "getMaxDamage", at = @At("RETURN"), cancellable = true)
    private void getCustomMaxDamage(ItemStack self, CallbackInfoReturnable<Integer> cir)
    {
        int origin = cir.getReturnValue();
        if (origin == 0)
            return;

        AwakenPrefix prefix = NBTUtil.deserializePrefix(self);
        AwakenSuffix suffix = NBTUtil.deserializeSuffix(self);
        AwakenQuality quality = NBTUtil.deserializeQuality(self);

        if (prefix != null)
            origin += prefix.getDurability();

        if (suffix != null)
            origin += suffix.addition();

        cir.setReturnValue((int) (origin * (quality == null? 1: quality.getFactor())));
    }

    @Inject(method = "getAllEnchantments", at = @At("RETURN"), cancellable = true)
    private void getAllEnchantments(
            ItemStack stack,
            HolderLookup.RegistryLookup<Enchantment> lookup,
            CallbackInfoReturnable<ItemEnchantments> cir
    )
    {
        AwakenPrefix prefix = NBTUtil.deserializePrefix(stack);
        if (prefix == null)
            return;

        ImmutableList<EnchantmentInstance> enchs = prefix.getBaseEnchantments();
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(cir.getReturnValue());
        enchs.forEach(ench -> mutable.set(ench.enchantment, ench.level));
        cir.setReturnValue(mutable.toImmutable());
    }
}