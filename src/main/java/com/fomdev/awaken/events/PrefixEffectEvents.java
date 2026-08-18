package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.raw.AwakenPrefix;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Arrays;

@EventBusSubscriber(modid = Awaken.MODID)
public class PrefixEffectEvents
{
    @SubscribeEvent
    public static void onTick(
            EntityTickEvent.Post event
    )
    {
        if (!(event.getEntity() instanceof Player player))
            return;

        for (EquipmentSlot slot: EquipmentSlot.values())
            processItemStack(player.getItemBySlot(slot), player);
    }

    private static void processItemStack(
            ItemStack stack,
            Player player
    )
    {
        if (stack.isEmpty())
            return;

        AwakenPrefix prefix = NBTUtil.deserializePrefix(stack);
        if (prefix == null)
            return;

        Arrays.stream(prefix.effects()).map(MobEffectInstance::new).forEach(player::addEffect);
    }
}