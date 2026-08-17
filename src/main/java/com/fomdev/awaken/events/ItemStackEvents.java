package com.fomdev.awaken.events;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class ItemStackEvents
{
    @SubscribeEvent
    public static void onItemPickup(ItemEntityPickupEvent.Pre event)
    {
        Player player = event.getPlayer();
        if (!(player instanceof ServerPlayer serverPlayer) || player.isCreative())
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
            event.setCanPickup(TriState.FALSE);
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

        stack.copyAndClear();
    }
}