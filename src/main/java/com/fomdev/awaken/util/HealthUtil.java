package com.fomdev.awaken.util;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class HealthUtil
{
    public static void addAdditionalHealthPersistent(
            Player player,
            float amount
    )
    {
        serializeAdditionalHealthPersistent(player, deserializeAdditionalHealthPersistent(player) + amount);
    }

    public static float calculateMobHealth(
            LivingEntity entity,
            float original
    )
    {
        if (!(entity.level() instanceof ServerLevel level))
            return original;

        float factor = (DifficultyManager.getDimensionFactor(level) / 10);
        return original * Math.max((int) factor, 1);
    }

    public static float deserializeAdditionalHealthPersistent(
            Player player
    )
    {
        Float original = player.getData(AwakenAttachmentTypes.PLAYER_ADDITIONAL_HEALTH);
        if (original < 0.0F)
            serializeAdditionalHealthPersistent(player, 0.0F);

        return player.getData(AwakenAttachmentTypes.PLAYER_ADDITIONAL_HEALTH);
    }

    public static void serializeAdditionalHealthPersistent(
            Player player,
            float amount
    )
    {
        player.setData(AwakenAttachmentTypes.PLAYER_ADDITIONAL_HEALTH, amount);
    }
}