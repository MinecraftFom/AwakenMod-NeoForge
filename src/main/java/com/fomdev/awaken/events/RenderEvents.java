package com.fomdev.awaken.events;

import com.fomdev.awaken.gui.AwakenDataGUI;
import com.fomdev.awaken.init.Awaken;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class RenderEvents
{
    @SubscribeEvent
    public static void onRenderPlayerOverlay(
            RegisterGuiLayersEvent event
    )
    {
        event.registerAboveAll(
                ResourceLocation.fromNamespaceAndPath(
                        Awaken.MODID,
                        "info_gui"
                ),
                new AwakenDataGUI()
        );
    }
}