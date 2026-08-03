package com.fomdev.awaken.spawn;

import com.fomdev.awaken.entries.raw.AwakenInfix;
import com.fomdev.awaken.entries.raw.AwakenPrefix;
import com.fomdev.awaken.entries.raw.AwakenQuality;
import com.fomdev.awaken.entries.raw.AwakenSuffix;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Util;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EquipmentManager
{
    public static void shuffleForItemStack(
            ItemStack stack,
            float diff,
            float factor,
            RandomSource random
    )
    {
        float d = diff * factor;

        AwakenQuality quality = ShuffledRegistries.WEIGHTED_AWAKEN_QUALITY.calculate(d, random);

        AwakenInfix infix = ShuffledRegistries.WEIGHTED_AWAKEN_INFIX.calculate(d, random);
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
        return Math.clamp(1, n, 5);
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