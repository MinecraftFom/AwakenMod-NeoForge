package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.AwakenSuffix;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;
import net.minecraft.world.entity.ai.attributes.Attributes;

@AutoProxy
public class AwakenSuffixes
{
    public static final RegistryTable<AwakenSuffix> REGISTRY =
            new RegistryTable<>(Awaken.MODID, AwakenRegistries.AWAKEN_SUFFIX);

    public static final AwakenSuffix SUFFIX_NORMAL =
            register(
                    new AwakenSuffix(
                            "normal",
                            5,
                            1.125F,
                            Attributes.ARMOR
                    ),
                    50.0F,
                    0.0F
            );

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        REGISTRY.register();
    }

    private static AwakenSuffix register(
            AwakenSuffix suffix,
            float chance,
            float minDiff
    )
    {
        REGISTRY.register(suffix);
        ShuffledRegistries.WEIGHTED_AWAKEN_SUFFIX.push(suffix, chance, minDiff);
        return suffix;
    }
}