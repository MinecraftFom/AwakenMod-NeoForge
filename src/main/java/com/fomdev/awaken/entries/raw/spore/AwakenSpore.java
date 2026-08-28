package com.fomdev.awaken.entries.raw.spore;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.util.Constants;
import com.fomdev.flame.register.Registry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.ArrayList;
import java.util.List;

public abstract class AwakenSpore extends Registry
{
    public static final AwakenSpore NONE = new AwakenSpore("NULL", null)
    {
        @Override
        public double getAmount(int level)
        {
            return -1;
        }
    };

    private final Holder<Attribute> attribute;

    public AwakenSpore(
            String id,
            Holder<Attribute> attribute
    )
    {
        super(id);

        this.attribute = attribute;
    }

    public static AwakenSpore of(
            ResourceLocation location
    )
    {
        AwakenSpore spore = location == Constants.NULL? NONE: AwakenRegistries.AWAKEN_SPORE.getRegistry(location);
        return spore == null? NONE: spore;
    }

    public abstract double getAmount(
            int level
    );

    public final Holder<Attribute> getAttribute()
    {
        return this.attribute;
    }

    public boolean isEmpty()
    {
        return this.getLocation().equals(Constants.NULL);
    }

    public static class SporeInstance
    {
        private final AwakenSpore spore;
        private int level;

        public SporeInstance(
                AwakenSpore spore,
                int level
        )
        {
            this.spore = spore;
            this.level = level;
        }

        public void add(
                int level
        )
        {
            this.level += level;
        }

        public int getLevel()
        {
            return this.level;
        }

        public AwakenSpore getRepresentation()
        {
            return this.spore;
        }

        public static final Codec<SporeInstance> CODEC =
                RecordCodecBuilder.create(
                        inst ->
                                inst
                                        .group(
                                                AwakenSpore.CODEC
                                                        .fieldOf("spore")
                                                        .forGetter(SporeInstance::getRepresentation)
                                        )
                                        .and(
                                                Codec.INT
                                                        .fieldOf("level")
                                                        .forGetter(SporeInstance::getLevel)
                                        )
                                        .apply(
                                                inst,
                                                SporeInstance::new
                                        )
                );

        public static final StreamCodec<ByteBuf, SporeInstance> STREAM_CODEC =
                StreamCodec.composite(
                        AwakenSpore.STREAM_CODEC,
                        SporeInstance::getRepresentation,
                        ByteBufCodecs.INT,
                        SporeInstance::getLevel,
                        SporeInstance::new
                );
    }

    public record SporeContainer(List<SporeInstance> spores)
    {
        public SporeContainer(
                List<SporeInstance> spores
        )
        {
            this.spores = new ArrayList<>(spores);
        }

        public SporeContainer()
        {
            this(new ArrayList<>());
        }

        public void merge(
                SporeInstance instance
        )
        {
            for (SporeInstance inst : spores)
                if (inst.equals(instance))
                    inst.add(inst.getLevel());

            spores.add(instance);
        }

        public static final Codec<SporeContainer> CODEC =
                SporeInstance.CODEC.listOf().xmap(
                        SporeContainer::new,
                        SporeContainer::spores
                );

        public static final StreamCodec<ByteBuf, SporeContainer> STREAM_CODEC =
                SporeInstance.STREAM_CODEC.apply(ByteBufCodecs.list()).map(
                        SporeContainer::new,
                        SporeContainer::spores
                );
    }

    public static final Codec<AwakenSpore> CODEC =
            ResourceLocation.CODEC.xmap(
                    AwakenSpore::of,
                    AwakenSpore::getLocation
            );

    public static final StreamCodec<ByteBuf, AwakenSpore> STREAM_CODEC =
            ResourceLocation.STREAM_CODEC.map(
                    AwakenSpore::of,
                    AwakenSpore::getLocation
            );

    static
    {
        NONE.setLocation(Constants.NULL);
    }
}