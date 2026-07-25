package com.fomdev.awaken.packet;

import com.fomdev.awaken.init.Awaken;
import io.netty.buffer.ByteBuf;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DifficultySyncPacketPayloadResponder(
        float difficulty
) implements CustomPacketPayload
{
    public static final Type<DifficultySyncPacketPayloadResponder> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            Awaken.MODID,
                            "difficulty_responder"
                    )
            );

    public static final StreamCodec<ByteBuf, DifficultySyncPacketPayloadResponder> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT,
                    DifficultySyncPacketPayloadResponder::difficulty,
                    DifficultySyncPacketPayloadResponder::new
            );

    @Override
    public @MethodsReturnNonnullByDefault Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}