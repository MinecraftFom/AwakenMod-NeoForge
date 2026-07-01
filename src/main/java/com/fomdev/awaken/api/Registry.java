package com.fomdev.awaken.api;

import net.minecraft.resources.ResourceLocation;

public abstract class Registry
{
    protected ResourceLocation location;
    protected String id;

    public Registry(
            String id
    )
    {
        this.id = id;
    }

    public final String id()
    {
        return this.id;
    }

    public final ResourceLocation getLocation()
    {
        return this.location;
    }

    public final void setLocation(
            ResourceLocation location
    )
    {
        this.location = location;
    }
}