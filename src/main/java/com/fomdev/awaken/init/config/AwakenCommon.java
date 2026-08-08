package com.fomdev.awaken.init.config;

import com.fomdev.awaken.util.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class AwakenCommon
{
    public static final AwakenCommon CONFIG;
    public static final ModConfigSpec SPEC;

    public final ModConfigSpec.ConfigValue<List<String>> ENTITIES;
    public final ModConfigSpec.ConfigValue<List<String>> NAMES;

    public AwakenCommon(
            ModConfigSpec.Builder builder
    )
    {
        builder.push("literature");
        builder.push("The literature module (everything in the game that is about pure text)");

        this.NAMES = builder
                .comment("Names for generating mobs, structure: [NAME]|[CHANCE]")
                .define("name_entries", List.of(
                        Constants.defaultNames
                ));

        builder.push("spawn");
        builder.comment("The generation module (about mob generating)");

        ENTITIES = builder
                .comment(
                        "Decides mobs that will be generated. Structure: MOB_TYPE_ID | DIM0 & DIM1 & ... & DIM_N"
                )
                .define(
                        "entity_spawn",
                        List.of(
                                Constants.defaultNames
                        )
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