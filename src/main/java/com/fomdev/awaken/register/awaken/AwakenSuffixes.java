package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.AwakenSuffix;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.attribute.AwakenAttributes;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.List;

@AutoProxy
public class AwakenSuffixes
{
    public static final RegistryTable<AwakenSuffix> REGISTRY =
            new RegistryTable<>(Awaken.MODID, AwakenRegistries.AWAKEN_SUFFIX);

    public static void init()
    {
        register$fast(Attributes.ARMOR, "normal", "imperfect");
        register$fast(Attributes.ARMOR_TOUGHNESS, "strength", "toughness");
        register$fast(Attributes.ATTACK_DAMAGE, "violent", "crime");
        register$fast(Attributes.ATTACK_KNOCKBACK, "defend", "aura");
        register$fast(Attributes.ATTACK_SPEED, "proficiency", "efficiency");
        register$fast(Attributes.BLOCK_BREAK_SPEED, "miner", "mineral");
        register$fast(Attributes.BLOCK_INTERACTION_RANGE, "future", "observant");
        register$fast$opposite(Attributes.BURNING_TIME, "breeze", "freeze");
        register$fast(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, "liquidize", "destroy");
        register$fast(Attributes.ENTITY_INTERACTION_RANGE, "eliminate", "telepathy");
        register$fast(Attributes.FALL_DAMAGE_MULTIPLIER, "lite", "light");
        register$fast(Attributes.FLYING_SPEED, "flyable", "windful");
        register$fast(Attributes.FOLLOW_RANGE, "leadership", "companionship");
        register$fast$opposite(Attributes.GRAVITY, "gravitate", "weightless");
        register$fast(Attributes.JUMP_STRENGTH, "height", "spring");
        register$fast(Attributes.KNOCKBACK_RESISTANCE, "weighted", "strong");
        register$fast(Attributes.LUCK, "fortune", "blessed");
        register$fast(Attributes.MAX_HEALTH, "healthy", "heartful");
        register$fast(Attributes.MINING_EFFICIENCY, "worker", "undergrounder");
        register$fast(Attributes.MOVEMENT_EFFICIENCY, "jogger", "runner");
        register$fast(Attributes.MOVEMENT_SPEED, "racer", "speeded");
        register$fast(Attributes.OXYGEN_BONUS, "alien", "underwater");
        register$fast(Attributes.SAFE_FALL_DISTANCE, "feather", "bird");
        register$fast(Attributes.SNEAKING_SPEED, "ninja", "assassin");
        register$fast(Attributes.STEP_HEIGHT, "long-legged", "unstoppable");
        register$fast(Attributes.SWEEPING_DAMAGE_RATIO, "aoe", "multi-elimination");
        register$fast(Attributes.WATER_MOVEMENT_EFFICIENCY, "fish", "water-goer");
        register$fast(NeoForgeMod.SWIM_SPEED, "swimmer", "axolotl");
        register$fast(AwakenAttributes.ENCHANTMENT, "enchanter", "magician");
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        init();
        REGISTRY.register();
    }

    private static void register$fast(
            List<String> id,
            Holder<Attribute> target
    )
    {
        for (String str: id)
            register(str, 5, 0.5F, target,500.0F, 0.0F);
    }

    private static void register$fast$opposite(
            List<String> id,
            Holder<Attribute> target
    )
    {
        for (String str: id)
            register(str, 5, 1 / 0.5F, target,500.0F, 0.0F);
    }

    private static void register$fast(
            Holder<Attribute> target,
            String... id
    )
    {
        register$fast(List.of(id), target);
    }

    private static void register$fast$opposite(
            Holder<Attribute> target,
            String... id
    )
    {
        register$fast$opposite(List.of(id), target);
    }

    private static AwakenSuffix register(
            String id,
            int durability,
            float factor,
            Holder<Attribute> target,
            float chance,
            float diff
    )
    {
        return register(
                new AwakenSuffix(
                        id,
                        durability,
                        factor,
                        target
                ),
                chance,
                diff
        );
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