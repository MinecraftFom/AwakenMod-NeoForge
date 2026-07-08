package com.fomdev.awaken.difficulty;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@OnlyIn(Dist.DEDICATED_SERVER)
public class DifficultyManager
{
    public static final Random random = new Random(System.currentTimeMillis());
    public static final String namespace = "awakenedDifficulty";
    public static final Map<ServerLevel, SavedDifficultyLevel> levels = new HashMap<>();

    public static final Map<ResourceLocation, Float> dimensionFactor = new HashMap<>();

    public static float getLevelDifficulty(
            ServerLevel level
    )
    {
        if (!levels.containsKey(level))
            return 0.0F;

        SavedDifficultyLevel lvl = levels.get(level);
        if (lvl == null)
            return 0.0F;

        return lvl.getLevel();
    }

    public static void registerDimensionFactor(
            ResourceLocation dimensionID,
            Float factor
    )
    {
        if (dimensionFactor.containsKey(dimensionID))
            return;

        dimensionFactor.put(dimensionID, factor);
    }

    public static void registerDimensionFactor(
            String dimensionID,
            Float factor
    )
    {
        registerDimensionFactor(
                ResourceLocation.parse(dimensionID),
                factor
        );
    }

    public static void setLevelDifficulty(
            ServerLevel level,
            float diff
    )
    {
        if (!levels.containsKey(level))
            levels.put(level, readDifficulty(level));

        SavedDifficultyLevel sl = levels.get(level);
        if (sl != null)
            sl.setLevel(diff);
    }

    public static SavedDifficultyLevel readDifficulty(
            ServerLevel level
    )
    {
        DimensionDataStorage storage = level.getDataStorage();

        return storage.computeIfAbsent(
                new SavedData.Factory<SavedDifficultyLevel>(
                        SavedDifficultyLevel::new,
                        SavedDifficultyLevel::load,
                        null
                ),
                namespace
        );
    }

    public static float getDimensionFactor(
            ServerLevel level
    )
    {
        ResourceLocation location = level.dimension().location();

        if (!dimensionFactor.containsKey(location))
            return 1.0F;

        Float factor = dimensionFactor.get(location);
        if (factor == null)
            return 1.0F;

        return factor;
    }

    public static class SavedDifficultyLevel extends SavedData
    {
        private float level = 0.0F;

        public SavedDifficultyLevel() {}

        public static SavedDifficultyLevel load(CompoundTag tag, HolderLookup.Provider provider)
        {
            SavedDifficultyLevel data = new SavedDifficultyLevel();
            data.level = tag.contains(namespace)? tag.getFloat(namespace): 0.0F;

            return data;
        }

        public float getLevel()
        {
            return this.level;
        }

        public void setLevel(float level)
        {
            this.level = level;
            this.setDirty();
        }

        @Override
        public @NotNull CompoundTag save(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider)
        {
            tag.putFloat(namespace, this.level);
            return tag;
        }
    }
}