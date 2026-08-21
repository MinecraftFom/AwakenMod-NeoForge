package com.fomdev.awaken.register.items;

import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.TooltipUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AwakenAspectStone extends Item
{
    public AwakenAspectStone(
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
        components.addAll(1, TooltipUtil.castAspectTooltip(flag, NBTUtil.deserializeAspects(stack)));
    }
}