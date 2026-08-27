package com.fomdev.awaken.entries.raw;

import com.fomdev.awaken.util.Constants;
import com.fomdev.flame.register.Registry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AwakenAspect extends Registry
{
    private static final AwakenAspect NONE =
            new AwakenAspect("NULL", Color.WHITE);

    private final Color color;

    public AwakenAspect(
            String id,
            Color color
    )
    {
        super(id);
        this.color = color;
    }

    public Color getColor()
    {
        return this.color;
    }

    public static AwakenAspect of(
            ResourceLocation location
    )
    {
        AwakenAspect aspect = location == Constants.NULL? NONE: AwakenRegistries.AWAKEN_ASPECT.getRegistry(location);
        return aspect == null? NONE: aspect;
    }

    public AspectInstance toInstance(
            int value
    )
    {
        return new AspectInstance(this, value);
    }

    public static class AspectInstance extends AwakenAspect
    {
        private int amount;

        public AspectInstance(
                AwakenAspect aspect,
                int amount
        )
        {
            super(aspect.id(), aspect.getColor());
            this.amount = amount;
            setLocation(aspect.getLocation());
        }

        public AwakenAspect getRepresentation()
        {
            return this;
        }

        public void add(
                int amount
        )
        {
            this.amount += amount;
        }

        public int amount()
        {
            return this.amount;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (!(obj instanceof AspectInstance instance))
                return false;

            return this.getLocation().equals(instance.location);
        }

        public static final Codec<AspectInstance> CODEC =
                RecordCodecBuilder.create(
                        inst ->
                                inst
                                        .group(
                                                AwakenAspect.CODEC
                                                        .fieldOf("aspect")
                                                        .forGetter(AspectInstance::getRepresentation)
                                        )
                                        .and(
                                                Codec.INT
                                                        .fieldOf("amount")
                                                        .forGetter(AspectInstance::amount)
                                        )
                                        .apply(
                                                inst,
                                                AwakenAspect::toInstance
                                        )
                );

        public static final StreamCodec<ByteBuf, AspectInstance> STREAM_CODEC =
                StreamCodec.composite(
                        AwakenAspect.STREAM_CODEC,
                        AspectInstance::getRepresentation,
                        ByteBufCodecs.INT,
                        AspectInstance::amount,
                        AwakenAspect::toInstance
                );
    }

    public static class AspectContainer
    {
        private final List<AspectInstance> aspects;

        public AspectContainer(
                List<AspectInstance> aspects
        )
        {
            this.aspects = new ArrayList<>(aspects);
        }

        public AspectContainer()
        {
            this.aspects = new ArrayList<>();
        }

        public void merge(
                AspectInstance instance
        )
        {
            for (AspectInstance inst: aspects)
                if (inst.equals(instance))
                    inst.add(inst.amount);

            aspects.add(instance);
        }

        public List<AspectInstance> getAspects()
        {
            return this.aspects;
        }

        public static final Codec<AspectContainer> CODEC =
                AspectInstance.CODEC.listOf().xmap(
                        AspectContainer::new,
                        AspectContainer::getAspects
                );

        public static final StreamCodec<ByteBuf, AspectContainer> STREAM_CODEC =
                AspectInstance.STREAM_CODEC.apply(ByteBufCodecs.list()).map(
                        AspectContainer::new,
                        AspectContainer::getAspects
                );
    }

    public static final Codec<AwakenAspect> CODEC =
            ResourceLocation.CODEC.xmap(
                    AwakenAspect::of,
                    AwakenAspect::getLocation
            );

    public static final StreamCodec<ByteBuf, AwakenAspect> STREAM_CODEC =
            ResourceLocation.STREAM_CODEC.map(
                    AwakenAspect::of,
                    AwakenAspect::getLocation
            );
}