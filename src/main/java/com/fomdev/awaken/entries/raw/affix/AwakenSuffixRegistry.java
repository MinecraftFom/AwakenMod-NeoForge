package com.fomdev.awaken.entries.raw.affix;

import com.fomdev.flame.register.FreezingRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

public class AwakenSuffixRegistry extends FreezingRegistry<AwakenSuffix<?>>
{
    public AwakenSuffixRegistry(
            ResourceKey<Registry<AwakenSuffix<?>>> key
    )
    {
        super(key);
    }

    @Override
    protected void onRegister(FMLCommonSetupEvent event)
    {
        super.onRegister(event);
        for (AwakenSuffix<?> suffix: getRegistries())
            suffix.register(NeoForge.EVENT_BUS);
    }
}