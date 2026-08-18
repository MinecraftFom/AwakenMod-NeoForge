package com.fomdev.awaken.init;

import com.fomdev.awaken.enchant.EnchantManager;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.literature.Literature;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.spawn.EquipmentManager;
import com.fomdev.awaken.spawn.MobSpawnManager;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.awaken.util.NBTUtil;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
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
        EnchantManager.init();
        EquipmentManager.loadFromConfig();
        Literature.init();
        MobSpawnManager.loadFromConfig();
        ShuffledRegistries.initFromConfig();
    }

    @SubscribeEvent
    public void onFMLClientSetup(
            FMLClientSetupEvent event
    )
    {
        event.enqueueWork(() ->
            ItemProperties.register(
                    AwakenItems.SOUL_FRAGMENT.asItem(),
                    ResourceLocation.fromNamespaceAndPath(
                            MODID,
                            "soul"
                    ),
                    (stack, level, entity, seed) -> NBTUtil.deserializeSoul(stack).current()
            )
        );
    }
}