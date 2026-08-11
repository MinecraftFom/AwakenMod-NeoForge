package com.fomdev.awaken.entries.shuffle;

import com.fomdev.flame.register.FreezingRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.*;

public class EquippedQueue<T> extends FreezingRegistry<WeightedRegistry<T>>
{
    private final Map<EquipmentSlot, Queue<WeightedRegistry<T>>> registry = new HashMap<>();
    private final Map<EquipmentSlot, Float> totalWeights = new HashMap<>();

    public EquippedQueue(ResourceKey<Registry<WeightedRegistry<T>>> key)
    {
        super(key);
    }

    public void push(
            T stack,
            EquipmentSlot slot,
            float chance,
            float minDiff
    )
    {
        registry.computeIfAbsent(
                slot,
                s -> new ArrayDeque<>()
        ).add(
                new WeightedRegistry<>(
                        chance,
                        minDiff,
                        stack
                )
        );
    }

    public void refresh(
            float diff
    )
    {
        totalWeights.clear();

        for (Map.Entry<EquipmentSlot, Queue<WeightedRegistry<T>>> queue: registry.entrySet())
            for (WeightedRegistry<T> registry: queue.getValue())
                if (registry.minDiff < diff)
                    totalWeights.put(queue.getKey(), totalWeights.getOrDefault(queue.getKey(), 0.0F) + registry.weight);

    }

    public T calculate(
            EquipmentSlot slot,
            float diff,
            RandomSource random
    )
    {
        refresh(diff);
        if (!registry.containsKey(slot) || !totalWeights.containsKey(slot))
            return null;

        float weight = random.nextFloat() * totalWeights.get(slot);
        Queue<WeightedRegistry<T>> newQueue = new ArrayDeque<>(registry.get(slot));
        WeightedRegistry<T> current = null;

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
}