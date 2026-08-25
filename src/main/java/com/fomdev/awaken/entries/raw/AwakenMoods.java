package com.fomdev.awaken.entries.raw;

import com.fomdev.flame.register.Registry;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class AwakenMoods extends Registry
{
    private final Holder<Attribute> reinforce;
    private final Holder<Attribute> weaken;

    private final float reinforceAmount;
    private final float weakenAmount;

    private final int quotes;

    public AwakenMoods(
            String id,
            Holder<Attribute> reinforce,
            float reinforceAmount,
            Holder<Attribute> weaken,
            float weakenAmount,
            int quotes
    )
    {
        super(id);
        this.reinforce = reinforce;
        this.weaken = weaken;

        this.reinforceAmount = reinforceAmount;
        this.weakenAmount = weakenAmount;

        this.quotes = quotes;
    }

    public Holder<Attribute> getReinforce()
    {
        return this.reinforce;
    }

    public float getReinforceAmount()
    {
        return this.reinforceAmount;
    }

    public Holder<Attribute> getWeaken()
    {
        return this.weaken;
    }

    public float getWeakenAmount()
    {
        return this.weakenAmount;
    }

    public int getQuotes()
    {
        return this.quotes;
    }
}