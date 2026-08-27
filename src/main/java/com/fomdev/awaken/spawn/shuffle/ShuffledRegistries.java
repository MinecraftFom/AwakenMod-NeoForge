package com.fomdev.awaken.spawn.shuffle;

import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.entries.raw.affix.AwakenInfix;
import com.fomdev.awaken.entries.raw.affix.AwakenPrefix;
import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.entries.shuffle.EquippedQueue;
import com.fomdev.awaken.entries.shuffle.WeightedQueue;
import com.fomdev.awaken.entries.shuffle.WeightedRegistry;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.spawn.MobTier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;

import java.util.List;

public class ShuffledRegistries
{
    public static final String SIG_AWAKEN_STACKS =
            "generatable_stacks";

    public static final String SIG_AWAKEN_TIERS =
            "generatable_tiers";

    public static final ResourceKey<Registry<WeightedRegistry<AwakenInfix>>> RES_WEIGHTED_INFIX =
            createKey(AwakenRegistries.SIG_AWAKEN_INFIX);

    public static final ResourceKey<Registry<WeightedRegistry<AwakenPrefix>>> RES_WEIGHTED_PREFIX =
            createKey(AwakenRegistries.SIG_AWAKEN_PREFIX);

    public static final ResourceKey<Registry<WeightedRegistry<AwakenQuality>>> RES_WEIGHTED_QUALITY =
            createKey(AwakenRegistries.SIG_AWAKEN_QUALITY);

    public static final ResourceKey<Registry<WeightedRegistry<AwakenSuffix>>> RES_WEIGHTED_SUFFIX =
            createKey(AwakenRegistries.SIG_AWAKEN_SUFFIX);

    public static final ResourceKey<Registry<WeightedRegistry<ItemStack>>> RES_WEIGHTED_STACK =
            createKey(SIG_AWAKEN_STACKS);

    public static final ResourceKey<Registry<WeightedRegistry<MobTier>>> RES_WEIGHTED_TIER =
            createKey(SIG_AWAKEN_TIERS);

    public static final EquippedQueue<AwakenInfix> WEIGHTED_AWAKEN_INFIX =
            new EquippedQueue<>(RES_WEIGHTED_INFIX);

    public static final WeightedQueue<AwakenPrefix> WEIGHTED_AWAKEN_PREFIX =
            new WeightedQueue<>(RES_WEIGHTED_PREFIX);

    public static final WeightedQueue<AwakenQuality> WEIGHTED_AWAKEN_QUALITY =
            new WeightedQueue<>(RES_WEIGHTED_QUALITY);

    public static final WeightedQueue<AwakenSuffix> WEIGHTED_AWAKEN_SUFFIX =
            new WeightedQueue<>(RES_WEIGHTED_SUFFIX);

    public static final EquippedQueue<ItemStack> WEIGHTED_AWAKEN_STACK =
            new EquippedQueue<>(RES_WEIGHTED_STACK);

    public static final WeightedQueue<MobTier> WEIGHTED_AWAKEN_TIER =
            new WeightedQueue<>(RES_WEIGHTED_TIER);

    public static void register(
            IEventBus bus
    )
    {
        WEIGHTED_AWAKEN_INFIX.attach(bus);
        WEIGHTED_AWAKEN_PREFIX.attach(bus);
        WEIGHTED_AWAKEN_QUALITY.attach(bus);
        WEIGHTED_AWAKEN_SUFFIX.attach(bus);

        WEIGHTED_AWAKEN_STACK.attach(bus);
    }

    public static void initFromConfig()
    {
        loadFromConfig(EquipmentSlot.HEAD, AwakenCommon.CONFIG.HELMET.get());
        loadFromConfig(EquipmentSlot.CHEST, AwakenCommon.CONFIG.CHESTPLATE.get());
        loadFromConfig(EquipmentSlot.LEGS, AwakenCommon.CONFIG.LEGGINGS.get());
        loadFromConfig(EquipmentSlot.FEET, AwakenCommon.CONFIG.BOOTS.get());
        loadFromConfig(EquipmentSlot.MAINHAND, AwakenCommon.CONFIG.MAIN_HAND.get());
        loadFromConfig(EquipmentSlot.OFFHAND, AwakenCommon.CONFIG.OFF_HAND.get());
    }

    public static void loadFromConfig(
            EquipmentSlot slot,
            List<? extends String> raw
    )
    {
        raw
                .stream()
                .map(ShuffledRegistries::loadFromString)
                .forEach(
                        tuple ->
                                WEIGHTED_AWAKEN_STACK.push(
                                        tuple.getA(),
                                        slot,
                                        tuple.getB().getA(),
                                        tuple.getB().getB()
                                )
                );
    }

    public static Tuple<ItemStack, Tuple<Integer, Float>> loadFromString(
            String raw
    )
    {
        String[] components = raw.split("\\|");
        if (components.length != 3)
            throw new IllegalArgumentException("Invalid argument: required 2 parts");

        String header = components[0].strip();
        String body_0 = components[1].strip();
        String body_1 = components[2].strip();

        ResourceLocation itemPath = ResourceLocation.parse(header);
        Integer chance = Integer.parseInt(body_0);
        Float diff = Float.parseFloat(body_1);

        Item item = BuiltInRegistries.ITEM.get(itemPath);
        return new Tuple<>(item.getDefaultInstance(), new Tuple<>(chance, diff));
    }

    private static <T> ResourceKey<Registry<T>> createKey(
            String key
    )
    {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Awaken.MODID, key));
    }
}