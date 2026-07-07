package com.fomdev.awaken.register.blocks;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spawner.AwakenSpawnerBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AwakenBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> REGISTER =
            DeferredRegister.create(
                    Registries.BLOCK_ENTITY_TYPE,
                    Awaken.MODID
            );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AwakenSpawnerBlockEntity>> BE_AWAKEN_SPAWNER =
            REGISTER.register("awaken_spawner",
                    () -> BlockEntityType.Builder.of(
                            AwakenSpawnerBlockEntity::new,
                            AwakenBlocks.BLOCK_AWAKEN_SPAWNER.get()
                    ).build(null)
            );

    public static void register(
            IEventBus bus
    )
    {
        REGISTER.register(bus);
    }
}