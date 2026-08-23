package com.fomdev.awaken.mixin;

import com.fomdev.awaken.enchant.EnchantManager;
import com.fomdev.awaken.entries.raw.AwakenAspect;
import com.fomdev.awaken.entries.raw.AwakenPrefix;
import com.fomdev.awaken.entries.raw.AwakenQuality;
import com.fomdev.awaken.entries.raw.AwakenSuffix;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

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

        ImmutableList<Records.EnchantmentHolder> enchs = prefix.getBaseEnchantments();
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(cir.getReturnValue());
        enchs
                .stream()
                .map(ench -> ench.toInstance(lookup))
                .forEach(ench -> mutable.set(ench.enchantment, ench.level));
        cir.setReturnValue(mutable.toImmutable());
    }

    @Inject(method = "isPrimaryItemFor", at = @At("RETURN"), cancellable = true)
    private void isPrimaryItemFor(
            ItemStack stack,
            Holder<Enchantment> enchantment,
            CallbackInfoReturnable<Boolean> cir
    )
    {
        ResourceKey<Enchantment> key = enchantment.getKey();
        if (key == null)
            return;

        List<AwakenAspect.AspectInstance> available = NBTUtil.deserializeAspects(stack);
        List<AwakenAspect.AspectInstance> required = EnchantManager.get(key.location(), 1);
        boolean flag0;
        boolean flag1;

        flag0 = EnchantManager.isPrimaryItemFor(stack, enchantment);
        flag1 = EnchantManager.meetsRequirements(available, required);


        cir.setReturnValue(flag0 && flag1);
    }

    @Inject(method = "supportsEnchantment", at = @At("RETURN"), cancellable = true)
    private void supportsEnchantment(
            ItemStack stack,
            Holder<Enchantment> enchantment,
            CallbackInfoReturnable<Boolean> cir
    )
    {
        ResourceKey<Enchantment> key = enchantment.getKey();
        if (key == null)
            return;

        List<AwakenAspect.AspectInstance> available = NBTUtil.deserializeAspects(stack);
        List<AwakenAspect.AspectInstance> required = EnchantManager.get(key.location(), 1);
        boolean flag0;
        boolean flag1;

        flag0 = EnchantManager.supportsEnchantment(stack, enchantment);
        flag1 = EnchantManager.meetsRequirements(available, required);


        cir.setReturnValue(flag0 && flag1);
    }
}