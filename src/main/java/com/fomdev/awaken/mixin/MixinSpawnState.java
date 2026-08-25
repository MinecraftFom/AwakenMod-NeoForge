package com.fomdev.awaken.mixin;

import com.fomdev.awaken.init.config.AwakenCommon;
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
        return AwakenCommon.CONFIG.MAX_MOB_COUNT.get();
    }
}