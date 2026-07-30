package com.fomdev.awaken.spawn;

import com.fomdev.awaken.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.awt.*;

public class MobSpawnManager
{
    public static void noviceSpawnLogic(
            LivingEntity original,
            float diff,
            int strength,
            int auraSize,
            Color color,
            Component title,
            ItemStack item,
            EquipmentSlot slot,
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
                item,
                slot,
                level
        );
    }

    public static void reinforceSpawnLogic(
            LivingEntity original,
            float diff,
            int strength,
            int auraSize,
            Color color,
            Component title,
            ItemStack item,
            EquipmentSlot slot,
            Level level,
            RandomSource random
    )
    {
        /* TODO: FINISH */
    }

    public static void enlightenSpawnLogic(
            LivingEntity original,
            float diff,
            int strength,
            int auraSize,
            Color color,
            Component title,
            ItemStack item,
            EquipmentSlot slot,
            Level level,
            RandomSource random
    )
    {
        /* TODO: FINISH */
    }

    public static void awakenSpawnLogic(
            LivingEntity original,
            float diff,
            int strength,
            int auraSize,
            Color color,
            Component title,
            ItemStack item,
            EquipmentSlot slot,
            Level level,
            RandomSource random
    )
    {
        /* TODO: FINISH */
    }

    public static void spawn()
    {

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
                ItemStack item,
                EquipmentSlot slot,
                Level level,
                RandomSource random
        );
    }
}