package com.fomdev.awaken.difficulty;

import com.fomdev.awaken.packet.DifficultySyncPacketPayloadResponder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DifficultyManager
{
    public static final Random random = new Random(System.currentTimeMillis());
    public static final String namespace = "awakenedDifficulty";
    public static final Map<ServerLevel, SavedDifficultyLevel> levels = new HashMap<>();

    public static final Map<ResourceLocation, Float> dimensionFactor = new HashMap<>();

    public static BigDecimal getLevelDifficulty(
            ServerLevel level
    )
    {
        if (!levels.containsKey(level))
            return new BigDecimal("0.0");

        SavedDifficultyLevel lvl = levels.get(level);
        if (lvl == null)
            return new BigDecimal("0.0");

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
            BigDecimal diff
    )
    {
        if (!levels.containsKey(level))
            levels.put(level, readDifficulty(level));

        SavedDifficultyLevel sl = levels.get(level);
        if (sl != null)
            sl.setLevel(diff.setScale(2, RoundingMode.HALF_UP));

        level.getServer().getPlayerList().getPlayers().forEach(sp -> sp.connection.send(new DifficultySyncPacketPayloadResponder(levels.get(level).getLevel())));
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
        private BigDecimal level = new BigDecimal("0.0");

        public SavedDifficultyLevel() {}

        public static SavedDifficultyLevel load(CompoundTag tag, HolderLookup.Provider provider)
        {
            SavedDifficultyLevel data = new SavedDifficultyLevel();
            data.level = tag.contains(namespace)? new BigDecimal(tag.getString(namespace)): new BigDecimal("0.0");

            return data;
        }

        public BigDecimal getLevel()
        {
            return this.level;
        }

        public void setLevel(
                BigDecimal level
        )
        {
            this.level = level;
            this.setDirty();
        }

        @Override
        public @NotNull CompoundTag save(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider)
        {
            tag.putString(namespace, this.level.toPlainString());
            return tag;
        }
    }
}