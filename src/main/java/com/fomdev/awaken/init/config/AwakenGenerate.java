package com.fomdev.awaken.init.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class AwakenGenerate
{
    public static AwakenGenerate CONFIG;
    public static ModConfigSpec SPEC;

    public final ModConfigSpec.ConfigValue<List<String>> ENTITIES;

    public AwakenGenerate(
            ModConfigSpec.Builder builder
    )
    {
        builder.push("spawn");
        builder.comment("The generation module");

        ENTITIES = builder
                .comment(
                        "Decides mobs that will be generated. Structure: MOB_TYPE_ID | DIM0 & DIM1 & ... & DIM_N"
                )
                .define(
                        "entity_spawn",
                        List.of()
                );
    }

    static
    {
        Pair<AwakenGenerate, ModConfigSpec> pair = new ModConfigSpec.Builder()
                .configure(AwakenGenerate::new);

        CONFIG = pair.getLeft();
        SPEC = pair.getRight();
    }
}