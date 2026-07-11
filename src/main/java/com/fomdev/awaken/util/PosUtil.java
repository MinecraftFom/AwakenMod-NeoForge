package com.fomdev.awaken.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PosUtil
{
    public static List<LivingEntity> calculateNearestEntities(
            double centerX,
            double centerY,
            double centerZ,
            double radius,
            Level world
    )
    {
        Vec3 center = new Vec3(
                centerX,
                centerY,
                centerZ
        );

        AABB aabb = AABB.ofSize(
                center,
                radius * 2,
                radius * 2,
                radius * 2
        );

        return world.getEntitiesOfClass(
                LivingEntity.class,
                aabb,
                e -> e.isAlive() && center.distanceToSqr(e.position()) <= radius * radius
        );
    }

    public static Vec3 calculatePlayerTarget(
            Player player
    )
    {
        Vec3 startPos = player.getEyePosition();

        Vec3 lookVec = player.getLookAngle();
        Vec3 endPos = startPos.add(lookVec.scale(Short.MAX_VALUE));

        ClipContext context = new ClipContext(startPos, endPos,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        );

        return context.getTo();
    }
}