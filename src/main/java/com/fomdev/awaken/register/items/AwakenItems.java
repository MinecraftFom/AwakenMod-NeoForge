package com.fomdev.awaken.register.items;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.flame.annotation.AutoRegister;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@AutoRegister
public class AwakenItems
{
    @AutoRegister.Registrable
    public static final DeferredRegister.Items REGISTER =
            DeferredRegister.Items.createItems(
                    Awaken.MODID
            );

    public static final DeferredItem<?> ASPECT_STONE =
            REGISTER.registerItem(
                    "aspect_stone",
                    AwakenAspectStone::new
            );

    public static final DeferredItem<?> ATTRIBUTE_STONE =
            REGISTER.registerItem(
                    "attribute_stone",
                    Item::new
            );

    public static final DeferredItem<?> AWAKEN_ESSENCE =
            REGISTER.registerItem(
                    "awaken_essence",
                    Item::new
            );

    public static final DeferredItem<?> AWAKEN_INFIX_BOOK =
            REGISTER.registerItem(
                    "awaken_infix_book",
                    AwakenInfixBooks::new
            );

    public static final DeferredItem<?> AWAKEN_PREFIX_BOOK =
            REGISTER.registerItem(
                    "awaken_prefix_book",
                    AwakenPrefixBooks::new
            );

    public static final DeferredItem<?> AWAKEN_SUFFIX_BOOK =
            REGISTER.registerItem(
                    "awaken_suffix_book",
                    AwakenSuffixBooks::new
            );

    public static final DeferredItem<?> SOUL_BOTTLE =
            REGISTER.registerItem(
                    "soul_bottle",
                    AwakenSoulBottle::new
            );

    public static final DeferredItem<?> SOUL_FRAGMENT =
            REGISTER.registerItem(
                    "soul_fragment",
                    Item::new
            );

    public static final DeferredItem<?> UNKNOWN_AMULET =
            REGISTER.registerItem(
                    "unawaken_amulet",
                    prop -> new Item(prop.stacksTo(1))
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