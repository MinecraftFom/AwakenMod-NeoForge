package com.fomdev.awaken.compat;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.affix.AwakenInfix;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.awaken.util.Constants;
import com.fomdev.awaken.util.Records;
import com.fomdev.flame.register.RegistryTable;
import com.github.L_Ender.cataclysm.init.ModAttribute;
import dev.shadowsoffire.apothic_attributes.api.ALObjects;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.tslat.aoa3.common.registration.AoAAttributes;
import twilightforest.init.TFAttributes;

import java.util.Arrays;
import java.util.List;

public class InfixAddition
{
    public static final RegistryTable<AwakenInfix> REGISTRY =
            new RegistryTable<>(
                    Awaken.MODID,
                    AwakenRegistries.AWAKEN_INFIX
            );

    public static void activeIfAoa3Installed()
    {
        register$fast(AoAAttributes.AGGRO_RANGE, Constants.HAND_SLOTS, 1.25F, "aggro");
        register$fast(AoAAttributes.CRITICAL_HIT_MULTIPLIER, Constants.HAND_SLOTS, 1.5F, "critical-thought");
        register$fast(AoAAttributes.RANGED_ATTACK_DAMAGE, Constants.HAND_SLOTS, 1.5F, "ranger");
    }

    public static void activeIfApotheosisInstalled()
    {
        register$fast(ALObjects.Attributes.ARMOR_PIERCE, Constants.BODY_SLOTS, 1.25F, "pierce");
        register$fast(ALObjects.Attributes.ARMOR_SHRED, Constants.BODY_SLOTS, 1.5F, "shred");
        register$fast(ALObjects.Attributes.ARROW_DAMAGE, Constants.HAND_SLOTS, 1.25F, "ranged-enforce");
        register$fast(ALObjects.Attributes.ARROW_VELOCITY, Constants.HAND_SLOTS, 1.25F, "newton-reforged");
        register$fast(ALObjects.Attributes.COLD_DAMAGE, Constants.BODY_SLOTS, 1.5F, "climate", "extreme-coldness");
        register$fast(ALObjects.Attributes.COOLDOWN_REDUCTION, Constants.HAND_SLOTS, 1.25F, "unweighted");
        register$fast(ALObjects.Attributes.CRIT_CHANCE, Constants.HAND_SLOTS, 1.25F, "master-critical");
        register$fast(ALObjects.Attributes.CRIT_DAMAGE, Constants.HAND_SLOTS, 1.25F, "mastered-critical");
        register$fast(ALObjects.Attributes.CURRENT_HP_DAMAGE, Constants.HAND_SLOTS, 1.5F, "equivalent");
        register$fast(ALObjects.Attributes.DODGE_CHANCE, Constants.HAND_SLOTS, 1.5F, "dodge");
        register$fast(ALObjects.Attributes.DRAW_SPEED, Constants.HAND_SLOTS, 1.25F, "faster-draw");
        register$fast(ALObjects.Attributes.ELYTRA_FLIGHT, Constants.HAND_SLOTS, 1.25F, "heroic-flight");
        register$fast(ALObjects.Attributes.EXPERIENCE_GAINED, Constants.ALL_SLOTS, 2.0F, "experienced");
        register$fast$opposite(ALObjects.Attributes.FIRE_DAMAGE, Constants.BODY_SLOTS, 1.5F, "yeti");
        register$fast(ALObjects.Attributes.GHOST_HEALTH, Constants.ALL_SLOTS, 2.0F, "phantom");
        register$fast(ALObjects.Attributes.HEALING_RECEIVED, Constants.ALL_SLOTS, 1.75F, "healed");
        register$fast(ALObjects.Attributes.LIFE_STEAL, Constants.HAND_SLOTS, 1.25F, "vampire");
        register$fast(ALObjects.Attributes.MINING_SPEED, Constants.HAND_SLOTS, 1.5F, "efficient-miner");
        register$fast(ALObjects.Attributes.OVERHEAL, Constants.BODY_SLOTS, 1.75F, "oh"); // od is not good...
        register$fast(ALObjects.Attributes.PROJECTILE_DAMAGE, Constants.HAND_SLOTS, 1.5F, "ranged-reinforce");
        register$fast(ALObjects.Attributes.PROT_PIERCE, Constants.HAND_SLOTS, 1.5F, "prot-pierce");
        register$fast(ALObjects.Attributes.PROT_SHRED, Constants.HAND_SLOTS, 1.75F, "prot-shred");
    }

    public static void activeIfCataclysmInstalled()
    {
        register$fast(ModAttribute.ADDITIONAL_CRITICAL_DAMAGE, Constants.HAND_SLOTS, 2.5F, "cataclysm", "critical");
        register$fast(ModAttribute.CHARGE_TIME, Constants.HAND_SLOTS, 1.5F, "fast_charger");
        register$fast(ModAttribute.EAT_SPEED, Constants.ALL_SLOTS, 1.5F, "eater", "stomach");
        register$fast(ModAttribute.NATURE_HEAL, Constants.ALL_SLOTS, 2.0F, "natural-blessed", "healing");
    }

