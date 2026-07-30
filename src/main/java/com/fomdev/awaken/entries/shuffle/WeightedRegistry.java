package com.fomdev.awaken.entries.shuffle;

import com.fomdev.flame.register.Registry;

public class WeightedRegistry<T extends Registry>
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
        this.weight = weight;
        this.minDiff = minDiff;
        this.entry = entry;
    }
}