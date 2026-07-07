package com.fomdev.awaken.register.data;

import com.fomdev.awaken.init.Awaken;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AwakenAttachmentTypes
{
    public static final DeferredRegister<AttachmentType<?>> REGISTER =
            DeferredRegister.create(
                    NeoForgeRegistries.ATTACHMENT_TYPES,
                    Awaken.MODID
            );

    public static final Supplier<AttachmentType<Integer>> SPAWNER_MAX_USE_ATTACHMENT =
            REGISTER.register("spawner_max_use",
                    () -> AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT)
                            .build()
            );

    public static final Supplier<AttachmentType<Integer>> SPAWNER_USE_ATTACHMENT =
            REGISTER.register("spawner_use",
                    () -> AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT)
                            .build()
            );

    public static void register(
            IEventBus bus
    )
    {
        REGISTER.register(bus);
    }
}