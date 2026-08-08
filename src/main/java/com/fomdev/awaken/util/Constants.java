package com.fomdev.awaken.util;

import net.minecraft.world.entity.EquipmentSlot;

public class Constants
{
    public static final EquipmentSlot[] HAND_SLOTS = new EquipmentSlot[]{
            EquipmentSlot.MAINHAND,
            EquipmentSlot.OFFHAND
    };

    public static final EquipmentSlot[] BODY_SLOTS = new EquipmentSlot[]{
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    public static final EquipmentSlot[] ALL_SLOTS = new EquipmentSlot[]{
            EquipmentSlot.MAINHAND,
            EquipmentSlot.OFFHAND,
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    // Lost tinkers
    public static final String[] defaultNames = new String[]{
            "James-Harper|800",
            "Michael-Bullock|700",
            "William-Sterling|600",
            "Robert-Payton|700",
            "David-Crawford|600",
            "Richard-Fletcher|700",
            "Joseph-Winslow|600",
            "Thomas-Bradley|700",
            "Charles-Randall|600",
            "Christopher-Morrison|700",
            "Daniel-Sherman|600",
            "Matthew-Griffith|700",
            "Anthony-Carter|600",
            "Mark-Summers|700",
            "Steven-Norris|700",
            "SM-Huang|2", //Co-Developer
            "Simon-Suns|2", // Co-Developer
            "Lucas-Fom|1" // Main-Developer
    };

    public static final String[] defaultSpawning = new String[]{
            "zombie|overworld",
            "skeleton|overworld&nether",
            "spider|overworld",
            "creeper|overworld"
    };
}