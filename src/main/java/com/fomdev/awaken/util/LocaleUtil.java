package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.AwakenInfix;
import com.fomdev.awaken.entries.AwakenPrefix;
import com.fomdev.awaken.entries.AwakenQuality;
import com.fomdev.awaken.entries.AwakenSuffix;
import net.minecraft.network.chat.Component;

public class LocaleUtil
{
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