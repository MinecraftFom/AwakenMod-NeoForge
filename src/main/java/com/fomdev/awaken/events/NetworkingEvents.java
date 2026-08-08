package com.fomdev.awaken.events;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.network.DifficultyHandler;
import com.fomdev.awaken.packet.DifficultySyncPacketPayloadResponder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Awaken.MODID)
public class NetworkingEvents
{
    public static final String PAYLOAD_VERSION =
            "1.0.1-bugfix"; // Bug-Fix

    @SubscribeEvent
    public static void onRegister(RegisterPayloadHandlersEvent event)
    {
        PayloadRegistrar registrar = event.registrar(PAYLOAD_VERSION);
        registrar.playToClient(
                DifficultySyncPacketPayloadResponder.TYPE,
                DifficultySyncPacketPayloadResponder.STREAM_CODEC,
                DifficultyHandler::handleResponse
        );
    }
}