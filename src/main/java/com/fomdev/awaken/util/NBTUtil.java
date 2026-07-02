package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class NBTUtil
{
    public static AwakenPrefix deserializePrefix(
            CompoundTag tag
    )
    {
        if (!tag.contains("prefix"))
            return null;

        return AwakenRegistries.AWAKEN_PREFIX.getRegistry(ResourceLocation.parse(tag.getString("prefix")));
    }

    public static AwakenQuality deserializeQuality(
            CompoundTag tag
    )
    {
        if (!tag.contains("quality"))
            return null;

        return AwakenRegistries.AWAKEN_QUALITY.getRegistry(ResourceLocation.parse(tag.getString("quality")));
    }

    public static AwakenSpiritual deserializeSpiritual(
            CompoundTag tag
    )
    {
        if (!tag.contains("spiritual"))
            return null;

        return AwakenRegistries.AWAKEN_SPIRIT.getRegistry(ResourceLocation.parse(tag.getString("spiritual")));
    }

    public static AwakenSuffix deserializeSuffix(
            CompoundTag tag
    )
    {
        if (!tag.contains("suffix"))
            return null;

        return AwakenRegistries.AWAKEN_SUFFIX.getRegistry(ResourceLocation.parse(tag.getString("suffix")));
    }

    public static AwakenTitle deserializeTitle(
            CompoundTag tag
    )
    {
        if (!tag.contains("title"))
            return null;

        return AwakenRegistries.AWAKEN_TITLE.getRegistry(ResourceLocation.parse(tag.getString("title")));
    }

    public static void serializePrefix(
            CompoundTag tag,
            AwakenPrefix prefix
    )
    {
        tag.putString("prefix", prefix.getLocation().toString());
    }

    public static void serializeQuality(
            CompoundTag tag,
            AwakenQuality quality
    )
    {
        tag.putString("quality", quality.getLocation().toString());
    }

    public static void serializeSpiritual(
            CompoundTag tag,
            AwakenSpiritual spiritual
    )
    {
        tag.putString("spiritual", spiritual.getLocation().toString());
    }

    public static void serializeSuffix(
            CompoundTag tag,
            AwakenSuffix suffix
    )
    {
        tag.putString("suffix", suffix.getLocation().toString());
    }

    public static void serializeTitle(
            CompoundTag tag,
            AwakenTitle title
    )
    {
        tag.putString("title", title.getLocation().toString());
    }
}