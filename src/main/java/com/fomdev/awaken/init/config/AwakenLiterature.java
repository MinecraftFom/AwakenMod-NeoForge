package com.fomdev.awaken.init.config;

import com.fomdev.awaken.util.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class AwakenLiterature
{
    public static final AwakenLiterature CONFIG;
    public static final ModConfigSpec SPEC;

    public final ModConfigSpec.ConfigValue<List<String>> NAMES;

    public AwakenLiterature(
            ModConfigSpec.Builder builder
    )
    {
        builder.push("literature");

        this.NAMES = builder
                .comment("Names for generating mobs, structure: [NAME]|[CHANCE]")
                .define("name_entries", List.of(
                        Constants.defaultNames
                ));
    }

    static
    {
        Pair<AwakenLiterature, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(AwakenLiterature::new);

        CONFIG = pair.getLeft();
        SPEC = pair.getRight();
    }
}