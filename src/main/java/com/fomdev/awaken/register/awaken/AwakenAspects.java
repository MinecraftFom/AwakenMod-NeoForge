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

    public static final AwakenAspect ASPECT_DIVERSITY = register("diversity", Color.WHITE); // Placeholder for un-registered enchantments, default requirement is 100

    public static void init()
    {
        register("abyss", "advent", "ascend", "blood", "cell", "death", "destiny", "doom", "earth", "fate", "faith", "grace", "greed", "hearty", "humanity", "ice", "kill", "liquidize", "luxury", "misery", "natural", "oomph", "peace", "quality", "rock", "sanity", "soul", "truth", "vintage", "vividity", "void", "water", "world", "zen");
    }

    public static void register(
            String... values
    )
    {
        int max = values.length;
        float per = 360.0F / max;

        for (int i = 0; i < max; i++)
        {
            float ch = i * per;
            Color color = Color.getHSBColor(ch, 1.0F, 1.0F);
            register(values[i], color);
        }
    }

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
    public static void register()
    {
        init();
        REGISTRY.register();
    }
}