package com.fomdev.awaken.init;

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
    }
}