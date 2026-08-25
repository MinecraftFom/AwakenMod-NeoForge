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

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicReference;

public class RankHelper
{
    public static BigDecimal calculateRank(
            Level level,
            BlockPos pos
    )
    {
        ResourceLocation dimension = level.dimension().location();
        Float diff = DifficultyManager.dimensionFactor.get(dimension);
        if (diff == null)
            return new BigDecimal("0.0");

        return calculateRank(
                diff,
                dimension,
                level,
                pos
        );
    }

    public static BigDecimal calculateRank(
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
            return new BigDecimal("0.0");

        return new BigDecimal(diffFactor * dimFactor * blockFactor).abs();
    }

    public static <T extends LivingEntity> BigDecimal getRank(
            T entity
    )
    {
        BigDecimal base = NBTUtil.deserializeAwakenLevel(entity);
        for (EquipmentSlot slot: EquipmentSlot.values())
            base = base.multiply(new BigDecimal(processItemStack(entity.getItemBySlot(slot))));

        return base.setScale(2, RoundingMode.HALF_UP).abs();
    }

    public static BigDecimal randomizeRank(
            ServerLevel level,
            float factor,
            RandomSource random
    )
    {
        AtomicReference<BigDecimal> base = new AtomicReference<>(new BigDecimal("0.0"));
        level.players().forEach(p -> base.updateAndGet(v -> v.add(NBTUtil.deserializeAwakenLevel(p))));
        BigDecimal factor2 = base.get();

        if (factor2.compareTo(new BigDecimal("0")) <= 0)
            return new BigDecimal(factor);

        BigDecimal factor3 = new BigDecimal(random.nextFloat());
        return new BigDecimal(factor).multiply(
                factor3
                        .remainder(factor2)
                        .sqrt(new MathContext(2))
                        .sqrt(new MathContext(2))
                        .sqrt(new MathContext(2))
                        .sqrt(new MathContext(2))
                )
                .abs();
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