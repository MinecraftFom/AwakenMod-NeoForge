package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class AwakenSuffix extends Registry
{
    private final int durability;
    private final double factor;
    private final Attribute target;

    public AwakenSuffix(
            String id,
            int durability,
            double factor,
            Attribute target
    )
    {
        super(id);

        this.durability = durability;
        this.factor = factor;
        this.target = target;
    }

    public int addition()
    {
        return this.durability;
    }

    public double factor()
    {
        return this.factor;
    }

    public Attribute getTarget()
    {
        return this.target;
    }

    public boolean should(
            Attribute attribute
    )
    {
        return this.target == attribute;
    }
}