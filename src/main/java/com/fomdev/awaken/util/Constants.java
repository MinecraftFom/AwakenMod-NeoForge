package com.fomdev.awaken.util;

import net.minecraft.world.entity.EquipmentSlot;

public class Constants
{
    EquipmentSlot[] HAND_SLOTS = new EquipmentSlot[]{
            EquipmentSlot.MAINHAND,
            EquipmentSlot.OFFHAND
    };

    EquipmentSlot[] BODY_SLOTS = new EquipmentSlot[]{
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    EquipmentSlot[] ALL_SLOTS = new EquipmentSlot[]{
            EquipmentSlot.MAINHAND,
            EquipmentSlot.OFFHAND,
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };
}