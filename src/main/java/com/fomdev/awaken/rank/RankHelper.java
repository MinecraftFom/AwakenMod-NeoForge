package com.fomdev.awaken.rank;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.entries.raw.AwakenPrefix;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicReference;

public class RankHelper
{
    public static float calculateRank(
            Level level,
            BlockPos pos
    )
    {
        ResourceLocation dimension = level.dimension().location();
        Float diff = DifficultyManager.dimensionFactor.get(dimension);
        if (diff == null)
            return 0.0F;

        return calculateRank(
                diff,
                dimension,
                level,
                pos
        );
    }

    public static float calculateRank(
            float diff,
            ResourceLocation dimension,
            Level level,
            BlockPos pos
    )
    {
        Float dimFactor = DifficultyManager.dimensionFactor.get(dimension);
        Float blockFactor = level.getBlockState(pos).getDestroySpeed(level, pos);
        Float diffFactor = (float) Math.pow(diff, 1.0 / 20.0);

        if (Util.ifNull(dimFactor, blockFactor, diffFactor))
            return 0.0F;

        return diffFactor * dimFactor * blockFactor;
    }

    public static <T extends LivingEntity> float getRank(
            T entity
    )
    {
        float base = NBTUtil.deserializeAwakenLevel(entity);
        for (EquipmentSlot slot: EquipmentSlot.values())
            base *= processItemStack(entity.getItemBySlot(slot));

        return base;
    }

    public static float randomizeRank(
            ServerLevel level,
            float factor,
            RandomSource random
    )
    {
        AtomicReference<Float> base = new AtomicReference<>(0.0F);
        level.players().forEach(p -> base.updateAndGet(v -> v + NBTUtil.deserializeAwakenLevel(p)));
        float factor2 = base.get();

        if (factor2 < 0)
            return factor;

        return factor * (random.nextFloat() % factor2);
    }

    private static float processItemStack(
            ItemStack stack
    )
    {
        if (stack.isEmpty())
            return 1.0F;

        AwakenPrefix prefix = NBTUtil.deserializePrefix(stack);
        return prefix == null? 1.0F: prefix.getRankFactor();
    }
}