package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;

public class AwakenAspect extends Registry
{
    public AwakenAspect(String id)
    {
        super(id);
    }

    public record AspectInstance(
            AwakenAspect aspect,
            int amount
    )
    {}
}