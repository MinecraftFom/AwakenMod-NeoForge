package com.fomdev.awaken.mixin;

import com.fomdev.awaken.entries.AwakenPrefix;
import com.fomdev.awaken.entries.AwakenQuality;
import com.fomdev.awaken.entries.AwakenSuffix;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.world.item.ItemStack;
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
            origin += prefix.addition();

        if (suffix != null)
            origin += suffix.addition();

        cir.setReturnValue((int) (origin * (quality == null? 1: quality.getFactor())));
    }
}