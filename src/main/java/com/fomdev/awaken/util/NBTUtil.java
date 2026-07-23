package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.*;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class NBTUtil
{
    public static void addAwakenLevel(
            Player player,
            float amount
    )
    {
        serializeAwakenLevel(player, deserializeAwakenLevel(player) + amount);
    }

    public static List<AwakenAspect.AspectInstance> deserializeAspects(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return List.of();

        List<CompoundTag> component = stack.get(AwakenDataComponents.AWAKEN_ASPECT_STORAGE.get());
        if (component == null)
            return List.of();

        List<AwakenAspect.AspectInstance> instances = new ArrayList<>();
        for (CompoundTag tag: component)
        {
            if (!tag.contains("id") || !tag.contains("level"))
                continue;

            AwakenAspect aspect = AwakenRegistries.AWAKEN_ASPECT.getRegistry(ResourceLocation.parse(tag.getString("id")));
            int level = tag.getInt("level");

            if (aspect == null)
                continue;

            instances.add(new AwakenAspect.AspectInstance(aspect, level));
        }

        return instances;
    }

    public static AwakenEpoch deserializeEpoch(
            ItemStack stack
    )
    {
        String component = stack.get(AwakenDataComponents.AWAKEN_EPOCH_STORAGE);
        if (component == null)
            return null;

        return AwakenRegistries.AWAKEN_EPOCH.getRegistry(ResourceLocation.parse(component));
    }

    public static float deserializeAwakenLevel(
            Player player
    )
    {
        Records.AwakenLevelComponent data = player.getExistingDataOrNull(AwakenAttachmentTypes.PLAYER_AWAKEN_LEVEL_ATTACHMENT);
        if (data == null)
            serializeAwakenLevel(player, 0.0F);

        data = player.getExistingDataOrNull(AwakenAttachmentTypes.PLAYER_AWAKEN_LEVEL_ATTACHMENT);
        assert data != null;
        return data.level();
    }

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

        List<CompoundTag> component = stack.get(AwakenDataComponents.AWAKEN_POLLINATE_STORAGE.get());
        if (component == null)
            return List.of();

        if (component.isEmpty())
            return List.of();

        List<AwakenPollinate.PollinateInstance> pollinates = new ArrayList<>();

        for (CompoundTag tag: component)
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

        String component = stack.get(AwakenDataComponents.AWAKEN_QUALITY_STORAGE.get());
        if (component == null)
            return null;

        return AwakenRegistries.AWAKEN_QUALITY.getRegistry(ResourceLocation.parse(component));
    }

    public static AwakenSpiritual deserializeSpiritual(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return null;

        String component = stack.get(AwakenDataComponents.AWAKEN_SPIRITUAL_STORAGE.get());
        if (component == null)
            return null;

        return AwakenRegistries.AWAKEN_SPIRIT.getRegistry(ResourceLocation.parse(component));
    }

    public static List<AwakenSpore.SporeInstance> deserializeSpores(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return List.of();

        List<CompoundTag> component = stack.get(AwakenDataComponents.AWAKEN_SPORE_STORAGE.get());
        if (component == null)
            return List.of();

        if (component.isEmpty())
            return List.of();

        List<AwakenSpore.SporeInstance> spores = new ArrayList<>();

        for (CompoundTag tag: component)
        {
            if (!tag.contains("id") || !tag.contains("level"))
                continue;

            AwakenSpore spore = AwakenRegistries.AWAKEN_SPORE.getRegistry(ResourceLocation.parse(tag.getString("id")));
            int level = tag.getInt("level");

            if (spore == null || level <= 0)
                continue;

            spores.add(new AwakenSpore.SporeInstance(spore, level));
        }

        return spores;
    }

    public static void serializeAwakenLevel(
            Player player,
            float level
    )
    {
        player.setData(AwakenAttachmentTypes.PLAYER_AWAKEN_LEVEL_ATTACHMENT, new Records.AwakenLevelComponent(level));
    }

    private static Records.AwakenDescriberComponent deserializeDescriber(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return null;

        return stack.get(AwakenDataComponents.AWAKEN_DESCRIBER_STORAGE.get());
    }
}