package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenAspect;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;

import java.awt.*;

@AutoProxy
public class AwakenAspects
{
    public static final RegistryTable<AwakenAspect> REGISTRY =
            new RegistryTable<>(
                    Awaken.MODID,
                    AwakenRegistries.AWAKEN_ASPECT
            );

    public static final AwakenAspect ASPECT_BLOOD = register("blood", Color.RED);
    public static final AwakenAspect ASPECT_DEATH = register("death", Color.BLACK);
    public static final AwakenAspect ASPECT_DIVERSITY = register("diversity", Color.WHITE); // Placeholder for un-registered enchantments, default requirement is 100
    public static final AwakenAspect ASPECT_HUMANITY = register("humanity", Color.GREEN);
    public static final AwakenAspect ASPECT_INSANITY = register("insanity", Color.PINK);
    public static final AwakenAspect ASPECT_LONELINESS = register("loneliness", Color.LIGHT_GRAY); // Sa Bi Shi I
    public static final AwakenAspect ASPECT_NATURAL = register("natural", Color.CYAN);
    public static final AwakenAspect ASPECT_SPIRITUAL = register("spiritual", Color.BLUE);
    public static final AwakenAspect ASPECT_VIVIDITY = register("vividity", Color.GREEN);
    public static final AwakenAspect ASPECT_VOID = register("void", new Color(255, 255, 255, 0));
    public static final AwakenAspect ASPECT_WATER = register("water", Color.BLUE);

    public static AwakenAspect register(
            String id,
            Color color
    )
    {
        return REGISTRY.register(
                new AwakenAspect(id, color)
        );
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void onRegister()
    {
        REGISTRY.register();
    }
}