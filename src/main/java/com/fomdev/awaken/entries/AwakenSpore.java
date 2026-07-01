package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;

public abstract class AwakenSpore extends Registry
{
    private final Attribute attribute;
    private final EquipmentSlot[] suitable;

    public AwakenSpore(
            String id,
            Attribute attribute,
            EquipmentSlot[] suitable
    )
    {
        super(id);

        this.attribute = attribute;
        this.suitable = suitable;
    }

    public abstract double getAmount(
            int level
    );

    public Attribute getAttribute()
    {
        return this.attribute;
    }

    public EquipmentSlot[] getSlots()
    {
        return this.suitable;
    }
}