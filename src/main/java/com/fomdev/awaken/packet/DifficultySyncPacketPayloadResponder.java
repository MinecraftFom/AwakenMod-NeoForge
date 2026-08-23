package com.fomdev.awaken.packet;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import io.netty.buffer.ByteBuf;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.math.BigDecimal;

public record DifficultySyncPacketPayloadResponder(
        BigDecimal difficulty
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
                    AwakenDataComponents.BIG_DECIMAL_STREAM_CODEC,
                    DifficultySyncPacketPayloadResponder::difficulty,
                    DifficultySyncPacketPayloadResponder::new
            );

    @Override
    public @MethodsReturnNonnullByDefault Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}