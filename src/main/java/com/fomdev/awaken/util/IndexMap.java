package com.fomdev.awaken.util;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.Map;

public class IndexMap<T> extends HashMap<Integer, T>
{
    public IndexMap(
            Map<Integer, T> parent
    )
    {
        super(parent);
    }

    public IndexMap(
            int count,
            T empty
    )
    {
        for (int i = 0; i < count; i++)
            put(i, empty);
    }

    public Map<Integer, T> serialize()
    {
        return this;
    }

    public static <U> Codec<IndexMap<U>> createCodec(
            Codec<U> codec
    )
    {
        Codec<Map<Integer, U>> mapCodec = Codec.unboundedMap(Codec.INT, codec);
        return mapCodec.xmap(
                IndexMap::new,
                IndexMap::serialize
        );
    }

    public static <U>StreamCodec<ByteBuf, IndexMap<U>> createStreamCodec(
            StreamCodec<ByteBuf, U> streamCodec
    )
    {
        StreamCodec<ByteBuf, Map<Integer, U>> mapStreamCodec = ByteBufCodecs.map(HashMap::new, ByteBufCodecs.INT, streamCodec);
        return mapStreamCodec.map(
                IndexMap::new,
                IndexMap::serialize
        );
    }
}