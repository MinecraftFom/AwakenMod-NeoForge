package com.fomdev.awaken.entries.raw;

/* FUTURE UPDATES */
public record AwakenRelics(
        AwakenInfix.InfixInstance infix,
        AwakenPrefix.PrefixInstance prefix,
        AwakenSuffix.SuffixInstance suffix,
        AwakenQuality quality
)
{
}