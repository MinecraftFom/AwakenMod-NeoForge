package com.fomdev.awaken.compat;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.flame.annotation.AutoProxy;
import net.neoforged.fml.ModList;

@AutoProxy
public class CompatManager
{
    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.FML_SETUP)
    public static void init()
    {
        if (ModList.get().isLoaded("twilightforest"))
        {
            ItemAddition.activeIfTFInstalled();
            EntityAddition.activeIfTFInstalled();
            Awaken.LOGGER.info("Hello. Nice to meet you! Dealt telepathy to mod Twilight-Forest!");
        }
    }
}