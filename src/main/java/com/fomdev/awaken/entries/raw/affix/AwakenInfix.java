package com.fomdev.awaken.entries.raw.affix;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.util.Constants;
import com.fomdev.awaken.util.IndexMap;
import com.fomdev.awaken.util.Records;
import com.fomdev.flame.register.Registry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class AwakenInfix extends Registry
{
    // WARNING: NO REGISTERING !!!!!!!!!!
    // If registration empty -> crash game while used
    public static final AwakenInfix NONE =
            new AwakenInfix("NULL", new Records.AttributeHolder(null, 0.0F, null));

    private final Records.AttributeHolder attribute;

    public AwakenInfix(
            String id,
            Records.AttributeHolder attribute
    )
    {
        super(id);

        this.attribute = attribute;
    }

    public static AwakenInfix of(
            ResourceLocation location
    )
    {
        AwakenInfix infix = location.equals(Constants.NULL)? NONE: AwakenRegistries.AWAKEN_INFIX.getRegistry(location);
        return infix == null? NONE: infix;
    }

    public Records.AttributeHolder getAttribute()
    {
        return this.attribute;
    }

    public boolean isEmpty()
    {
        return this.getLocation().equals(Constants.NULL);
    }

    public static class InfixInstance extends AwakenInfix implements Comparable<InfixInstance>
    {
        public static final InfixInstance EMPTY =
                new InfixInstance(AwakenInfix.NONE, -1);

        private final AwakenInfix infix;
        private final int level;

        public InfixInstance(
                AwakenInfix parent,
                int level
        )
        {
            super(
                    parent.id(),
                    new Records.AttributeHolder(
                            parent.getAttribute().attr(),
                            parent.getAttribute().amount() * level / 2,
                            parent.getAttribute().operation(),
                            parent.getAttribute().slot()
                    )
            );

            this.infix = parent;
            this.level = level;
            setLocation(parent.getLocation());
        }

        public InfixInstance setLevel(
                int newLevel
        )
        {
            return new InfixInstance(infix, newLevel);
        }

        public int getLevel()
        {
            return this.level;
        }

        public AwakenInfix getRepresent()
        {
            return this;
        }

        public static final Codec<InfixInstance> CODEC =
                RecordCodecBuilder.create(
                        inst ->
                                inst
                                        .group(
                                                AwakenInfix.CODEC
                                                        .fieldOf("infix")
                                                        .forGetter(InfixInstance::getRepresent)
                                        )
                                        .and(
                                                Codec.INT
                                                        .fieldOf("level")
                                                        .forGetter(InfixInstance::getLevel)
                                        )
                                        .apply(
                                                inst,
                                                InfixInstance::new
                                        )
                );

        public static final StreamCodec<ByteBuf, InfixInstance> STREAM_CODEC =
                StreamCodec.composite(
                        AwakenInfix.STREAM_CODEC,
                        InfixInstance::getRepresent,
                        ByteBufCodecs.INT,
                        InfixInstance::getLevel,
                        InfixInstance::new
                );

        @Override
        public int compareTo(
                @NotNull AwakenInfix.InfixInstance o
        )
        {
            return Integer.compare(this.level, o.level);
        }

        @Override
        public boolean equals(Object obj)
        {
            if (!(obj instanceof InfixInstance instance))
                return false;

            return this.getLocation().equals(instance.getLocation());
        }
    }

    public static class InfixSlot
    {
        public static final InfixSlot EMPTY =
                new InfixSlot();

        private InfixInstance infix;
        private boolean present;

        public InfixSlot(
                InfixInstance infix
        )
        {
            this.infix = infix;
            this.present = infix == NONE;
        }

        public InfixSlot()
        {
            this(InfixInstance.EMPTY);
        }

        public InfixInstance getInfix()
        {
            return this.infix;
        }

        public boolean isPresent()
        {
            return this.present;
        }

        public void remove()
        {
            this.infix = InfixInstance.EMPTY;
            this.present = false;
        }

        public void set(
                InfixInstance infix
        )
        {
            remove();
            this.infix = infix;
            this.present = true;
        }

        public boolean compare(
                InfixInstance infix
        )
        {
            return this.infix.equals(infix);
        }

        public void update(
                int level
        )
        {
            this.infix = this.infix.setLevel(level);
        }

        public void upgrade(
                int level
        )
        {
            this.infix = this.infix.setLevel(this.infix.getLevel() + level);
        }

        public void upgrade(
                InfixInstance target
        )
        {
            if (!compare(target))
                return;

            switch (this.infix.compareTo(target))
            {
                case 0 -> upgrade(1);
                case 1 -> update(target.level);
            }
        }

        public static final Codec<InfixSlot> CODEC =
                InfixInstance.CODEC
                        .xmap(
                                InfixSlot::new,
                                InfixSlot::getInfix
                        );

        public static final StreamCodec<ByteBuf, InfixSlot> STREAM_CODEC =
                StreamCodec.composite(
                        InfixInstance.STREAM_CODEC,
                        InfixSlot::getInfix,
                        InfixSlot::new
                );
    }

    public record InfixContainer(IndexMap<InfixSlot> slots, List<Integer> empty)
    {
        public static final InfixContainer EMPTY =
                new InfixContainer(0);

        public InfixContainer(
                IndexMap<InfixSlot> slots,
                List<Integer> empty
        )
        {
            this.slots = IndexMap.of(slots);
            this.empty = new ArrayList<>(empty);
        }

        public InfixContainer(
                int slotCount
        )
        {
            this(new IndexMap<>(slotCount, new InfixSlot()), initialize(slotCount));
        }

        public boolean add(
                InfixInstance infix
        )
        {
            if (empty.isEmpty())
                return false;

            int slotId = empty.removeFirst();
            return set(slotId, infix);
        }

        public boolean set(
                    int slotId,
                    InfixInstance infix
        )
        {
            if (!empty.contains(slotId) || !slots.containsKey(slotId))
                return false;

            slots.get(slotId).upgrade(infix);
            return true;
        }

        public boolean merge(
                InfixInstance infix
        )
        {
            for (Map.Entry<Integer, InfixSlot> entry : this.slots.entrySet())
            {
                Integer slotId = entry.getKey();
                InfixSlot slot = entry.getValue();
                if (slot.compare(infix))
                {
                    if (!set(slotId, infix))
                        break;
                    else
                        return true;
                }
            }

            return add(infix);
        }

        public void extend(
                int count
        )
        {
            int basement = this.slots.size();
            for (int i = 0; i < count; i++)
            {
                int index = i + basement;
                this.empty.add(index);
                this.slots.put(index, InfixSlot.EMPTY);
            }
        }

        private static List<Integer> initialize(
                int count
        )
        {
            List<Integer> slots = new ArrayList<>();
            for (int i = 0; i < count; i++)
                slots.add(i);

            return slots;
        }

        public static final Codec<IndexMap<InfixSlot>> MAP_CODEC =
                IndexMap.createCodec(InfixSlot.CODEC);

        public static final StreamCodec<ByteBuf, IndexMap<InfixSlot>> MAP_STREAM_CODEC =
                IndexMap.createStreamCodec(InfixSlot.STREAM_CODEC);

        public static final Codec<InfixContainer> CODEC =
                RecordCodecBuilder.create(
                        inst ->
                                inst
                                        .group(
                                                MAP_CODEC
                                                        .fieldOf("infixes")
                                                        .forGetter(InfixContainer::slots)
                                        )
                                        .and(
                                                Codec.INT.listOf()
                                                        .fieldOf("empties")
                                                        .forGetter(InfixContainer::empty)
                                        )
                                        .apply(
                                                inst,
                                                InfixContainer::new
                                        )
                );

        public static final StreamCodec<ByteBuf, InfixContainer> STREAM_CODEC =
                StreamCodec.composite(
                        MAP_STREAM_CODEC,
                        InfixContainer::slots,
                        ByteBufCodecs.INT.apply(ByteBufCodecs.list()),
                        InfixContainer::empty,
                        InfixContainer::new
                );
    }

    public static final Codec<AwakenInfix> CODEC =
            ResourceLocation.CODEC
                    .xmap(
                            AwakenInfix::of,
                            AwakenInfix::getLocation
                    );

    public static final StreamCodec<ByteBuf, AwakenInfix> STREAM_CODEC =
            ResourceLocation.STREAM_CODEC
                    .map(
                            AwakenInfix::of,
                            AwakenInfix::getLocation
                    );

    static
    {
        NONE.setLocation(Constants.NULL);
    }
}