package com.fomdev.awaken.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.awt.*;
import java.util.List;

public class Records
{
    public record AwakenAspectComponent
            (
                    List<CompoundTag> data
            )
    {}

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

    public record AwakenPollinateComponent
            (
                    List<CompoundTag> data
            )
    {}

    public record AwakenQualityComponent
            (
                    String key
            )
    {}

    public record AwakenSpiritualComponent
            (
                    String key
            )
    {}

    public record AwakenSporeComponent
            (
                    List<CompoundTag> data
            )
    {}

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