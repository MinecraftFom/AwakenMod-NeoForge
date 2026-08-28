package com.fomdev.awaken.entries.raw.affix.suffix.block;

import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.util.Constants;

import java.util.List;

public abstract class BlockBaseSuffix extends AwakenSuffix
{
    public BlockBaseSuffix(
            String id
    )
    {
        super(
                id,
                List.of(Constants.HAND_SLOTS)
        );
    }
}