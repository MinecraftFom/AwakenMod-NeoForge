package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.affix.AwakenPrefix;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.awaken.util.Records;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.List;

@AutoProxy
public class AwakenPrefixes
{
    public static final RegistryTable<AwakenPrefix> REGISTRY =
            new RegistryTable<>(Awaken.MODID, AwakenRegistries.AWAKEN_PREFIX);

    public static final Records.EnchantmentHolder PROTECTION = new Records.EnchantmentHolder(Enchantments.PROTECTION);
    public static final Records.EnchantmentHolder FIRE_PROTECTION = new Records.EnchantmentHolder(Enchantments.FIRE_PROTECTION);
    public static final Records.EnchantmentHolder FEATHER_FALLING = new Records.EnchantmentHolder(Enchantments.FEATHER_FALLING);
    public static final Records.EnchantmentHolder BLAST_PROTECTION = new Records.EnchantmentHolder(Enchantments.BLAST_PROTECTION);
    public static final Records.EnchantmentHolder PROJECTILE_PROTECTION = new Records.EnchantmentHolder(Enchantments.PROJECTILE_PROTECTION);
    public static final Records.EnchantmentHolder RESPIRATION = new Records.EnchantmentHolder(Enchantments.RESPIRATION);
    public static final Records.EnchantmentHolder AQUA_AFFINITY = new Records.EnchantmentHolder(Enchantments.AQUA_AFFINITY);
    public static final Records.EnchantmentHolder THORNS = new Records.EnchantmentHolder(Enchantments.THORNS);
    public static final Records.EnchantmentHolder DEPTH_STRIDER = new Records.EnchantmentHolder(Enchantments.DEPTH_STRIDER);
    public static final Records.EnchantmentHolder FROST_WALKER = new Records.EnchantmentHolder(Enchantments.FROST_WALKER);
    public static final Records.EnchantmentHolder SOUL_SPEED = new Records.EnchantmentHolder(Enchantments.SOUL_SPEED);
    public static final Records.EnchantmentHolder SWIFT_SNEAK = new Records.EnchantmentHolder(Enchantments.SWIFT_SNEAK);
    public static final Records.EnchantmentHolder SHARPNESS = new Records.EnchantmentHolder(Enchantments.SHARPNESS);
    public static final Records.EnchantmentHolder SMITE = new Records.EnchantmentHolder(Enchantments.SMITE);
    public static final Records.EnchantmentHolder BANE_OF_ARTHROPODS = new Records.EnchantmentHolder(Enchantments.BANE_OF_ARTHROPODS);
    public static final Records.EnchantmentHolder KNOCKBACK = new Records.EnchantmentHolder(Enchantments.KNOCKBACK);
    public static final Records.EnchantmentHolder FIRE_ASPECT = new Records.EnchantmentHolder(Enchantments.FIRE_ASPECT);
    public static final Records.EnchantmentHolder LOOTING = new Records.EnchantmentHolder(Enchantments.LOOTING);
    public static final Records.EnchantmentHolder SWEEPING_EDGE = new Records.EnchantmentHolder(Enchantments.SWEEPING_EDGE);
    public static final Records.EnchantmentHolder EFFICIENCY = new Records.EnchantmentHolder(Enchantments.EFFICIENCY);
    public static final Records.EnchantmentHolder SILK_TOUCH = new Records.EnchantmentHolder(Enchantments.SILK_TOUCH);
    public static final Records.EnchantmentHolder UNBREAKING = new Records.EnchantmentHolder(Enchantments.UNBREAKING);
    public static final Records.EnchantmentHolder FORTUNE = new Records.EnchantmentHolder(Enchantments.FORTUNE);
    public static final Records.EnchantmentHolder POWER = new Records.EnchantmentHolder(Enchantments.POWER);
    public static final Records.EnchantmentHolder PUNCH = new Records.EnchantmentHolder(Enchantments.PUNCH);
    public static final Records.EnchantmentHolder FLAME = new Records.EnchantmentHolder(Enchantments.FLAME);
    public static final Records.EnchantmentHolder INFINITY = new Records.EnchantmentHolder(Enchantments.INFINITY);
    public static final Records.EnchantmentHolder LUCK_OF_THE_SEA = new Records.EnchantmentHolder(Enchantments.LUCK_OF_THE_SEA);
    public static final Records.EnchantmentHolder LURE = new Records.EnchantmentHolder(Enchantments.LURE);
    public static final Records.EnchantmentHolder LOYALTY = new Records.EnchantmentHolder(Enchantments.LOYALTY);
    public static final Records.EnchantmentHolder IMPALING = new Records.EnchantmentHolder(Enchantments.IMPALING);
    public static final Records.EnchantmentHolder RIPTIDE = new Records.EnchantmentHolder(Enchantments.RIPTIDE);
    public static final Records.EnchantmentHolder CHANNELING = new Records.EnchantmentHolder(Enchantments.CHANNELING);
    public static final Records.EnchantmentHolder MULTISHOT = new Records.EnchantmentHolder(Enchantments.MULTISHOT);
    public static final Records.EnchantmentHolder QUICK_CHARGE = new Records.EnchantmentHolder(Enchantments.QUICK_CHARGE);
    public static final Records.EnchantmentHolder PIERCING = new Records.EnchantmentHolder(Enchantments.PIERCING);
    public static final Records.EnchantmentHolder DENSITY = new Records.EnchantmentHolder(Enchantments.DENSITY);
    public static final Records.EnchantmentHolder BREACH = new Records.EnchantmentHolder(Enchantments.BREACH);
    public static final Records.EnchantmentHolder WIND_BURST = new Records.EnchantmentHolder(Enchantments.WIND_BURST);
    public static final Records.EnchantmentHolder MENDING = new Records.EnchantmentHolder(Enchantments.MENDING);

