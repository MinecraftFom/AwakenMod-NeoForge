package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.entries.raw.affix.suffix.digger.MultiMineSuffix;
import com.fomdev.awaken.entries.raw.affix.suffix.weapon.DoubleCriticalSuffix;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
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

    public static void initDigger()
    {
        register(new MultiMineSuffix("multimine"), 50.0F, 1000.0F);
    }

    public static void initWeapon()
    {
        register(new DoubleCriticalSuffix("double_critical"), 100.0F, 500.0F);
    }

    public static void register(
            AwakenSuffix suffix,
            float diff,
            float weight
    )
    {
        REGISTRY.register(suffix);
        ShuffledRegistries.WEIGHTED_AWAKEN_SUFFIX.push(suffix, suffix.getSlot().getFirst(), weight, diff);
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        initDigger();
        initWeapon();
        REGISTRY.register();
    }
}