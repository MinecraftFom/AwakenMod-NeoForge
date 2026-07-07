package com.fomdev.awaken.spawner;

import com.fomdev.awaken.register.blocks.AwakenBlockEntities;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AwakenSpawnerBlockEntity extends BlockEntity implements Spawner
{
    private final BaseSpawner spawner = new BaseSpawner()
    {
        public void broadcastEvent(Level level, @NotNull BlockPos pos, int p_155769_)
        {
            level.blockEvent(pos, Blocks.SPAWNER, p_155769_, 0);
        }

        public void setNextSpawnData(@Nullable Level level, @NotNull BlockPos pos, @NotNull SpawnData data)
        {
            super.setNextSpawnData(level, pos, data);
            if (level != null)
            {
                BlockState state = level.getBlockState(pos);
                level.sendBlockUpdated(pos, state, state, 4);
            }

        }

        public Either<BlockEntity, Entity> getOwner()
        {
            return Either.left(AwakenSpawnerBlockEntity.this);
        }
    };

    public AwakenSpawnerBlockEntity(BlockPos pos, BlockState state)
    {
        super(AwakenBlockEntities.BE_AWAKEN_SPAWNER.get(), pos, state);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider)
    {
        super.loadAdditional(tag, provider);
        this.spawner.load(this.level, this.worldPosition, tag);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider)
    {
        super.saveAdditional(tag, provider);
        this.spawner.save(tag);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, AwakenSpawnerBlockEntity entity)
    {
        entity.spawner.clientTick(level, pos);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AwakenSpawnerBlockEntity entity)
    {
        entity.spawner.serverTick((ServerLevel) level, pos);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    @NotNull
    public CompoundTag getUpdateTag(@NotNull HolderLookup.Provider provider)
    {
        CompoundTag compoundtag = this.saveCustomOnly(provider);
        compoundtag.remove("SpawnPotentials");
        return compoundtag;
    }

    @Override
    public boolean triggerEvent(int p_59797_, int p_59798_)
    {
        assert this.level != null;
        return this.spawner.onEventTriggered(this.level, p_59797_) || super.triggerEvent(p_59797_, p_59798_);
    }

    @Override
    public boolean onlyOpCanSetNbt()
    {
        return true;
    }

    @Override
    public void setEntityId(@NotNull EntityType<?> type, @NotNull RandomSource source)
    {
        this.spawner.setEntityId(type, this.level, source, this.worldPosition);
        this.setChanged();
    }

    public BaseSpawner getSpawner()
    {
        return this.spawner;
    }
}