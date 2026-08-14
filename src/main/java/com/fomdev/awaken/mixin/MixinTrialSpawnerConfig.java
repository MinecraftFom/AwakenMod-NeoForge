package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TrialSpawnerConfig.class)
public class MixinTrialSpawnerConfig
{
    @Inject(method = "calculateTargetTotalMobs", at = @At("RETURN"), cancellable = true)
    private void calculateTargetTotalMobs(
            int players,
            CallbackInfoReturnable<Integer> cir
    )
    {
        cir.setReturnValue((int) DifficultyManager.getLevelDifficulty(Minecraft.getInstance().getSingleplayerServer().overworld()) * cir.getReturnValue());
    }

    @Inject(method = "calculateTargetSimultaneousMobs", at = @At("RETURN"), cancellable = true)
    private void calculateTargetSimultaneousMobs(
            int players,
            CallbackInfoReturnable<Integer> cir
    )
    {
        cir.setReturnValue((int) DifficultyManager.getLevelDifficulty(Minecraft.getInstance().getSingleplayerServer().overworld()) * cir.getReturnValue());
    }
}