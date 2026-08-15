package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenInfix;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.awaken.util.Constants;
import com.fomdev.awaken.util.Records;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Arrays;

@AutoProxy
public class AwakenInfixes
{
    public static final RegistryTable<AwakenInfix> REGISTRY =
            new RegistryTable<>(Awaken.MODID, AwakenRegistries.AWAKEN_INFIX);

    public static final AwakenInfix INFIX_NORMAL =
            register(
                    new AwakenInfix(
                            "normal",
                            new Records.AttributeHolder(
                                    Attributes.ARMOR,
                                    1.0F,
                                    AttributeModifier.Operation.ADD_VALUE,
                                    Constants.BODY_SLOTS
                            )
                    ),
                    50.0F,
                    0.0F
            );

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        REGISTRY.register();
    }

    private static AwakenInfix register(
            AwakenInfix infix,
            float chance,
            float minDiff
    )
    {
        REGISTRY.register(infix);
        Arrays.stream(infix.getAttribute().slot()).forEach(s -> ShuffledRegistries.WEIGHTED_AWAKEN_INFIX.push(infix, s, chance, minDiff));
        return infix;
    }
}