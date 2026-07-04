package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.*;
import com.fomdev.awaken.register.AwakenDataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NBTUtil
{
    public static List<AwakenPollinate.PollinateInstance> deserializePollinates(
            ItemStack stack
    )
    {
        CompoundTag tag = getModTag(stack);
        return deserializePollinates(tag);
    }

    private static List<AwakenPollinate.PollinateInstance> deserializePollinates(
            CompoundTag tag
    )
    {
        if (!tag.contains("pollinate"))
            return List.of();

        List<AwakenPollinate.PollinateInstance> instances = new ArrayList<>();
        ListTag list = tag.getList("pollinate", 10);
        for (Tag t: list)
        {
            if (!(t instanceof CompoundTag cpt))
                continue;

            AwakenPollinate pollinate = AwakenRegistries.AWAKEN_POLLINATE.getRegistry(ResourceLocation.parse(cpt.getString("id")));
            if (pollinate == null)
                continue;

            int level = cpt.getInt("level");
            instances.add(new AwakenPollinate.PollinateInstance(pollinate, level));
        }

        return instances;
    }

    public static AwakenPrefix deserializePrefix(
            ItemStack stack
    )
    {
        CompoundTag tag = getModTag(stack);
        return deserializePrefix(tag);
    }

    private static AwakenPrefix deserializePrefix(
            CompoundTag tag
    )
    {
        if (!tag.contains("prefix"))
            return null;

        return AwakenRegistries.AWAKEN_PREFIX.getRegistry(ResourceLocation.parse(tag.getString("prefix")));
    }

    public static AwakenQuality deserializeQuality(
            ItemStack stack
    )
    {
        CompoundTag tag = getModTag(stack);
        return deserializeQuality(tag);
    }

    private static AwakenQuality deserializeQuality(
            CompoundTag tag
    )
    {
        if (!tag.contains("quality"))
            return null;

        return AwakenRegistries.AWAKEN_QUALITY.getRegistry(ResourceLocation.parse(tag.getString("quality")));
    }

    public static AwakenSpiritual deserializeSpiritual(
            ItemStack stack
    )
    {
        CompoundTag tag = getModTag(stack);
        return deserializeSpiritual(tag);
    }

    private static AwakenSpiritual deserializeSpiritual(
            CompoundTag tag
    )
    {
        if (!tag.contains("spiritual"))
            return null;

        return AwakenRegistries.AWAKEN_SPIRIT.getRegistry(ResourceLocation.parse(tag.getString("spiritual")));
    }

    public static List<AwakenSpore.SporeInstance> deserializeSpores(
            ItemStack stack
    )
    {
        CompoundTag tag = getModTag(stack);
        return deserializeSpores(tag);
    }

    private static List<AwakenSpore.SporeInstance> deserializeSpores(
            CompoundTag tag
    )
    {
        if (!tag.contains("spore"))
            return List.of();

        List<AwakenSpore.SporeInstance> instances = new ArrayList<>();
        ListTag list = tag.getList("spore", 9);

        for (Tag t: list)
        {
            if (!(t instanceof CompoundTag cpt))
                continue;

           AwakenSpore spore = AwakenRegistries.AWAKEN_SPORE.getRegistry(ResourceLocation.parse(cpt.getString("id")));
            if (spore == null)
                continue;

            int level = cpt.getInt("level");
            instances.add(new AwakenSpore.SporeInstance(spore, level));
        }

        return instances;
    }

    public static AwakenSuffix deserializeSuffix(
            ItemStack stack
    )
    {
        CompoundTag tag = getModTag(stack);
        return deserializeSuffix(tag);
    }

    private static AwakenSuffix deserializeSuffix(
            CompoundTag tag
    )
    {
        if (!tag.contains("suffix"))
            return null;

        return AwakenRegistries.AWAKEN_SUFFIX.getRegistry(ResourceLocation.parse(tag.getString("suffix")));
    }

    public static AwakenTitle deserializeTitle(
            ItemStack stack
    )
    {
        CompoundTag tag = getModTag(stack);
        return deserializeTitle(tag);
    }

    private static AwakenTitle deserializeTitle(
            CompoundTag tag
    )
    {
        if (!tag.contains("title"))
            return null;

        return AwakenRegistries.AWAKEN_TITLE.getRegistry(ResourceLocation.parse(tag.getString("title")));
    }

    private static CompoundTag getModTag(
            ItemStack stack
    )
    {
        AwakenDataComponents.AwakenDataStorage storage = stack.get(AwakenDataComponents.AWAKEN_DATA_STORAGE.get());
        if (storage == null)
            stack.set(AwakenDataComponents.AWAKEN_DATA_STORAGE.get(), new AwakenDataComponents.AwakenDataStorage(new CompoundTag()));

        storage = stack.get(AwakenDataComponents.AWAKEN_DATA_STORAGE.get());
        if (storage == null)
            throw new IllegalStateException(
                    "Unable to initialize storage."
                    + "Stack: " + stack
            );

        return Objects.requireNonNull(storage, "Meet an unexpected error. The storage shouldn't be null").tag();
    }

    private static void serializePrefix(
            CompoundTag tag,
            AwakenPrefix prefix
    )
    {
        tag.putString("prefix", prefix.getLocation().toString());
    }

    private static void serializeQuality(
            CompoundTag tag,
            AwakenQuality quality
    )
    {
        tag.putString("quality", quality.getLocation().toString());
    }

    private static void serializeSpiritual(
            CompoundTag tag,
            AwakenSpiritual spiritual
    )
    {
        tag.putString("spiritual", spiritual.getLocation().toString());
    }

    private static void serializeSuffix(
            CompoundTag tag,
            AwakenSuffix suffix
    )
    {
        tag.putString("suffix", suffix.getLocation().toString());
    }

    private static void serializeTitle(
            CompoundTag tag,
            AwakenTitle title
    )
    {
        tag.putString("title", title.getLocation().toString());
    }
}