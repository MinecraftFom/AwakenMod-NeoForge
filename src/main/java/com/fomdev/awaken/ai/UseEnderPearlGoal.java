package com.fomdev.awaken.ai;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class UseEnderPearlGoal extends Goal
{
    private final Mob mob;
    private int time;

    public UseEnderPearlGoal(
            Mob mob
    )
    {
        this.mob = mob;
        this.time = 0;
    }

    @Override
    public boolean canUse()
    {
        LivingEntity target = this.mob.getTarget();
        if (target == null)
            return false;

        float dist = mob.distanceTo(target);
        return dist > 15.0
                && time <= 0
                && (mob.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.ENDER_PEARL) || mob.getItemBySlot(EquipmentSlot.OFFHAND).is(Items.ENDER_PEARL));
    }

    @Override
    public boolean canContinueToUse()
    {
        LivingEntity target = this.mob.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public void start()
    {
        this.time = 200;
        this.mob.getLookControl().setLookAt(mob.getTarget(), 10.0F, 10.0F);
        throwEnderPearl(
                this.mob.getHealth() < this.mob.getHealth() * 2 / 3
                        ? this.mob.getTarget().getEyePosition().subtract(0, -1, 0)
                        : reverse(this.mob.getTarget()));
    }

    @Override
    public void tick()
    {
        this.time--;
    }

    private Vec3 reverse(
            LivingEntity target
    )
    {
        Vec3 vec = target.getLookAngle();
        return new Vec3(vec.x, this.mob.getY(), vec.z);
    }

    private void throwEnderPearl(
            Vec3 pos
    )
    {
        Level level = this.mob.level();
        if (level.isClientSide()) return;

        ThrownEnderpearl pearl = new ThrownEnderpearl(level, this.mob);

        double d0 = pos.x() - this.mob.getX();
        double d1 = pos.y() - 1.1 - this.mob.getY();
        double d2 = pos.z() - this.mob.getZ();
        double f = Math.sqrt(d0 * d0 + d2 * d2);
        pearl.shoot(d0, d1 + f * 0.2F, d2, 1.5F, 1.0F);

        level.addFreshEntity(pearl);
    }
}