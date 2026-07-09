package com.fomdev.awaken.enchant;

import com.fomdev.awaken.entries.AwakenAspect;
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
    public static final List<Color> colors;

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

    static
    {
        colors = List.of(
                Color.BLACK,
                Color.DARK_GRAY,
                Color.GRAY,
                Color.LIGHT_GRAY,
                Color.WHITE,
                new Color(0xBF1718),
                new Color(0xE7C818),
                Color.ORANGE,
                Color.YELLOW,
                new Color(0xE7FF00),
                new Color(0x99E718),
                new Color(0x49E718),
                Color.GREEN,
                new Color(0x00FF35),
                new Color(0x00EFFF),
                Color.CYAN,
                Color.BLUE,
                new Color(0x4100FF),
                new Color(0x8A00FF),
                Color.MAGENTA
        );
    }
}