package com.fomdev.awaken.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class Records
{
    public record AttributeHolder
            (
                    Attribute attr,
                    double amount,
                    AttributeModifier.Operation operation,
                    EquipmentSlot[] slot
            )
    {
        public AttributeHolder(
                Attribute attr,
                double amount,
                EquipmentSlot slots
        )
        {
            this(attr, amount, AttributeModifier.Operation.ADD_VALUE, new EquipmentSlot[]{slots});
        }
    }
}