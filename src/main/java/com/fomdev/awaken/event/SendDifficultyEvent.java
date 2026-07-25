package com.fomdev.awaken.event;

import net.neoforged.bus.api.Event;

@Deprecated(since = "1.0.0-beta")
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