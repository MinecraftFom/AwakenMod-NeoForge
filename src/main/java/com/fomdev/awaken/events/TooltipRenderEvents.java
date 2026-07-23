package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.*;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.TooltipUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber(modid = Awaken.MODID)
public class TooltipRenderEvents
{
    @SubscribeEvent
    public static void onAddTooltipForItem(
            ItemTooltipEvent event
    )
    {
        // moved from mixin to here
        ItemStack stack = event.getItemStack();
        TooltipFlag flag = event.getFlags();
        List<Component> list = event.getToolTip();

        AwakenInfix infix = NBTUtil.deserializeInfix(stack);
        AwakenPrefix prefix = NBTUtil.deserializePrefix(stack);
        AwakenSuffix suffix = NBTUtil.deserializeSuffix(stack);
        AwakenQuality quality = NBTUtil.deserializeQuality(stack);
        List<AwakenAspect.AspectInstance> aspects = NBTUtil.deserializeAspects(stack);
        List<AwakenPollinate.PollinateInstance> pollinates = NBTUtil.deserializePollinates(stack);
        List<AwakenSpore.SporeInstance> spores = NBTUtil.deserializeSpores(stack);

        for (AwakenPollinate.PollinateInstance instance: pollinates)
            list.addAll(TooltipUtil.castPollinateTooltip(flag, instance));

        for (AwakenSpore.SporeInstance instance: spores)
            list.addAll(TooltipUtil.castSporeTooltip(flag, instance));

        if (infix != null && prefix != null && suffix != null)
        {
            list.addAll(TooltipUtil.castInfixTooltip(flag, infix, (float) (quality != null? quality.getFactor() * suffix.factor(): suffix.factor())));
            list.addAll(TooltipUtil.castPrefixTooltip(flag, prefix));
            list.addAll(TooltipUtil.castSuffixTooltip(flag, suffix));
        }

        if (quality != null)
            list.addAll(TooltipUtil.castQualityTooltip(flag, quality));
    }
}