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

@EventBusSubscriber(modid = Awaken.MODID)
@OnlyIn(Dist.CLIENT)
public class ClientDifficultyManager
{
    private static float difficulty = 0.0F;

    public static void setDifficulty(
            float value
    )
    {
        difficulty = value;
    }

    public static float getDifficulty()
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

        setDifficulty(0.0F);
    }
}