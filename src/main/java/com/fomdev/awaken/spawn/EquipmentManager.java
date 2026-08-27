package com.fomdev.awaken.spawn;

import com.fomdev.awaken.compat.IronSpellCompat;
import com.fomdev.awaken.enchant.EnchantManager;
import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.entries.raw.affix.AwakenInfix;
import com.fomdev.awaken.entries.raw.affix.AwakenPrefix;
import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
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
import net.minecraft.network.chat.Component;
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

import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
            BigDecimal diff,
            float factor,
            RandomSource random
    )
    {
        List<EnchantmentInstance> insts = shuffleEnchantments(stack, level, diff, factor, random);
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        insts.forEach(i -> mutable.set(i.enchantment, i.level));
        stack.set(DataComponents.ENCHANTMENTS, mutable.toImmutable());
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

    public static int shuffleAffixCount(
            int max,
            float factor,
            RandomSource random
    )
    {
        return (int) Math.clamp(max * (random.nextInt(max) / (float) max) * factor, 1, max);
    }

    public static int shuffleAffixLevel(
            float factor,
            RandomSource random
    )
    {
        float factor1 = (float) Math.pow(AwakenCommon.CONFIG.GENERATABLE_MAX.get(), 1.0 / 4.0);
        float factor2 = factor1 * (float) Math.pow(factor, 1.0 / 5.0);
        int result1 = (int) factor2;
        int result2 = Math.max(result1, 1);
        return random.nextInt(result2) + 1;
    }

    public static int shuffleAffixMaxCount(
            int count,
            int max,
            float factor,
            RandomSource random
    )
    {
        int maxCount = (int) (count * factor);
        maxCount = random.nextInt(maxCount);
        return Math.clamp(maxCount, count, max);
    }

    public static AwakenInfix.InfixContainer shuffleAffix$Infix(
            float diff,
            float factor,
            EquipmentSlot slot,
            RandomSource random
    )
    {
        int max = ShuffledRegistries.WEIGHTED_AWAKEN_INFIX.size(slot);
        int count = shuffleAffixCount(max, factor, random);
        int maxCount = shuffleAffixMaxCount(count, max, factor, random);
        AwakenInfix.InfixContainer container = new AwakenInfix.InfixContainer(maxCount);

        for (int i = 0; i < count; i++)
        {
            AwakenInfix infix = ShuffledRegistries.WEIGHTED_AWAKEN_INFIX.calculate(slot, diff, random);
            int level = shuffleAffixLevel(factor, random);
            container.merge(new AwakenInfix.InfixInstance(infix, level));
        }

        return container;
    }

    public static AwakenPrefix.PrefixInstance shuffleAffix$Prefix(
            float diff,
            float factor,
            RandomSource random
    )
    {
        AwakenPrefix prefix = ShuffledRegistries.WEIGHTED_AWAKEN_PREFIX.calculate(diff, random);
        int level = shuffleAffixLevel(factor, random);
        return new AwakenPrefix.PrefixInstance(prefix, level);
    }

    public static AwakenAspect.AspectInstance shuffleAspect(
            RandomSource random
    )
    {
        List<AwakenAspect> aspects = AwakenRegistries.AWAKEN_ASPECT.getRegistries();
        if (aspects.isEmpty())
            return new AwakenAspect.AspectInstance(AwakenAspect.NONE, -1); // Usually, this won't happen, just to make sure some d*****s won't inject my mod

        AwakenAspect aspect = aspects.get(random.nextInt(aspects.size()));
        return aspect.toInstance(random.nextInt(250) + 1);
    }

    public static int shuffleEffectCount(
            BigDecimal diff,
            float factor,
            RandomSource random
    )
    {
        BigDecimal n = diff.sqrt(new MathContext(2)).sqrt(new MathContext(2)).multiply(new BigDecimal(random.nextInt(Math.max((int) factor, 1)) / factor));
        return n.multiply(new BigDecimal(EFFECTS.size())).min(new BigDecimal(EFFECTS.size())).intValue();
    }

    public static int shuffleEffectLevel(
            Tuple<ResourceLocation, Tuple<Integer, Integer>> effect,
            BigDecimal diff,
            float factor,
            RandomSource random
    )
    {
        BigDecimal n = diff.sqrt(new MathContext(2)).sqrt(new MathContext(2)).multiply(new BigDecimal(random.nextInt(Math.max((int) factor, 1)) / factor));
        return n.multiply(new BigDecimal(effect.getB().getB() - 1)).min(new BigDecimal(effect.getB().getB())).intValue(); // MC Effect levels starts at 0
    }

    public static List<MobEffectInstance> shuffleEffects(
            Level level,
            BigDecimal diff,
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
            BigDecimal diff,
            float factor,
            RandomSource random
    )
    {
        BigDecimal n = diff
                .sqrt(new MathContext(2))
                .sqrt(new MathContext(2))
                .sqrt(new MathContext(2))
                .sqrt(new MathContext(2))
                .multiply(new BigDecimal(max / factor * random.nextInt(Math.max((int) factor, 1))));
        return n.min(new BigDecimal(max)).intValue();
    }

    public static int shuffleEnchantmentLevel(
            int max,
            BigDecimal diff,
            float factor,
            RandomSource random
    )
    {
        BigDecimal n = diff
                .sqrt(new MathContext(2))
                .sqrt(new MathContext(2))
                .sqrt(new MathContext(2))
                .multiply(new BigDecimal(max / factor * random.nextInt(Math.max((int) factor, 1))));
        return n.divide(new BigDecimal(5), RoundingMode.HALF_UP)
                .min(new BigDecimal(max))
                .intValue(); // RESTRICTED: LOWER LEVEL FOR BALANCE
    }

    public static List<EnchantmentInstance> shuffleEnchantments(
            ItemStack stack,
            Level level,
            BigDecimal diff,
            float factor,
            RandomSource random
    )
    {
        Registry<Enchantment> registry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        List<Holder<Enchantment>> candidates = new ArrayList<>();
        for (Holder<Enchantment> holder: registry.holders().toList())
            if (EnchantManager.isPrimaryItemFor(stack, holder) && !holder.is(EnchantmentTags.CURSE))
                candidates.add(holder);

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
            if (lvl <= 0)
                continue;

            result.add(new EnchantmentInstance(enchantment, lvl));
        }

        return result;
    }

    public static Records.AwakenEpochComponent shuffleEpoch(
            BigDecimal diff,
            float factor,
            RandomSource random
    )
    {
        BigDecimal factor1 = diff.max(new BigDecimal("1"));
        BigDecimal factor2 = new BigDecimal(random.nextInt()).remainder(factor1.multiply(new BigDecimal("2")));
        BigDecimal result1 = factor2.multiply(new BigDecimal(factor)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal maxLevel = AwakenRegistries.AWAKEN_LEVEL.getMaxLevel();
        if (maxLevel.compareTo(new BigDecimal("0")) <= 0)
            return new Records.AwakenEpochComponent(new BigDecimal("0.0"), result1.abs());

        BigDecimal factor3 = new BigDecimal(random.nextInt()).remainder(maxLevel);
        BigDecimal factor4 = factor3.multiply(new BigDecimal(Math.sqrt(factor)));
        BigDecimal result2 = factor4.setScale(2, RoundingMode.HALF_UP);
        return new Records.AwakenEpochComponent(result2.abs(), result1.abs());
    }

    public static void shuffleForItemStack(
            Level level,
            ItemStack stack,
            EquipmentSlot slot,
            BigDecimal diff,
            float factor,
            Color color,
            RandomSource random
    )
    {
        stack.set(AwakenDataComponents.AWAKEN_SLOT_STORAGE, slot);

        Component component = stack.get(DataComponents.CUSTOM_NAME);
        if (component == null)
        {
            Component component1 = stack.get(DataComponents.ITEM_NAME);
            component = component1 != null? component1 : stack.getItem().getName(stack);
        }

        stack.set(DataComponents.ITEM_NAME, Component.empty().append(component).withColor(color.getRGB()));
        IronSpellCompat.forStackIfPresent(stack, random);

        BigDecimal d = diff.multiply(new BigDecimal(factor));
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

        List<AwakenMoods> moods = AwakenRegistries.AWAKEN_MOOD.getRegistries();
        if (!moods.isEmpty() && random.nextInt(100) < 50) // 50% chance of being annoying!
        {
            AwakenMoods mood = moods.get(random.nextInt(moods.size()));
            NBTUtil.serializeMood(stack, mood);
        }

        AwakenQuality quality = ShuffledRegistries.WEIGHTED_AWAKEN_QUALITY.calculate(d.floatValue(), random);
        AwakenInfix.InfixContainer infix = shuffleAffix$Infix(d.floatValue(), factor, slot, random);
        AwakenPrefix.PrefixInstance prefix = shuffleAffix$Prefix(d.floatValue(), factor, random);
        AwakenSuffix suffix = ShuffledRegistries.WEIGHTED_AWAKEN_SUFFIX.calculate(d.floatValue(), random);

        if (Util.ifNull(quality, prefix, suffix))
            return;


        NBTUtil.serializeDescriber(
                stack,
                infix,
                prefix,
                new AwakenSuffix.SuffixInstance(suffix, shuffleAffixLevel(factor, random))
        );

        NBTUtil.serializeQuality(
                stack,
                quality
        );
    }

    public static ItemStack shuffleItemStack(
            EquipmentSlot slot,
            BigDecimal diff,
            float factor,
            RandomSource random
    )
    {
        BigDecimal d = diff.multiply(new BigDecimal(factor));

        return ShuffledRegistries.WEIGHTED_AWAKEN_STACK.calculate(slot, d.floatValue(), random);
    }

    public static int shuffleSlotCount(
            BigDecimal diff,
            float factor,
            RandomSource random
    )
    {
        int n = random.nextInt(diff.max(new BigDecimal("1")).intValue()) + (int) factor;
        return Math.clamp(n, 1, 7);
    }

    public static EquipmentSlot[] shuffleSlots(
            BigDecimal diff,
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