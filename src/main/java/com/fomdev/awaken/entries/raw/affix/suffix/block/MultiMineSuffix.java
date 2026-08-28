package com.fomdev.awaken.entries.raw.affix.suffix.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class MultiMineSuffix extends BlockBaseSuffix
{
    // THE SIZE_X, SIZE_Y, SIZE_Z ARE RADIUS!!!
    public MultiMineSuffix(
            String id
    )
    {
        super(id);
    }

    private void facingHorizontal(
            Entity entity,
            Level level,
            Direction direction,
            BlockPos central,
            int sizeX,
            int sizeY,
            int sizeZ
    )
    {
        Direction left = direction.getCounterClockWise();
        Direction right = direction.getClockWise();

        BlockPos topLeft = central.above(sizeY).relative(left, sizeX);
        BlockPos bottomRight = central.below(sizeY).relative(right, sizeX).relative(direction, sizeZ * 2);

        for (BlockPos pos: BlockPos.betweenClosed(topLeft, bottomRight))
            level.destroyBlock(pos, true, entity);
    }

    private void facingVertical(
            Entity entity,
            Level level,
            Direction direction,
            BlockPos central,
            int sizeX,
            int sizeY,
            int sizeZ
    )
    {
        BlockPos topLeft = central.north(sizeX).west(sizeZ);
        BlockPos bottomRight = central.south(sizeX).east(sizeZ).relative(direction, sizeY * 2);

        for (BlockPos pos: BlockPos.betweenClosed(topLeft, bottomRight))
            level.destroyBlock(pos, true, entity);
    }

    @Override
    public Component getDescription(Map<String, String> args)
    {
        return null;
    }

    @Override
    public void executeAsDigger(ItemStack stack, Entity entity, BlockPos pos, BlockState state, Map<String, String> args)
    {
        Direction direction = entity.getDirection();

        int sizeX = Integer.parseInt(args.get("sizeX"));
        int sizeY = Integer.parseInt(args.get("sizeY"));
        int sizeZ = Integer.parseInt(args.get("sizeZ"));

        if (direction == Direction.UP || direction == Direction.DOWN)
            facingVertical(entity, entity.level(), direction, pos, sizeX, sizeY, sizeZ);
        else
            facingHorizontal(entity, entity.level(), direction, pos, sizeX, sizeY, sizeZ);
    }

    @Override
    public Map<String, String> randomize(float diff, float factor, RandomSource random)
    {
        return Map.of(
                "sizeX",
                "" + random.nextInt((int) (factor * 2)),
                "sizeY",
                "" + random.nextInt((int) (factor * 2)),
                "sizeZ",
                "" + random.nextInt((int) (factor * 2))
        );
    }
}