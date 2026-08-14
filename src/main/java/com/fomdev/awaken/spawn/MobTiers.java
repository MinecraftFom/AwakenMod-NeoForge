package com.fomdev.awaken.spawn;

import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.flame.annotation.AutoProxy;

import java.util.Arrays;

@AutoProxy
public enum MobTiers implements MobTier
{
    NAIVE(0.0F, 50, "tier.awaken.naive.name", false), /* NOTHING, JUST AS A PLACEHOLDER FOR CAPABILITY */
    REINFORCE(1.5F, 25, "tier.awaken.reinforce.name", true), /* REINFORCE IS JUST SIMPLE MOBS, NOT BOSS */
    ENLIGHTEN(7.5F, 15, "tier.awaken.enlighten.name", false), /* ENLIGHTEN IS NOVICE, AWAKEN IS A STEP FORWARD */
    AWAKEN(MobSpawnManager::awakenSpawnLogic, 15.0F, 10, "tier.awaken.awaken.name"); /* THE MOD TITLE, THE STRONGEST BOSS OF ALL */

    public static int totalWeight = 100; // Not finalized for further extension

    public final MobSpawnManager.ISpawningLogic logic;
    public final float factor;
    public final float weight;
    public final String desc;
    public final boolean should;

    MobTiers(
            MobSpawnManager.ISpawningLogic logic,
            float factor,
            float weight,
            String desc
    )
    {
        this.logic = logic;

        this.factor = factor;
        this.weight = weight;
        this.desc = desc;

        this.should = true;
    }

    MobTiers(
            float factor,
            float weight,
            String desc,
            boolean should
    )
    {
        this.logic = (p0, p1, p2, p3, p4, p5, p6, p7) -> {};

        this.factor = factor;
        this.weight = weight;
        this.desc = desc;

        this.should = should;
    }

    @Override
    public float factor()
    {
        return this.factor;
    }

    @Override
    public float weight()
    {
        return this.weight;
    }

    @Override
    public String getDescriptionID()
    {
        return this.desc;
    }

    @Override
    public MobSpawnManager.ISpawningLogic additionalSpawn()
    {
        return this.logic;
    }

    @Override
    public boolean shouldSpawn()
    {
        return this.should;
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.FML_SETUP)
    public static void register()
    {
        Arrays.stream(values()).forEach(tier -> ShuffledRegistries.WEIGHTED_AWAKEN_TIER.push(tier, tier.factor(), tier.weight()));
    }
}