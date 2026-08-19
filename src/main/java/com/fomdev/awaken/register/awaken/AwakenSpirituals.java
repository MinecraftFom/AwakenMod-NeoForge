package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.AwakenSpiritual;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spiritual.ExplosionSpiritual;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;

@AutoProxy
public class AwakenSpirituals
{
    public static final RegistryTable<AwakenSpiritual> REGISTRY =
            new RegistryTable<>(
                    Awaken.MODID,
                    AwakenRegistries.AWAKEN_SPIRIT
            );

    public static void init()
    {
        REGISTRY.register(
                new ExplosionSpiritual("explosion")
        );
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        init();
        REGISTRY.register();
    }
}