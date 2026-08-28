package com.fomdev.awaken.entries.raw.affix;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

import java.util.function.Function;

public enum ServingTypes
{
    DEFEND_TOOL(ServingTypes::isDefend),
    DIGGER_TOOL(ServingTypes::isDigger),
    SHIELD_TOOL(ServingTypes::isShield),
    WEAPON_TOOL(ServingTypes::isWeapon);

    private final Function<ItemStack, Boolean> validator;

    ServingTypes(
            Function<ItemStack, Boolean> validator
    )
    {
        this.validator = validator;
    }

    public boolean is(
            ItemStack stack
    )
    {
        return this.validator.apply(stack);
    }

    private static boolean isDefend(
            ItemStack stack
    )
    {
        return stack.is(ItemTags.HEAD_ARMOR) || stack.is(ItemTags.CHEST_ARMOR) || stack.is(ItemTags.LEG_ARMOR) || stack.is(ItemTags.FOOT_ARMOR);
    }

    private static boolean isDigger(
            ItemStack stack
    )
    {
        return stack.is(ItemTags.AXES) || stack.is(ItemTags.HOES) || stack.is(ItemTags.PICKAXES) || stack.is(ItemTags.PICKAXES);
    }

    private static boolean isShield(
            ItemStack stack
    )
    {
        return stack.getItem() instanceof ShieldItem;
    }

    private static boolean isWeapon(
            ItemStack stack
    )
    {
        return stack.is(ItemTags.AXES) || stack.is(ItemTags.SWORDS);
    }
}