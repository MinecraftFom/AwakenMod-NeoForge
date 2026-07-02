package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class AwakenQuality extends Registry
{
    private final List<Color> colors;
    private final Double factor;
    private final ColorPattern pattern;

    public AwakenQuality(
            String id,
            List<Color> colors,
            Double factor,
            ColorPattern pattern
    )
    {
        super(id);

        this.colors = new ArrayList<>(colors);
        this.factor = factor;
        this.pattern = pattern;
    }

    public List<Color> getColors()
    {
        return new ArrayList<>(this.colors); // Prevent modification
    }

    public double getFactor()
    {
        return this.factor;
    }

    public ColorPattern getPattern()
    {
        return this.pattern;
    }

    public enum ColorPattern
    {
        SINGLE,
        MULTIPLE,
        CONTINUE
    }
}