package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Mixin(TrialSpawner.class)
public class MixinTrialSpawner
{
    @Inject(method = "getTargetCooldownLength", at = @At("RETURN"), cancellable = true)
    private void getTargetCooldownLength(
            CallbackInfoReturnable<Integer> cir
    )
    {
        int original = cir.getReturnValue();
        // patch: getSingleplayerServer() may result in null when unloading game
        IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
        if (server == null)
            return;

        BigDecimal diff = DifficultyManager.getLevelDifficulty(server.overworld());
        BigDecimal factor = diff.sqrt(new MathContext(2)).sqrt(new MathContext(2)).sqrt(new MathContext(2)).sqrt(new MathContext(2));
        BigDecimal factor2 = factor.max(new BigDecimal("1"));
        BigDecimal factor3 = new BigDecimal(original);
        BigDecimal factor4 = factor3.divide(factor2, RoundingMode.HALF_UP);
        cir.setReturnValue(factor4.intValueExact());
    }
}