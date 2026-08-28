package com.fomdev.awaken.entries.raw.affix;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.util.Constants;
import com.fomdev.awaken.util.Records;
import com.fomdev.flame.register.Registry;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;

// I love serialization!
public class AwakenPrefix extends Registry
{
    // WARNING: NO REGISTERING !!!!!!!!!!
    // If registration empty -> crash game while used
    public static final AwakenPrefix NONE =
            new AwakenPrefix("0NULL", -1, -1, Collections.emptyList());

    private final int durability;
    private final float rank;
    private final List<Records.EnchantmentHolder> baseEnchantments;

    public AwakenPrefix(
            String id,
            int durability,
            float rankFactor,
            List<Records.EnchantmentHolder> baseEnchantments
    )
    {
        super(id);

        this.durability = durability;
        this.rank = rankFactor;
        this.baseEnchantments = List.copyOf(baseEnchantments);
    }

    public static AwakenPrefix of(
            ResourceLocation location
    )
    {
        AwakenPrefix prefix = location.equals(Constants.NULL)? NONE: AwakenRegistries.AWAKEN_PREFIX.getRegistry(location);
        return prefix == null? NONE: prefix;
    }

    public int getDurability()
    {
        return this.durability;
    }

    public float getRankFactor()
    {
        return this.rank;
    }

    public ImmutableList<Records.EnchantmentHolder> getBaseEnchantments()
    {
        return ImmutableList.copyOf(this.baseEnchantments);
    }

    public boolean isEmpty()
    {
        return this.getLocation().equals(Constants.NULL);
    }

    public static class PrefixInstance extends AwakenPrefix
    {
        public static final PrefixInstance EMPTY =
                new PrefixInstance(AwakenPrefix.NONE, -1);

        private final int level;

        public PrefixInstance(
                AwakenPrefix parent,
                Integer level
        )
        {
            super(
                    parent.id(),
                    parent.getDurability() * level,
                    parent.getRankFactor() * (float) Math.pow(level, 1.0 / 4.0),
                    castEnchantments(parent.getBaseEnchantments(), level)
            );
            this.level = level;
            setLocation(parent.getLocation());
        }

        public AwakenPrefix getRepresent()
        {
            return this;
        }

        public int getLevel()
        {
            return this.level;
        }

        private static List<Records.EnchantmentHolder> castEnchantments(
            ImmutableList<Records.EnchantmentHolder> original,
            int level
        )
        {
            return ImmutableList.copyOf(
                    original
                            .stream()
                            .map(inst ->
                                    new Records.EnchantmentHolder(
                                            inst.enchantment(),
                                            inst.level() * (int) Math.sqrt(level))
                            )
                            .toList()
            );
        }

        public static final Codec<PrefixInstance> CODEC =
                RecordCodecBuilder.create(
                        inst ->
                                inst
                                        .group(
                                                AwakenPrefix.CODEC
                                                        .fieldOf("prefix")
                                                        .forGetter(PrefixInstance::getRepresent)
                                        )
                                        .and(
                                                Codec.INT
                                                        .fieldOf("level")
                                                        .forGetter(PrefixInstance::getLevel)
                                        )
                                        .apply(
                                                inst,
                                                PrefixInstance::new
                                        )
                );

        public static final StreamCodec<ByteBuf, PrefixInstance> STREAM_CODEC =
            StreamCodec.composite(
                    AwakenPrefix.STREAM_CODEC,
                    PrefixInstance::getRepresent,
                    ByteBufCodecs.INT,
                    PrefixInstance::getLevel,
                    PrefixInstance::new
            );
    }

    public static final Codec<AwakenPrefix> CODEC =
            ResourceLocation.CODEC
                    .xmap(
                            AwakenPrefix::of,
                            AwakenPrefix::getLocation
                    );



    public static final StreamCodec<ByteBuf, AwakenPrefix> STREAM_CODEC =
            ResourceLocation.STREAM_CODEC
                    .map(
                            AwakenPrefix::of,
                            AwakenPrefix::getLocation
                    );

    static
    {
        NONE.setLocation(Constants.NULL);
    }
}