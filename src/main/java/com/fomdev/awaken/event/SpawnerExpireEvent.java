package com.fomdev.awaken.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;

public class SpawnerExpireEvent extends Event
{
    private final Level level;
    private final BlockPos pos;

    public SpawnerExpireEvent(
            Level level,
            BlockPos pos
    )
    {
        this.level = level;
        this.pos = pos;
    }

    public Level getLevel()
    {
        return this.level;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }
}