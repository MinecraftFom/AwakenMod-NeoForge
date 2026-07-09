package com.fomdev.awaken.register.items;

import com.fomdev.awaken.init.Awaken;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AwakenItems
{
    public static final DeferredRegister.Items REGISTER =
            DeferredRegister.Items.createItems(
                    Awaken.MODID
            );

    public static final DeferredItem<?> UNKNOWN_ITEM =
            REGISTER.registerItem(
                    "unawaken_item",
                    Item::new
            );

    public static void register(
            IEventBus bus
    )
    {
        REGISTER.register(bus);
    }
}