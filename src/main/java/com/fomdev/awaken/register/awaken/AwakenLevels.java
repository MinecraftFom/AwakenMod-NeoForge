package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.level.AwakenLevel;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;

import java.awt.*;
import java.math.BigDecimal;

@AutoProxy
public class AwakenLevels
{
    public static final RegistryTable<AwakenLevel> REGISTRY =
            new RegistryTable<>(
                    Awaken.MODID,
                    AwakenRegistries.AWAKEN_LEVEL
            );

    public static void init()
    {
        register(
                0.0F,
                10.0F,
                5.0F,
                "unawaken", "naive", "deeper", "learner", "skilled", "mature", "expert", "honored", "strengthened", "reinforced", "master", "brilliant", "out-standing", "successor", "uptown", "centralized", "awaken"
        );
    }

    public static void register(
            float start,
            float distance,
            float factor,
            String... values
    )
    {
        int max = values.length;
        float per = 360.0F / max;

        double current;
        double last = start;

        for (int i = 0; i < max; i++)
        {
            float ch = per * i;
            Color color = Color.getHSBColor(ch, 1.0F, 1.0F);
            register(new AwakenLevel(values[i], color, new BigDecimal(last), i * factor));

            current = last + distance * Math.pow(factor * i, 2);
            last = current;
        }
    }

    public static AwakenLevel register(
            AwakenLevel level
    )
    {
        return REGISTRY.register(level);
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        init();
        REGISTRY.register();
    }
}