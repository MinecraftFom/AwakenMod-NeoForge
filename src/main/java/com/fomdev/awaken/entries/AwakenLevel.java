package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;

import java.awt.*;

public class AwakenLevel extends Registry
{
    private final Color color;
    private final Double min;
    private final Float health;

    public AwakenLevel(
            String id,
            Color color,
            Double min,
            Float health
    )
    {
        super(id);

        this.color = color;
        this.min = min;
        this.health = health;
    }

    public Color getColor()
    {
        return this.color;
    }

    public Double getMin()
    {
        return this.min;
    }

    public Float getHealth()
    {
        return this.health;
    }
}