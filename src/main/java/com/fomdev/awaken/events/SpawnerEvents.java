package com.fomdev.awaken.events;

import com.fomdev.awaken.event.SpawnerExpireEvent;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.SpawnerUtil;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class SpawnerEvents
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpawnerExpire(
            SpawnerExpireEvent event
    )
    {
        event.getLevel().explode(
                null,
                event.getPos().getX(),
                event.getPos().getY(),
                event.getPos().getZ(),
                2.5F,
                Level.ExplosionInteraction.TNT
        );
    }

    @SubscribeEvent
    public static void onSpawnerSpawn(
            FinalizeSpawnEvent event
    )
    {
        if (event.getSpawnType() != MobSpawnType.SPAWNER)
            return;

        if (!SpawnerUtil.increaseUse(event.getLevel().getLevel(), event.getSpawner().left().get().getBlockPos())) // 100% non-null
            NeoForge.EVENT_BUS.post(new SpawnerExpireEvent(event.getLevel().getLevel(), event.getSpawner().left().get().getBlockPos()));
    }
}