package com.fomdev.awaken.register.data;

import com.fomdev.awaken.entries.raw.spore.AwakenSpore;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.speech.SpeechInstance;
import com.fomdev.awaken.util.HealthUtil;
import com.fomdev.awaken.util.Records;
import com.fomdev.flame.annotation.AutoRegister;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

@AutoRegister
public class AwakenAttachmentTypes
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

    public static final Codec<Records.AwakenLevelComponent> AWAKEN_LEVEL_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            BIG_DECIMAL_CODEC
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
                    BIG_DECIMAL_STREAM_CODEC,
                    Records.AwakenLevelComponent::level,
                    Records.AwakenLevelComponent::new
            );

    public static final Codec<Records.AwakenKnowledgeComponent> AWAKEN_KNOWLEDGE_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            Codec.FLOAT
                                                    .fieldOf("experience")
                                                    .forGetter(Records.AwakenKnowledgeComponent::experience)
                                    )
                                    .and(
                                            Codec.FLOAT
                                                    .fieldOf("insight")
                                                    .forGetter(Records.AwakenKnowledgeComponent::insight)
                                    )
                                    .and(
                                            Codec.FLOAT
                                                    .fieldOf("proficiency")
                                                    .forGetter(Records.AwakenKnowledgeComponent::proficiency)
                                    )
                                    .and(
                                            Codec.FLOAT
                                                    .fieldOf("skill")
                                                    .forGetter(Records.AwakenKnowledgeComponent::skill)
                                    )
                                    .apply(
                                            inst,
                                            Records.AwakenKnowledgeComponent::new
                                    )
            );

    public static final Codec<SpeechInstance> AWAKEN_SPEECH_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            SpeechInstance.COMPONENT_CODEC.listOf()
                                                    .fieldOf("speeches")
                                                    .forGetter(SpeechInstance::getSpeech)
                                    )
                                    .and(
                                            Codec.INT
                                                    .fieldOf("delay")
                                                    .forGetter(SpeechInstance::getRemainingDelay)
                                    )
                                    .apply(
                                            inst,
                                            SpeechInstance::new
                                    )
            );

    public static final StreamCodec<ByteBuf, Records.AwakenKnowledgeComponent> AWAKEN_KNOWLEDGE_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT,
                    Records.AwakenKnowledgeComponent::experience,
                    ByteBufCodecs.FLOAT,
                    Records.AwakenKnowledgeComponent::insight,
                    ByteBufCodecs.FLOAT,
                    Records.AwakenKnowledgeComponent::proficiency,
                    ByteBufCodecs.FLOAT,
                    Records.AwakenKnowledgeComponent::skill,
                    Records.AwakenKnowledgeComponent::new
            );

    public static final Codec<HealthUtil.AwakenAdditionalHealth> AWAKEN_ADDITIONAL_HEALTH_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            Codec.FLOAT
                                                    .fieldOf("health")
                                                    .forGetter(HealthUtil.AwakenAdditionalHealth::health)
                                    )
                                    .apply(
                                            inst,
                                            HealthUtil.AwakenAdditionalHealth::new
                                    )
            );

    public static final StreamCodec<ByteBuf, HealthUtil.AwakenAdditionalHealth> AWAKEN_ADDITIONAL_HEALTH_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT,
                    HealthUtil.AwakenAdditionalHealth::health,
                    HealthUtil.AwakenAdditionalHealth::new
            );

    public static final StreamCodec<ByteBuf, SpeechInstance> AWAKEN_SPEECH_STREAM_CODEC =
            StreamCodec.composite(
                    SpeechInstance.COMPONENT_STREAM_CODEC.apply(ByteBufCodecs.list()),
                    SpeechInstance::getSpeech,
                    ByteBufCodecs.INT,
                    SpeechInstance::getRemainingDelay,
                    SpeechInstance::new
            );

    @AutoRegister.Registrable
    public static final DeferredRegister<AttachmentType<?>> REGISTER =
            DeferredRegister.create(
                    NeoForgeRegistries.ATTACHMENT_TYPES,
                    Awaken.MODID
            );

    public static final Supplier<AttachmentType<Boolean>> IS_AWAKEN =
            REGISTER.register("awaken",
                    () -> AttachmentType.<Boolean>builder(() -> false)
                            .serialize(Codec.BOOL)
                            .sync(ByteBufCodecs.BOOL)
                            .build()
            );

    public static final Supplier<AttachmentType<HealthUtil.AwakenAdditionalHealth>> PLAYER_ADDITIONAL_HEALTH =
            REGISTER.register("additional_health",
                    () -> AttachmentType.builder(() -> new HealthUtil.AwakenAdditionalHealth(1.0F))
                            .serialize(AWAKEN_ADDITIONAL_HEALTH_CODEC)
                            .sync((holder, to) -> holder == to, AWAKEN_ADDITIONAL_HEALTH_STREAM_CODEC)
                            .build()
            );

    public static final Supplier<AttachmentType<Records.AwakenLevelComponent>> PLAYER_AWAKEN_LEVEL_ATTACHMENT =
            REGISTER.register("awaken_level",
                    () -> AttachmentType.builder(() -> new Records.AwakenLevelComponent(new BigDecimal("0.0")))
                            .serialize(AWAKEN_LEVEL_CODEC)
                            .sync((holder, to) -> holder == to, AWAKEN_LEVEL_STREAM_CODEC)
                            .build()
            );

    public static final Supplier<AttachmentType<Records.AwakenKnowledgeComponent>> PLAYER_AWAKEN_KNOWLEDGE_ATTACHMENT =
            REGISTER.register("awaken_knowledge",
                    () -> AttachmentType.builder(() -> new Records.AwakenKnowledgeComponent(0.0F, 0.0F, 0.0F, 0.0F))
                            .serialize(AWAKEN_KNOWLEDGE_CODEC)
                            .sync((holder, to) -> holder == to, AWAKEN_KNOWLEDGE_STREAM_CODEC)
                            .build()
            );

    public static final Supplier<AttachmentType<SpeechInstance>> PLAYER_SPEECH_QUEUE =
            REGISTER.register("awaken_speech",
                    () -> AttachmentType.<SpeechInstance>builder(() -> new SpeechInstance(List.of(), 0))
                            .serialize(AWAKEN_SPEECH_CODEC)
                            .sync(AWAKEN_SPEECH_STREAM_CODEC)
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

    public static final Supplier<AttachmentType<AwakenSpore.SporeContainer>> SPORE_ATTACHMENT =
            REGISTER.register(
                    "awaken_spore",
                    () -> AttachmentType.builder(() -> new AwakenSpore.SporeContainer(List.<AwakenSpore.SporeInstance>of()))
                            .serialize(AwakenSpore.SporeContainer.CODEC)
                            .sync(AwakenSpore.SporeContainer.STREAM_CODEC)
                            .build()
            );

    public static void register(
            IEventBus bus
    )
    {
        REGISTER.register(bus);
    }
}