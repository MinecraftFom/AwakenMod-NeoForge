package com.fomdev.awaken.spawn.shuffle;

import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.entries.shuffle.WeightedQueue;
import com.fomdev.awaken.entries.shuffle.WeightedRegistry;
import com.fomdev.awaken.init.Awaken;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;

public class ShuffledRegistries
{
    public static final ResourceKey<Registry<WeightedRegistry<AwakenInfix>>> RES_WEIGHTED_INFIX =
            createKey(AwakenRegistries.SIG_AWAKEN_INFIX);

    public static final ResourceKey<Registry<WeightedRegistry<AwakenPrefix>>> RES_WEIGHTED_PREFIX =
            createKey(AwakenRegistries.SIG_AWAKEN_PREFIX);

    public static final ResourceKey<Registry<WeightedRegistry<AwakenQuality>>> RES_WEIGHTED_QUALITY =
            createKey(AwakenRegistries.SIG_AWAKEN_QUALITY);

    public static final ResourceKey<Registry<WeightedRegistry<AwakenSuffix>>> RES_WEIGHTED_SUFFIX =
            createKey(AwakenRegistries.SIG_AWAKEN_SUFFIX);

    public static final WeightedQueue<AwakenInfix> WEIGHTED_AWAKEN_INFIX =
            new WeightedQueue<>(RES_WEIGHTED_INFIX);

    public static final WeightedQueue<AwakenPrefix> WEIGHTED_AWAKEN_PREFIX =
            new WeightedQueue<>(RES_WEIGHTED_PREFIX);

    public static final WeightedQueue<AwakenQuality> WEIGHTED_AWAKEN_QUALITY =
            new WeightedQueue<>(RES_WEIGHTED_QUALITY);

    public static final WeightedQueue<AwakenSuffix> WEIGHTED_AWAKEN_SUFFIX =
            new WeightedQueue<>(RES_WEIGHTED_SUFFIX);

    public static void register(
            IEventBus bus
    )
    {
        WEIGHTED_AWAKEN_INFIX.attach(bus);
        WEIGHTED_AWAKEN_PREFIX.attach(bus);
        WEIGHTED_AWAKEN_QUALITY.attach(bus);
        WEIGHTED_AWAKEN_SUFFIX.attach(bus);
    }

    private static <T> ResourceKey<Registry<T>> createKey(
            String key
    )
    {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Awaken.MODID, key));
    }
}