package com.fomdev.awaken.init;

import com.fomdev.awaken.entries.AwakenRegistries;
import com.fomdev.awaken.register.blocks.AwakenBlockEntities;
import com.fomdev.awaken.register.blocks.AwakenBlocks;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import com.fomdev.awaken.register.awaken.AwakenPollinates;
import com.fomdev.awaken.register.awaken.AwakenQualities;
import com.fomdev.awaken.register.items.AwakenItems;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
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
        AwakenRegistries.register(bus);
        AwakenBlocks.register(bus);
        AwakenItems.register(bus);

        AwakenBlockEntities.register(bus);
        AwakenAttachmentTypes.register(bus);
        AwakenDataComponents.register(bus);

        AwakenPollinates.register();
        AwakenQualities.register();
    }
}