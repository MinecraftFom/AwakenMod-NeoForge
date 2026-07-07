package com.fomdev.awaken.enchant;

import java.awt.*;
import java.util.List;

public class EnchantManager
{
    public static final List<Color> colors;

    static
    {
        colors = List.of(
                Color.BLACK,
                Color.DARK_GRAY,
                Color.GRAY,
                Color.LIGHT_GRAY,
                Color.WHITE,
                new Color(0xBF1718),
                new Color(0xE7C818),
                Color.ORANGE,
                Color.YELLOW,
                new Color(0xE7FF00),
                new Color(0x99E718),
                new Color(0x49E718),
                Color.GREEN,
                new Color(0x00FF35),
                new Color(0x00EFFF),
                Color.CYAN,
                Color.BLUE,
                new Color(0x4100FF),
                new Color(0x8A00FF),
                Color.MAGENTA
        );
    }
}