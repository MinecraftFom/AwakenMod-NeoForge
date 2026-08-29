package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenMoods;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;
import net.minecraft.world.entity.ai.attributes.Attributes;

@AutoProxy
public class AwakenMoodses
{
    public static final RegistryTable<AwakenMoods> REGISTRY =
            new RegistryTable<>(
                    Awaken.MODID,
                    AwakenRegistries.AWAKEN_MOOD
            );

    public static void init()
    {
        register(new AwakenMoods("sad", Attributes.MAX_ABSORPTION, 1.5F, Attributes.ARMOR, 1.5F, 1)); // I don't like speaking...
        register(new AwakenMoods("normal", Attributes.ATTACK_SPEED, 1.25F, Attributes.ATTACK_KNOCKBACK, 1.25F, 5));
        register(new AwakenMoods("happy", Attributes.MOVEMENT_SPEED, 1.25F, Attributes.ATTACK_DAMAGE, 1.5F, 20));
        register(new AwakenMoods("fury", Attributes.ATTACK_DAMAGE, 2.0F, Attributes.ATTACK_SPEED, 2.5F, 10)); // F**k YOU!
    }

    public static AwakenMoods register(
            AwakenMoods moods
    )
    {
        return REGISTRY.register(moods);
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        init();
        REGISTRY.register();
    }
}