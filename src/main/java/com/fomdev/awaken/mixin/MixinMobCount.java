package com.fomdev.awaken.mixin;

import com.fomdev.awaken.init.config.AwakenCommon;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.level.LocalMobCapCalculator$MobCounts")
public class MixinMobCount
{
    @Shadow
    @Final
    private Object2IntMap<MobCategory> counts;

    @Inject(method = "canSpawn", at = @At("RETURN"), cancellable = true)
    private void canSpawn(
            MobCategory category,
            CallbackInfoReturnable<Boolean> cir
    )
    {
        cir.setReturnValue(this.counts.getOrDefault(category, 0) < AwakenCommon.CONFIG.GENERATABLE_MAX.get());
    }
}