package com.fomdev.awaken.init;

import com.fomdev.awaken.entries.AwakenRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Awaken.MODID)
public class Awaken
{
    public static final String MODID = "awaken";

    public Awaken(
            IEventBus bus,
            ModContainer container
    )
    {
        AwakenRegistries.register(bus);
    }
}