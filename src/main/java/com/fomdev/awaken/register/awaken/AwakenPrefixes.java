package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenAspect;
import com.fomdev.awaken.entries.raw.AwakenPrefix;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.Arrays;
import java.util.List;

@AutoProxy
public class AwakenPrefixes
{
    public static final RegistryTable<AwakenPrefix> REGISTRY =
            new RegistryTable<>(Awaken.MODID, AwakenRegistries.AWAKEN_PREFIX);

    public static void init()
    {
        register("imperfection", List.of(AwakenAspects.ASPECT_DEATH.toInstance(5)), 0, 500.0F, 0.0F);
        register("normal", List.of(), 10, 500.0F, 0.0F);
        register("insanity", List.of(AwakenAspects.ASPECT_BLOOD.toInstance(50)), 20, 500.0F, 0.0F);
        register("zenless", List.of(AwakenAspects.ASPECT_VINTAGE.toInstance(50)), 30, 500.0F, 0.0F); // In zenless world
        register("violent", List.of(AwakenAspects.ASPECT_LONELINESS.toInstance(100)), 100, 300.0F, 100.0F); // In gray world
        register("colorless", List.of(AwakenAspects.ASPECT_INSANITY.toInstance(100)), 100, 300.0F, 100.0F);
        register("protective", List.of(), 200, 10.0F, 1000.0F, MobEffects.ABSORPTION);
        register("aquatic", List.of(), 200, 10.0F, 1000.0F, MobEffects.CONDUIT_POWER);
        register("miner", List.of(), 200, 10.0F, 1000.0F, MobEffects.DIG_SPEED);
        register("phoenix", List.of(), 200, 10.0F, 1000.0F, MobEffects.FIRE_RESISTANCE);
        register("medical", List.of(), 200, 10.0F, 1000.0F, MobEffects.HEAL);
        register("heroic", List.of(), 200, 10.0F, 1000.0F, MobEffects.HERO_OF_THE_VILLAGE);
        register("rabbit", List.of(), 200, 10.0F, 1000.0F, MobEffects.JUMP); // Rabbit hole ?!
        register("fast", List.of(), 200, 10.0F, 1000.0F, MobEffects.MOVEMENT_SPEED);
        register("hawk", List.of(), 200, 10.0F, 1000.0F, MobEffects.NIGHT_VISION);
        register("hearty", List.of(), 200, 10.0F, 1000.0F, MobEffects.SATURATION);
        register("parachute", List.of(), 200, 10.0F, 1000.0F, MobEffects.SLOW_FALLING);
        register("firstaid", List.of(), 200, 10.0F, 1000.0F, MobEffects.REGENERATION);
        register("fish", List.of(), 200, 10.0F, 1000.0F, MobEffects.WATER_BREATHING);

        register("infinity", List.of(
                AwakenAspects.ASPECT_BLOOD.toInstance(2147483647),
                AwakenAspects.ASPECT_DEATH.toInstance(2147483647),
                AwakenAspects.ASPECT_DIVERSITY.toInstance(2147483647),
                AwakenAspects.ASPECT_HUMANITY.toInstance(2147483647),
                AwakenAspects.ASPECT_INSANITY.toInstance(2147483647),
                AwakenAspects.ASPECT_LONELINESS.toInstance(2147483647),
                AwakenAspects.ASPECT_LONELINESS.toInstance(2147483647),
                AwakenAspects.ASPECT_NATURAL.toInstance(2147483647),
                AwakenAspects.ASPECT_SPIRITUAL.toInstance(2147483647),
                AwakenAspects.ASPECT_VIVIDITY.toInstance(2147483647),
                AwakenAspects.ASPECT_VOID.toInstance(2147483647),
                AwakenAspects.ASPECT_WATER.toInstance(2147483647)),
                (int) Math.pow(10, 5),
                0.01F,
                1000000.0F,
                MobEffects.ABSORPTION,
                MobEffects.CONDUIT_POWER,
                MobEffects.DAMAGE_RESISTANCE,
                MobEffects.DIG_SPEED,
                MobEffects.DOLPHINS_GRACE,
                MobEffects.FIRE_RESISTANCE,
                MobEffects.HEAL,
                MobEffects.HERO_OF_THE_VILLAGE,
                MobEffects.JUMP,
                MobEffects.LUCK,
                MobEffects.MOVEMENT_SPEED,
                MobEffects.NIGHT_VISION,
                MobEffects.SATURATION,
                MobEffects.SLOW_FALLING,
                MobEffects.REGENERATION,
                MobEffects.WATER_BREATHING
        ); // 1 * 10 ^ 5
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        init();
        REGISTRY.register();
    }

    private static void register(
            String id,
            List<AwakenAspect.AspectInstance> aspects,
            int durability,
            float chance,
            float diff,
            Holder<MobEffect>... effects
    )
    {
        register(
                new AwakenPrefix(
                        id,
                        aspects,
                        durability,
                        Arrays.stream(effects).map(effect -> new MobEffectInstance(effect, 200, 1)).toArray(MobEffectInstance[]::new)
                ),
                chance,
                diff
        );
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