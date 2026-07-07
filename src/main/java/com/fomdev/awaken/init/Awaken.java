package com.fomdev.awaken.init;

import com.fomdev.awaken.entries.AwakenRegistries;
import com.fomdev.awaken.register.AwakenAttachmentTypes;
import com.fomdev.awaken.register.AwakenDataComponents;
import com.fomdev.awaken.register.awaken.AwakenPollinates;
import com.fomdev.awaken.register.awaken.AwakenQualities;
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
        AwakenAttachmentTypes.register(bus);
        AwakenDataComponents.register(bus);

        AwakenPollinates.register();
        AwakenQualities.register();
    }
}