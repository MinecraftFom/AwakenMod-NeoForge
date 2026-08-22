package com.fomdev.awaken.entries.raw;

import com.fomdev.awaken.util.Records;
import com.fomdev.flame.register.Registry;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class AwakenPrefix extends Registry
{
    private final int durability;
    private final float rank;
    private final List<Records.EnchantmentHolder> baseEnchantments;

    public AwakenPrefix(
            String id,
            int durability,
            float rankFactor,
            List<Records.EnchantmentHolder> baseEnchantments
    )
    {
        super(id);

        this.durability = durability;
        this.rank = rankFactor;
        this.baseEnchantments = List.copyOf(baseEnchantments);
    }

    public int getDurability()
    {
        return this.durability;
    }

    public float getRankFactor()
    {
        return this.rank;
    }

    public ImmutableList<Records.EnchantmentHolder> getBaseEnchantments()
    {
        return ImmutableList.copyOf(this.baseEnchantments);
    }

    public static class PrefixInstance extends AwakenPrefix
    {
        private final int level;

        public PrefixInstance(
                AwakenPrefix parent,
                int level
        )
        {
            super(parent.id(), parent.getDurability() * level, parent.getRankFactor() * (float) Math.pow(level, 1.0 / 4.0), AwakenPrefix.castEnchantments(parent.getBaseEnchantments(), level));
            this.level = level;
            setLocation(parent.getLocation());
        }

        public int getLevel()
        {
            return this.level;
        }
    }

    private static List<Records.EnchantmentHolder> castEnchantments(
            ImmutableList<Records.EnchantmentHolder> original,
            int level
    )
    {
        return ImmutableList.copyOf(
                original
                        .stream()
                        .map(inst -> new Records.EnchantmentHolder(inst.enchantment(), inst.level() * (int) Math.sqrt(level)))
                        .toList()
        );
    }
}