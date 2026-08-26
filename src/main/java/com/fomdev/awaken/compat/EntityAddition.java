package com.fomdev.awaken.compat;

import com.fomdev.awaken.spawn.MobSpawnManager;
import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.common.registration.entity.AoAMonsters;
import net.tslat.aoa3.common.registration.worldgen.AoADimensions;
import twilightforest.init.TFDimension;
import twilightforest.init.TFEntities;

import java.util.ArrayList;
import java.util.function.Consumer;

public class EntityAddition
{
    public static void activeIfAoa3Installed()
    {
        Consumer<ResourceLocation> registerer = loc -> {
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.ABYSS.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.BARATHOS.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.CANDYLAND.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.CELEVE.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.CREEPONIA.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.CRYSTEVIA.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.DEEPLANDS.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.DUSTOPIA.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.GARDENCIA.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.GRECKON.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.HAVEN.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.IROMINE.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.LBOREAN.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.LELYETIA.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.LUNALUS.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.MYSTERIUM.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.NOWHERE.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.PRECASIA.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.RUNANDOR.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.SHYRELANDS.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.VOX_PONDS.location(), l -> new ArrayList<>()).add(loc);

            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(AoADimensions.OVERWORLD.location(), l -> new ArrayList<>()).add(loc);
        };

        registerer.accept(AoAMonsters.GHOST.getId());
        registerer.accept(AoAMonsters.ICE_GIANT.getId());
        registerer.accept(AoAMonsters.LEAFY_GIANT.getId());
        registerer.accept(AoAMonsters.NETHENGEIC_BEAST.getId());
        registerer.accept(AoAMonsters.SAND_GIANT.getId());
        registerer.accept(AoAMonsters.STONE_GIANT.getId());
        registerer.accept(AoAMonsters.TREE_SPIRIT.getId());
        registerer.accept(AoAMonsters.WOOD_GIANT.getId());
        registerer.accept(AoAMonsters.ANCIENT_GOLEM.getId());
        registerer.accept(AoAMonsters.ATTERCOPUS.getId());
        registerer.accept(AoAMonsters.BOMB_CARRIER.getId());
        registerer.accept(AoAMonsters.BUSH_BABY.getId());
        registerer.accept(AoAMonsters.CHARGER.getId());
        registerer.accept(AoAMonsters.CHOMPER.getId());
        registerer.accept(AoAMonsters.CYCLOPS.getId());
        registerer.accept(AoAMonsters.DUNKLEOSTEUS.getId());
        registerer.accept(AoAMonsters.ECHODAR.getId());
        registerer.accept(AoAMonsters.ELITE_KING_BAMBAMBAM.getId());
        registerer.accept(AoAMonsters.ELITE_NETHENGEIC_WITHER.getId());
        registerer.accept(AoAMonsters.ELITE_SKELETRON.getId());
        registerer.accept(AoAMonsters.ELITE_SMASH.getId());
        registerer.accept(AoAMonsters.ELITE_TYROSAUR.getId());
        registerer.accept(AoAMonsters.EMBRAKE.getId());
        registerer.accept(AoAMonsters.FLAMEWALKER.getId());
        registerer.accept(AoAMonsters.GOBLIN.getId());
        registerer.accept(AoAMonsters.INFERNAL.getId());
        registerer.accept(AoAMonsters.KING_BAMBAMBAM.getId());
        registerer.accept(AoAMonsters.KING_CHARGER.getId());
        registerer.accept(AoAMonsters.LITTLE_BAM.getId());
        registerer.accept(AoAMonsters.MEGANEUROPSIS.getId());
        registerer.accept(AoAMonsters.NETHENGEIC_WITHER.getId());
        registerer.accept(AoAMonsters.NOSPIKE.getId());
        registerer.accept(AoAMonsters.SASQUATCH.getId());
        registerer.accept(AoAMonsters.SCOLOPENDIS.getId());
        registerer.accept(AoAMonsters.SKELETAL_ABOMINATION.getId());
        registerer.accept(AoAMonsters.SKELETRON.getId());
        registerer.accept(AoAMonsters.SMASH.getId());
        registerer.accept(AoAMonsters.SMILODON.getId());
        registerer.accept(AoAMonsters.SPINOLEDON.getId());
        registerer.accept(AoAMonsters.THARAFLY.getId());
        registerer.accept(AoAMonsters.TYROSAUR.getId());
        registerer.accept(AoAMonsters.VELORAPTOR.getId());
        registerer.accept(AoAMonsters.VOID_WALKER.getId());
        registerer.accept(AoAMonsters.WOUNDED_TYROSAUR.getId());
        registerer.accept(AoAMonsters.YETI.getId());
    }

    public static void activeIfCataclysmInstalled()
    {
        Consumer<ResourceLocation> registerer = loc -> {
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(Level.OVERWORLD.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(Level.NETHER.location(), l -> new ArrayList<>()).add(loc);
            MobSpawnManager.LEVELED_ENTITIES.computeIfAbsent(Level.END.location(), l -> new ArrayList<>()).add(loc);
        };

        registerer.accept(ModEntities.ENDER_GOLEM.getId());
        registerer.accept(ModEntities.ENDER_GUARDIAN.getId());
        registerer.accept(ModEntities.NETHERITE_MINISTROSITY.getId());
        registerer.accept(ModEntities.NETHERITE_MONSTROSITY.getId());
        registerer.accept(ModEntities.IGNIS.getId());
        registerer.accept(ModEntities.DEEPLING.getId());
        registerer.accept(ModEntities.DEEPLING_BRUTE.getId());
        registerer.accept(ModEntities.DEEPLING_ANGLER.getId());
        registerer.accept(ModEntities.DEEPLING_PRIEST.getId());
        registerer.accept(ModEntities.DEEPLING_WARLOCK.getId());
        registerer.accept(ModEntities.THE_HARBINGER.getId());
        registerer.accept(ModEntities.THE_PROWLER.getId());
        registerer.accept(ModEntities.THE_WATCHER.getId());
        registerer.accept(ModEntities.THE_LEVIATHAN.getId());
        registerer.accept(ModEntities.WALL_WATCHER.getId());
        registerer.accept(ModEntities.ACCRETION.getId());
        registerer.accept(ModEntities.VOID_VORTEX.getId());
        registerer.accept(ModEntities.ANCIENT_DESERT_STELE.getId());
        registerer.accept(ModEntities.ANCIENT_REMNANT.getId());
        registerer.accept(ModEntities.MODERN_REMNANT.getId());
        registerer.accept(ModEntities.KOBOLEDIATOR.getId());
        registerer.accept(ModEntities.KOBOLETON.getId());
        registerer.accept(ModEntities.WADJET.getId());
        registerer.accept(ModEntities.SANDSTORM_PROJECTILE.getId());
        registerer.accept(ModEntities.MALEDICTUS.getId());
        registerer.accept(ModEntities.DRAUGR.getId());
        registerer.accept(ModEntities.ROYAL_DRAUGR.getId());
        registerer.accept(ModEntities.ELITE_DRAUGR.getId());
        registerer.accept(ModEntities.APTRGANGR.getId());
        registerer.accept(ModEntities.HIPPOCAMTUS.getId());
        registerer.accept(ModEntities.CINDARIA.getId());
        registerer.accept(ModEntities.CLAWDIAN.getId());
        registerer.accept(ModEntities.SCYLLA.getId());
        registerer.accept(ModEntities.URCHINKIN.getId());
        registerer.accept(ModEntities.DROWNED_HOST.getId());
        registerer.accept(ModEntities.SYMBIOCTO.getId());
    }

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