package com.fomdev.awaken.spawn.shuffle;

import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.entries.shuffle.WeightedQueue;
import net.neoforged.bus.api.IEventBus;

public class ShuffledRegistries
{
    public static final WeightedQueue<AwakenInfix> WEIGHTED_AWAKEN_INFIX =
            new WeightedQueue<>(AwakenRegistries.RES_AWAKEN_INFIX);

    public static final WeightedQueue<AwakenPrefix> WEIGHTED_AWAKEN_PREFIX =
            new WeightedQueue<>(AwakenRegistries.RES_AWAKEN_PREFIX);

    public static final WeightedQueue<AwakenQuality> WEIGHTED_AWAKEN_QUALITY =
            new WeightedQueue<>(AwakenRegistries.RES_AWAKEN_QUALITY);

    public static final WeightedQueue<AwakenSuffix> WEIGHTED_AWAKEN_SUFFIX =
            new WeightedQueue<>(AwakenRegistries.RES_AWAKEN_SUFFIX);

    public static void register(
            IEventBus bus
    )
    {
        WEIGHTED_AWAKEN_INFIX.attach(bus);
        WEIGHTED_AWAKEN_PREFIX.attach(bus);
        WEIGHTED_AWAKEN_QUALITY.attach(bus);
        WEIGHTED_AWAKEN_SUFFIX.attach(bus);
    }
}