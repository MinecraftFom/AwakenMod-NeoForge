package com.fomdev.awaken.compat;

import com.fomdev.awaken.spawn.MobSpawnManager;
import net.minecraft.resources.ResourceLocation;
import twilightforest.init.TFDimension;
import twilightforest.init.TFEntities;

import java.util.ArrayList;
import java.util.function.Consumer;

public class EntityAddition
{
    public static void activeIfTFInstalled()
    {
        Consumer<ResourceLocation> registerer = loc -> MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(TFDimension.DIMENSION, l -> new ArrayList<>()).add(loc);

        registerer.accept(TFEntities.ADHERENT.getId());
        registerer.accept(TFEntities.ARMORED_GIANT.getId());
        registerer.accept(TFEntities.LICH.getId());
        registerer.accept(TFEntities.UR_GHAST.getId());
        registerer.accept(TFEntities.UPPER_GOBLIN_KNIGHT.getId());
        registerer.accept(TFEntities.LOWER_GOBLIN_KNIGHT.getId());
        registerer.accept(TFEntities.ALPHA_YETI.getId());
        registerer.accept(TFEntities.BLOCKCHAIN_GOBLIN.getId());
        registerer.accept(TFEntities.CARMINITE_GHASTGUARD.getId());
        registerer.accept(TFEntities.CARMINITE_GHASTLING.getId());
        registerer.accept(TFEntities.CARMINITE_BROODLING.getId());
        registerer.accept(TFEntities.CARMINITE_GOLEM.getId());
        registerer.accept(TFEntities.GIANT_MINER.getId());
        registerer.accept(TFEntities.HEDGE_SPIDER.getId());
        registerer.accept(TFEntities.HELMET_CRAB.getId());
        registerer.accept(TFEntities.HOSTILE_WOLF.getId());
        registerer.accept(TFEntities.SWARM_SPIDER.getId());
        registerer.accept(TFEntities.HYDRA.getId());
        registerer.accept(TFEntities.MINOTAUR.getId());
        registerer.accept(TFEntities.RISING_ZOMBIE.getId());
        registerer.accept(TFEntities.MOSQUITO_SWARM.getId());
        registerer.accept(TFEntities.SLIME_BEETLE.getId());
        registerer.accept(TFEntities.WINTER_WOLF.getId());
        registerer.accept(TFEntities.NAGA.getId());
        registerer.accept(TFEntities.MAZE_SLIME.getId());
        registerer.accept(TFEntities.LICH.getId());
        registerer.accept(TFEntities.MINOSHROOM.getId());
        registerer.accept(TFEntities.SNOW_QUEEN.getId());
        registerer.accept(TFEntities.KNIGHT_PHANTOM.getId());
    }
}