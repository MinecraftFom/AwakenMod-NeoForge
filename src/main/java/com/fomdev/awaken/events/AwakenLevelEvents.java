package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.AwakenLevel;
import com.fomdev.awaken.entries.AwakenRegistries;
import com.fomdev.awaken.event.PlayerLevelUpgradeEvent;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.HealthUtil;
import com.fomdev.awaken.util.LocaleUtil;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;

import java.util.Random;

@EventBusSubscriber(modid = Awaken.MODID)
public class AwakenLevelEvents
{
    public static final Random random = new Random();

    @SubscribeEvent
    public static void onLevelUp(PlayerLevelUpgradeEvent event)
    {
        event.getPlayer().connection.send(
                new ClientboundSetActionBarTextPacket(
                        Component
                                .translatable(
                                        "chat.congrates_player_upgrade.msg",
                                        LocaleUtil.localizeAwakenLevel(
                                                event.getCurrentLevel()
                                        ).getString()
                                )
                                .withStyle(ChatFormatting.GOLD)
                )
        );

        int awakenLevel = AwakenRegistries.AWAKEN_LEVEL.getLevel(event.getCurrentLevel());
        if (awakenLevel > 0)
            HealthUtil.addAdditionalHealthPersistent(event.getPlayer(), event.getPreviousLevel().getHealth());
    }

    @SubscribeEvent
    public static void onPlayerWake(PlayerWakeUpEvent event)
    {
        Player player = event.getEntity();

        if (event.wakeImmediately())
            return;

        NBTUtil.addAwakenLevel(player, random.nextFloat(10.0F));
    }

    @SubscribeEvent
    public static void onDeathPlayerPunish(LivingDeathEvent event)
    {
        if (!(event.getEntity() instanceof Player player))
            return;

        float level = NBTUtil.deserializeAwakenLevel(player);
        AwakenLevel awakenLevel = AwakenRegistries.AWAKEN_LEVEL.getLevel(level);
        int lvl = AwakenRegistries.AWAKEN_LEVEL.getLevel(awakenLevel);
        int factor = (int) level / (10 * lvl);
        NBTUtil.addAwakenLevel(player, -random.nextInt(factor <= 0? 1: factor));
    }
}