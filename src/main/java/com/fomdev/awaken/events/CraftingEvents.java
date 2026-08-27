package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.raw.AwakenAspect;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

import java.util.List;

@EventBusSubscriber(modid = Awaken.MODID)
public class CraftingEvents
{
    @SubscribeEvent
    public static void onAnvilCast(
            AnvilUpdateEvent event
    )
    {
        ItemStack stack = event.getLeft();
        ItemStack material = event.getRight();

        if ((stack.isDamageableItem() || stack.is(AwakenItems.ASPECT_STONE)) && material.is(AwakenItems.ASPECT_STONE) && material.has(AwakenDataComponents.AWAKEN_ASPECT_STORAGE))
        {
            List<AwakenAspect.AspectInstance> aspects = NBTUtil.deserializeAspects(material).aspects();
            AwakenAspect.AspectContainer container = NBTUtil.deserializeAspects(stack);
            int exp = 0;
            for (AwakenAspect.AspectInstance aspect: aspects)
            {
                exp += (int) Math.sqrt(aspect.amount());
                container.merge(aspect);
            }

            ItemStack output = stack.copy();
            NBTUtil.serializeAspects(output, container);

            event.setCost(exp);
            event.setOutput(output);
            event.setMaterialCost(1);
        }
    }
}