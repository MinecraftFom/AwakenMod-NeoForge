package com.fomdev.awaken.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentUser;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class Util
{
    public static String castTickToString(
            int tick
    )
    {
        int seconds = tick / 20;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        if (hours != 0)
        {
            int mins = minutes - hours * 60;
            int secs = seconds - minutes * 60;
            return "(" + hours + ":" + mins + ":" + secs + ")";
        }

        if (minutes != 0)
        {
            int secs = seconds - minutes * 60;
            return "(" + minutes + ":" + secs + ")";
        }

        return "(" + seconds + "s)";
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
}