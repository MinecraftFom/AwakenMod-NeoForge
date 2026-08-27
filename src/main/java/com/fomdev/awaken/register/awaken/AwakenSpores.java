package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.spore.AwakenSpore;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.attribute.AwakenAttributes;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;

@AutoProxy
public class AwakenSpores
{
    public static final RegistryTable<AwakenSpore> REGISTRY =
            new RegistryTable<>(
                    Awaken.MODID,
                    AwakenRegistries.AWAKEN_SPORE
            );

    public static void init()
    {
        register("soften_spore", Attributes.ARMOR, 0.25F);
        register("weaken_spore", Attributes.ARMOR_TOUGHNESS, 0.1F);
        register("damageless_spore", Attributes.ATTACK_DAMAGE, 0.5F);
        register("powerless_spore", Attributes.ATTACK_KNOCKBACK, 0.2F);
        register("inflexible_spore", Attributes.ATTACK_SPEED, 0.25F);
        register("inefficient_spore", Attributes.BLOCK_BREAK_SPEED, 0.5F);
        register("shorten_spore", Attributes.BLOCK_INTERACTION_RANGE, 0.2F);
        register("burnable_spore", Attributes.BURNING_TIME, 0.3F);
        register("weightless_spore", Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, 0.2F);
        register("insightless_spore", Attributes.ENTITY_INTERACTION_RANGE, 0.15F);
        register("weighted_spore", Attributes.FALL_DAMAGE_MULTIPLIER, -0.25F);
        register("blocking_spore", Attributes.FLYING_SPEED, 0.35F);
        register("unrecognizable_spore", Attributes.FOLLOW_RANGE, 0.5F);
        register("heavy_spore", Attributes.GRAVITY, -0.6F);
        register("lower_spore", Attributes.JUMP_STRENGTH, 0.5F);
        register("weightful_spore", Attributes.KNOCKBACK_RESISTANCE, 0.4F);
        register("unlucky_spore", Attributes.LUCK, 0.75F);
        register("protectionless_spore", Attributes.MAX_ABSORPTION, 0.4F);
        register("dishearten_spore", Attributes.MAX_HEALTH, 0.5F);
        register("unfocusing_spore", Attributes.MINING_EFFICIENCY, 0.3F);
        register("snail_spore", Attributes.MOVEMENT_EFFICIENCY, 0.6F);
        register("slow_spore", Attributes.MOVEMENT_SPEED, 0.45F);
        register("drown_spore", Attributes.OXYGEN_BONUS, 0.6F);
        register("minimize_spore", Attributes.SAFE_FALL_DISTANCE, 0.4F);
        register("inpatient_spore", Attributes.SNEAKING_SPEED, 0.5F);
        register("short_legged_spore", Attributes.STEP_HEIGHT, 0.25F);
        register("unpowered_spore", Attributes.SWEEPING_DAMAGE_RATIO, 0.4F);
        register("above_water_spore", Attributes.WATER_MOVEMENT_EFFICIENCY, 0.6F);
        register("land_born_spore", NeoForgeMod.SWIM_SPEED, 0.3F);
        register("stupid_spore", AwakenAttributes.ENCHANTMENT, 0.7F);
    }

    public static void register(
            String id,
            Holder<Attribute> target,
            float factor
    )
    {
        register(
                new AwakenSpore(
                        id,
                        target
                )
                {
                    @Override
                    public double getAmount(int level)
                    {
                        return -(level * factor);
                    }
                }
        );
    }

    public static AwakenSpore register(
            AwakenSpore spore
    )
    {
        return REGISTRY.register(spore);
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        init();
        REGISTRY.register();
    }
}