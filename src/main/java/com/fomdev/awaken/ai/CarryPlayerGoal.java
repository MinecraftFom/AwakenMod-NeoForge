package com.fomdev.awaken.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

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
    public boolean canContinueToUse() {
        return mob.getRandom().nextInt(100) < 5 && mob.getTarget() instanceof Player && cooldown <= 0;
    }

    @Override
    public void start()
    {
        LivingEntity target = mob.getTarget();
        target.startRiding(mob);
        cooldown = 200;

        super.start();
    }

    @Override
    public void tick()
    {
        cooldown--;

        super.tick();
    }
}