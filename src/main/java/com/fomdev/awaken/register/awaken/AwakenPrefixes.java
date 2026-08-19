package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenPrefix;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;

@AutoProxy
public class AwakenPrefixes
{
    public static final RegistryTable<AwakenPrefix> REGISTRY =
            new RegistryTable<>(Awaken.MODID, AwakenRegistries.AWAKEN_PREFIX);

    public static void init()
    {
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