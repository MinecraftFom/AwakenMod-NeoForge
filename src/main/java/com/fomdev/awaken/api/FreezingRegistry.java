package com.fomdev.awaken.api;

import com.fomdev.awaken.event.RegisterEvent;
import com.fomdev.awaken.util.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FreezingRegistry<T extends Registry>
{
    private final Logger logger;

    private final Map<ResourceLocation, T> registryTable;
    private final ResourceKey<net.minecraft.core.Registry<T>> key;
    private IEventBus bus;

    private boolean frozen;

    public FreezingRegistry(
            ResourceKey<net.minecraft.core.Registry<T>> key
    )
    {
        this.logger = LoggerContext.getContext().getLogger(this.getClass().getName() + "/" + key);

        this.registryTable = new LinkedHashMap<>();
        this.key = key;

        this.frozen = false;
    }

    public void attach(
            IEventBus bus
    )
    {
        this.bus = bus;
        this.bus.addListener(this::onRegister);
    }

    public List<T> getRegistries()
    {
        return new ArrayList<>(registryTable.values());
    }

    public T getRegistry(
            ResourceLocation location
    )
    {
        return registryTable.get(location);
    }

    public T register(
            String modid,
            T object
    )
    {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(modid, object.id());
        if (frozen)
            throw new IllegalStateException(Util.format("Illegal registry. Registry type: {}, ResourceLocation: {}, Error: Frozen registry", key, location));

        if (registryTable.containsKey(location))
            throw new IllegalArgumentException(Util.format("Illegal registry. Registry type: {}, ResourceLocation: {}, Error: Duplicated registry", key, location));

        object.setLocation(location);
        return registryTable.put(location, object);
    }

    private void onRegister(
            FMLCommonSetupEvent event
    )
    {
        logger.info("> Running step 'freeze registry'");
        logger.info("> Recognized {} registries, proceeding", registryTable.size());

        logger.info("> Detailed data: (TYPE: {}, OWNER: {})", key, bus);

        NeoForge.EVENT_BUS.post(new RegisterEvent<>(key, this));
        frozen = true;

        logger.info("> Frozen registry {} on FML-Common-Setup", key);
    }
}