package com.fomdev.awaken.register.data;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.Records;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AwakenAttachmentTypes
{
    public static final Codec<Records.AwakenLevelComponent> AWAKEN_LEVEL_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            Codec.FLOAT
                                                    .fieldOf("awaken_level")
                                                    .forGetter(Records.AwakenLevelComponent::level)
                                    )
                                    .apply(
                                            inst,
                                            Records.AwakenLevelComponent::new
                                    )
            );

    public static final StreamCodec<ByteBuf, Records.AwakenLevelComponent> AWAKEN_LEVEL_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT,
                    Records.AwakenLevelComponent::level,
                    Records.AwakenLevelComponent::new
            );

    public static final DeferredRegister<AttachmentType<?>> REGISTER =
            DeferredRegister.create(
                    NeoForgeRegistries.ATTACHMENT_TYPES,
                    Awaken.MODID
            );

    public static final Supplier<AttachmentType<Float>> PLAYER_ADDITIONAL_HEALTH =
            REGISTER.register("additional_health",
                    () -> AttachmentType.builder(() -> 0.0F)
                            .serialize(Codec.FLOAT)
                            .build()
            );

    public static final Supplier<AttachmentType<Records.AwakenLevelComponent>> PLAYER_AWAKEN_LEVEL_ATTACHMENT =
            REGISTER.register("awaken_level",
                    () -> AttachmentType.builder(() -> new Records.AwakenLevelComponent(0.0F))
                            .serialize(AWAKEN_LEVEL_CODEC)
                            .sync((holder, to) -> holder == to, AWAKEN_LEVEL_STREAM_CODEC)
                            .build()
            );

    public static final Supplier<AttachmentType<Integer>> SPAWNER_MAX_USE_ATTACHMENT =
            REGISTER.register("spawner_max_use",
                    () -> AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT)
                            .sync((holder, to) -> true, ByteBufCodecs.INT)
                            .build()
            );

    public static final Supplier<AttachmentType<Integer>> SPAWNER_USE_ATTACHMENT =
            REGISTER.register("spawner_use",
                    () -> AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT)
                            .sync((holder, to) -> true, ByteBufCodecs.INT)
                            .build()
            );

    public static void register(
            IEventBus bus
    )
    {
        REGISTER.register(bus);
    }
}