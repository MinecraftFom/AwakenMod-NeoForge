package com.fomdev.awaken.spawn;

import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import com.fomdev.awaken.util.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EquipmentManager
{
    // Tuple.A: DURATION Tuple.B: MAX_LEVEL
    public static final List<Tuple<ResourceLocation, Tuple<Integer, Integer>>> EFFECTS = new ArrayList<>();

    private static final Random random = new Random(); // For random.nextDouble(bound)

    public static void enchant(
            ItemStack stack,
            Level level,
            float diff,
            float factor,
            RandomSource random
    )
    {
        List<EnchantmentInstance> insts = shuffleEnchantments(stack, level, diff, factor, random);
        stack.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        insts.forEach(i -> stack.enchant(i.enchantment, i.level));
    }

    public static Holder<MobEffect> findEffect(Level level, ResourceLocation effect)
    {
        Registry<MobEffect> effects = level.registryAccess().registryOrThrow(Registries.MOB_EFFECT);
        return effects.getHolder(effect).orElseThrow();
    }

    public static EquipmentSlot forSlot(
            ItemStack stack
    )
    {
        if (stack.getItem() instanceof ArmorItem item)
            return item.getEquipmentSlot();
        else if (stack.getItem() instanceof ShieldItem item)
            return item.getEquipmentSlot();
        else
            return EquipmentSlot.MAINHAND;
    }

    public static void loadFromConfig()
    {
        AwakenCommon.CONFIG.EFFECTS.get()
                .stream()
                .map(
                        EquipmentManager::loadFromString
                ).forEach(
                        EFFECTS::add
                );
    }

    public static Tuple<ResourceLocation, Tuple<Integer, Integer>> loadFromString(
            String raw
    )
    {
        String[] components = raw.strip().split("\\|");
        if (components.length != 3)
            throw new IllegalArgumentException("Illegal config: required 3 parts");

        String effect = components[0];
        String duration = components[1];
        String max_level = components[2];

        return new Tuple<>(ResourceLocation.parse(effect), new Tuple<>(Integer.parseInt(duration), Integer.parseInt(max_level)));
    }

    public static boolean shouldShuffleEpoch()
    {
        return random.nextDouble(100) < AwakenCommon.CONFIG.EPOCH_RARITY.get();
    }

    public static int shuffleEffectCount(
            float diff,
            float factor,
            RandomSource random
    )
    {
        float n = (float) Math.sqrt(Math.sqrt(diff)) * random.nextInt(Math.max((int) factor, 1)) / factor;
        return Math.min((int) (n * EFFECTS.size()), EFFECTS.size());
    }

    public static int shuffleEffectLevel(
            Tuple<ResourceLocation, Tuple<Integer, Integer>> effect,
            float diff,
            float factor,
            RandomSource random
    )
    {
        float n = (float) Math.sqrt(Math.sqrt(diff)) * random.nextInt(Math.max((int) factor, 1)) / factor;
        return Math.min((int) (n * effect.getB().getB()) - 1, effect.getB().getB()); // MC Effect levels starts at 0
    }

    public static List<MobEffectInstance> shuffleEffects(
            Level level,
            float diff,
            float factor,
            RandomSource random
    )
    {
        if (EFFECTS.isEmpty())
            return List.of();

        int count = shuffleEffectCount(diff, factor, random);
        List<Holder<MobEffect>> exist = new ArrayList<>();
        List<MobEffectInstance> insts = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            Tuple<ResourceLocation, Tuple<Integer, Integer>> effect = EFFECTS.get(random.nextInt(EFFECTS.size()));
            Holder<MobEffect> eff = findEffect(level, effect.getA());

            if (exist.contains(eff))
                continue;

            exist.add(eff);
            int lvl = shuffleEffectLevel(effect, diff, factor, random);
            insts.add(new MobEffectInstance(eff, effect.getB().getA(), lvl));
        }

        return insts;
    }

    public static int shuffleEnchantmentCount(
            int max,
            float diff,
            float factor,
            RandomSource random
    )
    {
        float n = (float) Math.sqrt(Math.sqrt(diff)) * (random.nextInt(Math.max((int) factor, 1)) / factor);
        return Math.min((int) (n * max), max);
    }

    public static int shuffleEnchantmentLevel(
            int max,
            float diff,
            float factor,
            RandomSource random
    )
    {
        float n = (float) Math.sqrt(Math.sqrt(diff)) * (random.nextInt(Math.max((int) factor, 1)) / factor);
        return Math.min((int) (n * max) / 2, max); // RESTRICTED: LOWER LEVEL FOR BALANCE
    }

    public static List<EnchantmentInstance> shuffleEnchantments(
            ItemStack stack,
            Level level,
            float diff,
            float factor,
            RandomSource random
    )
    {
        Registry<Enchantment> registry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        List<Holder<Enchantment>> candidates = new ArrayList<>();
        for (Holder<Enchantment> holder: registry.holders().toList())
        {
            Enchantment enchant = holder.value();

            if (enchant.isSupportedItem(stack) && !holder.is(EnchantmentTags.CURSE))
                candidates.add(holder);
        }

        if (candidates.isEmpty())
            return List.of();

        int count = shuffleEnchantmentCount(candidates.size(), diff, factor, random);
        List<EnchantmentInstance> result = new ArrayList<>();
        List<Enchantment> selected = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            Holder<Enchantment> enchantment = candidates.get(random.nextInt(candidates.size()));
            if (selected.contains(enchantment.value()))
                continue;

            selected.add(enchantment.value());
            int lvl = shuffleEnchantmentLevel(enchantment.value().getMaxLevel(), diff, factor, random);
            result.add(new EnchantmentInstance(enchantment, lvl));
        }

        return result;
    }

    public static Records.AwakenEpochComponent shuffleEpoch(
            float diff,
            float factor,
            RandomSource random
    )
    {
        float minDiff = (float) (random.nextFloat() % (factor * Math.sqrt(diff / 2)));
        double maxLevel = AwakenRegistries.AWAKEN_LEVEL.getMaxLevel();
        if (maxLevel <= 0)
            return new Records.AwakenEpochComponent(minDiff, 0.0F);
        double minLevel = (float) (random.nextDouble() % (factor * Math.sqrt(maxLevel)));
        return new Records.AwakenEpochComponent(minLevel, minDiff);
    }

    public static void shuffleForItemStack(
            Level level,
            ItemStack stack,
            EquipmentSlot slot,
            float diff,
            float factor,
            RandomSource random
    )
    {
        float d = diff * factor;
        enchant(stack, level, diff, factor, random);
        if (shouldShuffleEpoch())
        {
            Records.AwakenEpochComponent epoch = shuffleEpoch(diff, factor, random);
            NBTUtil.serializeEpoch(stack, epoch);
        }

        AwakenQuality quality = ShuffledRegistries.WEIGHTED_AWAKEN_QUALITY.calculate(d, random);

        AwakenInfix infix = ShuffledRegistries.WEIGHTED_AWAKEN_INFIX.calculate(slot, d, random);
        AwakenPrefix prefix = ShuffledRegistries.WEIGHTED_AWAKEN_PREFIX.calculate(d, random);
        AwakenSuffix suffix = ShuffledRegistries.WEIGHTED_AWAKEN_SUFFIX.calculate(d, random);

        if (Util.ifNull(quality, infix, prefix, suffix))
            return;

        NBTUtil.serializeDescriber(
                stack,
                infix,
                prefix,
                suffix
        );

        NBTUtil.serializeQuality(
                stack,
                quality
        );
    }

    public static ItemStack shuffleItemStack(
            EquipmentSlot slot,
            float diff,
            float factor,
            RandomSource random
    )
    {
        float d = diff * factor;

        return ShuffledRegistries.WEIGHTED_AWAKEN_STACK.calculate(slot, d, random);
    }

    public static int shuffleSlotCount(
            float diff,
            float factor,
            RandomSource random
    )
    {
        int n = random.nextInt(Math.max((int) diff, 1)) + (int) factor;
        return Math.clamp(n, 1, 7);
    }

    public static EquipmentSlot[] shuffleSlots(
            float diff,
            float factor,
            RandomSource random
    )
    {
        int slots = shuffleSlotCount(
                diff,
                factor,
                random
        );

        List<EquipmentSlot> selected = new ArrayList<>();
        while (selected.size() != slots)
        {
            EquipmentSlot slot = EquipmentSlot.values()[random.nextInt(EquipmentSlot.values().length)];
            if (selected.contains(slot))
                continue;

            selected.add(slot);
        }

        return selected.toArray(
                new EquipmentSlot[]{}
        );
    }
}