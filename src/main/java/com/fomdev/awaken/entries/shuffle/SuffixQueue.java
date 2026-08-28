package com.fomdev.awaken.entries.shuffle;

import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.entries.raw.affix.ServingTypes;
import com.fomdev.flame.register.FreezingRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;

public class SuffixQueue extends FreezingRegistry<WeightedRegistry<AwakenSuffix>>
{
    private final Map<ServingTypes, Queue<WeightedRegistry<AwakenSuffix>>> registry = new HashMap<>();
    private final Map<ServingTypes, Float> totalWeights = new HashMap<>();

    public SuffixQueue(ResourceKey<Registry<WeightedRegistry<AwakenSuffix>>> key)
    {
        super(key);
    }

    public void push(
            AwakenSuffix suffix,
            ServingTypes type,
            float chance,
            float minDiff
    )
    {
        registry.computeIfAbsent(
                type,
                s -> new ArrayDeque<>()
        ).add(
                new WeightedRegistry<>(
                        chance,
                        minDiff,
                        suffix
                )
        );
    }

    public void refresh(
            float diff
    )
    {
        totalWeights.clear();

        for (Map.Entry<ServingTypes, Queue<WeightedRegistry<AwakenSuffix>>> queue: registry.entrySet())
            for (WeightedRegistry<AwakenSuffix> registry: queue.getValue())
                if (registry.minDiff < diff)
                    totalWeights.put(queue.getKey(), totalWeights.getOrDefault(queue.getKey(), 0.0F) + registry.weight);

    }

    @ApiStatus.Internal
    public AwakenSuffix calculate(
            ServingTypes slot,
            float diff,
            RandomSource random
    )
    {
        refresh(diff);
        if (!registry.containsKey(slot) || !totalWeights.containsKey(slot))
            return null;

        float weight = random.nextFloat() * totalWeights.get(slot);
        Queue<WeightedRegistry<AwakenSuffix>> newQueue = new ArrayDeque<>(registry.get(slot));
        WeightedRegistry<AwakenSuffix> current = null;

        while (weight > 0)
        {
            current = newQueue.poll();
            if (current == null)
                continue;

            weight -= current.weight;
        }

        if (current == null)
            return null;

        return current.entry;
    }

    public AwakenSuffix calculate(
            ItemStack stack,
            float diff,
            RandomSource random
    )
    {
        List<ServingTypes> types = new ArrayList<>(List.of(ServingTypes.values()));
        int length = types.size();

        for (int i = 0; i < length; i++)
        {
            ServingTypes t = types.remove(random.nextInt(types.size()));
            if (t.is(stack))
                return calculate(t, diff, random);
        }

        return null;
    }

    public int size(
            ItemStack stack
    )
    {
        int value = 0;

        for (ServingTypes type: ServingTypes.values())
        {
            if (type.is(stack))
                value += this.registry.getOrDefault(type, new ArrayDeque<>()).size();
        }

        return value;
    }
}