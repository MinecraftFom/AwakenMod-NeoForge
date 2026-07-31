package com.fomdev.awaken.entries.shuffle;

import com.fomdev.flame.register.Registry;
import com.fomdev.flame.register.RegistryTable;

public class WeightedRegistryTable<T extends Registry> extends RegistryTable<WeightedRegistry<T>>
{
    public WeightedRegistryTable(
            String modid,
            WeightedQueue<T> queue
    )
    {
        super(modid, queue);
    }

    public void register(
            T registry,
            float weight,
            float minDiff
    )
    {
        super.register(
                new WeightedRegistry<>(
                        weight,
                        minDiff,
                        registry
                )
        );
    }
}