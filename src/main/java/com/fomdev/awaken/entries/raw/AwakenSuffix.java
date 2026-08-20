package com.fomdev.awaken.entries.raw;

import com.fomdev.flame.register.Registry;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class AwakenSuffix extends Registry
{
    private final int durability;
    private final double factor;
    private final Holder<Attribute> target;

    public AwakenSuffix(
            String id,
            int durability,
            double factor,
            Holder<Attribute> target
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

    public Holder<Attribute> getTarget()
    {
        return this.target;
    }

    public boolean should(
            Holder<Attribute> attribute
    )
    {
        return this.target == attribute;
    }

    public static class SuffixInstance extends AwakenSuffix
    {
        private final int level;

        public SuffixInstance(
                AwakenSuffix parent,
                int level
        )
        {
            super(parent.id(), parent.addition() * level, parent.factor() * Math.sqrt(level), parent.getTarget());
            this.level = level;
            setLocation(parent.getLocation());
        }

        public int getLevel()
        {
            return this.level;
        }
    }
}