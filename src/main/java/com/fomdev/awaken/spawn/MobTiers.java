package com.fomdev.awaken.spawn;

public enum MobTiers
{
    NAIVE(MobSpawnManager::noviceSpawnLogic), /* NOTHING, JUST AS A PLACEHOLDER FOR CAPABILITY */
    REINFORCE(MobSpawnManager::reinforceSpawnLogic), /* REINFORCE IS JUST SIMPLE MOBS, NOT BOSS */
    ENLIGHTEN(MobSpawnManager::enlightenSpawnLogic), /* ENLIGHTEN IS NOVICE, AWAKEN IS A STEP FORWARD */
    AWAKEN(MobSpawnManager::awakenSpawnLogic); /* THE MOD TITLE, THE STRONGEST BOSS OF ALL */

    public final MobSpawnManager.ISpawningLogic logic;

    MobTiers(
            MobSpawnManager.ISpawningLogic logic
    )
    {
        this.logic = logic;
    }
}