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
        register("failure", List.of(Color.BLACK), 0.0F, AwakenQuality.ColorPattern.SINGLE, 1.0F, 100000.0F); // Nobody wants this...

        register("fatigue", List.of(Color.DARK_GRAY), -5.0F, AwakenQuality.ColorPattern.SINGLE, 1000.0F, 0.0F);
        register("broken", List.of(Color.DARK_GRAY), -4.75F, AwakenQuality.ColorPattern.SINGLE, 1000.0F, 0.0F);
        register("weak", List.of(Color.LIGHT_GRAY), -4.5F, AwakenQuality.ColorPattern.SINGLE, 1000.0F, 0.0F);
        register("imperfect", List.of(Color.LIGHT_GRAY), -4.25F, AwakenQuality.ColorPattern.SINGLE, 1000.0F, 0.0F);
        register("raw", List.of(Color.LIGHT_GRAY), -4.0F, AwakenQuality.ColorPattern.SINGLE, 1000.0F, 0.0F);
        register("uncomfortable", List.of(Color.GRAY), -3.75F, AwakenQuality.ColorPattern.SINGLE, 950.0F, 500.0F);
        register("unsophisticate", List.of(Color.GRAY), -3.25F, AwakenQuality.ColorPattern.SINGLE, 950.0F, 500.0F);
        register("forgotten", List.of(Color.GRAY), -2.75F, AwakenQuality.ColorPattern.SINGLE, 950.0F, 500.0F);
        register("chaotic", List.of(Color.GRAY), -2.25F, AwakenQuality.ColorPattern.SINGLE, 950.0F, 500.0F);
        register("mayhem", List.of(Color.GRAY), -1.5F, AwakenQuality.ColorPattern.SINGLE, 800.0F, 1000.0F);
        register("deprecated", List.of(Color.WHITE), -1.0F, AwakenQuality.ColorPattern.SINGLE, 800.0F, 1000.0F);
        register("unstable", List.of(Color.WHITE), -0.5F, AwakenQuality.ColorPattern.SINGLE, 800.0F, 1000.0F);
        register("normal", List.of(Color.GREEN), 0.5F, AwakenQuality.ColorPattern.SINGLE, 750.0F, 1500.0F);
        register("naive", List.of(Color.GREEN), 1.0F, AwakenQuality.ColorPattern.SINGLE, 750.0F, 1500.0F);
        register("good", List.of(Color.GREEN), 2.0F, AwakenQuality.ColorPattern.SINGLE, 750.0F, 1500.0F);
        register("strengthen", List.of(Color.GREEN), 3.0F, AwakenQuality.ColorPattern.SINGLE, 500.0F, 5000.0F);
        register("enforced", List.of(Color.GREEN), 3.5F, AwakenQuality.ColorPattern.SINGLE, 500.0F, 5000.0F);
        register("delighted", List.of(Color.CYAN), 4.0F, AwakenQuality.ColorPattern.SINGLE, 500.0F, 5000.0F);
        register("expert", List.of(Color.CYAN), 4.5F, AwakenQuality.ColorPattern.SINGLE, 500.0F, 5000.0F);
        register("master", List.of(Color.CYAN), 5.0F, AwakenQuality.ColorPattern.SINGLE, 500.0F, 5000.0F);
        register("perfect", List.of(Color.BLUE), 5.5F, AwakenQuality.ColorPattern.SINGLE, 500.0F, 5000.0F);
        register("immortal", List.of(Color.BLUE), 6.0F, AwakenQuality.ColorPattern.SINGLE, 500.0F, 5000.0F);
        register("esteem", List.of(Color.BLUE), 6.5F, AwakenQuality.ColorPattern.SINGLE, 500.0F, 5000.0F);
        register("vintage", List.of(Color.BLUE), 7.0F, AwakenQuality.ColorPattern.SINGLE, 500.0F, 5000.0F);
        register("ancient", List.of(Color.BLUE), 7.5F, AwakenQuality.ColorPattern.SINGLE, 500.0F, 5000.0F);
        register("transcend", List.of(Color.RED), 8.5F, AwakenQuality.ColorPattern.SINGLE, 250.0F, 10000.0F);
        register("well-made", List.of(Color.RED), 10.0F, AwakenQuality.ColorPattern.SINGLE, 250.0F, 10000.0F);
        register("great", List.of(Color.RED), 12.0F, AwakenQuality.ColorPattern.SINGLE, 250.0F, 10000.0F);

        register("infinity", List.of(Color.RED, Color.BLUE), 255.0F, AwakenQuality.ColorPattern.CONTINUE, 0.001F, 100000.0F);
        register("holy", List.of(Color.YELLOW, Color.MAGENTA), 255.0F, AwakenQuality.ColorPattern.CONTINUE, 0.001F, 100000.0F);
        register("masterpiece", List.of(Color.PINK, Color.CYAN), 255.0F, AwakenQuality.ColorPattern.CONTINUE, 0.001F, 100000.0F);
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