package com.fomdev.awaken.register.data;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.Records;
import com.fomdev.flame.annotation.AutoRegister;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.math.BigDecimal;
import java.util.List;

@AutoRegister
public class AwakenDataComponents
{
    public static final Codec<BigDecimal> BIG_DECIMAL_CODEC =
            Codec.STRING.xmap(
                    BigDecimal::new,
                    BigDecimal::toPlainString
            );

    public static final StreamCodec<ByteBuf, BigDecimal> BIG_DECIMAL_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    BigDecimal::toPlainString,
                    BigDecimal::new
            );

    public static final Codec<Records.AwakenDescriberComponent> AWAKEN_DESCRIBER_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            CompoundTag.CODEC
                                                    .fieldOf("infix")
                                                    .forGetter(Records.AwakenDescriberComponent::infix)
                                    )
                                    .and(
                                            CompoundTag.CODEC
                                                    .fieldOf("prefix")
                                                    .forGetter(Records.AwakenDescriberComponent::prefix)
                                    )
                                    .and(
                                            CompoundTag.CODEC
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
                    ByteBufCodecs.COMPOUND_TAG,
                    Records.AwakenDescriberComponent::infix,
                    ByteBufCodecs.COMPOUND_TAG,
                    Records.AwakenDescriberComponent::prefix,
                    ByteBufCodecs.COMPOUND_TAG,
                    Records.AwakenDescriberComponent::suffix,
                    Records.AwakenDescriberComponent::new
            );

    public static final Codec<Records.AwakenEpochComponent> AWAKEN_EPOCH_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            BIG_DECIMAL_CODEC
                                                    .fieldOf("requiredAwakenLevel")
                                                    .forGetter(Records.AwakenEpochComponent::requiredAwakenLevel)
                                    )
                                    .and(
                                            BIG_DECIMAL_CODEC
                                                    .fieldOf("requiredMinDifficulty")
                                                    .forGetter(Records.AwakenEpochComponent::requiredMinDifficulty)
                                    )
                                    .apply(
                                            inst,
                                            Records.AwakenEpochComponent::new
                                    )
            );

    public static final StreamCodec<ByteBuf, Records.AwakenEpochComponent> AWAKEN_EPOCH_STREAM_CODEC =
            StreamCodec.composite(
                    BIG_DECIMAL_STREAM_CODEC,
                    Records.AwakenEpochComponent::requiredAwakenLevel,
                    BIG_DECIMAL_STREAM_CODEC,
                    Records.AwakenEpochComponent::requiredMinDifficulty,
                    Records.AwakenEpochComponent::new
            );

    public static final Codec<Records.AwakenMedicineComponent> AWAKEN_MEDICINE_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            Codec.STRING
                                                    .fieldOf("medicine")
                                                    .forGetter(Records.AwakenMedicineComponent::medicineType)
                                    )
                                    .and(
                                            Codec.FLOAT
                                                    .fieldOf("amount")
                                                    .forGetter(Records.AwakenMedicineComponent::value)
                                    )
                                    .apply(
                                            inst,
                                            Records.AwakenMedicineComponent::new
                                    )
            );

    public static final StreamCodec<ByteBuf, Records.AwakenMedicineComponent> AWAKEN_MEDICINE_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    Records.AwakenMedicineComponent::medicineType,
                    ByteBufCodecs.FLOAT,
                    Records.AwakenMedicineComponent::value,
                    Records.AwakenMedicineComponent::new
            );

    public static final Codec<Records.AwakenSoulComponent> AWAKEN_SOUL_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            Codec.FLOAT
                                                    .fieldOf("current")
                                                    .forGetter(Records.AwakenSoulComponent::current)
                                    )
                                    .and(
                                            Codec.FLOAT
                                                    .fieldOf("maximum")
                                                    .forGetter(Records.AwakenSoulComponent::maximum)
                                    )
                                    .apply(
                                            inst,
                                            Records.AwakenSoulComponent::new
                                    )
            );

    public static final StreamCodec<ByteBuf, Records.AwakenSoulComponent> AWAKEN_SOUL_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT,
                    Records.AwakenSoulComponent::current,
                    ByteBufCodecs.FLOAT,
                    Records.AwakenSoulComponent::maximum,
                    Records.AwakenSoulComponent::new
            );

    @AutoRegister.Registrable
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

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Records.AwakenEpochComponent>> AWAKEN_EPOCH_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_epoch",
                    builder ->
                            builder
                                    .persistent(AWAKEN_EPOCH_CODEC)
                                    .networkSynchronized(AWAKEN_EPOCH_STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Records.AwakenDescriberComponent>> AWAKEN_DESCRIBER_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_describer",
                    builder ->
                            builder
                                    .persistent(AWAKEN_DESCRIBER_CODEC)
                                    .networkSynchronized(AWAKEN_DESCRIBER_STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Records.AwakenMedicineComponent>> AWAKEN_MEDICINE_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_medicine",
                    builder ->
                            builder
                                    .persistent(AWAKEN_MEDICINE_CODEC)
                                    .networkSynchronized(AWAKEN_MEDICINE_STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> AWAKEN_OWNER =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_owner",
                    builder ->
                            builder
                                    .persistent(Codec.STRING)
                                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
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

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<EquipmentSlot>> AWAKEN_SLOT_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_slot",
                    builder ->
                            builder
                                    .persistent(EquipmentSlot.CODEC)
                                    .networkSynchronized(ByteBufCodecs.fromCodecWithRegistries(EquipmentSlot.CODEC))
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Records.AwakenSoulComponent>> AWAKEN_SOUL_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_soul",
                    builder ->
                            builder
                                    .persistent(AWAKEN_SOUL_CODEC)
                                    .networkSynchronized(AWAKEN_SOUL_STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> AWAKEN_SPIRITUAL_STORAGE =
            COMPONENT_REGISTER.registerComponentType(
                    "awaken_spiritual",
                    builder ->
                            builder
                                    .persistent(Codec.STRING)
                                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
            );

    public static void register(
            IEventBus bus
    )
    {
        COMPONENT_REGISTER.register(bus);
    }
}