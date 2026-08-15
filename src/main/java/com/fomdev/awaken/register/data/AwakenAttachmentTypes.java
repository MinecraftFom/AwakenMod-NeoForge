package com.fomdev.awaken.register.data;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.Records;
import com.fomdev.flame.annotation.AutoRegister;
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

@AutoRegister
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

    public static final Supplier<AttachmentType<Records.AwakenKnowledgeComponent>> PLAYER_AWAKEN_KNOWLEDGE_ATTACHMENT =
            REGISTER.register("awaken_knowledge",
                    () -> AttachmentType.<Records.AwakenKnowledgeComponent>builder(() -> new Records.AwakenKnowledgeComponent(0.0F, 0.0F, 0.0F, 0.0F))
                            .serialize(AWAKEN_KNOWLEDGE_CODEC)
                            .sync((holder, to) -> holder == to, AWAKEN_KNOWLEDGE_STREAM_CODEC)
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