package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.affix.AwakenInfix;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
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

    public static void init()
    {
        register$fast(Attributes.ARMOR, Constants.BODY_SLOTS, 1.5F, "normal", "imperfect");
        register$fast(Attributes.ARMOR_TOUGHNESS, Constants.BODY_SLOTS, 1.1F, "strength", "toughness");
        register$fast(Attributes.ATTACK_DAMAGE, Constants.HAND_SLOTS, 1.5F, "violent", "crime");
        register$fast(Attributes.ATTACK_KNOCKBACK, Constants.HAND_SLOTS, 1.25F, "defend", "aura");
        register$fast(Attributes.ATTACK_SPEED, Constants.HAND_SLOTS, 1.75F, "proficiency", "efficiency");
        register$fast(Attributes.BLOCK_BREAK_SPEED, Constants.HAND_SLOTS, 1.25F, "miner", "mineral");
        register$fast(Attributes.BLOCK_INTERACTION_RANGE, Constants.HAND_SLOTS, 2.0F, "future", "observant");
        register$fast(Attributes.BURNING_TIME, Constants.BODY_SLOTS, 1.0F, "breeze", "freeze");
        register$fast(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, Constants.BODY_SLOTS, 1.75F, "liquidize", "destroy");
        register$fast(Attributes.ENTITY_INTERACTION_RANGE, Constants.HAND_SLOTS, 2.0F, "eliminate", "telepathy");
        register$fast(Attributes.FALL_DAMAGE_MULTIPLIER, Constants.HAND_SLOTS, 1.5F, "lite", "light");
        register$fast(Attributes.FLYING_SPEED, Constants.BODY_SLOTS, 1.5F, "flyable", "windful");
        register$fast(Attributes.FOLLOW_RANGE, Constants.ALL_SLOTS, 1.75F, "leadership", "companionship");
        register$fast(Attributes.GRAVITY, Constants.BODY_SLOTS, 1F, "gravitate", "weightless");
        register$fast(Attributes.JUMP_STRENGTH, Constants.BODY_SLOTS, 1.5F, "height", "spring");
        register$fast(Attributes.KNOCKBACK_RESISTANCE, Constants.BODY_SLOTS, 2.75F, "weighted", "strong");
        register$fast(Attributes.LUCK, Constants.ALL_SLOTS, 1.5F, "fortune", "blessed");
        register$fast(Attributes.MAX_HEALTH, Constants.BODY_SLOTS, 3.5F, "healthy", "heartful");
        register$fast(Attributes.MINING_EFFICIENCY, Constants.BODY_SLOTS, 2.75F, "worker", "undergrounder");
        register$fast(Attributes.MOVEMENT_EFFICIENCY, Constants.BODY_SLOTS, 1.2F, "jogger", "runner");
        register$fast(Attributes.MOVEMENT_SPEED, Constants.BODY_SLOTS, 1.2F, "racer", "speeded");
        register$fast(Attributes.OXYGEN_BONUS, Constants.BODY_SLOTS, 2.75F, "alien", "underwater");
        register$fast(Attributes.SAFE_FALL_DISTANCE, Constants.BODY_SLOTS, 1.75F, "feather", "bird");
        register$fast(Attributes.SNEAKING_SPEED, Constants.BODY_SLOTS, 1.5F, "ninja", "assassin");
        register$fast(Attributes.STEP_HEIGHT, Constants.BODY_SLOTS, 1.05F, "long-legged", "unstoppable");
        register$fast(Attributes.SWEEPING_DAMAGE_RATIO, Constants.HAND_SLOTS, 3.5F, "aoe", "multi-elimination");
        register$fast(Attributes.WATER_MOVEMENT_EFFICIENCY, Constants.BODY_SLOTS, 1.5F, "fish", "water-goer");
        register$fast(NeoForgeMod.SWIM_SPEED, Constants.BODY_SLOTS, 1.5F, "swimmer", "axolotl");
        register$fast(AwakenAttributes.ENCHANTMENT, Constants.ALL_SLOTS, 5.0F, "enchanter", "magician");
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
                        new Records.AttributeHolder(target, amount, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, slots)
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