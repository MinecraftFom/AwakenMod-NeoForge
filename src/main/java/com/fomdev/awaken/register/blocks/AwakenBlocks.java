package com.fomdev.awaken.register.blocks;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spawner.AwakenSpawner;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AwakenBlocks
{
    public static final DeferredRegister.Blocks REGISTER =
            DeferredRegister.Blocks.createBlocks(Awaken.MODID);

    public static final DeferredHolder<Block, Block> BLOCK_AWAKEN_SPAWNER =
            REGISTER.registerBlock(
                    "awaken_spawner",
                    AwakenSpawner::new
            );

    public static void register(
            IEventBus bus
    )
    {
        REGISTER.register(bus);
    }
}