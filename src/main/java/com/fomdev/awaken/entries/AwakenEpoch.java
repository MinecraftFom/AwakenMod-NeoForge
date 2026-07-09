package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;

public class AwakenEpoch extends Registry
{
    private final AwakenLevel requiredLevel;
    private final float requiredDifficulty;

    public AwakenEpoch(
            String id,
            AwakenLevel requiredLevel,
            float requiredDifficulty
    )
    {
        super(id);

        this.requiredLevel = requiredLevel;
        this.requiredDifficulty = requiredDifficulty;
    }

    public AwakenLevel getRequiredLevel()
    {
        return this.requiredLevel;
    }

    public float getRequiredDifficulty()
    {
        return this.requiredDifficulty;
    }
}