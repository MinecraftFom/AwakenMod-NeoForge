package com.fomdev.awaken.entries.raw.affix;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.affix.suffix.NoneSuffix;
import com.fomdev.awaken.util.Constants;
import com.fomdev.awaken.util.IndexMap;
import com.fomdev.flame.register.Registry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class AwakenSuffix extends Registry
{
    private final List<ServingTypes> slot;

    public AwakenSuffix(
            String id,
            List<ServingTypes> slot
    )
    {
        super(id);

        this.slot = slot;
    }

    public static AwakenSuffix of(
            ResourceLocation location
    )
    {
        AwakenSuffix suffix = location.equals(Constants.NULL)? NoneSuffix.NONE: AwakenRegistries.AWAKEN_SUFFIX.getRegistry(location);
        return suffix == null? NoneSuffix.NONE: suffix;
    }

    public List<ServingTypes> getSlot()
    {
        return this.slot;
    }

    public abstract Component getDescription(
            Map<String, String> args
    );

    public void executeAsDefend(
            ItemStack stack,
            Map<String, String> args,
            ArmorHurtEvent event
    ) {}

    public void executeAsDigger(
            ItemStack stack,
            Map<String, String> args,
            BlockEvent.BreakEvent event
    ) {}

    public void executeAsShield(
            ItemStack stack,
            Map<String, String> args,
            LivingShieldBlockEvent event
    ) {}

    public void executeAsWeapon(
            ItemStack stack,
            Map<String, String> args,
            LivingIncomingDamageEvent event
    ) {}

    public abstract Map<String, String> randomize(
            float diff,
            float factor,
            RandomSource random
    );

    public boolean isEmpty()
    {
        return this.getLocation().equals(NoneSuffix.NONE.getLocation());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof AwakenSuffix suffix))
            return false;

        return this.getLocation().equals(suffix.getLocation());
    }

    public record SuffixInstance(AwakenSuffix suffix, Map<String, String> args)
    {
        public static final SuffixInstance EMPTY =
                new SuffixInstance(NoneSuffix.NONE, Map.of());

        public SuffixInstance(
                AwakenSuffix suffix,
                Map<String, String> args
        )
        {
            this.suffix = suffix;
            this.args = Map.copyOf(args);
        }

        public Component getDescription()
        {
            return this.suffix.getDescription(this.args);
        }

        public static final Codec<SuffixInstance> CODEC =
                RecordCodecBuilder.create(
                        inst ->
                                inst
                                        .group(
                                                AwakenSuffix.CODEC
                                                        .fieldOf("suffix")
                                                        .forGetter(SuffixInstance::suffix)
                                        )
                                        .and(
                                                Codec.unboundedMap(
                                                            Codec.STRING,
                                                                Codec.STRING
                                                        )
                                                        .fieldOf("args")
                                                        .forGetter(SuffixInstance::args)
                                        )
                                        .apply(
                                                inst,
                                                SuffixInstance::new
                                        )
                );

        public static final StreamCodec<ByteBuf, SuffixInstance> STREAM_CODEC =
                StreamCodec.composite(
                        AwakenSuffix.STREAM_CODEC,
                        SuffixInstance::suffix,
                        ByteBufCodecs.map(
                                HashMap::new,
                                ByteBufCodecs.STRING_UTF8,
                                ByteBufCodecs.STRING_UTF8
                        ),
                        SuffixInstance::args,
                        SuffixInstance::new
                );
    }

    public static class SuffixSlot
    {
        public static final SuffixSlot EMPTY =
                new SuffixSlot();

        private SuffixInstance suffix;
        private boolean present;

        public SuffixSlot(
                SuffixInstance suffix
        )
        {
            this.suffix = suffix;
            this.present = !suffix.suffix.isEmpty();
        }

        public SuffixSlot()
        {
            this(new AwakenSuffix.SuffixInstance(NoneSuffix.NONE, Map.of()));
        }

        public void remove()
        {
            this.suffix = SuffixInstance.EMPTY;
            this.present = false;
        }

        public void set(
                SuffixInstance suffix
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

        public SuffixInstance getSuffix()
        {
            return this.suffix;
        }

        public static final Codec<SuffixSlot> CODEC =
                SuffixInstance.CODEC.xmap(
                        SuffixSlot::new,
                        SuffixSlot::getSuffix
                );

        public static final StreamCodec<ByteBuf, SuffixSlot> STREAM_CODEC =
                SuffixInstance.STREAM_CODEC.map(
                        SuffixSlot::new,
                        SuffixSlot::getSuffix
                );
    }

    public record SuffixContainer(
            IndexMap<SuffixSlot> slots,
            List<Integer> empty
    )
    {
        public static final SuffixContainer EMPTY =
                new SuffixContainer(0);

        public SuffixContainer(
                IndexMap<SuffixSlot> slots,
                List<Integer> empty
        )
        {
            this.slots = slots;
            this.empty = new ArrayList<>(empty);
        }

        public SuffixContainer(
                int slotCount
        )
        {
            this(new IndexMap<>(slotCount, new SuffixSlot()), initialize(slotCount));
        }

        public boolean add(
                SuffixInstance suffix
        )
        {
            for (Map.Entry<Integer, SuffixSlot> entry: this.slots.entrySet())
                if (entry.getValue().isPresent() && entry.getValue().getSuffix().equals(suffix))
                {
                    entry.getValue().set(suffix);
                    return true;
                }

            if (this.empty.isEmpty())
                return false;

            int slotId = empty.removeFirst();
            this.slots.get(slotId).set(suffix);
            return true;
        }

        public void extend(
                int count
        )
        {
            int basement = this.slots.size();
            for (int i = 0; i < count; i++)
            {
                int index = basement + i;
                this.empty.add(index);
                this.slots.put(index, SuffixSlot.EMPTY);
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
                                                        .fieldOf("slots")
                                                        .forGetter(SuffixContainer::slots)
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
                        SuffixContainer::slots,
                        ByteBufCodecs.INT.apply(ByteBufCodecs.list()),
                        SuffixContainer::empty,
                        SuffixContainer::new
                );
    }

    public static final Codec<AwakenSuffix> CODEC =
            ResourceLocation.CODEC.xmap(
                    AwakenSuffix::of,
                    AwakenSuffix::getLocation
            );

    public static final StreamCodec<ByteBuf, AwakenSuffix> STREAM_CODEC =
            ResourceLocation.STREAM_CODEC.map(
                    AwakenSuffix::of,
                    AwakenSuffix::getLocation
            );
}