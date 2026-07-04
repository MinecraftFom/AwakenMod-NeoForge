package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.AwakenQuality;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.ColorUtil;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class TooltipColorEvents
{
    @SubscribeEvent
    public static void onColor(
            RenderTooltipEvent.Color event
    )
    {
        ItemStack stack = event.getItemStack();
        if (stack.is(Items.AIR))
            return;

        AwakenQuality quality = NBTUtil.deserializeQuality(stack);
        if (quality == null)
            return;

        ColorUtil.apply(event, ColorUtil.render(quality.getColors(), quality.getPattern()));
    }
}