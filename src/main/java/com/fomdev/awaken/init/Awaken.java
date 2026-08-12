package com.fomdev.awaken.init;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.literature.Literature;
import com.fomdev.awaken.spawn.EquipmentManager;
import com.fomdev.awaken.spawn.MobSpawnManager;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import org.slf4j.Logger;

@Mod(Awaken.MODID)
public class Awaken
{
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "awaken";

    public Awaken(
            IEventBus bus,
            ModContainer container
    )
    {
        bus.register(this);

        AwakenRegistries.register(bus);
        ShuffledRegistries.register(bus);

        container.registerConfig(
                ModConfig.Type.COMMON,
                AwakenCommon.SPEC
        );
    }

    @SubscribeEvent
    public void onConfigLoad(
            ModConfigEvent.Loading event
    )
    {
        EquipmentManager.loadFromConfig();
        Literature.init();
        MobSpawnManager.loadFromConfig();
        ShuffledRegistries.initFromConfig();
    }
}