package com.fomdev.awaken.difficulty;

import com.fomdev.awaken.event.SendDifficultyEvent;
import com.fomdev.awaken.init.Awaken;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

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
    public static void onSync(
            SendDifficultyEvent event
    )
    {
        setDifficulty(event.getDifficulty());
    }
}