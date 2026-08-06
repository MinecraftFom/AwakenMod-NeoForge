package com.fomdev.awaken.spawn;

import com.fomdev.awaken.literature.Literature;
import com.fomdev.awaken.util.ColorUtil;
import com.fomdev.awaken.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MobSpawnManager
{
    /* Here, the resourcelocation should be the key of the level (dimension) the entities are generating in */
    public static final Map<ResourceLocation, List<Class<? extends Mob>>> LEVELED_ENTITIES
            = new HashMap<>();

    public static void noviceSpawnLogic(
            LivingEntity original,
            float diff,
            int strength,
            int auraSize,
            Color color,
            Component title,
            Level level,
            RandomSource random
    )
    {
        /* PLACEHOLDER */
        Util.placeholder(
                original,
                diff,
                strength,
                auraSize,
                color,
                title,
                level,
                random
        );
    }

    public static void reinforceSpawnLogic(
            LivingEntity original,
            float diff,
            int strength,
            int auraSize,
            Color color,
            Component title,
            Level level,
            RandomSource random
    )
    {
        if (!(level instanceof ServerLevel server))
            return;
        if (!(original instanceof EquipmentUser user))
            return;

        original.setCustomName(
                Component.empty().append(title).withStyle(ColorUtil.colorStyle(color))
        );

        AttributeInstance instance = Objects.requireNonNull(original.getAttribute(Attributes.ATTACK_DAMAGE));
        instance.setBaseValue(strength);

        EquipmentSlot[] slots = EquipmentManager.shuffleSlots(
                diff,
                1.5F,
                random
        );

        for (EquipmentSlot slot: slots)
        {
            ItemStack item = EquipmentManager.shuffleItemStack(
                    slot,
                    diff,
                    1.5F,
                    random
            );

            EquipmentManager.shuffleForItemStack(
                    item,
                    diff,
                    1.0F,
                    random
            );

            user.setItemSlot(
                    slot,
                    item
            );
        }
//        AwakenParticlePlayer.playReinforceMobGenerate(
//            server,
//            original.position().toVector3f(),
//            auraSize
//        );
    }

    public static void enlightenSpawnLogic(
            LivingEntity original,
            float diff,
            int strength,
            int auraSize,
            Color color,
            Component title,
            Level level,
            RandomSource random
    )
    {
        if (!(level instanceof ServerLevel server))
            return;
        if (!(original instanceof EquipmentUser user))
            return;

        original.setCustomName(
                Component.empty().append(title).withStyle(ColorUtil.colorStyle(color))
        );

        AttributeInstance instance = Objects.requireNonNull(original.getAttribute(Attributes.ATTACK_DAMAGE));
        instance.setBaseValue(strength);

        EquipmentSlot[] slots = EquipmentManager.shuffleSlots(
                diff,
                7.5F,
                random
        );

        for (EquipmentSlot slot: slots)
        {
            ItemStack item = EquipmentManager.shuffleItemStack(
                    slot,
                    diff,
                    7.5F,
                    random
            );

            EquipmentManager.shuffleForItemStack(
                    item,
                    diff,
                    7.5F,
                    random
            );

            user.setItemSlot(
                    slot,
                    item
            );
        }

        /* TODO: ADD PARTICLE */
    }

    public static void awakenSpawnLogic(
            LivingEntity original,
            float diff,
            int strength,
            int auraSize,
            Color color,
            Component title,
            Level level,
            RandomSource random
    )
    {
        if (!(level instanceof ServerLevel server))
            return;
        if (!(original instanceof EquipmentUser user))
            return;

        original.setCustomName(
                Component.empty().append(title).withStyle(ColorUtil.colorStyle(color))
        );

        AttributeInstance instance = Objects.requireNonNull(original.getAttribute(Attributes.ATTACK_DAMAGE));
        instance.setBaseValue(strength);

        EquipmentSlot[] slots = EquipmentManager.shuffleSlots(
                diff,
                15.0F,
                random
        );

        for (EquipmentSlot slot: slots)
        {
            ItemStack item = EquipmentManager.shuffleItemStack(
                    slot,
                    diff,
                    15.0F,
                    random
            );

            EquipmentManager.shuffleForItemStack(
                    item,
                    diff,
                    15.0F,
                    random
            );

            user.setItemSlot(
                    slot,
                    item
            );
        }

        /* TODO: ADD PARTICLE */
    }

    public static Color shuffleColor(
            RandomSource random
    )
    {
        int h = random.nextInt(360);

        return Color.getHSBColor(h, 1.0F, 1.0f);
    }

    public static MobTiers shuffleTier(
            RandomSource random
    )
    {
        int totalWeight = random.nextInt(MobTiers.totalWeight);
        int i = 0;

        while (totalWeight >= 0)
        {
            MobTiers tier = MobTiers.values()[i];
            totalWeight -= tier.chance;
        }

        return MobTiers.values()[i];
    }

    public static Component shuffleTitle(
            RandomSource random
    )
    {
        return Literature.NAMES_INSTANCE.get(random);
    }

    public static void spawn(
            LivingEntity entity,
            float diff,
            Level level,
            RandomSource random
    )
    {
        MobTiers tier = shuffleTier(
                random
        );

        int strength = random.nextInt(Math.max((int) diff * 100, 1));
        int auraSize = random.nextInt(20);
        Color color = shuffleColor(
                random
        );
        Component title = shuffleTitle(
                random
        );

        tier.logic.onSpawn(
                entity,
                diff,
                strength,
                auraSize,
                color,
                title,
                level,
                random
        );
    }

    @FunctionalInterface
    public interface ISpawningLogic
    {
        void onSpawn(
                LivingEntity original,
                float diff,
                int strength,
                int auraSize,
                Color color,
                Component title,
                Level level,
                RandomSource random
        );
    }
}