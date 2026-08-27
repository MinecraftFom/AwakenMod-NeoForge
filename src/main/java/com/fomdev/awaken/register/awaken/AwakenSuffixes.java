package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
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
        register$fast(Attributes.ARMOR, 1.5F, "normal", "imperfect");
        register$fast(Attributes.ARMOR_TOUGHNESS, 0.75F, "strength", "toughness");
        register$fast(Attributes.ATTACK_DAMAGE, 0.5F, "violent", "crime");
        register$fast(Attributes.ATTACK_KNOCKBACK, 0.1F, "defend", "aura");
        register$fast(Attributes.ATTACK_SPEED, 0.5F, "proficiency", "efficiency");
        register$fast(Attributes.BLOCK_BREAK_SPEED, 1.5F, "miner", "mineral");
        register$fast(Attributes.BLOCK_INTERACTION_RANGE, 2.0F, "future", "observant");
        register$fast$opposite(Attributes.BURNING_TIME, 0.25F, "breeze", "freeze");
        register$fast(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, 0.1F, "liquidize", "destroy");
        register$fast(Attributes.ENTITY_INTERACTION_RANGE, 2.0F, "eliminate", "telepathy");
        register$fast$opposite(Attributes.FALL_DAMAGE_MULTIPLIER, 0.75F, "lite", "light");
        register$fast(Attributes.FLYING_SPEED, 1.5F, "flyable", "windful");
        register$fast(Attributes.FOLLOW_RANGE, 3.0F, "leadership", "companionship");
        register$fast$opposite(Attributes.GRAVITY, 0.01F, "gravitate", "weightless");
        register$fast(Attributes.JUMP_STRENGTH, 0.1F, "height", "spring");
        register$fast(Attributes.KNOCKBACK_RESISTANCE, 1.0F, "weighted", "strong");
        register$fast(Attributes.LUCK, 2.5F, "fortune", "blessed");
        register$fast(Attributes.MAX_HEALTH, 1.8F, "healthy", "heartful");
        register$fast(Attributes.MINING_EFFICIENCY, 1.5F, "worker", "undergrounder");
        register$fast(Attributes.MOVEMENT_EFFICIENCY, 0.75F, "jogger", "runner");
        register$fast(Attributes.MOVEMENT_SPEED, 0.75F, "racer", "speeded");
        register$fast(Attributes.OXYGEN_BONUS, 1.25F, "alien", "underwater");
        register$fast(Attributes.SAFE_FALL_DISTANCE, 0.9F, "feather", "bird");
        register$fast(Attributes.SNEAKING_SPEED, 2.5F, "ninja", "assassin");
        register$fast(Attributes.STEP_HEIGHT, 1.2F, "long-legged", "unstoppable");
        register$fast(Attributes.SWEEPING_DAMAGE_RATIO, 3.5F, "aoe", "multi-elimination");
        register$fast(Attributes.WATER_MOVEMENT_EFFICIENCY, 1.4F, "fish", "water-goer");
        register$fast(NeoForgeMod.SWIM_SPEED, 1.75F, "swimmer", "axolotl");
        register$fast(AwakenAttributes.ENCHANTMENT, 5.0F, "enchanter", "magician");
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        init();
        REGISTRY.register();
    }

    private static void register$fast(
            List<String> id,
            Holder<Attribute> target,
            float factor
    )
    {
        for (String str: id)
            register(str, (int) (5 * factor), factor, target,500.0F * factor, 0.0F);
    }

    private static void register$fast$opposite(
            List<String> id,
            Holder<Attribute> target,
            float factor
    )
    {
        for (String str: id)
            register(str, (int) (5 * factor), 1 / factor, target,500.0F * factor, 0.0F);
    }

    private static void register$fast(
            Holder<Attribute> target,
            float factor,
            String... id
    )
    {
        register$fast(List.of(id), target, factor);
    }

    private static void register$fast$opposite(
            Holder<Attribute> target,
            float factor,
            String... id
    )
    {
        register$fast$opposite(List.of(id), target, factor);
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