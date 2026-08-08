package com.fomdev.awaken.literature;

import com.fomdev.awaken.init.config.AwakenCommon;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractList;
import java.util.List;

public class NameDictionary extends AbstractList<NameDictionary.NameEntry> implements List<NameDictionary.NameEntry>
{
    public int weight;

    private final int size;
    private final NameEntry[] nameEntries;

    public NameDictionary()
    {
        this.nameEntries = loadFromConfig();
        this.size = this.nameEntries.length;

        for (NameEntry entry: this)
            weight += entry.weight;
    }

    public Component get(
            RandomSource random
    )
    {
        int currentIndex = 0;
        int totalWeight = random.nextInt(weight);

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

    public record NameEntry(Component value, int weight)
    {
        @Override
        public @NotNull String toString()
        {
            return value + "|" + weight;
        }
    }

    public static NameEntry[] loadFromConfig()
    {
        return AwakenCommon.CONFIG.NAMES.get()
                .stream()
                .map(
                        NameDictionary::loadFromString
                )
                .toArray(
                        NameEntry[]::new
                );
    }

    public static NameEntry loadFromString(
            String raw
    )
    {
        String[] components = raw.strip().split("\\|");
        if (components.length != 2)
            throw new IllegalArgumentException("Illegal argument structure, required 2 parts");

        String header = components[0];
        String body = components[1];

        Component title = Component.literal(header);
        Integer chance = Integer.getInteger(body);

        return new NameEntry(title, chance);
    }
}