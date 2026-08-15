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

    public static final AwakenQuality QUALITY_NORMAL =
            register(
                    new AwakenQuality(
                            "normal",
                            List.of(
                                    Color.GRAY
                            ),
                            0.1D,
                            AwakenQuality.ColorPattern.SINGLE
                    ),
                    500.0F,
                    0.0F
            );

    public static final AwakenQuality QUALITY_INFINITY =
            register(
                    new AwakenQuality(
                            "infinity",
                            List.of(
                                    Color.RED,
                                    Color.BLUE
                            ),
                            32767.0D,
                            AwakenQuality.ColorPattern.CONTINUE
                    ),
                    0.000001F,
                    10000.0F
            );

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
}