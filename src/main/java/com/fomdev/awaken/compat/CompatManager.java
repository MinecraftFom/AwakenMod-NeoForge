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
            InfixAddition.activeIfTFInstalled();
            ItemAddition.activeIfTFInstalled();
            EntityAddition.activeIfTFInstalled();
            Awaken.LOGGER.info("Hello. Nice to meet you! Dealt telepathy to mod Twilight-Forest!");
        }

        if (ModList.get().isLoaded("aoa3")) // I hadn't played Aoa3. This was requested from co-developer @modic_M.
        {
            InfixAddition.activeIfAoa3Installed();
            ItemAddition.activeIfAoa3Installed();
            EntityAddition.activeIfAoa3Installed();
            Awaken.LOGGER.info("Hello. Nice to meet you! Dealt telepathy to mod AoA3!");
        }

        if (ModList.get().isLoaded("cataclysm"))
        {
            InfixAddition.activeIfCataclysmInstalled();
            ItemAddition.activeIfCataclysmInstalled();
            EntityAddition.activeIfCataclysmInstalled();
            Awaken.LOGGER.info("Hello. Nice to meet you! Dealt telepathy to mod L_Ender's Cataclysm!");
        }

        if (ModList.get().isLoaded("irons_spellbooks"))
        {
            InfixAddition.activeIfIronSpellbookInstalled();
            Awaken.LOGGER.info("Hello. Nice to meet you! Dealt telepathy to mod Iron's SpellBooks!");
        }

        if (ModList.get().isLoaded("apothic_attributes"))
        {
            InfixAddition.activeIfApotheosisInstalled();
            Awaken.LOGGER.info(".");
        }

        InfixAddition.init();
    }
}