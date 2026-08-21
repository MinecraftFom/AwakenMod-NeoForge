package com.fomdev.awaken.network;

import com.fomdev.awaken.packet.SpeechSyncPacketPayload;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SpeechHandler
{
    public static void handleResponse(
            SpeechSyncPacketPayload payload,
            IPayloadContext context
    )
    {
        context.enqueueWork(() ->
            context.player().setData(AwakenAttachmentTypes.PLAYER_SPEECH_QUEUE, payload.speech())
        );
    }
}