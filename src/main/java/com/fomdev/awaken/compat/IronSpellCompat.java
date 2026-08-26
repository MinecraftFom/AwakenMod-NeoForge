package com.fomdev.awaken.compat;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.ISpellContainerMutable;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.List;

public class IronSpellCompat
{
    // Wrapper
    public static ItemStack shuffleScrollIfPresent(
            RandomSource random
    )
    {
        return ModList.get().isLoaded("irons_spellbooks")? shuffleScroll(random): ItemStack.EMPTY;
    }

    public static ItemStack shuffleScroll(
            RandomSource random
    )
    {
        AbstractSpell spell = shuffleSpell(random);
        if (spell == null)
            return ItemStack.EMPTY;

        ItemStack stack = ItemRegistry.SCROLL.get().getDefaultInstance();
        castInitialSpell(stack, spell, random.nextInt(spell.getMaxLevel()) + 1);
        return stack;
    }

    public static AbstractSpell shuffleSpell(
            RandomSource random
    )
    {
        List<AbstractSpell> spells = SpellRegistry.getEnabledSpells();
        if (spells.isEmpty())
            return null;

        return spells.get(random.nextInt(spells.size()));
    }

    public static void castInitialSpell(
            ItemStack stack,
            AbstractSpell spell,
            int level
    )
    {
        ISpellContainerMutable mutable = ISpellContainer.getOrCreate(stack).mutableCopy();
        mutable.addSpell(spell, level, false);
        ISpellContainer.set(stack, mutable.toImmutable());
    }

    public static void forStack(
            ItemStack stack,
            RandomSource random
    )
    {
        AbstractSpell spell = shuffleSpell(random);
        if (spell == null)
            return;

        int level = random.nextInt(spell.getMaxLevel()) + 1;
        castInitialSpell(stack, spell, level);
    }

    // Wrapper
    public static void forStackIfPresent(
            ItemStack stack,
            RandomSource random
    )
    {
        if (ModList.get().isLoaded("irons_spellbooks"))
            forStack(stack, random);
    }
}