package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AwakenPrefix extends Registry
{
    private final List<AwakenAspect.AspectInstance> aspects;
    private final int durability;
    private final MobEffectInstance[] effects;

    public AwakenPrefix(
            String id,
            List<AwakenAspect.AspectInstance> aspects,
            int durability,
            MobEffectInstance[] effects
    )
    {
        super(id);

        this.aspects = new ArrayList<>(aspects);
        this.durability = durability;
        this.effects = effects;
    }

    public AwakenPrefix(
            String id,
            List<AwakenAspect.AspectInstance> aspects,
            int durability,
            MobEffect[] effects
    )
    {
        this(
                id,
                aspects,
                durability,
                Arrays.stream(effects).map(e -> new MobEffectInstance(new Holder.Direct<>(e))).toArray(MobEffectInstance[]::new)
        );
    }

    public AwakenPrefix(
            String id,
            List<AwakenAspect.AspectInstance> aspects,
            int durability
    )
    {
        this(
                id,
                aspects,
                durability,
                new MobEffectInstance[]{}
        );
    }

    public int addition()
    {
        return this.durability;
    }

    public List<AwakenAspect.AspectInstance> aspects()
    {
        return this.aspects;
    }

    public MobEffectInstance[] effects()
    {
        return this.effects;
    }
}