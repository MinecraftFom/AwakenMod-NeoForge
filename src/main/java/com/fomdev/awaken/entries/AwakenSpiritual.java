package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class AwakenSpiritual extends Registry
{
    private final int radius;

    public AwakenSpiritual(
            String id,
            int radius
    )
    {
        super(id);

        this.radius = radius;
    }

    public abstract void effect(
            int centerX,
            int centerY,
            int centerZ,
            int effectX,
            int effectY,
            int effectZ,
            Player player,
            Entity[] effected,
            Level level
    );

    public abstract void render(
            int centerX,
            int centerY,
            int centerZ,
            int effectX,
            int effectY,
            int effectZ,
            Level level
    );

    public final int getRadius()
    {
        return this.radius;
    }
}