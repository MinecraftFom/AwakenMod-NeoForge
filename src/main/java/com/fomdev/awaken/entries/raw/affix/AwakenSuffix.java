package com.fomdev.awaken.entries.raw.affix;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.affix.suffix.NoneSuffix;
import com.fomdev.awaken.util.Constants;
import com.fomdev.awaken.util.IndexMap;
import com.fomdev.flame.register.Registry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AwakenSuffix<T extends Event> extends Registry
{
    private final int durability;
    private final List<EquipmentSlot> slot;
    private final Component description;

    public AwakenSuffix(
            String id,
            int durability,
            Component description,
            List<EquipmentSlot> slot
    )
    {
        super(id);

        this.description = description;
        this.durability = durability;
        this.slot = slot;
    }

    public static AwakenSuffix<?> of(
            ResourceLocation location
    )
    {
        AwakenSuffix<?> suffix = location.equals(Constants.NULL)? NoneSuffix.NONE: AwakenRegistries.AWAKEN_SUFFIX.getRegistry(location);
        return suffix == null? NoneSuffix.NONE: suffix;
    }

    public int addition()
    {
        return this.durability;
    }

    public List<EquipmentSlot> getSlot()
    {
        return this.slot;
    }

    public void register(
            IEventBus bus
    )
    {
        bus.addListener(this::onEvent);
    }

    public Component getDescription()
    {
        return this.description;
    }

    public abstract void onEvent(
            T event
    );

    public boolean isEmpty()
    {
        return this.getLocation().equals(NoneSuffix.NONE.getLocation());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof AwakenSuffix<?> suffix))
            return false;

        return this.getLocation().equals(suffix.getLocation());
    }

    public static class SuffixSlot
    {
        private AwakenSuffix<?> suffix;
        private boolean present;

        public SuffixSlot(
                AwakenSuffix<?> suffix
        )
        {
            this.suffix = suffix;
            this.present = !suffix.isEmpty();
        }

        public void remove()
        {
            this.suffix = NoneSuffix.NONE;
            this.present = false;
        }

        public void set(
                AwakenSuffix<?> suffix
        )
        {
            remove();
            this.suffix = suffix;
            this.present = true;
        }

        public boolean isPresent()
        {
            return this.present;
        }

        public AwakenSuffix<?> getSuffix()
        {
            return this.suffix;
        }

        public static final Codec<SuffixSlot> CODEC =
                AwakenSuffix.CODEC.xmap(
                        SuffixSlot::new,
                        SuffixSlot::getSuffix
                );

        public static final StreamCodec<ByteBuf, SuffixSlot> STREAM_CODEC =
                AwakenSuffix.STREAM_CODEC.map(
                        SuffixSlot::new,
                        SuffixSlot::getSuffix
                );
    }

    public record SuffixContainer(
            IndexMap<SuffixSlot> suffixes,
            List<Integer> empty
    )
    {
        public SuffixContainer(
                IndexMap<SuffixSlot> suffixes,
                List<Integer> empty
        )
        {
            this.suffixes = suffixes;
            this.empty = new ArrayList<>(empty);
        }

        public boolean add(
                AwakenSuffix<?> suffix
        )
        {
            for (Map.Entry<Integer, SuffixSlot> entry: this.suffixes.entrySet())
                if (entry.getValue().isPresent() && entry.getValue().getSuffix().equals(suffix))
                {
                    entry.getValue().set(suffix);
                    return true;
                }

            if (this.empty.isEmpty())
                return false;

            int slotId = empty.removeFirst();
            this.suffixes.get(slotId).set(suffix);
            return true;
        }

        public static final Codec<IndexMap<SuffixSlot>> MAP_CODEC =
                IndexMap.createCodec(SuffixSlot.CODEC);

        public static final StreamCodec<ByteBuf, IndexMap<SuffixSlot>> MAP_STREAM_CODEC =
                IndexMap.createStreamCodec(SuffixSlot.STREAM_CODEC);

        public static final Codec<SuffixContainer> CODEC =
                RecordCodecBuilder.create(
                        inst ->
                                inst
                                        .group(
                                                MAP_CODEC
                                                        .fieldOf("suffixes")
                                                        .forGetter(SuffixContainer::suffixes)
                                        )
                                        .and(
                                                Codec.INT.listOf()
                                                        .fieldOf("empties")
                                                        .forGetter(SuffixContainer::empty)
                                        )
                                        .apply(
                                                inst,
                                                SuffixContainer::new
                                        )
                );

        public static final StreamCodec<ByteBuf, SuffixContainer> STREAM_CODEC =
                StreamCodec.composite(
                        MAP_STREAM_CODEC,
                        SuffixContainer::suffixes,
                        ByteBufCodecs.INT.apply(ByteBufCodecs.list()),
                        SuffixContainer::empty,
                        SuffixContainer::new
                );
    }

    public static final Codec<AwakenSuffix<?>> CODEC =
            ResourceLocation.CODEC.xmap(
                    AwakenSuffix::of,
                    AwakenSuffix::getLocation
            );

    public static final StreamCodec<ByteBuf, AwakenSuffix<?>> STREAM_CODEC =
            ResourceLocation.STREAM_CODEC.map(
                    AwakenSuffix::of,
                    AwakenSuffix::getLocation
            );
}