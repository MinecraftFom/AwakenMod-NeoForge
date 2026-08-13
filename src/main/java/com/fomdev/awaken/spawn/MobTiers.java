package com.fomdev.awaken.spawn;

public enum MobTiers
{
    NAIVE(MobSpawnManager::noviceSpawnLogic, 50, "tier.awaken.naive.name"), /* NOTHING, JUST AS A PLACEHOLDER FOR CAPABILITY */
    REINFORCE(MobSpawnManager::reinforceSpawnLogic, 25, "tier.awaken.reinforce.name"), /* REINFORCE IS JUST SIMPLE MOBS, NOT BOSS */
    ENLIGHTEN(MobSpawnManager::enlightenSpawnLogic, 15, "tier.awaken.enlighten.name"), /* ENLIGHTEN IS NOVICE, AWAKEN IS A STEP FORWARD */
    AWAKEN(MobSpawnManager::awakenSpawnLogic, 10, "tier.awaken.awaken.name"); /* THE MOD TITLE, THE STRONGEST BOSS OF ALL */

    public static int totalWeight = 100; // Not finalized for further extension

    public final MobSpawnManager.ISpawningLogic logic;
    public final int chance;
    public final String desc;

    MobTiers(
            MobSpawnManager.ISpawningLogic logic,
            int chance,
            String desc
    )
    {
        this.logic = logic;
        this.chance = chance;
        this.desc = desc;
    }
}