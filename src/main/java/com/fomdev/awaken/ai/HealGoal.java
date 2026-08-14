package com.fomdev.awaken.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class HealGoal extends Goal
{
    private final Mob mob;
    private float delay = 20;

    public HealGoal(
            Mob mob
    )
    {
        this.mob = mob;
    }

    @Override
    public boolean canUse()
    {
        return mob.getHealth() <= mob.getMaxHealth() / 3;
    }

    @Override
    public boolean canContinueToUse()
    {
        return delay <= 0;
    }

    @Override
    public void tick()
    {
        delay--;
    }

    @Override
    public void start()
    {
        this.mob.heal(0.5F);
        this.delay = 20;
    }
}