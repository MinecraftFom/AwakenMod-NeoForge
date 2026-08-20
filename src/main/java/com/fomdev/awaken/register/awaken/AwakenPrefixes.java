package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenPrefix;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;

import java.util.List;

@AutoProxy
public class AwakenPrefixes
{
    public static final RegistryTable<AwakenPrefix> REGISTRY =
            new RegistryTable<>(Awaken.MODID, AwakenRegistries.AWAKEN_PREFIX);

    public static void init()
    {
        register(
                new AwakenPrefix("normal", 0, 1.0F, List.of(), List.of()),
                500.0F,
                0.0F
        );
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        init();
        REGISTRY.register();
    }

    private static AwakenPrefix register(
            AwakenPrefix prefix,
            float chance,
            float minDiff
    )
    {
        REGISTRY.register(prefix);
        ShuffledRegistries.WEIGHTED_AWAKEN_PREFIX.push(prefix, chance, minDiff);
        return prefix;
    }
}