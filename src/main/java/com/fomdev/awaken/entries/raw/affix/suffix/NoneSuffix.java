package com.fomdev.awaken.entries.raw.affix.suffix;

import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.util.Constants;
import net.neoforged.bus.api.Event;

public class NoneSuffix extends AwakenSuffix<Event>
{
    public static final AwakenSuffix<Event> NONE =
            new NoneSuffix(
                    "NULL",
                    -1
            );

    public NoneSuffix(
            String id,
            int durability
    )
    {
        super(id, durability);
    }

    @Override
    public void onEvent(Event event)
    {
    }

    static
    {
        NONE.setLocation(Constants.NULL);
    }
}