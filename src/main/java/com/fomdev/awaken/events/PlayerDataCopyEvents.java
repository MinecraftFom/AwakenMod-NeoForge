package com.fomdev.awaken.events;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.HealthUtil;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class PlayerDataCopyEvents
{
    @SubscribeEvent
    public static void onClone(
            PlayerEvent.Clone event
    )
    {
        Player original = event.getOriginal();
        Player current = event.getEntity();

        cloneAwakenEvent(original, current);
        cloneHealthEvent(original, current);
    }

    private static void cloneAwakenEvent(
            Player original,
            Player current
    )
    {
        float originalLevel = NBTUtil.deserializeAwakenLevel(original);
        NBTUtil.serializeAwakenLevel(current, originalLevel);
    }

    private static void cloneHealthEvent(
            Player original,
            Player current
    )
    {
        float originalAmount = HealthUtil.deserializeAdditionalHealthPersistent(original);
        HealthUtil.serializeAdditionalHealthPersistent(current, originalAmount);
    }
}