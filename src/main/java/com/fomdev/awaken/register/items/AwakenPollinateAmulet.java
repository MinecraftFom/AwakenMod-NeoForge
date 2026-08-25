package com.fomdev.awaken.register.items;

import com.fomdev.awaken.entries.raw.AwakenPollinate;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.TooltipUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AwakenPollinateAmulet extends Item
{
    public AwakenPollinateAmulet(
            Properties properties
    )
    {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(
            @NotNull ItemStack stack,
            @NotNull TooltipContext context,
            @NotNull List<Component> components,
            @NotNull TooltipFlag flag
    )
    {
        List<AwakenPollinate.PollinateInstance> pollinates = NBTUtil.deserializePollinates(stack);
        pollinates
                .stream()
                .map(p -> TooltipUtil.castPollinateTooltip(flag, p))
                .forEach(components::addAll);
    }
}