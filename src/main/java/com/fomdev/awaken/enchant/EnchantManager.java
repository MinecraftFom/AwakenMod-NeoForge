package com.fomdev.awaken.enchant;

import com.fomdev.awaken.entries.raw.AwakenAspect;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.awt.*;
import java.util.*;
import java.util.List;

public class EnchantManager
{
    public static final Map<Holder<Enchantment>, List<AwakenAspect.AspectInstance>> aspects = new HashMap<>();
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
        if (max == 1)
            return Color.GRAY;

        if (isCurse)
            return Color.RED;

        double factor = (double) (level - 1)/ (double) (max - 1);
        factor = Math.clamp(factor, 0.0f, 1.0f);

        double hue = factor * 300.0f / 360.0f;

        return Color.getHSBColor((float) hue, 1.0f, 1.0f);
    }

    static
    {
        maxLevel = 20;
    }
}