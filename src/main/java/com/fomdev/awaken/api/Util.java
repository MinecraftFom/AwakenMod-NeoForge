package com.fomdev.awaken.api;

public class Util
{
    public static String format(
            String str,
            Object... args
    )
    {
        String value = str;

        for (Object argv: args)
            value = value.replaceFirst("\\{}", argv.toString());

        return value;
    }
}