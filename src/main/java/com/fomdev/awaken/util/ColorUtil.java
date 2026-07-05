package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.AwakenQuality;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

import java.awt.*;
import java.util.List;

import static java.lang.Math.clamp;

public class ColorUtil
{
    public static final int ALPHA = 0xA4;

    public static void apply(
            RenderTooltipEvent.Color event,
            Records.ColorHolder holder
    )
    {
        event.setBackgroundStart(holder.frontStart().getRGB());
        event.setBackgroundEnd(holder.frontEnd().getRGB());
        event.setBorderStart(holder.backStart().getRGB());
        event.setBorderEnd(holder.backEnd().getRGB());
    }

    public static Style colorStyle(
            Color color
    )
    {
        return Style.EMPTY.withColor(TextColor.fromRgb(color.getRGB()));
    }

    public static Color opposite(
            Color color
    )
    {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        return new Color(255 - red, 255 - green, 255 - blue);
    }

    public static Records.ColorHolder render(
            List<Color> colors,
            AwakenQuality.ColorPattern pattern
    )
    {
        return switch (pattern)
        {
            case SINGLE -> renderSingle(colors.getFirst());
            case MULTIPLE -> renderMultiple(colors);
            case CONTINUE -> renderContinue(colors.get(0), colors.get(1));
        };
    }

    public static Records.ColorHolder renderContinue(
            Color c0,
            Color c1
    )
    {
//        if (c0.getRGB() == c1.getRGB())
//            return new Records.ColorHolder(c0, opposite(c0));
//
//        int red0 = c0.getRed();
//        int green0 = c0.getGreen();
//        int blue0 = c0.getBlue();
//        int red1 = c1.getRed();
//        int green1 = c1.getGreen();
//        int blue1 = c1.getBlue();
//
//        int distRed = Math.abs(red0 - red1);
//        int distGreen = Math.abs(green0 - green1);
//        int distBlue = Math.abs(blue0 - blue1);
//
//        long millis = System.currentTimeMillis();
//
//        int offsetRed = distRed == 0? distRed: Math.toIntExact(millis % distRed);
//        int offsetGreen = distGreen == 0? distGreen: Math.toIntExact(millis % distGreen);
//        int offsetBlue = distBlue == 0? distBlue: Math.toIntExact(millis % distBlue);
//
//        if (c0.getRGB() > c1.getRGB())
//        {
//            Color startFront = new Color(validate(red0 - offsetRed), validate(green0 - offsetGreen), validate(blue0 - offsetBlue), ALPHA);
//            Color startEnd = new Color(validate(red1 + offsetRed), validate(green1 + offsetGreen), validate(blue1 + offsetBlue));
//
//            return new Records.ColorHolder(startFront, startEnd, opposite(startFront), opposite(startEnd));
//        } else
//        {
//            Color startFront = new Color(validate(red0 + offsetRed), validate(green0 + offsetGreen), validate(blue0 + offsetBlue), ALPHA);
//            Color startEnd = new Color(validate(red1 - offsetRed), validate(green1 - offsetGreen), validate(blue1 - offsetBlue));
//
//            return new Records.ColorHolder(startFront, startEnd, opposite(startFront), opposite(startEnd));
//        }
        long millis = System.currentTimeMillis();

        float offStart = (float) (Math.sin(millis * 0.001) + 1) / 2;
        float offEnd = (float) (Math.sin(millis * 0.0012 + Math.PI) + 1) / 2;

        int distRed = c1.getRed() - c0.getRed();
        int distGreen = c1.getGreen() - c0.getGreen();
        int distBlue = c1.getBlue() - c0.getBlue();

        int startRed = clamp((int) (c0.getRed() + distRed * offStart), 0, 255);
        int startGreen = clamp((int) (c0.getGreen() + distGreen * offStart), 0, 255);
        int startBlue = clamp((int) (c0.getBlue() + distBlue * offStart), 0, 255);

        int endRed = clamp((int) (c1.getRed() - distRed * offEnd), 0, 255);
        int endGreen = clamp((int) (c1.getGreen() - distGreen * offEnd), 0, 255);
        int endBlue = clamp((int) (c1.getBlue() - distBlue * offEnd), 0, 255);

        Color frontStart = new Color(startRed, startGreen, startBlue, ALPHA);
        Color frontEnd = new Color(endRed, endGreen, endBlue, ALPHA);

        return new Records.ColorHolder(frontStart, frontEnd, opposite(frontStart), opposite(frontEnd));
    }

    public static Records.ColorHolder renderMultiple(
            List<Color> colors
    )
    {
        long millis = System.currentTimeMillis() / 100; // Get the timer

        if (colors.isEmpty())
            return null;

        if (colors.size() == 1)
        {
            Color color = colors.getFirst();
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();

            Color front = new Color(red, green, blue, ALPHA);

            return new Records.ColorHolder(front, opposite(front));
        }

        int duration = 10;
        int cycleTime = duration * colors.size();
        long currentCycleTime = millis % cycleTime;

        int cInd = (int) (currentCycleTime / duration);
        float prog = (float) (currentCycleTime % duration) / duration;

        Color c0 = colors.get(cInd);
        Color c1 = colors.get((cInd + 1) % colors.size());

        int distRed = (c1.getRed() - c0.getRed());
        int distGreen = (c1.getGreen() - c0.getGreen());
        int distBlue = (c1.getBlue() - c0.getBlue());

        int red = (int) (c0.getRed() + distRed * prog);
        int green = (int) (c0.getGreen() + distGreen * prog);
        int blue = (int) (c0.getBlue() + distBlue * prog);

        Color front = new Color(red, green, blue, ALPHA);

        return new Records.ColorHolder(front, opposite(front));
    }

    public static Records.ColorHolder renderSingle(
            Color color
    )
    {
        return new Records.ColorHolder(color, opposite(color));
    }

    public static int validate(
            int color
    )
    {
        if (color < 0)
            return Math.abs(color);

        if (color > 255)
            return color - 255;

        return color;
    }
}