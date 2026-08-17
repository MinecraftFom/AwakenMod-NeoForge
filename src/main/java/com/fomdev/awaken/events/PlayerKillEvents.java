package com.fomdev.awaken.events;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class PlayerKillEvents
{
    @SubscribeEvent
    public static void onPlayerKill(
            LivingDeathEvent event
    )
    {
        Entity sin = event.getSource().getEntity();
        Entity vic = event.getEntity();

        if (!(vic instanceof Monster) || !(sin instanceof Player player))
            return;

        RandomSource random = player.getRandom();
        float awaken = NBTUtil.deserializeAwakenLevel(player);
        float factor = (float) Math.pow(awaken, 1.0 / 5.0);
        float factor2 = factor <= 0? 1: factor;

        ItemStack mainhand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);
        processSoulAdd(mainhand, factor2, random);
        processSoulAdd(offhand, factor2, random);
    }

    private static void processSoulAdd(
            ItemStack stack,
            float factor,
            RandomSource random
    )
    {
        if (!stack.is(AwakenItems.SOUL_BOTTLE))
            return;

        float max = NBTUtil.deserializeSoul(stack).maximum();
        float factor2 = (float) Math.sqrt(max);
        float soul = random.nextFloat() % (factor * factor2);
        NBTUtil.addSoul(stack, soul);
    }
}