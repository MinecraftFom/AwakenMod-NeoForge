package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.raw.AwakenAspect;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

import java.util.ArrayList;
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


        if ((stack.isDamageableItem() || stack.is(AwakenItems.ASPECT_STONE)) && material.is(AwakenItems.ASPECT_STONE))
        {
            List<AwakenAspect.AspectInstance> aspects = new ArrayList<>(NBTUtil.deserializeAspects(material));
            List<AwakenAspect.AspectInstance> original = NBTUtil.deserializeAspects(stack);
            int exp = 0;
            for (AwakenAspect.AspectInstance aspect: aspects)
                exp += (int) Math.sqrt(aspect.amount());

            aspects.addAll(original);
            ItemStack output = stack.copy();
            NBTUtil.serializeAspects(output, aspects);

            event.setCost(exp);
            event.setOutput(output);
            event.setMaterialCost(1);
        }
    }
}