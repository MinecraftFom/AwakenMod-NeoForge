package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TrialSpawner.class)
public class MixinTrialSpawner
{
    @Inject(method = "getTargetCooldownLength", at = @At("RETURN"), cancellable = true)
    private void getTargetCooldownLength(
            CallbackInfoReturnable<Integer> cir
    )
    {
        int original = cir.getReturnValue();
        float diff = DifficultyManager.getLevelDifficulty(Minecraft.getInstance().getSingleplayerServer().overworld());
        cir.setReturnValue(original / Math.max((int) Math.pow(diff, 1.0 / 20.0), 1));
    }
}