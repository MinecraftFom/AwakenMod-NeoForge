package com.fomdev.awaken.network;

import com.fomdev.awaken.difficulty.ClientDifficultyManager;
import com.fomdev.awaken.packet.DifficultySyncPacketPayloadResponder;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.math.BigDecimal;

public class DifficultyHandler
{
    public static void handleResponse(
            DifficultySyncPacketPayloadResponder payload,
            IPayloadContext context
    )
    {
        context.enqueueWork(() -> {
            BigDecimal data = payload.difficulty();
            ClientDifficultyManager.setDifficulty(data);
        });
    }
}