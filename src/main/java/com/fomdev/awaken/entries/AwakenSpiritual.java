package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class AwakenSpiritual extends Registry
{
    private final double radius;

    public AwakenSpiritual(
            String id,
            int radius
    )
    {
        super(id);

        this.radius = radius;
    }

    public abstract void effectAll(
            double centerX,
            double centerY,
            double centerZ,
            double effectX,
            double effectY,
            double effectZ,
            Player player,
            Level level
    );

    public abstract void effectEntity(
            double centerX,
            double centerY,
            double centerZ,
            double effectX,
            double effectY,
            double effectZ,
            Player player,
            LivingEntity effected,
            Level level
    );

    public abstract void renderAll(
            double centerX,
            double centerY,
            double centerZ,
            double effectX,
            double effectY,
            double effectZ,
            Level level
    );

    public abstract void renderEntity(
            double centerX,
            double centerY,
            double centerZ,
            double effectX,
            double effectY,
            double effectZ,
            double entityX,
            double entityY,
            double entityZ,
            Level level
    );

    public final double getRadius()
    {
        return this.radius;
    }
}