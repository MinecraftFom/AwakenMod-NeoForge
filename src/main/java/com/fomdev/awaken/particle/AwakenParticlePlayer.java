package com.fomdev.awaken.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class AwakenParticlePlayer
{
    public static void playBookConsumeParticle(
            ServerLevel level,
            ServerPlayer player,
            double centralX,
            double centralY,
            double centralZ,
            int tick
    )
    {
        double radius = 4.0;
        double radius2 = 2 * Math.sqrt(2.0);

        double p0x = centralX - radius;
        double p1x = centralX - radius2;
        double p1z = centralZ + radius2;
        double p2z = centralZ + radius;
        double p3x = centralX + radius2;
        double p3z = centralZ + radius2;
        double p4x = centralX + radius;
        double p5x = centralX + radius2;
        double p5z = centralZ - radius2;
        double p6z = centralZ - radius;
        double p7x = centralX - radius2;
        double p7z = centralZ - radius2;

        Vec3 p = new Vec3(centralX, centralY, centralZ);

        Vec3 p0 = new Vec3(p0x, centralY, centralZ);
        Vec3 p1 = new Vec3(p1x, centralY, p1z);
        Vec3 p2 = new Vec3(centralX, centralY, p2z);
        Vec3 p3 = new Vec3(p3x, centralY, p3z);
        Vec3 p4 = new Vec3(p4x, centralY, centralZ);
        Vec3 p5 = new Vec3(p5x, centralY, p5z);
        Vec3 p6 = new Vec3(centralX, centralY, p6z);
        Vec3 p7 = new Vec3(p7x, centralY, p7z);

        if (tick < 5)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p0,
                p
        );

        if (tick < 10)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p1,
                p
        );

        if (tick < 15)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p2,
                p
        );

        if (tick < 20)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p3,
                p
        );

        if (tick < 25)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p4,
                p
        );

        if (tick < 30)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p5,
                p
        );

        if (tick < 35)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p6,
                p
        );

        if (tick < 40)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p7,
                p
        );

        if (tick < 45)
            return;

        playCircle(
                level,
                player,
                ParticleTypes.SCULK_SOUL,
                p,
                radius
        );

        if (tick < 50)
            return;

        playBeacon(
            level,
            player,
            ParticleTypes.FLAME,
            p0,
            10
        );

        if (tick < 55)
            return;

        playBeacon(
                level,
                player,
                ParticleTypes.FLAME,
                p1,
                10
        );

        if (tick < 60)
            return;

        playBeacon(
            level,
            player,
            ParticleTypes.FLAME,
            p2,
            10
        );

        if (tick < 65)
            return;

        playBeacon(
                level,
                player,
                ParticleTypes.FLAME,
                p3,
                10
        );

        if (tick < 70)
            return;

        playBeacon(
                level,
                player,
                ParticleTypes.FLAME,
                p4,
                10
        );

        if (tick < 75)
            return;

        playBeacon(
                level,
                player,
                ParticleTypes.FLAME,
                p5,
                10
        );

        if (tick < 80)
            return;

        playBeacon(
                level,
                player,
                ParticleTypes.FLAME,
                p6,
                10
        );

        if (tick < 85)
            return;

        playBeacon(
            level,
            player,
            ParticleTypes.FLAME,
            p7,
            10
        );

        if (tick < 90)
            return;

        playCircle(
                level,
                player,
                ParticleTypes.ENCHANTED_HIT,
                p.add(0, 7, 0),
                radius
        );

        if (tick < 95)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p0.add(0, 10, 0),
                p.add(0, 7, 0)
        );

        if (tick < 100)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p1.add(0, 10, 0),
                p.add(0, 7, 0)
        );

        if (tick < 105)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p2.add(0, 10, 0),
                p.add(0, 7, 0)
        );

        if (tick < 110)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p3.add(0, 10, 0),
                p.add(0, 7, 0)
        );

        if (tick < 115)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p4.add(0, 10, 0),
                p.add(0, 7, 0)
        );

        if (tick < 120)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p5.add(0, 10, 0),
                p.add(0, 7, 0)
        );

        if (tick < 125)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p6.add(0, 10, 0),
                p.add(0, 7, 0)
        );

        if (tick < 130)
            return;

        playLine(
                level,
                player,
                ParticleTypes.ENCHANT,
                p7.add(0, 10, 0),
                p.add(0, 7, 0)
        );

        if (tick < 135)
            return;

        playBeacon(
                level,
                player,
                ParticleTypes.DRIPPING_OBSIDIAN_TEAR,
                p,
                7
        );
    }

    public static <T extends ParticleOptions> void playCircle(
            ServerLevel level,
            ServerPlayer player,
            T particle,
            Vec3 center,
            double radius
    )
    {
        int points = (int)(2 * Math.PI * radius * 2);
        double step = 2 * Math.PI / points;

        for (int i = 0; i < points; i++)
        {
            double theta = i * step;
            double x = center.x + radius * Math.cos(theta);
            double z = center.z + radius * Math.sin(theta);

            level.sendParticles(
                    player,
                    particle,
                    true,
                    x,
                    center.y,
                    z,
                    3,
                    0.0F,
                    0.0F,
                    0.0F,
                    0.0
            );
        }
    }

    public static <T extends ParticleOptions> void playLine(
            ServerLevel level,
            ServerPlayer player,
            T particle,
            Vec3 p0,
            Vec3 p1
    )
    {
        double spacing = 0.15;
        double distance = p0.distanceTo(p1);
        if (distance <= 0) return;

        int particleCount = (int) Math.ceil(distance / spacing);

        for (int i = 0; i <= particleCount; i++)
        {
            double t = (double) i / particleCount;
            Vec3 point = p0.lerp(p1, t);

            level.sendParticles(
                    player,
                    particle,
                    true,
                    point.x,
                    point.y,
                    point.z,
                    3,
                    0.0F,
                    0.0F,
                    0.0F,
                    0.0
            );
        }
    }

    public static <T extends ParticleOptions> void playBeacon(
            ServerLevel level,
            ServerPlayer player,
            T particle,
            Vec3 p0,
            double height
    )
    {
        playLine(
                level,
                player,
                particle,
                p0,
                p0.add(0, height, 0)
        );
    }
}