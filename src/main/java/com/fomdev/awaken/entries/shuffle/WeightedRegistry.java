package com.fomdev.awaken.entries.shuffle;

import com.fomdev.flame.register.Registry;

import java.util.UUID;

public class WeightedRegistry<T extends Registry> extends Registry
{
    final float weight;
    final float minDiff;
    final T entry;

    public WeightedRegistry(
            float weight,
            float minDiff,
            T entry
    )
    {
        super(UUID.randomUUID().toString());
        this.weight = weight;
        this.minDiff = minDiff;
        this.entry = entry;
    }
}