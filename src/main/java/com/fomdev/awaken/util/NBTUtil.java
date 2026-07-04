package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.*;
import com.fomdev.awaken.register.AwakenDataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class NBTUtil
{
    public static AwakenInfix deserializeInfix(
            ItemStack stack
    )
    {
        Records.AwakenDescriberComponent desc = deserializeDescriber(stack);
        if (desc == null)
            return null;

        if (desc.infix() == null)
            return null;

        return AwakenRegistries.AWAKEN_INFIX.getRegistry(ResourceLocation.parse(desc.infix()));
    }

    public static List<AwakenPollinate.PollinateInstance> deserializePollinates(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return List.of();

        Records.AwakenPollinateComponent component = stack.get(AwakenDataComponents.AWAKEN_POLLINATE_STORAGE.get());
        if (component == null)
            return List.of();

        if (component.data().isEmpty())
            return List.of();

        List<AwakenPollinate.PollinateInstance> pollinates = new ArrayList<>();

        for (CompoundTag tag: component.data())
        {
            if (!tag.contains("id") || !tag.contains("level"))
                continue;

            AwakenPollinate pollinate = AwakenRegistries.AWAKEN_POLLINATE.getRegistry(ResourceLocation.parse(tag.getString("id")));
            int level = tag.getInt("level");

            if (pollinate == null || level <= 0)
                continue;

            pollinates.add(new AwakenPollinate.PollinateInstance(pollinate, level));
        }

        return pollinates;
    }

    public static AwakenPrefix deserializePrefix(
            ItemStack stack
    )
    {
        Records.AwakenDescriberComponent desc = deserializeDescriber(stack);
        if (desc == null)
            return null;

        if (desc.prefix() == null)
            return null;

        return AwakenRegistries.AWAKEN_PREFIX.getRegistry(ResourceLocation.parse(desc.prefix()));
    }

    public static AwakenSuffix deserializeSuffix(
            ItemStack stack
    )
    {
        Records.AwakenDescriberComponent desc = deserializeDescriber(stack);
        if (desc == null)
            return null;

        if (desc.suffix() == null)
            return null;

        return AwakenRegistries.AWAKEN_SUFFIX.getRegistry(ResourceLocation.parse(desc.suffix()));
    }

    public static AwakenQuality deserializeQuality(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return null;

        Records.AwakenQualityComponent component = stack.get(AwakenDataComponents.AWAKEN_QUALITY_STORAGE.get());
        if (component == null)
            return null;

        if (component.key() == null)
            return null;

        return AwakenRegistries.AWAKEN_QUALITY.getRegistry(ResourceLocation.parse(component.key()));
    }

    private static Records.AwakenDescriberComponent deserializeDescriber(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return null;

        Records.AwakenDescriberComponent component = stack.get(AwakenDataComponents.AWAKEN_DESCRIBER_STORAGE.get());
        if (component == null)
            stack.set(AwakenDataComponents.AWAKEN_DESCRIBER_STORAGE.get(), new Records.AwakenDescriberComponent(null, null, null));

        component = stack.get(AwakenDataComponents.AWAKEN_DESCRIBER_STORAGE.get());
        return component;
    }
}