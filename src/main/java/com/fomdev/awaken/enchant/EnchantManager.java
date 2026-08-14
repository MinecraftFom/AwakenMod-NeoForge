package com.fomdev.awaken.enchant;

import com.fomdev.awaken.entries.raw.AwakenAspect;
import com.fomdev.awaken.init.config.AwakenCommon;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class EnchantManager
{
    public static final Map<Holder<Enchantment>, List<AwakenAspect.AspectInstance>> aspects = new HashMap<>();

    public static final Codec<Integer> LEVEL_CODEC = Codec.intRange(0, Integer.MAX_VALUE);
    public static final Codec<Object2IntOpenHashMap<Holder<Enchantment>>> LEVELS_CODEC = Codec.unboundedMap(Enchantment.CODEC, LEVEL_CODEC).xmap(Object2IntOpenHashMap::new, Function.identity());
    public static final Codec<ItemEnchantments> FULL_CODEC = RecordCodecBuilder.create((p_337961_) -> p_337961_.group(LEVELS_CODEC.fieldOf("levels").forGetter(EnchantManager::getEnchantments), Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(EnchantManager::getShouldTooltip)).apply(p_337961_, EnchantManager::initItemEnchantments));
    public static final Codec<ItemEnchantments> CODEC = Codec.withAlternative(FULL_CODEC, LEVELS_CODEC, (p_340783_) -> EnchantManager.initItemEnchantments(p_340783_, true));;
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemEnchantments> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.map(Object2IntOpenHashMap::new, Enchantment.STREAM_CODEC, ByteBufCodecs.VAR_INT), EnchantManager::getEnchantments, ByteBufCodecs.BOOL, EnchantManager::getShouldTooltip, EnchantManager::initItemEnchantments);

    public static int xpLevel;
    public static int maxLevel;

    public static List<EnchantmentInstance> getAvailableEnchantments(
            List<AwakenAspect.AspectInstance> available,
            int slot,
            RandomSource random
    )
    {
        List<EnchantmentInstance> enchants = new ArrayList<>();

        for (Map.Entry<Holder<Enchantment>, List<AwakenAspect.AspectInstance>> entry: aspects.entrySet())
        {
            if (!meetsRequirements(available, entry.getValue()))
                continue;

            Holder<Enchantment> enchantment = entry.getKey();
            int level = enchantment.value().getMaxLevel() - enchantment.value().getMinLevel();
            int factor = level * 3 / slot;
            int finalValue = random.nextInt(factor == 0? 1: factor);

            enchants.add(new EnchantmentInstance(enchantment, finalValue == 0? 1: finalValue));
        }

        return enchants;
    }

    public static boolean meetsRequirements(List<AwakenAspect.AspectInstance> available, List<AwakenAspect.AspectInstance> requirements)
    {
        for (AwakenAspect.AspectInstance req: requirements)
        {
            for (AwakenAspect.AspectInstance ava: available)
            {
                if (ava.aspect() != req.aspect())
                    continue;

                if (ava.amount() < req.amount())
                    return false;
            }
        }

        return true;
    }

    public static Color calculateColor(
            int level,
            int max,
            boolean isCurse
    )
    {
        if (isCurse)
            return Color.RED;

        if (max == 1)
            return Color.MAGENTA;

        double factor = (double) (level - 1)/ (double) (max - 1);
        factor = Math.clamp(factor, 0.0f, 1.0f);

        double hue = factor * 300.0f / 360.0f;

        return Color.getHSBColor((float) hue, 1.0f, 1.0f);
    }

    public static void init()
    {
        xpLevel = AwakenCommon.CONFIG.XP_PER_LEVEL.get();
        maxLevel = AwakenCommon.CONFIG.MAX_ENCHANT_LEVEL.get();
    }

    private static ItemEnchantments initItemEnchantments(
            Object2IntOpenHashMap<Holder<Enchantment>> p0,
            boolean p1
    )
    {
        try
        {
            Constructor<ItemEnchantments> constructor = ItemEnchantments.class.getDeclaredConstructor(Object2IntOpenHashMap.class, boolean.class);
            constructor.setAccessible(true);
            return constructor.newInstance(p0, p1);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static Object2IntOpenHashMap<Holder<Enchantment>> getEnchantments(
            ItemEnchantments instance
    )
    {
        try
        {
            Field field = ItemEnchantments.class.getDeclaredField("enchantments");
            field.setAccessible(true);
            return (Object2IntOpenHashMap<Holder<Enchantment>>) field.get(instance);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean getShouldTooltip(
            ItemEnchantments instance
    )
    {
        try
        {
            Field field = ItemEnchantments.class.getDeclaredField("showInTooltip");
            field.setAccessible(true);
            return (boolean) field.get(instance);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }
}