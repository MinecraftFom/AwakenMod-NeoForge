package com.fomdev.awaken.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class CarryPlayerGoal extends Goal
{
    private final Mob mob;
    private float cooldown;

    public CarryPlayerGoal(
            Mob mob
    )
    {
        this.mob = mob;
        this.cooldown = 200;
    }

    @Override
    public boolean canUse()
    {
        return mob.getRandom().nextInt(100) < 5 && mob.getTarget() != null && mob.getTarget().distanceTo(mob) < 5;
    }

    @Override
    public boolean canContinueToUse()
    {
        return mob.getRandom().nextInt(100) < 5 && mob.getTarget() != null && cooldown <= 0;
    }

    @Override
    public void start()
    {
        if (cooldown > 0) // Double check
            return;

        LivingEntity target = mob.getTarget();
        assert target != null; // Forever true
        target.startRiding(mob);
        cooldown = 200;

        super.start();
    }

    @Override
    public void tick()
    {
        if (cooldown > 0) // Avoid meaningless operations
            cooldown--;

        super.tick();
    }
}