package com.fomdev.awaken.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.stream.Stream;

public class Util
{
    public static List<ItemStack> getStacks(
            Player player
    )
    {
        ItemStack main = player.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack off = player.getItemBySlot(EquipmentSlot.OFFHAND);
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = player.getItemBySlot(EquipmentSlot.FEET);

        return Stream.of(main, off, helmet, chest, legs, feet).filter(s -> !s.is(Items.AIR)).toList();
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