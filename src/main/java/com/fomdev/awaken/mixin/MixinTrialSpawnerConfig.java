package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigDecimal;
import java.math.MathContext;

@Mixin(TrialSpawnerConfig.class)
public class MixinTrialSpawnerConfig
{
    @Inject(method = "calculateTargetTotalMobs", at = @At("RETURN"), cancellable = true)
    private void calculateTargetTotalMobs(
            int players,
            CallbackInfoReturnable<Integer> cir
    )
    {
        IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
        if (server == null)
            return;

        BigDecimal diff = DifficultyManager.getLevelDifficulty(server.overworld());
        BigDecimal factor = diff.sqrt(new MathContext(2)).sqrt(new MathContext(2)).sqrt(new MathContext(2));
        BigDecimal factor2 = factor.compareTo(new BigDecimal(0)) <= 0? factor: new BigDecimal("1");

        cir.setReturnValue(factor2.intValueExact());
    }

    @Inject(method = "calculateTargetSimultaneousMobs", at = @At("RETURN"), cancellable = true)
    private void calculateTargetSimultaneousMobs(
            int players,
            CallbackInfoReturnable<Integer> cir
    )
    {
        IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
        if (server == null)
            return;

        BigDecimal diff = DifficultyManager.getLevelDifficulty(server.overworld());
        BigDecimal factor = diff.sqrt(new MathContext(2)).sqrt(new MathContext(2)).sqrt(new MathContext(2));
        BigDecimal factor2 = factor.compareTo(new BigDecimal(0)) <= 0? factor: new BigDecimal("1");

        cir.setReturnValue(factor2.intValueExact());
    }
}