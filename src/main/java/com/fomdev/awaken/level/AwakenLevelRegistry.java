package com.fomdev.awaken.level;

import com.fomdev.awaken.entries.raw.AwakenLevel;
import com.fomdev.flame.register.FreezingRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AwakenLevelRegistry extends FreezingRegistry<AwakenLevel>
{
    public AwakenLevelRegistry(ResourceKey<Registry<AwakenLevel>> key)
    {
        super(key);
    }

    private Map<ResourceLocation, AwakenLevel> frozenMap = null;
    private final List<AwakenLevel> sortedFrozenCache = new ArrayList<>();

    @Override
    protected void onRegister(
            FMLCommonSetupEvent event
    )
    {
        super.onRegister(event);
        concludeSortedData();
    }

    public AwakenLevel getLevel(BigDecimal level)
    {
        if (!frozen || frozenMap == null)
            throw new IllegalStateException("Current state: registration. You have no access to this method until the registration freeze take place");

        if (sortedFrozenCache.isEmpty())
            return null;

        for (int i = 0; i < sortedFrozenCache.size(); i++)
        {
            if (i == sortedFrozenCache.size() - 1)
                return sortedFrozenCache.get(i);

            AwakenLevel curr = sortedFrozenCache.get(i);
            AwakenLevel next = sortedFrozenCache.get(i + 1);
            if (curr.getMin().compareTo(level) < 0 && level.compareTo(next.getMin()) < 0)
                return curr;
        }

        return null;
    }

    public int getLevel(AwakenLevel level)
    {
        return sortedFrozenCache.indexOf(level);
    }

    public BigDecimal getMaxLevel()
    {
        if (sortedFrozenCache.isEmpty())
            return new BigDecimal("0.0");

        return sortedFrozenCache.getLast().getMin();
    }

    public AwakenLevel getNextLevel(AwakenLevel level)
    {
        int index = sortedFrozenCache.indexOf(level);
        if (index < 0)
            return null;

        if (index == sortedFrozenCache.size() - 1)
            return null;

        try
        {
            return sortedFrozenCache.get(index + 1);
        } catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private void concludeSortedData()
    {
        frozeMap();
        if (!frozen || frozenMap == null)
            throw new IllegalStateException("Currently, registration HAS NOT finished");

        for (AwakenLevel level: frozenMap.values())
        {
            if (sortedFrozenCache.isEmpty())
            {
                sortedFrozenCache.add(level);
                continue;
            }

            BigDecimal value = level.getMin();
            int min = 0;
            int max = sortedFrozenCache.size() - 1;
            int cord = 0;

            boolean found = false;
            while (!found)
            {
                int centerCoordinates = min + (max - min) / 2; // Center coordinates

                if (centerCoordinates == 0 && value.compareTo(sortedFrozenCache.getFirst().getMin()) < 0)
                {
                    // cord is defaulted to 0
                    found = true;
                    continue;
                }

                if (centerCoordinates == sortedFrozenCache.size() - 1 && sortedFrozenCache.getLast().getMin().compareTo(value) < 0)
                {
                    cord = sortedFrozenCache.size() - 1;
                    found = true;
                    continue;
                }

                BigDecimal curr = sortedFrozenCache.get(centerCoordinates).getMin();
                BigDecimal last = centerCoordinates - 1 <= 0? new BigDecimal("0.0"): sortedFrozenCache.get(centerCoordinates - 1).getMin();

                if (last.compareTo(value) <= 0 && value.compareTo(curr) <= 0)
                {
                    // Perfect situation!
                    cord = centerCoordinates;
                    found = true;
                }
                else if (value.compareTo(curr) < 0 && value.compareTo(last) < 0)
                {
                    // The coordinates are TOO big!
                    max = centerCoordinates - 1; // Makes sure size isn't 0
                }
                else if (curr.compareTo(value) < 0 && last.compareTo(value) < 0)
                {
                    // The coordinates are TOO small!
                    min = centerCoordinates + 1; // Makes sure size isn't 0
                }
            }

            sortedFrozenCache.add(cord, level);
        }
    }

    private void frozeMap()
    {
        frozenMap = new HashMap<>(super.registryTable);
    }
}