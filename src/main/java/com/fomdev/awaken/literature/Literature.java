package com.fomdev.awaken.literature;

import com.fomdev.awaken.init.config.AwakenCommon;

public class Literature
{
    public static NameDictionary NAMES_FIRST_INSTANCE;
    public static NameDictionary NAMES_LAST_INSTANCE;

    public static boolean initialized;

    /* WARNING: MUST ONLY CALL ONCE. MUST CALL AFTER MOD CONFIG BUILT */
    public static void init()
    {
        if (initialized)
            throw new IllegalStateException(
                    "Already initialized config"
            );

        initialized = true;
        NAMES_FIRST_INSTANCE = new NameDictionary(AwakenCommon.CONFIG.NAMES_FIRST.get());
        NAMES_LAST_INSTANCE = new NameDictionary(AwakenCommon.CONFIG.NAMES_LAST.get());
    }
}