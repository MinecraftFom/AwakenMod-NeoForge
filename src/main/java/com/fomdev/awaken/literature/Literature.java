package com.fomdev.awaken.literature;

public class Literature
{
    public static NameDictionary NAMES_INSTANCE;

    public static boolean initialized;

    /* WARNING: MUST ONLY CALL ONCE. MUST CALL AFTER MOD CONFIG BUILT */
    public static void init()
    {
        if (initialized)
            throw new IllegalStateException(
                    "Already initialized config"
            );

        initialized = true;
        NAMES_INSTANCE = new NameDictionary();
    }
}