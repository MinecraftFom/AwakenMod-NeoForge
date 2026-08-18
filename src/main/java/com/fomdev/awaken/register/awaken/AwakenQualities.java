package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenQuality;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;

import java.awt.*;
import java.util.List;

@AutoProxy
public class AwakenQualities
{
    public static final RegistryTable<AwakenQuality> REGISTRY = new RegistryTable<>(
            Awaken.MODID,
            AwakenRegistries.AWAKEN_QUALITY
    );

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        REGISTRY.register();
    }

    static
    {
        register("normal", List.of(Color.GRAY), 0.1D, AwakenQuality.ColorPattern.SINGLE, 500.0F, 0.0F);
        register("imperfect", List.of(Color.LIGHT_GRAY), 1.0D, AwakenQuality.ColorPattern.SINGLE, 500.0F, 0.0F);
        register("explicit", List.of(Color.WHITE), 1.5D, AwakenQuality.ColorPattern.SINGLE, 500.0F, 0.0F);
        register("novice", List.of(Color.WHITE), 1.75D, AwakenQuality.ColorPattern.SINGLE, 500.0F, 0.0F);
        register("unawaken", List.of(Color.BLACK), 1.75D, AwakenQuality.ColorPattern.SINGLE, 500.0F, 0.0F);
        register("starter", List.of(Color.GREEN), 2.0D, AwakenQuality.ColorPattern.SINGLE, 450.0F, 50.0F);
        register("reinforce", List.of(Color.GREEN), 2.5D, AwakenQuality.ColorPattern.SINGLE, 450.0F, 50.0F);
        register("student", List.of(Color.GREEN), 2.75D, AwakenQuality.ColorPattern.SINGLE, 450.0F, 50.0F);
        register("creative", List.of(Color.GREEN), 3.0D, AwakenQuality.ColorPattern.SINGLE, 450.0F, 50.0F);
        register("dream", List.of(Color.GREEN), 4.0D, AwakenQuality.ColorPattern.SINGLE, 450.0F, 50.0F);
        register("criminal", List.of(Color.RED), 4.5D, AwakenQuality.ColorPattern.SINGLE, 400.0F, 100.0F);
        register("violent", List.of(Color.RED), 5.0D, AwakenQuality.ColorPattern.SINGLE, 400.0F, 100.0F);
        register("vintage", List.of(Color.RED), 5.5D, AwakenQuality.ColorPattern.SINGLE, 400.0F, 100.0F);
        register("maniac", List.of(Color.RED), 6.0D, AwakenQuality.ColorPattern.SINGLE, 400.0F, 100.0F);
        register("mania", List.of(Color.RED), 6.5D, AwakenQuality.ColorPattern.SINGLE, 400.0F, 100.0F);
        register("disoriented", List.of(Color.RED), 7.0D, AwakenQuality.ColorPattern.SINGLE, 400.0F, 100.0F);
        register("pessimist", List.of(Color.RED), 7.5D, AwakenQuality.ColorPattern.SINGLE, 400.0F, 100.0F);
        register("insanity", List.of(Color.CYAN), 8.0D, AwakenQuality.ColorPattern.SINGLE, 350.0F, 100.0F);
        register("egoist", List.of(Color.CYAN), 9.0D, AwakenQuality.ColorPattern.SINGLE, 350.0F, 100.0F); // The breaker of optimistic~
        register("crazy", List.of(Color.CYAN), 10.0D, AwakenQuality.ColorPattern.SINGLE, 350.0F, 100.0F);
        register("offtrack", List.of(Color.CYAN), 11.0D, AwakenQuality.ColorPattern.SINGLE, 350.0F, 100.0F); // Zu be de re ku
        register("expert", List.of(Color.CYAN), 12.0D, AwakenQuality.ColorPattern.SINGLE, 350.0F, 100.0F);
        register("merchant", List.of(Color.CYAN), 13.0D, AwakenQuality.ColorPattern.SINGLE, 350.0F, 100.0F);
        register("mascot", List.of(Color.CYAN), 15.0D, AwakenQuality.ColorPattern.SINGLE, 250.0F, 500.0F);
        register("mystery", List.of(Color.CYAN), 17.0D, AwakenQuality.ColorPattern.SINGLE, 250.0F, 500.0F);
        register("misery", List.of(Color.CYAN), 19.0D, AwakenQuality.ColorPattern.SINGLE, 250.0F, 500.0F);
        register("lament", List.of(Color.CYAN), 21.0D, AwakenQuality.ColorPattern.SINGLE, 250.0F, 500.0F); // Yo shi wa lament
        register("perplex", List.of(Color.CYAN), 23.0D, AwakenQuality.ColorPattern.SINGLE, 250.0F, 500.0F);
        register("incredible", List.of(Color.CYAN), 25.0D, AwakenQuality.ColorPattern.SINGLE, 250.0F, 500.0F);
        register("mature", List.of(Color.YELLOW), 30.0D, AwakenQuality.ColorPattern.SINGLE, 100.0F, 1000.0F);
        register("fascinate", List.of(Color.YELLOW), 35.0D, AwakenQuality.ColorPattern.SINGLE, 100.0F, 1000.0F); // Ki mi wa bo ku no fasinator
        register("mesmerizer", List.of(Color.YELLOW), 40.0D, AwakenQuality.ColorPattern.SINGLE, 100.0F, 1000.0F);
        register("future", List.of(Color.YELLOW), 45.0D, AwakenQuality.ColorPattern.SINGLE, 100.0F, 1000.0F);
        register("greed", List.of(Color.YELLOW), 50.0D, AwakenQuality.ColorPattern.SINGLE, 100.0F, 1000.0F);
        register("superior", List.of(Color.PINK), 60.0D, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("transcend", List.of(Color.PINK), 70.0D, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("telepathy", List.of(Color.PINK), 80.0F, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("transparent", List.of(Color.PINK), 90.0F, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("labyrinth", List.of(Color.PINK), 100.0F, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("synchronized", List.of(Color.PINK), 110.0D, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("master", List.of(Color.PINK), 120.0D, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("escape", List.of(Color.PINK), 130.0D, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F); // Escape from reality
        register("reality", List.of(Color.PINK), 140.0D, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("virtual", List.of(Color.PINK), 150.0D, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("insight", List.of(Color.PINK), 160.0D, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("tremendous", List.of(Color.PINK), 170.0D, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("pizzazz", List.of(Color.BLUE), 180.0D, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("victim", List.of(Color.BLUE), 190.0D, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("dizzy", List.of(Color.BLUE), 200.0D, AwakenQuality.ColorPattern.SINGLE, 20.0F, 5000.0F);
        register("abyss", List.of(Color.BLUE), 250.0D, AwakenQuality.ColorPattern.SINGLE, 5.0F, 10000.0F);
        register("assemble", List.of(Color.BLUE), 300.0D, AwakenQuality.ColorPattern.SINGLE, 5.0F, 10000.0F);
        register("atomic", List.of(Color.BLUE), 400.0D, AwakenQuality.ColorPattern.SINGLE, 5.0F, 10000.0F);
        register("available", List.of(Color.BLUE), 500.0D, AwakenQuality.ColorPattern.SINGLE, 5.0F, 10000.0F);
        register("awaken", List.of(Color.PINK, Color.MAGENTA), 1000.0D, AwakenQuality.ColorPattern.CONTINUE, 0.01F, 10000.0F);
        register("infinity", List.of(Color.RED, Color.BLUE), 32767.0D, AwakenQuality.ColorPattern.CONTINUE, 0.000001F, 10000.0F);
        register("akanbe", List.of(Color.BLACK, Color.WHITE), 65535.0D, AwakenQuality.ColorPattern.CONTINUE, 0.000001F, 1000000.0F); // zannan badan akanbe
        register("sadomasochism", List.of(Color.BLACK), 65535.0D, AwakenQuality.ColorPattern.CONTINUE, 0.000001F, 1000000.0F);
    }

    private static AwakenQuality register(
            AwakenQuality quality,
            float chance,
            float minDiff
    )
    {
        REGISTRY.register(quality);
        ShuffledRegistries.WEIGHTED_AWAKEN_QUALITY.push(
                quality,
                chance,
                minDiff
        );
        return quality;
    }

    private static AwakenQuality register(
            String id,
            List<Color> colors,
            double factor,
            AwakenQuality.ColorPattern pattern,
            float chance,
            float minDiff
    )
    {
        return register(
                new AwakenQuality(
                        id,
                        colors,
                        factor,
                        pattern
                ),
                chance,
                minDiff
        );
    }
}