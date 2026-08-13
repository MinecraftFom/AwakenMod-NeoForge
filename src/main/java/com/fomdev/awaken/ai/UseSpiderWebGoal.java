package com.fomdev.awaken.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class UseSpiderWebGoal extends Goal
{
    private final Mob mob;

    public UseSpiderWebGoal(
            Mob mob
    )
    {
        this.mob = mob;
    }

    @Override
    public boolean canUse()
    {
        return mob.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.COBWEB) && mob.getTarget() != null && mob.getTarget().distanceTo(mob) < 5;
    }

    @Override
    public void start()
    {
        assert mob != null;
        LivingEntity entity = mob.getTarget();
        assert entity != null;
        Vec3 eyePosition = entity.getEyePosition();
        Vec3 actual = eyePosition.add(0, -1, 0);
        BlockPos pos = new BlockPos(new Vec3i((int) actual.x, (int) actual.y, (int) actual.z));
        BlockState state = mob.level().getBlockState(pos);
        if (!state.is(Blocks.AIR))
            return;

        mob.level().setBlock(pos, Blocks.COBWEB.defaultBlockState(), 0);
        mob.level().sendBlockUpdated(pos, Blocks.AIR.defaultBlockState(), Blocks.COBWEB.defaultBlockState(), 0);
    }
}