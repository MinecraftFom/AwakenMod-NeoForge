package com.fomdev.awaken.init.config;

import com.fomdev.awaken.util.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Objects;

public class AwakenCommon
{
    public static final AwakenCommon CONFIG;
    public static final ModConfigSpec SPEC;

    public final ModConfigSpec.ConfigValue<Double> EPOCH_RARITY;
    public final ModConfigSpec.ConfigValue<Double> MAX_HEALTH;
    public final ModConfigSpec.ConfigValue<Integer> CARRIER_GENERATION;

    public final ModConfigSpec.ConfigValue<List<? extends String>> ENTITIES;
    public final ModConfigSpec.ConfigValue<List<? extends String>> RIDE_ENTITIES;

    public final ModConfigSpec.ConfigValue<List<? extends String>> NAMES_FIRST;
    public final ModConfigSpec.ConfigValue<List<? extends String>> NAMES_LAST;

    public final ModConfigSpec.ConfigValue<List<? extends String>> MAIN_HAND;
    public final ModConfigSpec.ConfigValue<List<? extends String>> OFF_HAND;
    public final ModConfigSpec.ConfigValue<List<? extends String>> HELMET;
    public final ModConfigSpec.ConfigValue<List<? extends String>> CHESTPLATE;
    public final ModConfigSpec.ConfigValue<List<? extends String>> LEGGINGS;
    public final ModConfigSpec.ConfigValue<List<? extends String>> BOOTS;

    public final ModConfigSpec.ConfigValue<List<? extends String>> EFFECTS;

    public final ModConfigSpec.ConfigValue<Integer> MAX_ENCHANT_ABILITY;
    public final ModConfigSpec.ConfigValue<Integer> MAX_ENCHANT_LEVEL;
    public final ModConfigSpec.ConfigValue<Integer> XP_PER_LEVEL;

    public AwakenCommon(
            ModConfigSpec.Builder builder
    )
    {
        builder.push("literature");

        builder.push("name_entries");
        this.NAMES_FIRST = builder
                .comment("Names for generating mobs (first_name), structure: [NAME]|[CHANCE]")
                .defineList(
                        "first_name",
                        List.of(
                                Constants.defaultFirstNames
                        ),
                        Objects::nonNull
                );

        this.NAMES_LAST = builder
                .comment("Names for generating mobs (last_name), structure: [NAME]|[CHANCE]")
                .defineList(
                        "last_name",
                        List.of(
                                Constants.defaultLastNames
                        ),
                        Objects::nonNull
                );

        builder.pop();
        builder.pop();

        builder.push("spawn");

        EPOCH_RARITY = builder
                .comment(
                        "The chance of having 'epoch' attribute on tools"
                )
                .define(
                        "epoch_chance",
                        Constants.epochChance,
                        Objects::nonNull
                );

        ENTITIES = builder
                .comment(
                        "Decides mobs that will be generated. Structure: MOB_TYPE_ID | DIM0 & DIM1 & ... & DIM_N"
                )
                .defineList(
                        "entity_spawn",
                        List.of(
                                Constants.defaultSpawning
                        ),
                        Objects::nonNull
                );

        CARRIER_GENERATION = builder
                .comment("Decides when mobs will have carriers")
                .define(
                        "carrier_generate_difficulty",
                        Constants.carrierGenerateDiff,
                        Objects::nonNull
                );

        RIDE_ENTITIES = builder
                .comment("Decides mobs that may generate as carriers while rare mobs generate")
                .defineList(
                        "ride_entities",
                        List.of(
                                Constants.defaultRideEntities
                        ),
                        Objects::nonNull
                );

        EFFECTS =
                builder
                        .comment("Effects that will be generated on mobs. Structure: [EFFECT_ID]|[DURATION]|[MAX_LEVEL]")
                        .defineList(
                                "effects",
                                List.of(
                                        Constants.defaultEffects
                                ),
                                Objects::nonNull
                        );

        builder.push("equipments");
        MAIN_HAND = builder
                .comment("Equipments to be generated on the main hand. Structure: [ITEM_ID]|[CHANCE]|MINIMUM_DIFFICULTY")
                .defineList(
                        "main_hand",
                        List.of(
                                Constants.defaultEquipment$Hand
                        ),
                        Objects::nonNull
                );
        OFF_HAND = builder
                .comment("Equipments to be generated on the off hand. Structure: [ITEM_ID]|[CHANCE]|MINIMUM_DIFFICULTY")
                .defineList(
                        "off_hand",
                        List.of(
                                Constants.defaultEquipment$OffHand
                        ),
                        Objects::nonNull
                );
        HELMET = builder
                .comment("Equipments to be generated on the head. Structure: [ITEM_ID]|[CHANCE]|MINIMUM_DIFFICULTY")
                .defineList(
                        "helmet",
                        List.of(
                                Constants.defaultEquipment$Head
                        ),
                        Objects::nonNull
                );
        CHESTPLATE = builder
                .comment("Equipments to be generated on the chest. Structure: [ITEM_ID]|[CHANCE]|MINIMUM_DIFFICULTY")
                .defineList(
                        "chestplate",
                        List.of(
                                Constants.defaultEquipment$Chest
                        ),
                        Objects::nonNull
                );
        LEGGINGS = builder
                .comment("Equipments to be generated on the legs. Structure: [ITEM_ID]|[CHANCE]|MINIMUM_DIFFICULTY")
                .defineList(
                        "leggings",
                        List.of(
                                Constants.defaultEquipment$Legs
                        ),
                        Objects::nonNull
                );
        BOOTS = builder
                .comment("Equipments to be generated on the feet. Structure: [ITEM_ID]|[CHANCE]|MINIMUM_DIFFICULTY")
                .defineList(
                        "boots",
                        List.of(
                                Constants.defaultEquipment$Feet
                        ),
                        Objects::nonNull
                );

        builder.pop();
        builder.pop();

        builder.push("tweaks");
        MAX_HEALTH = builder
                .comment("Max health of mobs")
                .define(
                        "max_health",
                        Constants.maxHealth,
                        Objects::nonNull
                );

        MAX_ENCHANT_ABILITY = builder
                .comment("Max enchantment ability can a enchanting table have")
                .define(
                        "max_enchantment_ability",
                        Constants.enchantmentMax,
                        Objects::nonNull
                );

        MAX_ENCHANT_LEVEL = builder
                .comment("Max enchantment level accepted")
                .define(
                        "max_enchantment_level",
                        Constants.maxEnchant,
                        Objects::nonNull
                );

        XP_PER_LEVEL = builder
                .comment("Experience required for each level")
                .comment("WARNING: If you change this, some of your saves will break, and you aren't able to regain xp")
                .define(
                        "xp_per_level",
                        Constants.xpLevel,
                        Objects::nonNull
                );
    }

    static
    {
        Pair<AwakenCommon, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(AwakenCommon::new);

        CONFIG = pair.getLeft();
        SPEC = pair.getRight();
    }
}