package com.fomdev.awaken.events;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.packet.DifficultySyncPacketPayloadResponder;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;

@EventBusSubscriber(modid = Awaken.MODID)
public class DifficultyEvents
{
    @SubscribeEvent
    public static void onLevelDayPassed(
            LevelTickEvent.Post event
    )
    {
        if (!(event.getLevel() instanceof ServerLevel level))
            return;

        long dayTime = level.getDayTime() - 1;
        if (dayTime % 24000 == 0) // A new day
        {
            int day = Math.toIntExact(dayTime / 24000);
            if (day == 0)
                return; // The first day has no difficulty

            Difficulty diff = level.getDifficulty();
            float diffFactor = calculateDifficultyFactor(diff);

            int playerCount = level.getServer().getPlayerList().getPlayerCount();
            if (playerCount == 0)
                return;

            Float[] levels = level.getServer().getPlayerList().getPlayers().stream().map(NBTUtil::deserializeAwakenLevel).toArray(Float[]::new);
            float total = 0.0F;
            for (Float fl: levels) total += fl;
            float average = total / playerCount;
            float offset = day * diffFactor;

            if (average == 0)
                average = 0.01F;

            if (offset == 0)
                offset = 0.01F;

            float diffOffset = DifficultyManager.random.nextFloat(offset);
            if (diffOffset == 0)
                diffOffset = 0.01F;

            float randValue = DifficultyManager.random.nextFloat(diffOffset * average) * day;
            if (randValue == 0)
                randValue = 0.01F;

            BigDecimal currentDifficulty = DifficultyManager.getLevelDifficulty(level);
            BigDecimal dimedValue = new BigDecimal(randValue).multiply(new BigDecimal(DifficultyManager.getDimensionFactor(level)));
            BigDecimal finalDiff = currentDifficulty.add(dimedValue).setScale(2, RoundingMode.HALF_UP);
            DifficultyManager.setLevelDifficulty(level, finalDiff);
        }
    }

    @SubscribeEvent
    public static void onSyncData(
            LevelEvent.Load event
    )
    {
        if (!(event.getLevel() instanceof ServerLevel serverLevel))
            return;

        DifficultyManager.levels.put(serverLevel, DifficultyManager.readDifficulty(serverLevel));
    }

    @SubscribeEvent
    public static void onSaveData(
            LevelEvent.Unload event
    )
    {
        if (!(event.getLevel() instanceof ServerLevel serverLevel))
            return;

        DifficultyManager.SavedDifficultyLevel level = DifficultyManager.levels.get(serverLevel);
        if (level != null)
            level.setDirty();
    }

    @SubscribeEvent
    public static void onPlayerJoin(
            EntityJoinLevelEvent event
    )
    {
        if (!(event.getEntity() instanceof ServerPlayer player))
            return;

        if (!(event.getLevel() instanceof ServerLevel level))
            return;

        player.connection.send(new DifficultySyncPacketPayloadResponder(DifficultyManager.getLevelDifficulty(level)));
    }

    private static float calculateDifficultyFactor(
            Difficulty difficulty
    )
    {
        return switch (difficulty)
        {
            case PEACEFUL -> 1.0F;
            case EASY -> 2.0F;
            case NORMAL -> 10.0F;
            case HARD -> 20.0F;
        };
    }
}