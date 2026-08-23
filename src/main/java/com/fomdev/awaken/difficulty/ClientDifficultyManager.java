package com.fomdev.awaken.difficulty;

import com.fomdev.awaken.init.Awaken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;

import java.math.BigDecimal;

@EventBusSubscriber(modid = Awaken.MODID)
@OnlyIn(Dist.CLIENT)
public class ClientDifficultyManager
{
    private static BigDecimal difficulty = new BigDecimal("0.0");

    public static void setDifficulty(
            BigDecimal value
    )
    {
        difficulty = value;
    }

    public static BigDecimal getDifficulty()
    {
        return difficulty;
    }

    @SubscribeEvent
    public static void onRefresh(
            EntityLeaveLevelEvent event
    )
    {
        if (!(event.getEntity() instanceof LocalPlayer player))
            return;

        if (!(event.getLevel() instanceof ClientLevel))
            return;

        if (Minecraft.getInstance().player != player)
            return;

        setDifficulty(new BigDecimal("0.0"));
    }
}