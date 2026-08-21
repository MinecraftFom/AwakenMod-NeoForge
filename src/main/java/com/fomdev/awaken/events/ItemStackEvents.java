package com.fomdev.awaken.events;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = Awaken.MODID)
public class ItemStackEvents
{
    @SubscribeEvent
    public static void onItemPickup(ItemEntityPickupEvent.Pre event)
    {
        Player player = event.getPlayer();
        if (!(player instanceof ServerPlayer serverPlayer) || player.isCreative())
            return;

        if (player.getInventory().contains(stack -> stack.is(AwakenItems.UNKNOWN_AMULET)) || CuriosApi.getCuriosInventory(player).orElseThrow().isEquipped(AwakenItems.UNKNOWN_AMULET.asItem()))
            return;

        ItemStack stack = event.getItemEntity().getItem();
        float diff = DifficultyManager.getLevelDifficulty(serverPlayer.serverLevel());
        float awakenLevel = NBTUtil.deserializeAwakenLevel(player);

        if (stack.isEmpty())
            return;

        Records.AwakenEpochComponent epoch;
        if ((epoch = NBTUtil.deserializeEpoch(stack)) == null)
            return;

        if (epoch.requiredMinDifficulty() > diff || epoch.requiredAwakenLevel() > awakenLevel)
        {
            event.setCanPickup(TriState.FALSE);
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(
                    Component.translatable(
                            "bar.block.cant_pickup.info",
                            diff,
                            epoch.requiredMinDifficulty(),
                            awakenLevel,
                            epoch.requiredAwakenLevel()
                    )
            ));
        }
    }

    @SubscribeEvent
    public static void onItemPickup$SoulFragment(ItemEntityPickupEvent.Pre event)
    {
        ItemStack stack = event.getItemEntity().getItem();
        if (!stack.is(AwakenItems.SOUL_FRAGMENT))
            return;

        Records.AwakenSoulComponent soul = NBTUtil.deserializeSoul(stack);

        if (event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).is(AwakenItems.SOUL_BOTTLE))
            NBTUtil.addSoul(event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND), soul.current());
        else if (event.getPlayer().getItemInHand(InteractionHand.OFF_HAND).is(AwakenItems.SOUL_BOTTLE))
            NBTUtil.addSoul(event.getPlayer().getItemInHand(InteractionHand.OFF_HAND), soul.current());
        else
            return;

        stack.copyAndClear();
        if (event.getPlayer().level() instanceof ServerLevel serverLevel)
            serverLevel.players().forEach(p -> serverLevel.sendParticles(p, ParticleTypes.SCULK_SOUL, false, event.getPlayer().getX(), event.getPlayer().getY() + 1, event.getPlayer().getZ(), (int) (20 * soul.current()) , 1.0, 1.0, 1.0, 0.0));
    }
}