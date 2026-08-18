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
    public static final String[] defaultFirstNames = new String[]{
            "James|800",
            "Michael|700",
            "William|600",
            "Robert|700",
            "David|600",
            "Richard|700",
            "Joseph|600",
            "Thomas|700",
            "Charles|600",
            "Christopher|700",
            "Daniel|600",
            "Matthew|700",
            "Anthony|600",
            "Mark|700",
            "Steven|700"
    };

    public static final String[] defaultLastNames = new String[]{
            "Harper|800",
            "Bullock|700",
            "Sterling|600",
            "Payton|700",
            "Crawford|600",
            "Fletcher|700",
            "Winslow|600",
            "Bradley|700",
            "Randall|600",
            "Morrison|700",
            "Sherman|600",
            "Griffith|700",
            "Carter|600",
            "Summers|700",
            "Norris|700",
            "Huang|2", // Co-developer
            "Minos|2", // Co-developer
            "Fom477|1" // Main-developer
    };

    public static final String[] defaultSpawning = new String[]{
            "zombie|overworld",
            "husk|overworld",
            "drown|overworld",
            "skeleton|overworld&the_nether",
            "spider|overworld",
            "creeper|overworld",
            "enderman|overworld&the_nether&the_end"
    };

    public static final String[] defaultEquipment$Hand = new String[]{
            "wooden_axe|90|1.0",
            "wooden_hoe|100|1.0",
            "wooden_pickaxe|90|1.0",
            "wooden_shovel|100|1.0",
            "wooden_sword|85|1.0",
            "stone_axe|80|1.0",
            "stone_hoe|90|1.0",
            "stone_pickaxe|80|1.0",
            "stone_shovel|90|1.0",
            "stone_sword|75|1.0",
            "iron_axe|70|10.0",
            "iron_hoe|80|10.0",
            "iron_pickaxe|70|10.0",
            "iron_shovel|80|10.0",
            "iron_sword|65|10.0",
            "diamond_axe|60|50.0",
            "diamond_hoe|70|50.0",
            "diamond_pickaxe|60|50.0",
            "diamond_shovel|70|50.0",
            "diamond_sword|55|50.0",
            "netherite_axe|10|100.0",
            "netherite_hoe|20|100.0",
            "netherite_pickaxe|10|100.0",
            "netherite_shovel|20|100.0",
            "netherite_sword|5|100.0",
            "mace|1|10000.0",
            "bow|50|500.0",
            "cobweb|50|500.0",
            "ender_pearl|50|500.0"
    };

    public static final String[] defaultEquipment$OffHand = new String[]{
            "shield|20|1.0",
            "totem_of_undying|1|10000.0",
            "ender_pearl|50|500.0"
    };

    public static final String[] defaultEquipment$Head = new String[]{
            "leather_helmet|100|1.0",
            "chainmail_helmet|90|1.0",
            "iron_helmet|60|10.0",
            "golden_helmet|66|10.0", // BAD LUCK!
            "diamond_helmet|10|50.0",
            "netherite_helmet|1|100.0",
            "turtle_helmet|5|10.0"
    };

    public static final String[] defaultEquipment$Chest = new String[]{
            "leather_chestplate|120|1.0",
            "chainmail_chestplate|100|1.0",
            "iron_chestplate|75|10.0",
            "golden_chestplate|66|10.0",
            "diamond_chestplate|10|50.0",
            "netherite_chestplate|5|100.0",
            "elytra|2|10000.0",
            "chest|1|0.0"
    };

    public static final String[] defaultEquipment$Legs = new String[]{
            "leather_leggings|100|1.0",
            "chainmail_leggings|90|1.0",
            "iron_leggings|60|10.0",
            "golden_leggings|66|10.0",
            "diamond_leggings|10|50.0",
            "netherite_leggings|5|100.0",
            "end_rod|1|10000.0"
    };

    public static final String[] defaultEquipment$Feet = new String[]{
            "leather_boots|100|1.0",
            "chainmail_boots|90|1.0",
            "iron_boots|60|10.0",
            "golden_boots|66|10.0",
            "diamond_boots|10|50.0",
            "netherite_boots|1|100.0"
    };

    public static final String[] defaultRideEntities = new String[]{
            "phantom",
            "silverfish"
    };

    public static final String[] defaultEffects = new String[]{
            "health_boost|10000|10"
    };

    public static final String[] enchantmentAspects = new String[]{
//            "protection|awaken:humanity = 500",
//            "fire_protection|awaken:humanity = 450, awaken:spiritual = 50",
//            "feather_falling|awaken:humanity = 450, awaken:natural = 35, awaken:spiritual = 15",
//            "blast_protection|awaken:humanity = 450, awaken:water = 40, awaken:spiritual = 10",
//            "projectile_protection|awaken:humanity = 450, awaken:void = 50"
    };

    public static final int attributeFactor = 255;
    public static final int enchantmentMax = 255;

    public static final double epochChance = 100.0;
    public static final double maxHealth = 10000.0;

    public static final int maxAcceptableEnchant = Integer.MAX_VALUE;
    public static final int maxEnchant = 50;
    public static final int xpLevel = 200;

    public static final int carrierGenerateDiff = 10000;

    public static final double maxDurabilityFactor = 1000.0;

    public static final double defaultRequiredSoul = 10.0;
    public static final double defaultSoulFactor = 1.5;
}