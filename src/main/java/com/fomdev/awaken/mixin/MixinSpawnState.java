package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.init.config.AwakenCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NaturalSpawner.SpawnState.class)
public class MixinSpawnState
{
    @Redirect(method = "canSpawnForCategory", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/MobCategory;getMaxInstancesPerChunk()I"))
    private int canSpawnForCategory(
            MobCategory instance
    )
    {
        IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
        if (server == null)
            return AwakenCommon.CONFIG.MAX_MOB_COUNT.get();

        ServerLevel level = server.overworld();
        float diff = DifficultyManager.getLevelDifficulty(level).pow(5).floatValue();
        int max = AwakenCommon.CONFIG.MAX_MOB_COUNT.get();

        return Math.max((int) (diff / max), max);
    }
}