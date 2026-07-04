package com.fomdev.awaken.api;

import java.util.ArrayList;
import java.util.List;

public class RegistryTable<T extends Registry>
{
    private final FreezingRegistry<T> key;
    private final List<T> registries;
    private final String modid;

    public RegistryTable(
            String modid,
            FreezingRegistry<T> key
    )
    {
        this.key = key;
        this.registries = new ArrayList<>();
        this.modid = modid;
    }

    public void register()
    {
        registries.forEach(r -> key.register(modid, r));
    }

    public T register(T object)
    {
        registries.add(object);
        return object;
    }
}