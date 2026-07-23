package com.fomdev.awaken.register.data;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.Records;
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

import java.util.List;

public class AwakenDataComponents
{
    public static final Codec<Records.AwakenDescriberComponent> AWAKEN_DESCRIBER_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            Codec.STRING
                                                    .fieldOf("infix")
                                                    .forGetter(Records.AwakenDescriberComponent::infix)
                                    )
                                    .and(
                                            Codec.STRING
                                                    .fieldOf("prefix")
                                                    .forGetter(Records.AwakenDescriberComponent::prefix)
                                    )
                                    .and(
                                            Codec.STRING
                                                    .fieldOf("suffix")
                                                    .forGetter(Records.AwakenDescriberComponent::suffix)
                                    )
                                    .apply(
                                            inst,
                                            Records.AwakenDescriberComponent::new
                                    )
            );

    public static final StreamCodec<ByteBuf, Records.AwakenDescriberComponent> AWAKEN_DESCRIBER_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    Records.AwakenDescriberComponent::infix,
                    ByteBufCodecs.STRING_UTF8,
                    Records.AwakenDescriberComponent::prefix,
                    ByteBufCodecs.STRING_UTF8,
                    Records.AwakenDescriberComponent::suffix,
                    Records.AwakenDescriberComponent::new
            );

    public static final DeferredRegister.DataComponents COMPONENT_REGISTER =
            DeferredRegister.createDataComponents(
                    Registries.DATA_COMPONENT_TYPE,
                    Awaken.MODID
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<CompoundTag>>> AWAKEN_ASPECT_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_aspect",
                    builder ->
                            builder
                                    .persistent(CompoundTag.CODEC.listOf())
                                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG.apply(ByteBufCodecs.list()))
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> AWAKEN_EPOCH_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_epoch",
                    builder ->
                            builder
                                    .persistent(Codec.STRING)
                                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Records.AwakenDescriberComponent>> AWAKEN_DESCRIBER_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_describer",
                    builder ->
                            builder
                                    .persistent(AWAKEN_DESCRIBER_CODEC)
                                    .networkSynchronized(AWAKEN_DESCRIBER_STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<CompoundTag>>> AWAKEN_POLLINATE_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_pollinate",
                    builder ->
                            builder
                                    .persistent(CompoundTag.CODEC.listOf())
                                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG.apply(ByteBufCodecs.list()))
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> AWAKEN_QUALITY_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_quality",
                    builder ->
                            builder
                                    .persistent(Codec.STRING)
                                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> AWAKEN_SPIRITUAL_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_spiritual",
                    builder ->
                            builder
                                    .persistent(Codec.STRING)
                                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<CompoundTag>>> AWAKEN_SPORE_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_spore",
                    builder ->
                            builder
                                    .persistent(CompoundTag.CODEC.listOf())
                                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG.apply(ByteBufCodecs.list()))
            );

    public static void register(
            IEventBus bus
    )
    {
        COMPONENT_REGISTER.register(bus);
    }
}