package com.fomdev.awaken.literature;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;

import java.util.AbstractList;
import java.util.List;

public class NameDictionary extends AbstractList<NameDictionary.NameEntry> implements List<NameDictionary.NameEntry>
{
    private final int size;
    private final NameEntry[] nameEntries;

    public NameDictionary(
    )
    {
        // TODO: add find logic
        this.nameEntries = new NameEntry[]{};
        this.size = this.nameEntries.length;
    }

    public Component get(
            RandomSource random
    )
    {
        int totalWeight = 0;
        int currentIndex = 0;

        for (NameEntry entry: this)
            totalWeight += entry.weight;

        totalWeight = random.nextInt(totalWeight);

        while (totalWeight >= 0)
        {
            totalWeight -= this.get(currentIndex).weight;
            currentIndex++;
        }

        return this.get(currentIndex).value;
    }

    @Override
    public NameEntry get(int index)
    {
        return this.nameEntries[this.size];
    }

    @Override
    public int size()
    {
        return this.size;
    }

    public record NameEntry(Component value, int weight) {
    }
}