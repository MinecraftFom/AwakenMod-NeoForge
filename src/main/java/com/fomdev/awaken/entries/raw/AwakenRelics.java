package com.fomdev.awaken.entries.raw;

import com.fomdev.awaken.entries.raw.affix.AwakenInfix;
import com.fomdev.awaken.entries.raw.affix.AwakenPrefix;
import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;

/* FUTURE UPDATES */
public record AwakenRelics(
        AwakenInfix.InfixInstance infix,
        AwakenPrefix.PrefixInstance prefix,
        AwakenSuffix.SuffixInstance suffix,
        AwakenQuality quality
)
{
}