    public static void init()
    {
        register(-100, 0.125F, 5000.0F, List.of(), "useless", "speechless", "zenless", "edged", "pessimist");
        register(-75, 0.5F, 5000.0F, List.of(), "misery", "egoist", "pity", "laughter", "joke");
        register(-10, 0.75F, 5000.0F, List.of(), "novice", "naive", "childhood", "mindless", "insightless");
        register(10, 1.0F, 5000.0F, List.of(), "normal", "unknown", "daily", "invisible", "low"); // Unknown Mother-Goose
        register(100, 2.5F, 4500.0F, List.of(), "deeper", "learner", "adult", "survival", "individual");
        register(200, 4.0F, 4000.0F, List.of(), "identity", "copier", "knowledge", "started", "starter");
        register(500, 7.5F, 3000.0F, List.of(), "smooth", "skilled", "insighted", "mastered", "motivated");
        register(1000, 15.0F, 1000.0F, List.of(UNBREAKING.of(1)), "strengthen", "leveled", "advanced", "improved", "enhanced");
        register(2000, 20.0F, 500.0F, List.of(UNBREAKING.of(3)), "hard", "strong", "tough", "minded", "passionated");
        register(5000, 25.0F, 100.0F, List.of(PROTECTION.of(1), FIRE_PROTECTION.of(1), FEATHER_FALLING.of(1), BLAST_PROTECTION.of(1), PROJECTILE_PROTECTION.of(1)), "protective", "safety", "adaption", "home", "stable");
        register(5000, 30.0F, 50.0F, List.of(PROTECTION.of(5), FIRE_PROTECTION.of(5), FEATHER_FALLING.of(5), BLAST_PROTECTION.of(5), PROJECTILE_PROTECTION.of(5)), "reinforced", "force", "tank", "weighted", "mature");
        register(5000, 35.0F, 10.0F, List.of(KNOCKBACK.of(5), SHARPNESS.of(5), BANE_OF_ARTHROPODS.of(5), SMITE.of(5), SWEEPING_EDGE.of(5)), "killer", "knight", "solider", "fighter", "battler");
        register(5000, 40.0F, 5.0F, List.of(THORNS.of(3), DEPTH_STRIDER.of(3), FROST_WALKER.of(3), SOUL_SPEED.of(3), SWIFT_SNEAK.of(3), AQUA_AFFINITY.of(3)), "ninja", "sneaker", "creeper", "assassin", "vampier");
        register(5000, 40.0F, 5.0F, List.of(MULTISHOT.of(3), QUICK_CHARGE.of(2), PIERCING.of(2), POWER.of(4), PUNCH.of(4), FLAME.of(2), INFINITY.of(1)), "accurate", "far", "distant", "shoot", "archer");
        register(2500, 45.0F, 1.0F, List.of(LUCK_OF_THE_SEA.of(10), LURE.of(10), LOYALTY.of(3), IMPALING.of(5), RIPTIDE.of(5), CHANNELING.of(2)), "ocean", "aqua", "water", "liquid", "depth");
        register(10000, 50.0F, 0.05F, List.of(DENSITY.of(4), WIND_BURST.of(4), BREACH.of(4)), "dense", "god", "might", "weight", "sky");
        register(5000, 50.0F, 0.05F, List.of(FORTUNE.of(3), EFFICIENCY.of(3)), "miner", "underground", "speed", "efficiency", "worker");
        register(5000, 55.0F, 0.025F, List.of(FIRE_ASPECT.of(3), LOOTING.of(3)), "magician", "flame", "phoenix", "heat", "greed");
        register(1000, 100.0F, 0.00000001F, List.of(MENDING.of(1)), "lord", "awaken", "masterpiece");
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        init();
        REGISTRY.register();
    }

    private static void register(
            int durability,
            float factor,
            float chance,
            List<Records.EnchantmentHolder> enchants,
            String... ids
    )
    {
        register(durability, factor, enchants, 0.0F, chance, 5, ids);
    }

    private static void register(
            int durability,
            float factor,
            List<Records.EnchantmentHolder> enchants,
            float diff,
            float chance,
            float dist,
            String... ids
    )
    {
        for (int i = 0; i < ids.length; i++)
            register(
                    new AwakenPrefix(
                            ids[i],
                            (int) (durability * i * dist),
                            factor * i * dist,
                            enchants
                    ),
                    chance - dist * i,
                    diff + dist * i
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