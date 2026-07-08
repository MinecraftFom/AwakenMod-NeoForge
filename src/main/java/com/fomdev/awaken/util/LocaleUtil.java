package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.*;
import net.minecraft.network.chat.Component;

public class LocaleUtil
{
    public static Component localizeAwakenLevel(
            AwakenLevel level
    )
    {
        return Component.translatable("level." + level.id() + ".name");
    }

    public static Component localizeInfix(
            AwakenInfix infix
    )
    {
        return Component.translatable("infix." + infix.id() + ".name");
    }

    public static Component localizePrefix(
            AwakenPrefix prefix
    )
    {
        return Component.translatable("prefix." + prefix.id() + ".name");
    }

    public static Component localizeQuality(
            AwakenQuality quality
    )
    {
        return Component.translatable("quality." + quality.id() + ".name");
    }

    public static Component localizeSuffix(
            AwakenSuffix suffix
    )
    {
        return Component.translatable("suffix." + suffix.id() + ".name");
    }
}