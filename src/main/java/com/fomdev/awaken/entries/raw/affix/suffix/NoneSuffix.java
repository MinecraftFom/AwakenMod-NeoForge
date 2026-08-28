package com.fomdev.awaken.entries.raw.affix.suffix;

import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.util.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.Map;

public class NoneSuffix extends AwakenSuffix
{
    public static final AwakenSuffix NONE =
            new NoneSuffix(
                    "NULL"
            );

    public NoneSuffix(
            String id
    )
    {
        super(id, Collections.emptyList());
    }


    @Override
    public Component getDescription(Map<String, String> args)
    {
        return Component.empty();
    }

    @Override
    public void execute(ItemStack stack, Level level, BlockPos pos, Player player, Map<String, String> args)
    {
    }

    @Override
    public Map<String, String> randomize(float diff, float factor, RandomSource random)
    {
        return Map.of();
    }

    static
    {
        NONE.setLocation(Constants.NULL);
    }
}