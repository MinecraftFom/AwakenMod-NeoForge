package com.fomdev.awaken.util;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.awt.*;
import java.util.List;

public class Records
{
    public record AwakenLevelComponent
            (
                    Float level
            )
    {}

    public record AwakenDescriberComponent
            (
                    String infix,
                    String prefix,
                    String suffix
            )
    {}

    public record AttributeHolder
            (
                    Holder<Attribute> attr,
                    double amount,
                    AttributeModifier.Operation operation,
                    EquipmentSlot[] slot
            )
    {
        public AttributeHolder(
                Holder<Attribute> attr,
                double amount,
                EquipmentSlot slots
        )
        {
            this(attr, amount, AttributeModifier.Operation.ADD_VALUE, new EquipmentSlot[]{slots});
        }
    }

    public record ColorHolder
            (
                    Color frontStart,
                    Color frontEnd,
                    Color backStart,
                    Color backEnd
            )
    {
        public ColorHolder(
                Color front,
                Color end
        )
        {
            this(front, front, end, end);
        }
    }
}