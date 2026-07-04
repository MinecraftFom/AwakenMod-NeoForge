package com.fomdev.awaken.register;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.Records;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

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

    public static final Codec<Records.AwakenPollinateComponent> AWAKEN_POLLINATE_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            CompoundTag.CODEC.listOf()
                                                    .fieldOf("pollinate")
                                                    .forGetter(Records.AwakenPollinateComponent::data)
                                    )
                                    .apply(
                                            inst,
                                            Records.AwakenPollinateComponent::new
                                    )
            );

    public static final Codec<Records.AwakenQualityComponent> AWAKEN_QUALITY_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            Codec.STRING
                                                    .fieldOf("quality")
                                                    .forGetter(Records.AwakenQualityComponent::key)
                                    )
                                    .apply(
                                            inst,
                                            Records.AwakenQualityComponent::new
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

    public static final StreamCodec<ByteBuf, Records.AwakenPollinateComponent> AWAKEN_POLLINATE_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.COMPOUND_TAG.apply(ByteBufCodecs.list()),
                    Records.AwakenPollinateComponent::data,
                    Records.AwakenPollinateComponent::new
            );

    public static final StreamCodec<ByteBuf, Records.AwakenQualityComponent> AWAKEN_QUALITY_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    Records.AwakenQualityComponent::key,
                    Records.AwakenQualityComponent::new
            );

    public static final DeferredRegister.DataComponents COMPONENT_REGISTER =
            DeferredRegister.createDataComponents(
                    Registries.DATA_COMPONENT_TYPE,
                    Awaken.MODID
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Records.AwakenDescriberComponent>> AWAKEN_DESCRIBER_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_describer",
                    builder ->
                            builder
                                    .persistent(AWAKEN_DESCRIBER_CODEC)
                                    .networkSynchronized(AWAKEN_DESCRIBER_STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Records.AwakenPollinateComponent>> AWAKEN_POLLINATE_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_pollinate",
                    builder ->
                            builder
                                    .persistent(AWAKEN_POLLINATE_CODEC)
                                    .networkSynchronized(AWAKEN_POLLINATE_STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Records.AwakenQualityComponent>> AWAKEN_QUALITY_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_quality",
                    builder ->
                            builder
                                    .persistent(AWAKEN_QUALITY_CODEC)
                                    .networkSynchronized(AWAKEN_QUALITY_STREAM_CODEC)
            );

    public static void register(
            IEventBus bus
    )
    {
        COMPONENT_REGISTER.register(bus);
    }
}