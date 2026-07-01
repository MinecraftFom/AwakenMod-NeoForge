package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;
import com.fomdev.awaken.util.Records;

public class AwakenTitle extends Registry
{
    private final Records.AttributeHolder attribute;

    public AwakenTitle(
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
}