package com.fomdev.awaken.spawner;

import com.fomdev.awaken.register.blocks.AwakenBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AwakenSpawner extends SpawnerBlock
{
    public AwakenSpawner(Properties properties)
    {
        super(properties);
    }

    @NotNull
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state)
    {
        return new AwakenSpawnerBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type)
    {
        return createTickerHelper(type, AwakenBlockEntities.BE_AWAKEN_SPAWNER.get(), level.isClientSide? AwakenSpawnerBlockEntity::clientTick : AwakenSpawnerBlockEntity::serverTick);
    }
}