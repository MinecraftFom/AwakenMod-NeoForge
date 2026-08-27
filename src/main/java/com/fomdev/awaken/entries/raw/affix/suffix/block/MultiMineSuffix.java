package com.fomdev.awaken.entries.raw.affix.suffix.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.level.BlockEvent;

public class MultiMineSuffix extends BlockBaseSuffix<BlockEvent.BreakEvent>
{
    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;

    // THE SIZE_X, SIZE_Y, SIZE_Z ARE RADIUS!!!
    public MultiMineSuffix(
            String id,
            int sizeX,
            int sizeY,
            int sizeZ,
            int durability
    )
    {
        super(
                id,
                durability,
                Component.translatable(
                        "tooltip.suffix.multimine.info",
                        sizeX,
                        sizeY,
                        sizeZ
                )
        );

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
    }

    @Override
    public void onEvent(
            BlockEvent.BreakEvent event
    )
    {
        BlockPos center = event.getPos();
        Player player = event.getPlayer();
        Direction direction = player.getDirection();
        Level level = player.level();

        if (direction == Direction.UP || direction == Direction.DOWN)
            facingVertical(player, level, direction, center);
        else
            facingHorizontal(player, level, direction, center);
    }

    private void facingHorizontal(
            Player player,
            Level level,
            Direction direction,
            BlockPos central
    )
    {
        Direction left = direction.getCounterClockWise();
        Direction right = direction.getClockWise();

        BlockPos topLeft = central.above(sizeY).relative(left, sizeX);
        BlockPos bottomRight = central.below(sizeY).relative(right, sizeX).relative(direction, sizeZ * 2);

        for (BlockPos pos: BlockPos.betweenClosed(topLeft, bottomRight))
            level.destroyBlock(pos, true, player);
    }

    private void facingVertical(
            Player player,
            Level level,
            Direction direction,
            BlockPos central
    )
    {
        BlockPos topLeft = central.north(sizeX).west(sizeZ);
        BlockPos bottomRight = central.south(sizeX).east(sizeZ).relative(direction, sizeY * 2);

        for (BlockPos pos: BlockPos.betweenClosed(topLeft, bottomRight))
            level.destroyBlock(pos, true, player);
    }
}