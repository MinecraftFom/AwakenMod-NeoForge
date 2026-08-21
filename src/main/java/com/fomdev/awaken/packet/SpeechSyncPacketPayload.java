package com.fomdev.awaken.packet;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.fomdev.awaken.speech.SpeechInstance;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SpeechSyncPacketPayload(
        SpeechInstance speech
) implements CustomPacketPayload
{
    public static final Type<SpeechSyncPacketPayload> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            Awaken.MODID,
                            "speech_request"
                    )
            );

    public static final StreamCodec<ByteBuf, SpeechSyncPacketPayload> STREAM_CODEC =
            StreamCodec.composite(
                    AwakenAttachmentTypes.AWAKEN_SPEECH_STREAM_CODEC,
                    SpeechSyncPacketPayload::speech,
                    SpeechSyncPacketPayload::new
            );


    @Override
    public @NotNull Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}