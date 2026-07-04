package com.fomdev.awaken.register;

import com.fomdev.awaken.init.Awaken;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AwakenDataComponents
{
    public record AwakenDataStorage(
            CompoundTag tag
    ) {}

    public static final Codec<AwakenDataStorage> AWAKEN_DATA_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            CompoundTag.CODEC
                                                    .fieldOf("data")
                                                    .forGetter(AwakenDataStorage::tag)
                                    )
                                    .apply(inst, AwakenDataStorage::new)
            );

    public static final StreamCodec<ByteBuf, AwakenDataStorage> AWAKEN_DATA_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.COMPOUND_TAG,
                    AwakenDataStorage::tag,
                    AwakenDataStorage::new
            );

    public static final DeferredRegister.DataComponents COMPONENT_REGISTER =
            DeferredRegister.createDataComponents(
                    Registries.DATA_COMPONENT_TYPE,
                    Awaken.MODID
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<AwakenDataStorage>> AWAKEN_DATA_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awakened",
                    builder ->
                            builder
                                    .persistent(AWAKEN_DATA_CODEC)
                                    .networkSynchronized(AWAKEN_DATA_STREAM_CODEC)
            );

    public static void register(
            IEventBus bus
    )
    {
        COMPONENT_REGISTER.register(bus);
    }
}