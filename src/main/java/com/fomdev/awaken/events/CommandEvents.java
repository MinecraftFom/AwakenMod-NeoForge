package com.fomdev.awaken.events;

import com.fomdev.awaken.command.AwakenCommand;
import com.fomdev.awaken.init.Awaken;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class CommandEvents
{
    @SubscribeEvent
    public static void onRegisterCommand(
            RegisterCommandsEvent event
    )
    {
        AwakenCommand.register(event.getDispatcher(), event.getBuildContext());
    }
}