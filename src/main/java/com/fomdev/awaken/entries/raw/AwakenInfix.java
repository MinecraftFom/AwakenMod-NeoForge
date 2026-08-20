package com.fomdev.awaken.entries.raw;

import com.fomdev.awaken.util.Records;
import com.fomdev.flame.register.Registry;

public class AwakenInfix extends Registry
{
    private final Records.AttributeHolder attribute;

    public AwakenInfix(
            String id,
            Records.AttributeHolder attribute
    )
    {
        super(id);

        this.attribute = attribute;
    }

    public Records.AttributeHolder getAttribute()
    {
        return this.attribute;
    }

    public static class InfixInstance extends AwakenInfix
    {
        private final int level;

        public InfixInstance(
                AwakenInfix parent,
                int level
        )
        {
            super(parent.id(), new Records.AttributeHolder(parent.getAttribute().attr(), parent.getAttribute().amount() * level / 2, parent.getAttribute().operation(), parent.getAttribute().slot()));
            this.level = level;
            setLocation(parent.getLocation());
        }

        public int getLevel()
        {
            return this.level;
        }
    }
}