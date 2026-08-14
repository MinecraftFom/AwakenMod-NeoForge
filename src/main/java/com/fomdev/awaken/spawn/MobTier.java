package com.fomdev.awaken.spawn;

public interface MobTier
{
    default boolean shouldSpawn()
    {
        return true;
    }

    String getDescriptionID();
    float factor(); // We defaultly use MobSpawnManager::normalGenerate
    float weight();
    MobSpawnManager.ISpawningLogic additionalSpawn();
}