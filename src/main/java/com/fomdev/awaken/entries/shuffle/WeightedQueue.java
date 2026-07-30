package com.fomdev.awaken.entries.shuffle;

import com.fomdev.flame.register.FreezingRegistry;
import com.fomdev.flame.register.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;

import java.util.ArrayDeque;
import java.util.Queue;

public class WeightedQueue<T extends Registry> extends FreezingRegistry<T>
{
    private final Queue<WeightedRegistry<T>> queue;
    private float totalWeight;

    public WeightedQueue(
            ResourceKey<net.minecraft.core.Registry<T>> key
    )
    {
        super(key);
        this.queue = new ArrayDeque<>();
    }

    public void push(
            T entry,
            float chance,
            float minDiff
    )
    {
        queue.add(new WeightedRegistry<>(chance, minDiff, entry));
        totalWeight += chance;
    }

    public void refresh(
            float diff
    )
    {
        totalWeight = 0;

        for (WeightedRegistry<T> registry: queue)
            if (registry.minDiff <= diff)
                totalWeight += registry.weight;
    }

    public T calculate(
            float diff,
            RandomSource random
    )
    {
        refresh(diff);
        float weight = random.nextFloat() * totalWeight;
        Queue<WeightedRegistry<T>> newQueue = new ArrayDeque<>(queue);
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