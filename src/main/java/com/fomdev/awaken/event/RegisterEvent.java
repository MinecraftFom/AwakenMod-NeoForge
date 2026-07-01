package com.fomdev.awaken.event;

import com.fomdev.awaken.api.FreezingRegistry;
import com.fomdev.awaken.api.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.Event;

public final class RegisterEvent<T extends Registry> extends Event
{
    private final ResourceKey<net.minecraft.core.Registry<T>> key;
    private final FreezingRegistry<T> parent;

    public RegisterEvent(
            ResourceKey<net.minecraft.core.Registry<T>> key,
            FreezingRegistry<T> parent
    )
    {
        this.key = key;

        this.parent = parent;
    }

    public boolean is(
            ResourceKey<?> key
    )
    {
        return this.key == key;
    }

    public T register(
            String modid,
            T object
    )
    {
        return parent.register(
                modid,
                object
        );
    }
}