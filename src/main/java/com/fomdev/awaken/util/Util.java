package com.fomdev.awaken.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentUser;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Util
{
    public static BigDecimal clamp(
            BigDecimal value,
            BigDecimal min,
            BigDecimal max
    )
    {
        return value.max(min).max(max);
    }

    public static List<ItemStack> getStacks(
            EquipmentUser user
    )
    {
        return getStacks(Constants.ALL_SLOTS, user);
    }

    public static List<ItemStack> getStacks(
            EquipmentSlot[] slots,
            EquipmentUser entity
    )
    {
        List<ItemStack> stacks = new ArrayList<>();

        for (EquipmentSlot slot: slots)
            stacks.add(entity.getItemBySlot(slot));

        return stacks.stream().filter(s -> !s.is(Items.AIR)).toList();
    }

    public static String format(
            String str,
            Object... args
    )
    {
        String value = str;

        for (Object argv: args)
            value = value.replaceFirst("\\{}", argv.toString());

        return value;
    }

    public static boolean ifNull(Object... args)
    {
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }

    /* A function to solve the problem that idea warns unused parameters */
    public static void placeholder(Object... trash) {}
}