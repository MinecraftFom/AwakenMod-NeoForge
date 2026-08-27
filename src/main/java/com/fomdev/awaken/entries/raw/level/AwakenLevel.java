package com.fomdev.awaken.entries.raw.level;

import com.fomdev.flame.register.Registry;

import java.awt.*;
import java.math.BigDecimal;

public class AwakenLevel extends Registry
{
    private final Color color;
    private final BigDecimal min;
    private final Float health;

    public AwakenLevel(
            String id,
            Color color,
            BigDecimal min,
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

    public BigDecimal getMin()
    {
        return this.min;
    }

    public Float getHealth()
    {
        return this.health;
    }
}