    public static void activeIfIronSpellbookInstalled()
    {
        register$fast(AttributeRegistry.BLOOD_MAGIC_RESIST, Constants.ALL_SLOTS, 2.0F, "blood-resist");
        register$fast(AttributeRegistry.BLOOD_SPELL_POWER, Constants.ALL_SLOTS, 2.25F, "blood-magicful");
        register$fast(AttributeRegistry.CAST_TIME_REDUCTION, Constants.ALL_SLOTS, 1.5F, "caster");
        register$fast(AttributeRegistry.CASTING_MOVESPEED, Constants.ALL_SLOTS, 2.0F, "speeded-cast");
        register$fast(AttributeRegistry.COOLDOWN_REDUCTION, Constants.ALL_SLOTS, 2.0F, "is-lighter"); // Not to conflict with apothic-attributes:cooldown_reduction
        register$fast(AttributeRegistry.ELDRITCH_MAGIC_RESIST, Constants.ALL_SLOTS, 1.25F, "eldritch-resist");
        register$fast(AttributeRegistry.ELDRITCH_SPELL_POWER, Constants.ALL_SLOTS, 1.25F, "eldritch-magicful");
        register$fast(AttributeRegistry.ENDER_MAGIC_RESIST, Constants.ALL_SLOTS, 2.5F, "ender-resist");
        register$fast(AttributeRegistry.ENDER_SPELL_POWER, Constants.ALL_SLOTS, 2.25F, "ender-magicful");
        register$fast(AttributeRegistry.EVOCATION_MAGIC_RESIST, Constants.ALL_SLOTS, 1.75F, "evocation-resist");
        register$fast(AttributeRegistry.EVOCATION_SPELL_POWER, Constants.ALL_SLOTS, 1.25F, "evocation-magicful");
        register$fast(AttributeRegistry.FIRE_MAGIC_RESIST, Constants.ALL_SLOTS, 1.5F, "fire-resist");
        register$fast(AttributeRegistry.FIRE_SPELL_POWER, Constants.ALL_SLOTS, 1.25F, "fire-magicful");
        register$fast(AttributeRegistry.HOLY_MAGIC_RESIST, Constants.ALL_SLOTS, 1.75F, "holy-resist");
        register$fast(AttributeRegistry.HOLY_SPELL_POWER, Constants.ALL_SLOTS, 1.25F, "holy-magicful");
        register$fast(AttributeRegistry.ICE_MAGIC_RESIST, Constants.ALL_SLOTS, 1.75F, "ice-resist");
        register$fast(AttributeRegistry.ICE_SPELL_POWER, Constants.ALL_SLOTS, 1.5F, "ice-magicful");
        register$fast(AttributeRegistry.LIGHTNING_MAGIC_RESIST, Constants.ALL_SLOTS, 1.5F, "lightning-resist");
        register$fast(AttributeRegistry.LIGHTNING_SPELL_POWER, Constants.ALL_SLOTS, 1.25F, "lightning-magicful");
        register$fast(AttributeRegistry.MANA_REGEN, Constants.ALL_SLOTS, 1.5F, "resourceful");
        register$fast(AttributeRegistry.MAX_MANA, Constants.ALL_SLOTS, 1.75F, "manaic");
        register$fast(AttributeRegistry.NATURE_MAGIC_RESIST, Constants.ALL_SLOTS, 2.0F, "natural-resist");
        register$fast(AttributeRegistry.NATURE_SPELL_POWER, Constants.ALL_SLOTS, 2.25F, "natural-magicful");
        register$fast(AttributeRegistry.SPELL_RESIST, Constants.ALL_SLOTS, 1.25F, "resist");
        register$fast(AttributeRegistry.SPELL_POWER, Constants.ALL_SLOTS, 1.25F, "magicful");
        register$fast(AttributeRegistry.SUMMON_DAMAGE, Constants.ALL_SLOTS, 2.0F, "summoner");
    }

    public static void activeIfTFInstalled()
    {
        register$fast(TFAttributes.SHIELD_STRENGTH, Constants.ALL_SLOTS, 1.5F, "twilight", "delighted");
    }

    public static void init()
    {
        REGISTRY.register();
    }

    private static void register$fast(
            List<String> id,
            Holder<Attribute> target,
            EquipmentSlot[] slots,
            float factor
    )
    {
        for (String str: id)
            register(str, factor, target, slots, 500.0F * factor, 0.0F);
    }

    private static void register$fast$opposite(
            List<String> id,
            Holder<Attribute> target,
            EquipmentSlot[] slots,
            float factor
    )
    {
        for (String str: id)
            register(str, -factor, target, slots, 500.0F * factor, 0.0F);
    }

    private static void register$fast(
            Holder<Attribute> target,
            EquipmentSlot[] slots,
            float factor,
            String... id
    )
    {
        register$fast(List.of(id), target, slots, factor);
    }

    private static void register$fast$opposite(
            Holder<Attribute> target,
            EquipmentSlot[] slots,
            float factor,
            String... id
    )
    {
        register$fast$opposite(List.of(id), target, slots, factor);
    }

    private static void register(
            String id,
            float amount,
            Holder<Attribute> target,
            EquipmentSlot[] slots,
            float chance,
            float diff
    )
    {
        register(
                new AwakenInfix(
                        id,
                        new Records.AttributeHolder(target, amount, AttributeModifier.Operation.ADD_VALUE, slots)
                ),
                chance,
                diff
        );
    }

    private static AwakenInfix register(
            AwakenInfix infix,
            float chance,
            float minDiff
    )
    {
        REGISTRY.register(infix);
        Arrays.stream(infix.getAttribute().slot()).forEach(s -> ShuffledRegistries.WEIGHTED_AWAKEN_INFIX.push(infix, s, chance, minDiff));
        return infix;
    }
}