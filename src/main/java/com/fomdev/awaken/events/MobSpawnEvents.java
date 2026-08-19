package com.fomdev.awaken.events;

import com.fomdev.awaken.ai.*;
import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.fomdev.awaken.spawn.MobSpawnManager;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class MobSpawnEvents
{
    @SubscribeEvent
    public static void onEntityJoin(
            EntityJoinLevelEvent event
    )
    {
        if (!(event.getEntity() instanceof Monster monster))
            return;

        monster.goalSelector.addGoal(1, new UseMaceGoal(monster));
        monster.goalSelector.addGoal(1, new UseSpiderWebGoal(monster));
        monster.goalSelector.addGoal(1, new UseEnderPearlGoal(monster));
        monster.goalSelector.addGoal(2, new UseShieldGoal(monster));
        monster.goalSelector.addGoal(2, new UnuseShieldGoal(monster));
        if (!(monster instanceof RangedAttackMob))
            monster.goalSelector.addGoal(1, new UseBowGoal(monster, 1.0F, 20, 15.0F));

        if (!isAwaken(monster))
            return;
        monster.goalSelector.addGoal(1, new CarryPlayerGoal(monster));
        monster.goalSelector.addGoal(1, new HealGoal(monster));
    }

    @SubscribeEvent
    public static void onMobSpawn(
            FinalizeSpawnEvent event
    )
    {
        ResourceLocation level = event.getLevel().getLevel().dimension().location();
        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType());

        if (!MobSpawnManager.LEVELED_ENTITIES.containsKey(level) || !MobSpawnManager.LEVELED_ENTITIES.get(level).contains(key))
            return;

        RandomSource random = event.getEntity().getRandom();
        float diff = DifficultyManager.getLevelDifficulty(event.getLevel().getLevel());

        MobSpawnManager.spawn(
                event.getEntity(),
                diff,
                event.getLevel().getLevel(),
                random
        );
    }

    @SubscribeEvent
    public static void onMobDeath(
            LivingDeathEvent event
    )
    {
        Entity source = event.getSource().getEntity();
        LivingEntity target = event.getEntity();

        if (!(source instanceof Player player))
            return;

        if (!isAwaken(target))
            return;

        NBTUtil.addAwakenLevel(player, player.getRandom().nextInt((int) target.getMaxHealth()));
    }

    public static boolean isAwaken(
            Entity entity
    )
    {
        return entity.hasData(AwakenAttachmentTypes.IS_AWAKEN) && entity.getData(AwakenAttachmentTypes.IS_AWAKEN);
    }
}