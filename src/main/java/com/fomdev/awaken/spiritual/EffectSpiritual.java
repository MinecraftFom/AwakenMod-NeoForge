package com.fomdev.awaken.spiritual;

import com.fomdev.awaken.entries.raw.AwakenSpiritual;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EffectSpiritual extends AwakenSpiritual
{
    private final MobEffectInstance effect;

    public EffectSpiritual(
            String id,
            Holder<MobEffect> effect,
            int length, /* seconds */
            int level
    )
    {
        super(id, 5);
        this.effect = new MobEffectInstance(effect, length * 20, level);
    }

    @Override
    public void effectAll(double centerX, double centerY, double centerZ, double effectX, double effectY, double effectZ, Player player, Level level)
    {
        // Nothing happens
    }

    @Override
    public void effectEntity(double centerX, double centerY, double centerZ, double effectX, double effectY, double effectZ, Player player, LivingEntity effected, Level level)
    {
        effected.addEffect(
                new MobEffectInstance(
                        this.effect
                )
        );
    }

    @Override
    public void renderAll(double centerX, double centerY, double centerZ, double effectX, double effectY, double effectZ, Level level)
    {
        // Nothing happens
    }

    @Override
    public void renderEntity(double centerX, double centerY, double centerZ, double effectX, double effectY, double effectZ, double entityX, double entityY, double entityZ, Level level)
    {
        // Nothing happens
    }
}