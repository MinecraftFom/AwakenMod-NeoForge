package com.fomdev.awaken.events;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.entries.raw.AwakenLevel;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.event.PlayerLevelUpgradeEvent;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.attribute.AwakenAttributes;
import com.fomdev.awaken.util.HealthUtil;
import com.fomdev.awaken.util.LocaleUtil;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

        NBTUtil.addAwakenLevel(player, new BigDecimal(random.nextFloat(10.0F)));
    }

    @SubscribeEvent
    public static void onKillAwaken(LivingDeathEvent event)
    {
        if (!(event.getSource().getEntity() instanceof Player player) || !(MobSpawnEvents.isAwaken(event.getEntity())))
            return;

        AttributeInstance attr = player.getAttribute(AwakenAttributes.ENCHANTMENT);
        if (attr == null)
            return;

        double original = attr.getBaseValue();
        attr.setBaseValue(original + random.nextInt(Math.max((int) Math.pow(DifficultyManager.getLevelDifficulty((ServerLevel) player.level()).intValue(), 1.0 / 10.0), 1)));
    }
}