package com.fomdev.awaken.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Items;

public class UnuseShieldGoal extends HurtByTargetGoal
{
    private final Mob mob;

    public UnuseShieldGoal(
            PathfinderMob mob
    )
    {
        super(mob);
        this.mob = mob;
    }

    @Override
    public boolean canUse()
    {
        return mob.isUsingItem() && mob.getUseItem().is(Items.SHIELD) && super.canUse();
    }

    @Override
    public void start()
    {
        mob.stopUsingItem();
        super.start();
    }
}