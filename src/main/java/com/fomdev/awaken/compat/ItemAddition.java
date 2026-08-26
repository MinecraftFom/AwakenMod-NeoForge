package com.fomdev.awaken.compat;

import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Unbreakable;
import twilightforest.init.TFItems;

public class ItemAddition
{
    public static void activeIfTFInstalled()
    {
        registerHead(TFItems.IRONWOOD_HELMET.toStack(), 100.0F, 0.0F);
        registerHead(TFItems.STEELEAF_HELMET.toStack(), 95.0F, 50.0F);
        registerHead(TFItems.KNIGHTMETAL_HELMET.toStack(), 50.0F, 500.0F);
        registerHead(TFItems.FIERY_HELMET.toStack(), 40.0F, 1000.0F);
        registerHead(TFItems.ARCTIC_HELMET.toStack(), 40.0F, 1000.0F);
        registerHead(TFItems.YETI_HELMET.toStack(), 10.0F, 10000.0F);
        registerHead(TFItems.PHANTOM_HELMET.toStack(), 10.0F, 10000.0F);
        registerHead(TFItems.TRAVELLERS_GOGGLES.toStack(), 5.0F, 20000.0F);
        registerChest(TFItems.IRONWOOD_CHESTPLATE.toStack(), 100.F, 0.0F);
        registerChest(TFItems.STEELEAF_CHESTPLATE.toStack(), 95.0F, 50.0F);
        registerChest(TFItems.KNIGHTMETAL_CHESTPLATE.toStack(), 50.0F, 500.0F);
        registerChest(TFItems.FIERY_CHESTPLATE.toStack(), 40.0F, 1000.0F);
        registerChest(TFItems.ARCTIC_CHESTPLATE.toStack(), 40.0F, 1000.0F);
        registerChest(TFItems.YETI_CHESTPLATE.toStack(), 10.0F, 10000.0F);
        registerChest(TFItems.PHANTOM_CHESTPLATE.toStack(), 10.0F, 10000.0F);
        registerChest(TFItems.NAGA_CHESTPLATE.toStack(), 10.0F, 10000.0F);
        registerChest(TFItems.TRAVELLERS_VEST.toStack(), 5.0F, 20000.0F);
        registerLegs(TFItems.IRONWOOD_LEGGINGS.toStack(), 100.0F, 0.0F);
        registerLegs(TFItems.STEELEAF_LEGGINGS.toStack(), 95.0F, 50.0F);
        registerLegs(TFItems.KNIGHTMETAL_LEGGINGS.toStack(), 50.0F, 500.0F);
        registerLegs(TFItems.FIERY_LEGGINGS.toStack(), 40.0F, 1000.0F);
        registerLegs(TFItems.ARCTIC_LEGGINGS.toStack(), 40.0F, 1000.0F);
        registerLegs(TFItems.YETI_LEGGINGS.toStack(), 10.0F, 10000.0F);
        registerLegs(TFItems.NAGA_LEGGINGS.toStack(), 10.0F, 10000.0F);
        registerLegs(TFItems.TRAVELLERS_WINGS.toStack(), 5.0F, 20000.0F);
        registerFeet(TFItems.IRONWOOD_BOOTS.toStack(), 100.0F, 0.0F);
        registerFeet(TFItems.STEELEAF_BOOTS.toStack(), 95.0F, 50.0F);
        registerFeet(TFItems.KNIGHTMETAL_BOOTS.toStack(), 50.0F, 500.0F);
        registerFeet(TFItems.FIERY_BOOTS.toStack(), 40.0F, 1000.0F);
        registerFeet(TFItems.ARCTIC_BOOTS.toStack(), 40.0F, 1000.0F);
        registerFeet(TFItems.YETI_BOOTS.toStack(), 10.0F, 10000.0F);
        registerFeet(TFItems.TRAVELLERS_BOOTS.toStack(), 5.0F, 20000.0F);

        registerMainhand(TFItems.IRONWOOD_SWORD.toStack(), 100.0F, 0.0F);
        registerMainhand(TFItems.IRONWOOD_AXE.toStack(), 100.0F, 0.0F);
        registerMainhand(TFItems.IRONWOOD_HOE.toStack(), 100.0F, 0.0F);
        registerMainhand(TFItems.IRONWOOD_PICKAXE.toStack(), 100.0F, 0.0F);
        registerMainhand(TFItems.IRONWOOD_SHOVEL.toStack(), 100.0F, 0.0F);

        registerMainhand(TFItems.STEELEAF_SWORD.toStack(), 95.0F, 50.0F);
        registerMainhand(TFItems.STEELEAF_AXE.toStack(), 95.0F, 50.0F);
        registerMainhand(TFItems.STEELEAF_HOE.toStack(), 95.0F, 50.0F);
        registerMainhand(TFItems.STEELEAF_PICKAXE.toStack(), 95.0F, 50.0F);
        registerMainhand(TFItems.STEELEAF_SHOVEL.toStack(), 95.0F, 50.0F);

        registerMainhand(TFItems.KNIGHTMETAL_SWORD.toStack(), 50.0F, 500.0F);
        registerMainhand(TFItems.KNIGHTMETAL_AXE.toStack(), 50.0F, 500.0F);
        registerMainhand(TFItems.KNIGHTMETAL_PICKAXE.toStack(), 50.0F, 500.0F);
        registerMainhand(TFItems.BLOCK_AND_CHAIN.toStack(), 50.0F, 500.0F);
        registerOffhand(TFItems.KNIGHTMETAL_SHIELD.toStack(), 50.0F, 500.0F);

        registerMainhand(TFItems.FIERY_SWORD.toStack(), 40.0F, 1000.0F);
        registerMainhand(TFItems.FIERY_PICKAXE.toStack(), 40.0F, 1000.0F);

        registerMainhand(TFItems.MAZE_WAFER.toStack(), 5.0F, 50000.0F);
        registerMainhand(TFItems.GOLDEN_MINOTAUR_AXE.toStack(), 30.0F, 20000.0F);
        registerMainhand(TFItems.DIAMOND_MINOTAUR_AXE.toStack(), 25.0F, 20000.0F);
        registerMainhand(TFItems.ICE_SWORD.toStack(), 40.0F, 10000.0F);
        registerMainhand(TFItems.TRIPLE_BOW.toStack(), 40.0F, 10000.0F);
        registerMainhand(TFItems.SEEKER_BOW.toStack(), 40.0F, 10000.0F);
        registerMainhand(TFItems.ICE_BOW.toStack(), 40.0F, 10000.0F);
        registerMainhand(TFItems.ENDER_BOW.toStack(), 40.0F, 10000.0F);
        registerMainhand(TFItems.GIANT_SWORD.toStack(), 20.0F, 80000.0F);
        registerMainhand(TFItems.GIANT_PICKAXE.toStack(), 20.0F, 80000.0F);

        registerMainhand(TFItems.GLASS_SWORD.toStack(), 1.0F, 100000.0F);

        ItemStack creativeGlassSword = TFItems.GLASS_SWORD.toStack();
        creativeGlassSword.set(DataComponents.UNBREAKABLE, new Unbreakable(false));
        registerMainhand(creativeGlassSword, 0.00000001F, 65536 * 4);

        registerHead(TFItems.ALPHA_YETI_TROPHY.toStack(), 0.001F, 0.0F);
        registerHead(TFItems.HYDRA_TROPHY.toStack(), 0.001F, 0.0F);
        registerHead(TFItems.KNIGHT_PHANTOM_TROPHY.toStack(), 0.001F, 0.0F);
        registerHead(TFItems.LICH_TROPHY.toStack(), 0.001F, 0.0F);
        registerHead(TFItems.MINOSHROOM_TROPHY.toStack(), 0.001F, 0.0F);
        registerHead(TFItems.NAGA_TROPHY.toStack(), 0.001F, 0.0F);
        registerHead(TFItems.QUEST_RAM_TROPHY.toStack(), 0.001F, 0.0F);
        registerHead(TFItems.SNOW_QUEEN_TROPHY.toStack(), 0.001F, 0.0F);
        registerHead(TFItems.UR_GHAST_TROPHY.toStack(), 0.001F, 0.0F);
    }

