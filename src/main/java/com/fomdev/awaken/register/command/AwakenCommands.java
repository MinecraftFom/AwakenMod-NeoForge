package com.fomdev.awaken.register.command;

import com.fomdev.awaken.command.AwakenStringCommand;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.flame.annotation.AutoRegister;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@AutoRegister
public class AwakenCommands
{
    @AutoRegister.Registrable
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> REGISTER =
            DeferredRegister.create(
                    Registries.COMMAND_ARGUMENT_TYPE,
                    Awaken.MODID
            );

    public static final Supplier<ArgumentTypeInfo<?, ?>> ENUMS;

    static
    {
        ENUMS = REGISTER.register("string_enum", () -> ArgumentTypeInfos.registerByClass(AwakenStringCommand.class, new AwakenStringCommand.AwakenStringCommandInfo()));
    }
}