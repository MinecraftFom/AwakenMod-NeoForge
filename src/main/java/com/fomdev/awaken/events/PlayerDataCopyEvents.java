package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.raw.spore.AwakenSpore;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.fomdev.awaken.util.HealthUtil;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.math.BigDecimal;
import java.util.List;

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
        cloneSporeEvent(original, current);
    }

    @SubscribeEvent
    public static void onJoin(
            EntityJoinLevelEvent event
    )
    {
        if (!(event.getEntity() instanceof Player player))
            return;

        player.syncData(AwakenAttachmentTypes.PLAYER_ADDITIONAL_HEALTH);
    }

    private static void cloneAwakenEvent(
            Player original,
            Player current
    )
    {
        BigDecimal originalLevel = NBTUtil.deserializeAwakenLevel(original);
        NBTUtil.serializeAwakenLevel(current, originalLevel);
    }

    private static void cloneHealthEvent(
            Player original,
            Player current
    )
    {
        float originalAmount = HealthUtil.deserializeAdditionalHealthPersistent(original);
        HealthUtil.serializeAdditionalHealthPersistent(current, originalAmount);
        current.setHealth(original.getMaxHealth());
    }

    private static void cloneSporeEvent(
            Player original,
            Player current
    )
    {
        AwakenSpore.SporeContainer spores = NBTUtil.deserializeSpores(original);
        NBTUtil.serializeSpores(current, spores);
    }
}