    public static void registerMainhand(
            ItemStack stack,
            float weight,
            float minDiff
    )
    {
        register(stack, EquipmentSlot.MAINHAND, weight, minDiff);
    }

    public static void registerOffhand(
            ItemStack stack,
            float weight,
            float minDiff
    )
    {
        register(stack, EquipmentSlot.OFFHAND, weight, minDiff);
    }

    public static void registerHead(
            ItemStack stack,
            float weight,
            float minDiff
    )
    {
        register(stack, EquipmentSlot.HEAD, weight, minDiff);
    }

    public static void registerChest(
            ItemStack stack,
            float weight,
            float minDiff
    )
    {
        register(stack, EquipmentSlot.CHEST, weight, minDiff);
    }

    public static void registerLegs(
            ItemStack stack,
            float weight,
            float minDiff
    )
    {
        register(stack, EquipmentSlot.LEGS, weight, minDiff);
    }

    public static void registerFeet(
            ItemStack stack,
            float weight,
            float minDiff
    )
    {
        register(stack, EquipmentSlot.FEET, weight, minDiff);
    }

    public static void register(
            ItemStack stack,
            EquipmentSlot slot,
            float weight,
            float minDiff
    )
    {
        ShuffledRegistries.WEIGHTED_AWAKEN_STACK.push(stack, slot, weight, minDiff);
    }
}