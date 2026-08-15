package com.fomdev.awaken.mixin;

import com.fomdev.awaken.init.config.AwakenCommon;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RangedAttribute.class)
public abstract class MixinAttribute
{
    @Shadow
    public abstract double getMaxValue();

    @Shadow
    @Final
    private double minValue;

    @Inject(method = "getMaxValue", at = @At("RETURN"), cancellable = true)
    private void getMaxValue(
            CallbackInfoReturnable<Double> cir
    )
    {
        cir.setReturnValue(cir.getReturnValue() * AwakenCommon.CONFIG.MAX_ATTRIBUTE.get());
    }

    @Inject(method = "sanitizeValue", at = @At("RETURN"), cancellable = true)
    private void sanitizeValue(
            double value,
            CallbackInfoReturnable<Double> cir
    )
    {
        cir.setReturnValue(Double.isNaN(value) ? this.minValue : Mth.clamp(value, this.minValue, this.getMaxValue()));
    }
}