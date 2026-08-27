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
            Map<String, T> parent
    )
    {
        super(toIntMap(parent));
    }

    public static <U> IndexMap<U> of(
            Map<Integer, U> parent
    )
    {
        return new IndexMap<>(toStringMap(parent));
    }

    public IndexMap(
            int count,
            T empty
    )
    {
        for (int i = 0; i < count; i++)
            put(i, empty);
    }

    public Map<String, T> serialize()
    {
        return toStringMap(this);
    }

    public static <U> Codec<IndexMap<U>> createCodec(
            Codec<U> codec
    )
    {
        Codec<Map<String, U>> mapCodec = Codec.unboundedMap(Codec.STRING, codec);
        return mapCodec.xmap(
                IndexMap::new,
                IndexMap::serialize
        );
    }

    public static <U>StreamCodec<ByteBuf, IndexMap<U>> createStreamCodec(
            StreamCodec<ByteBuf, U> streamCodec
    )
    {
        StreamCodec<ByteBuf, Map<String, U>> mapStreamCodec = ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, streamCodec);
        return mapStreamCodec.map(
                IndexMap::new,
                IndexMap::serialize
        );
    }

    private static <U> Map<Integer, U> toIntMap(
            Map<String, U> raw
    )
    {
        Map<Integer, U> result = new HashMap<>();
        for (Map.Entry<String, U> entry : raw.entrySet())
            result.put(Integer.parseInt(entry.getKey()), entry.getValue());

        return result;
    }

    private static <U> Map<String, U> toStringMap(
            Map<Integer, U> raw
    )
    {
        Map<String, U> result = new HashMap<>();
        for (Map.Entry<Integer, U> entry: raw.entrySet())
            result.put("" + entry.getKey(), entry.getValue());

        return result;
    }
}