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

        if (ModList.get().isLoaded("aoa3")) // I hadn't played Aoa3. This was requested from co-developer @modic_M.
        {
            ItemAddition.activeIfAoa3Installed();
            EntityAddition.activeIfAoa3Installed();
            Awaken.LOGGER.info("Hello. Nice to meet you! Dealt telepathy to mod AoA3!");
        }

        if (ModList.get().isLoaded("cataclysm"))
        {
            ItemAddition.activeIfCataclysmInstalled();
            EntityAddition.activeIfCataclysmInstalled();
            Awaken.LOGGER.info("Hello. Nice to meet you! Dealt telepathy to mod Cataclysm");
        }
    }
}