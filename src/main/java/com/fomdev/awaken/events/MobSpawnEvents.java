package com.fomdev.awaken.events;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spawn.MobSpawnManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class MobSpawnEvents
{
    @SubscribeEvent
    public static void onMobSpawn(
            FinalizeSpawnEvent event
    )
    {
        ResourceLocation level = event.getLevel().getLevel().dimension().location();

        if (!MobSpawnManager.LEVELED_ENTITIES.containsKey(level) || !MobSpawnManager.LEVELED_ENTITIES.get(level).contains(event.getEntity().getClass()))
            return;

        RandomSource random = event.getEntity().getRandom();
        float diff = DifficultyManager.getDimensionFactor(event.getLevel().getLevel());

        if (random.nextInt(100) > 10 * diff)
            return;

        MobSpawnManager.spawn(
                event.getEntity(),
                diff,
                event.getLevel().getLevel(),
                random
        );
    }
}