package com.fomdev.awaken.event;

import net.neoforged.bus.api.Event;

public class SendDifficultyEvent extends Event
{
    private final float difficulty;

    public SendDifficultyEvent(
            float difficulty
    )
    {
        this.difficulty = difficulty;
    }

    public float getDifficulty()
    {
        return this.difficulty;
    }
}