package com.fomdev.awaken.spawn;

import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.register.data.AwakenDataComponents;
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
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        int factor1 = (int) diff;
        int factor2 = Math.max(factor1, 1);
        int factor3 = random.nextInt(factor2 * 2);
        float result1 = (int) (factor3 * factor * 100) / 100.0F;
        double maxLevel = AwakenRegistries.AWAKEN_LEVEL.getMaxLevel();
        if (maxLevel <= 0)
            return new Records.AwakenEpochComponent(0.0F, result1);

        int factor4 = (int) maxLevel;
        int factor5 = random.nextInt(factor4);
        int factor6 = (int) (factor5 * Math.sqrt(factor));
        float result2 = (int) (factor6 * 100) / 100.0F;
        return new Records.AwakenEpochComponent(result2, result1);
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
        stack.set(AwakenDataComponents.AWAKEN_SLOT_STORAGE, slot);

        float d = diff * factor;
        enchant(stack, level, diff, factor, random);
        if (shouldShuffleEpoch())
        {
            Records.AwakenEpochComponent epoch = shuffleEpoch(diff, factor, random);
            NBTUtil.serializeEpoch(stack, epoch);
        }

        if (slot.isArmor())
        {
            Holder.Reference<TrimMaterial> material = shuffleTrimMaterial(level, random);
            Holder.Reference<TrimPattern> pattern = shuffleTrimPattern(level, random);
            stack.set(DataComponents.TRIM, new ArmorTrim(material, pattern));
        }

        AwakenQuality quality = ShuffledRegistries.WEIGHTED_AWAKEN_QUALITY.calculate(d, random);

        AwakenInfix infix = ShuffledRegistries.WEIGHTED_AWAKEN_INFIX.calculate(slot, d, random);
        AwakenPrefix prefix = ShuffledRegistries.WEIGHTED_AWAKEN_PREFIX.calculate(d, random);
        AwakenSuffix suffix = ShuffledRegistries.WEIGHTED_AWAKEN_SUFFIX.calculate(d, random);

        if (Util.ifNull(quality, infix, prefix, suffix))
            return;

        float factor1 = (float) Math.pow(AwakenCommon.CONFIG.GENERATABLE_MAX.get(), 1.0 / 4.0);
        float factor2 = factor1 * (float) Math.pow(factor, 1.0 / 5.0);
        int result1 = (int) factor2;
        int result2 = Math.max(result1, 1);
        int infixLevel = random.nextInt(result2) + 1;
        int prefixLevel = random.nextInt(result2) + 1;
        int suffixLevel = random.nextInt(result2) + 1;

        NBTUtil.serializeDescriber(
                stack,
                new AwakenInfix.InfixInstance(infix, infixLevel),
                new AwakenPrefix.PrefixInstance(prefix, prefixLevel),
                new AwakenSuffix.SuffixInstance(suffix, suffixLevel)
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

    public static Holder.Reference<TrimMaterial> shuffleTrimMaterial(
            Level level,
            RandomSource random
    )
    {
        Registry<TrimMaterial> trimMaterials = level.registryAccess().registryOrThrow(Registries.TRIM_MATERIAL);
        Optional<Holder.Reference<TrimMaterial>> trim = trimMaterials.getRandom(random);
        return trim.orElseThrow();
    }

    public static Holder.Reference<TrimPattern> shuffleTrimPattern(
            Level level,
            RandomSource random
    )
    {
        Registry<TrimPattern> trimPatterns = level.registryAccess().registryOrThrow(Registries.TRIM_PATTERN);
        Optional<Holder.Reference<TrimPattern>> trim = trimPatterns.getRandom(random);
        return trim.orElseThrow();
    }
}