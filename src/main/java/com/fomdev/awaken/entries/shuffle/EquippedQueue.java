package com.fomdev.awaken.entries.shuffle;

import com.fomdev.flame.register.FreezingRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class EquippedQueue extends FreezingRegistry<WeightedRegistry<ItemStack>>
{
    private final Map<EquipmentSlot, Queue<WeightedRegistry<ItemStack>>> registry = new HashMap<>();
    private final Map<EquipmentSlot, Float> totalWeights = new HashMap<>();

    public EquippedQueue(ResourceKey<Registry<WeightedRegistry<ItemStack>>> key)
    {
        super(key);
    }

    public void push(
            ItemStack stack,
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

        for (Map.Entry<EquipmentSlot, Queue<WeightedRegistry<ItemStack>>> queue: registry.entrySet())
            for (WeightedRegistry<ItemStack> registry: queue.getValue())
                if (registry.minDiff <= diff)
                    totalWeights.put(queue.getKey(), totalWeights.getOrDefault(queue.getKey(), 0.0F) + registry.weight);

    }

    public ItemStack calculate(
            EquipmentSlot slot,
            float diff,
            RandomSource random
    )
    {
        if (!registry.containsKey(slot))
            return null;

        refresh(diff);
        float weight = random.nextFloat() * totalWeights.get(slot);
        Queue<WeightedRegistry<ItemStack>> newQueue = new ArrayDeque<>(registry.get(slot));
        WeightedRegistry<ItemStack> current = null;

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