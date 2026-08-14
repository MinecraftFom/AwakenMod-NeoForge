package com.fomdev.awaken.events;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.attribute.AwakenAttributes;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class AttributeEvents
{
    @SubscribeEvent
    public static void onRegisterAttribute(
            EntityAttributeModificationEvent event
    )
    {
        event.add(
                EntityType.PLAYER,
                AwakenAttributes.ENCHANTMENT
        );
    }
}