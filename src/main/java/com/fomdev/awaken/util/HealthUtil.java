package com.fomdev.awaken.util;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class HealthUtil
{
    public record AwakenAdditionalHealth(
            float health
    ) {}

    public static void addAdditionalHealthPersistent(
            Player player,
            float amount
    )
    {
//        serializeAdditionalHealthPersistent(player, deserializeAdditionalHealthPersistent(player) + amount);

        AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null) // How is this even possible?
            return;

        double original = maxHealth.getBaseValue();
        double current = original + amount;
        double legal = (int) (100 * current) / 100.0D;
        maxHealth.setBaseValue(legal);
    }

    public static float calculateMobHealth(
            LivingEntity entity,
            float original
    )
    {
        if (!(entity.level() instanceof ServerLevel level))
            return original;

        float factor = DifficultyManager.getLevelDifficulty(level);
        float df = (float) Math.pow(Math.max((int) factor, 1), 1.0 / 5.0);
        float value = df * original;
        float max = AwakenCommon.CONFIG.MAX_HEALTH.get().floatValue();
        return Math.clamp(value, original, max);
    }

    public static float deserializeAdditionalHealthPersistent(
            Player player
    )
    {
        if (player == null)
            return 0.0F;

        if (player instanceof ServerPlayer sp)
        {
            if (sp.connection == null)
                return 0.0F;
        } else if (player instanceof LocalPlayer lp)
        {
            if (lp.connection == null)
                return 0.0F;
        }

        float original = player.getData(AwakenAttachmentTypes.PLAYER_ADDITIONAL_HEALTH).health();
        if (original < 0.0F)
            serializeAdditionalHealthPersistent(player, 0.0F);

        return player.getData(AwakenAttachmentTypes.PLAYER_ADDITIONAL_HEALTH).health();
    }

    public static void serializeAdditionalHealthPersistent(
            Player player,
            float amount
    )
    {
        player.setData(AwakenAttachmentTypes.PLAYER_ADDITIONAL_HEALTH, new AwakenAdditionalHealth(amount));
    }
}