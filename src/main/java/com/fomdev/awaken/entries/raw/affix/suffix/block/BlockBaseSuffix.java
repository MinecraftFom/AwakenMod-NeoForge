package com.fomdev.awaken.entries.raw.affix.suffix.block;

import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.util.Constants;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.Event;

import java.util.List;

public abstract class BlockBaseSuffix<T extends Event> extends AwakenSuffix<T>
{
    public BlockBaseSuffix(
            String id,
            int durability,
            Component description
    )
    {
        super(
                id,
                durability,
                description,
                List.of(Constants.HAND_SLOTS)
        );
    }
}