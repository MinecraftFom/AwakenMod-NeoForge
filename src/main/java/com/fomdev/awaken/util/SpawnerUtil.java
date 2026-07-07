package com.fomdev.awaken.util;

import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;;

public class SpawnerUtil
{
    public static boolean increaseUse(Level level, BlockPos pos)
    {
        if (!level.getBlockState(pos).is(Blocks.SPAWNER))
            return true;

        if (getMaxUse(level, pos) <= 0)
            setMaxUse(level, pos, 20);
        setUse(level, pos, getUse(level, pos) + 1);
        return getUse(level, pos) < getMaxUse(level, pos);
    }

    public static int getMaxUse(Level level, BlockPos pos)
    {
        BlockEntity entity = level.getBlockEntity(pos);
        if (!(entity instanceof SpawnerBlockEntity spawner))
            return -1;

        return spawner.getData(AwakenAttachmentTypes.SPAWNER_MAX_USE_ATTACHMENT);
    }

    public static int getUse(Level level, BlockPos pos)
    {
        BlockEntity entity = level.getBlockEntity(pos);
        if (!(entity instanceof SpawnerBlockEntity spawner))
            return -1;

        return spawner.getData(AwakenAttachmentTypes.SPAWNER_USE_ATTACHMENT);
    }

    public static void setMaxUse(Level level, BlockPos pos, int max)
    {
        BlockEntity entity = level.getBlockEntity(pos);
        if (!(entity instanceof SpawnerBlockEntity spawner))
            return;

        spawner.setData(AwakenAttachmentTypes.SPAWNER_MAX_USE_ATTACHMENT, max);
    }

    public static void setUse(Level level, BlockPos pos, int use)
    {
        BlockEntity entity = level.getBlockEntity(pos);
        if (!(entity instanceof SpawnerBlockEntity spawner))
            return;

        spawner.setData(AwakenAttachmentTypes.SPAWNER_USE_ATTACHMENT, use);
    }
}