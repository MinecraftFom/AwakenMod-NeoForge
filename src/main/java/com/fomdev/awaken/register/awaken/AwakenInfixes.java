package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenInfix;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.AwakenSuffix;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.attribute.AwakenAttributes;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.awaken.util.Constants;
import com.fomdev.awaken.util.Records;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.Arrays;
import java.util.List;

@AutoProxy
public class AwakenInfixes
{
    public static final RegistryTable<AwakenInfix> REGISTRY =
            new RegistryTable<>(Awaken.MODID, AwakenRegistries.AWAKEN_INFIX);

    static
    {
        register$fast(Attributes.ARMOR, Constants.BODY_SLOTS, "normal", "imperfect");
        register$fast(Attributes.ARMOR_TOUGHNESS, Constants.BODY_SLOTS, "strength", "toughness");
        register$fast(Attributes.ATTACK_DAMAGE, Constants.HAND_SLOTS, "violent", "crime");
        register$fast(Attributes.ATTACK_KNOCKBACK, Constants.HAND_SLOTS, "defend", "aura");
        register$fast(Attributes.ATTACK_SPEED, Constants.HAND_SLOTS, "proficiency", "efficiency");
        register$fast(Attributes.BLOCK_BREAK_SPEED, Constants.HAND_SLOTS,"miner", "mineral");
        register$fast(Attributes.BLOCK_INTERACTION_RANGE, Constants.HAND_SLOTS, "future", "observant");
        register$fast(Attributes.BURNING_TIME, Constants.BODY_SLOTS,"breeze", "freeze");
        register$fast(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, Constants.BODY_SLOTS, "liquidize", "destroy");
        register$fast(Attributes.ENTITY_INTERACTION_RANGE, Constants.HAND_SLOTS, "eliminate", "telepathy");
        register$fast(Attributes.FALL_DAMAGE_MULTIPLIER, Constants.HAND_SLOTS, "lite", "light");
        register$fast(Attributes.FLYING_SPEED, Constants.BODY_SLOTS, "flyable", "windful");
        register$fast(Attributes.FOLLOW_RANGE, Constants.ALL_SLOTS, "leadership", "companionship");
        register$fast$opposite(Attributes.GRAVITY, Constants.BODY_SLOTS, "gravitate", "weightless");
        register$fast(Attributes.JUMP_STRENGTH, Constants.BODY_SLOTS, "height", "spring");
        register$fast(Attributes.KNOCKBACK_RESISTANCE, Constants.BODY_SLOTS, "weighted", "strong");
        register$fast(Attributes.LUCK, Constants.ALL_SLOTS, "fortune", "blessed");
        register$fast(Attributes.MAX_HEALTH, Constants.BODY_SLOTS, "healthy", "heartful");
        register$fast(Attributes.MINING_EFFICIENCY, Constants.BODY_SLOTS, "worker", "undergrounder");
        register$fast(Attributes.MOVEMENT_EFFICIENCY, Constants.BODY_SLOTS, "jogger", "runner");
        register$fast(Attributes.MOVEMENT_SPEED, Constants.BODY_SLOTS, "racer", "speeded");
        register$fast(Attributes.OXYGEN_BONUS, Constants.BODY_SLOTS, "alien", "underwater");
        register$fast(Attributes.SAFE_FALL_DISTANCE, Constants.BODY_SLOTS, "feather", "bird");
        register$fast(Attributes.SNEAKING_SPEED, Constants.BODY_SLOTS, "ninja", "assassin");
        register$fast(Attributes.STEP_HEIGHT, Constants.BODY_SLOTS, "long_legged", "unstoppable");
        register$fast(Attributes.SWEEPING_DAMAGE_RATIO, Constants.HAND_SLOTS, "aoe", "multi-elimination");
        register$fast(Attributes.WATER_MOVEMENT_EFFICIENCY, Constants.BODY_SLOTS, "fish", "water-goer");
        register$fast(NeoForgeMod.SWIM_SPEED, Constants.BODY_SLOTS, "swimmer", "axolotl");
        register$fast(AwakenAttributes.ENCHANTMENT, Constants.ALL_SLOTS, "enchanter", "magician");
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        REGISTRY.register();
    }

    private static void register$fast(
            List<String> id,
            Holder<Attribute> target,
            EquipmentSlot[] slots
    )
    {
        for (String str: id)
        {
            register(str, 0.5F, target, slots, 500.0F, 0.0F);
            register(str + "_2", 1.0F, target, slots, 450.0F, 50.0F);
            register(str + "_3", 2.5F, target, slots, 200.0F, 500.0F);
            register(str + "_4", 5.0F,  target, slots, 100.0F, 1000.0F);
            register(str + "_5", 10.0F,  target, slots, 50.0F, 5000.0F);
            register(str + "_6", 15.0F, target, slots, 25.0F, 10000.0F);
        }
    }

    private static void register$fast$opposite(
            List<String> id,
            Holder<Attribute> target,
            EquipmentSlot[] slots
    )
    {
        for (String str: id)
        {
            register(str, -0.5F, target, slots, 500.0F, 0.0F);
            register(str + "_2", -1.0F, target, slots, 450.0F, 50.0F);
            register(str + "_3", -2.5F, target, slots, 200.0F, 500.0F);
            register(str + "_4", -5.0F,  target, slots, 100.0F, 1000.0F);
            register(str + "_5", -10.0F,  target, slots, 50.0F, 5000.0F);
            register(str + "_6", -15.0F, target, slots, 25.0F, 10000.0F);
        }
    }

    private static void register$fast(
            Holder<Attribute> target,
            EquipmentSlot[] slots,
            String... id
    )
    {
        register$fast(List.of(id), target, slots);
    }

    private static void register$fast$opposite(
            Holder<Attribute> target,
            EquipmentSlot[] slots,
            String... id
    )
    {
        register$fast$opposite(List.of(id), target, slots);
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
        for (EquipmentSlot slot: slots)
            register(
                    new AwakenInfix(
                            id,
                            new Records.AttributeHolder(target, amount, slot)
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