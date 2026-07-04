package com.fomdev.awaken.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentUser;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class Util
{
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