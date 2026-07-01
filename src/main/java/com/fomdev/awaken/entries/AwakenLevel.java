package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;

import java.awt.*;

public class AwakenLevel extends Registry
{
    private final Color color;
    private final Double min;

    public AwakenLevel(
            String id,
            Color color,
            Double min
    )
    {
        super(id);

        this.color = color;
        this.min = min;
    }

    public Color getColor()
    {
        return this.color;
    }

    public Double getMin()
    {
        return this.min;
    }
}