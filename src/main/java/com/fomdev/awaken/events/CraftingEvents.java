package com.fomdev.awaken.events;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.knowledge.KnowledgeHelper;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class CraftingEvents
{
    @SubscribeEvent
    public static void onItemCrafted$MaxDamage(PlayerEvent.ItemCraftedEvent event)
    {
        ItemStack stack = event.getCrafting();
        Player player = event.getEntity();
        Records.AwakenKnowledgeComponent knowledge = NBTUtil.deserializeKnowledge(player);
        if (!stack.has(DataComponents.TOOL) || stack.getMaxStackSize() != 1)
            return;

        int originalDurability = stack.getMaxDamage();
        int changedDurability = KnowledgeHelper.calculateDurability(
                originalDurability,
                knowledge,
                player.getRandom()
        );

        NBTUtil.setMaxDurability(
                stack,
                changedDurability
        );
    }
}