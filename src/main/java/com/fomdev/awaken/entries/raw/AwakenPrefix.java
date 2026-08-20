package com.fomdev.awaken.entries.raw;

import com.fomdev.flame.register.Registry;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.List;

public class AwakenPrefix extends Registry
{
    private final int durability;
    private final float rank;
    private final List<EnchantmentInstance> baseEnchantments;
    private final List<AwakenSpore> immunise;

    public AwakenPrefix(
            String id,
            int durability,
            float rankFactor,
            List<EnchantmentInstance> baseEnchantments,
            List<AwakenSpore> immunise
    )
    {
        super(id);

        this.durability = durability;
        this.rank = rankFactor;

        this.baseEnchantments = List.copyOf(baseEnchantments);
        this.immunise = List.copyOf(immunise);
    }

    public int getDurability()
    {
        return this.durability;
    }

    public float getRankFactor()
    {
        return this.rank;
    }

    public ImmutableList<EnchantmentInstance> getBaseEnchantments()
    {
        return ImmutableList.copyOf(this.baseEnchantments);
    }

    public ImmutableList<AwakenSpore> getImmuniseAmounts()
    {
        return ImmutableList.copyOf(this.immunise);
    }

    public static class PrefixInstance extends AwakenPrefix
    {
        private final int level;

        public PrefixInstance(
                AwakenPrefix parent,
                int level
        )
        {
            super(parent.id(), parent.getDurability() * level, parent.getRankFactor() * (float) Math.pow(level, 1.0 / 4.0), AwakenPrefix.castEnchantments(parent.getBaseEnchantments(), level), parent.getImmuniseAmounts());
            this.level = level;
            setLocation(parent.getLocation());
        }

        public int getLevel()
        {
            return this.level;
        }
    }

    private static List<EnchantmentInstance> castEnchantments(
            ImmutableList<EnchantmentInstance> original,
            int level
    )
    {
        return ImmutableList.copyOf(
                original
                        .stream()
                        .map(inst -> new EnchantmentInstance(inst.enchantment, inst.level * (int) Math.sqrt(level)))
                        .toList()
        );
    }
}