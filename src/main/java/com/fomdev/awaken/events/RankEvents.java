package com.fomdev.awaken.events;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.rank.RankHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class RankEvents
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityAttack(
            LivingIncomingDamageEvent event
    )
    {
        Entity source = event.getSource().getEntity();
        LivingEntity target = event.getEntity();

        if (source instanceof Player player && player.isCreative())
            return;

        if (!(source instanceof LivingEntity mob0))
            return;

        float rankSource = RankHelper.getRank(mob0);
        float rankTarget = RankHelper.getRank(target);

        if (rankSource < rankTarget)
        {
            event.setCanceled(true);
            if (source instanceof ServerPlayer serverPlayer)
                serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(
                        Component.translatable(
                                "bar.rank.not_enough.entity.info",
                                rankSource,
                                rankTarget
                        )
                ));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityBreak(
            BlockEvent.BreakEvent event
    )
    {
        Player player = event.getPlayer();
        BlockPos pos = event.getPos();
        Level level = player.level();

        if (player.isCreative())
            return;

        float rankSource = RankHelper.getRank(player);
        float rankTarget = RankHelper.calculateRank(level, pos);

        if (rankSource < rankTarget)
        {
            event.setCanceled(true);
            if (player instanceof ServerPlayer serverPlayer)
                serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(
                        Component.translatable(
                                "bar.rank.not_enough.block.info",
                                rankSource,
                                rankTarget
                        )
                ));
        }
    }
}