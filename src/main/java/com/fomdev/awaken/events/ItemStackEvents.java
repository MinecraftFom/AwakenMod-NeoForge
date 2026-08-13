package com.fomdev.awaken.events;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import net.minecraft.server.level.ServerPlayer;
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
        if (!(player instanceof ServerPlayer serverPlayer))
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
}