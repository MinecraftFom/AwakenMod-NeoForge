package com.fomdev.awaken.spiritual;

import com.fomdev.awaken.entries.raw.AwakenSpiritual;
import com.fomdev.awaken.particle.AwakenParticlePlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ExplosionSpiritual extends AwakenSpiritual
{

    public ExplosionSpiritual(
            String id
    )
    {
        super(id, 50);
    }

    @Override
    public void effectAll(double centerX, double centerY, double centerZ, double effectX, double effectY, double effectZ, Player player, Level level)
    {
        // Nothing happens
    }

    @Override
    public void effectEntity(double centerX, double centerY, double centerZ, double effectX, double effectY, double effectZ, Player player, LivingEntity effected, Level level)
    {
        if (effected == player)
            return;

        level.explode(
                player,
                effected.getX(),
                effected.getY(),
                effected.getZ(),
                5,
                true,
                Level.ExplosionInteraction.MOB
        );
    }

    @Override
    public void renderAll(double centerX, double centerY, double centerZ, double effectX, double effectY, double effectZ, Level level)
    {
        if (!(level instanceof ServerLevel serverLevel))
            return;

        for (int i = 0; i < 10; i++)
        {
            int ref = i;
            serverLevel.players().forEach(p ->
                    AwakenParticlePlayer.playCircle(
                            serverLevel,
                            p,
                            ParticleTypes.SCULK_SOUL,
                            new Vec3(
                                    centerX,
                                    centerY + ref,
                                    centerZ
                            ),
                            50
                    )
            );
        }
    }

    @Override
    public void renderEntity(double centerX, double centerY, double centerZ, double effectX, double effectY, double effectZ, double entityX, double entityY, double entityZ, Level level)
    {
        if (!(level instanceof ServerLevel serverLevel))
            return;

        serverLevel.players().forEach(p ->
                serverLevel.sendParticles(
                        p,
                        ParticleTypes.EXPLOSION,
                        true,
                        entityX,
                        entityY,
                        entityZ,
                        20,
                        1.0F,
                        1.0F,
                        1.0F,
                        0
                )
        );

        serverLevel.players().forEach(p ->
                AwakenParticlePlayer.playBeacon(
                        serverLevel,
                        p,
                        ParticleTypes.FLAME,
                        new Vec3(
                                entityX,
                                entityY,
                                entityZ
                        ),
                        20
                )
        );

        serverLevel.players().forEach(p ->
                AwakenParticlePlayer.playLine(
                        serverLevel,
                        p,
                        ParticleTypes.END_ROD,
                        new Vec3(
                                entityX,
                                entityY,
                                entityZ
                        ),
                        new Vec3(
                                centerX,
                                centerY,
                                centerZ
                        )
                )
        );
    }
}