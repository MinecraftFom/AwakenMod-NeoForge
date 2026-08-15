package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenAspect;
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

    public static final AwakenPrefix PREFIX_IMPERFECTION = register("imperfection", List.of(AwakenAspects.ASPECT_DEATH.toInstance(5)), 0, 50.0F, 1.0F);
    public static final AwakenPrefix PREFIX_NORMAL = register("normal", List.of(), 5, 50.0F, 1.0F);
    public static final AwakenPrefix PREFIX_INFINITY = register("infinity", List.of(AwakenAspects.ASPECT_BLOOD.toInstance(2147483647), AwakenAspects.ASPECT_DEATH.toInstance(2147483647), AwakenAspects.ASPECT_DIVERSITY.toInstance(2147483647), AwakenAspects.ASPECT_HUMANITY.toInstance(2147483647), AwakenAspects.ASPECT_INSANITY.toInstance(2147483647), AwakenAspects.ASPECT_LONELINESS.toInstance(2147483647), AwakenAspects.ASPECT_LONELINESS.toInstance(2147483647), AwakenAspects.ASPECT_NATURAL.toInstance(2147483647), AwakenAspects.ASPECT_SPIRITUAL.toInstance(2147483647), AwakenAspects.ASPECT_VIVIDITY.toInstance(2147483647), AwakenAspects.ASPECT_VOID.toInstance(2147483647), AwakenAspects.ASPECT_WATER.toInstance(2147483647)), (int) Math.pow(10, 5), 0.01F, 10000.0F); // 1 * 10 ^ 5

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        REGISTRY.register();
    }

    private static AwakenPrefix register(
            String id,
            List<AwakenAspect.AspectInstance> aspects,
            int durability,
            float chance,
            float minDiff
    )
    {
        return register(
                new AwakenPrefix(
                        id,
                        aspects,
                        durability
                ),
                chance,
                minDiff
        );
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