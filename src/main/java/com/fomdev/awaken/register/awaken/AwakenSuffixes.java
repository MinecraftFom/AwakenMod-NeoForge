package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.entries.raw.affix.suffix.block.MultiMineSuffix;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;

@AutoProxy
public class AwakenSuffixes
{
    public static final RegistryTable<AwakenSuffix> REGISTRY =
            new RegistryTable<>(
                    Awaken.MODID,
                    AwakenRegistries.AWAKEN_SUFFIX
            );

    public static void init()
    {
        REGISTRY.register(new MultiMineSuffix("multimine"));
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        init();
        REGISTRY.register();
    }
}