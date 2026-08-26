package com.fomdev.awaken.compat;

import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Unbreakable;
import net.tslat.aoa3.common.registration.item.AoAArmour;
import net.tslat.aoa3.common.registration.item.AoATools;
import net.tslat.aoa3.common.registration.item.AoAWeapons;
import twilightforest.init.TFItems;

public class ItemAddition
{
    public static void activeIfAoa3Installed()
    {
        registerHead(AoAArmour.ACHELOS_HELMET.toStack(), 5.0F, 10000.0F);
        registerHead(AoAArmour.HELM_OF_THE_DEXTROUS.toStack(), 5.0F, 10000.0F);
        registerHead(AoAArmour.HELM_OF_THE_DRYAD.toStack(), 5.0F, 10000.0F);
        registerHead(AoAArmour.HELM_OF_THE_RITUALIST.toStack(), 5.0F, 10000.0F);
        registerHead(AoAArmour.HELM_OF_THE_TRAWLER.toStack(), 5.0F, 10000.0F);
        registerHead(AoAArmour.HELM_OF_THE_TREASURER.toStack(), 5.0F, 10000.0F);
        registerHead(AoAArmour.HELM_OF_THE_WARRIOR.toStack(), 5.0F, 10000.0F);

        registerSet(AoAArmour.ARCHAIC_ARMOUR, 20.0F, 500.0F);
        registerSet(AoAArmour.BARON_ARMOUR, 100.0F, 50.0F);
        registerSet(AoAArmour.BIOGENIC_ARMOUR, 95.0F, 100.0F);
        registerSet(AoAArmour.CANDY_ARMOUR, 80.0F, 500.0F);
        registerSet(AoAArmour.COMMANDER_ARMOUR, 70.0F, 800.0F); // Arknights: End Field
        registerSet(AoAArmour.EMBRODIUM_ARMOUR, 75.0F, 700.0F);
        registerSet(AoAArmour.EXPLOSIVE_ARMOUR, 50.0F, 800.0F);
        registerSet(AoAArmour.HAZMAT_ARMOUR, 100.0F, 0.0F);
        registerSet(AoAArmour.HYDRANGIC_ARMOUR, 80.0F, 500.0F);
        registerSet(AoAArmour.HYDROPLATE_ARMOUR, 60.0F, 900.0F);
        registerSet(AoAArmour.ICE_ARMOUR, 80.0F, 500.0F);
        registerSet(AoAArmour.INFERNAL_ARMOUR, 90.0F, 400.0F);
        registerSet(AoAArmour.LYNDAMYTE_ARMOUR, 90.0F, 400.0F);
        registerSet(AoAArmour.NECRO_ARMOUR, 50.0F, 1000.0F);
        registerSet(AoAArmour.NETHENGEIC_ARMOUR, 75.0F, 700.0F);
        registerSet(AoAArmour.OMNI_ARMOUR, 75.0F, 700.0F);
        registerSet(AoAArmour.PHANTASM_ARMOUR, 30.0F, 2000.0F);
        registerSet(AoAArmour.PREDATIOUS_ARMOUR, 40.0F, 1500.0F);
        registerSet(AoAArmour.PRIMORDIAL_ARMOUR, 45.0F, 1250.0F);
        registerSet(AoAArmour.RUNIC_ARMOUR, 30.0F, 2000.0F);
        registerSet(AoAArmour.SKELETAL_ARMOUR, 75.0F, 700.0F);
        registerSet(AoAArmour.SPACEKING_ARMOUR, 45.0F, 1250.0F);
        registerSet(AoAArmour.UTOPIAN_ARMOUR, 80.0F, 500.0F); // Anti-Utopian!
        registerSet(AoAArmour.VOID_ARMOUR, 75.0F, 700.0F);
        registerSet(AoAArmour.WITHER_ARMOUR, 80.0F, 500.0F);
        registerSet(AoAArmour.ZARGONITE_ARMOUR, 50.0F, 900.0F);

        registerMainhand(AoATools.EMBERSTONE_PICKAXE.toStack(), 100.0F, 0.0F);
        registerMainhand(AoATools.ENERGISTIC_PICKAXE.toStack(), 50.0F, 1000.0F);
        registerMainhand(AoATools.GEMCRACKER.toStack(), 75.0F, 500.0F);
        registerMainhand(AoATools.JADE_PICKAXE.toStack(), 500.0F, 0.0F);
        registerMainhand(AoATools.LIMONITE_PICKAXE.toStack(), 500.0F, 0.0F);
        registerMainhand(AoATools.OCCULT_PICKAXE.toStack(), 75.0F, 500.0F);
        registerMainhand(AoATools.PICKMAX.toStack(), 75.0F, 500.0F);
        registerMainhand(AoATools.SKELETAL_PICKAXE.toStack(), 90.0F, 400.0F);
        registerMainhand(AoATools.SOULSTONE_PICKAXE.toStack(), 75.0F, 500.0F);

        registerMainhand(AoATools.EMBERSTONE_SHOVEL.toStack(), 100.0F, 0.0F);
        registerMainhand(AoATools.ENERGISTIC_SHOVEL.toStack(), 50.0F, 1000.0F);
        registerMainhand(AoATools.JADE_SHOVEL.toStack(), 500.0F, 0.0F);
        registerMainhand(AoATools.LIMONITE_SHOVEL.toStack(), 500.0F, 0.0F);
        registerMainhand(AoATools.OCCULT_SHOVEL.toStack(), 75.0F, 500.0F);
        registerMainhand(AoATools.SKELETAL_SHOVEL.toStack(), 90.0F, 400.0F);
        registerMainhand(AoATools.SOULSTONE_SHOVEL.toStack(), 75.0F, 500.0F);

        registerMainhand(AoATools.EMBERSTONE_AXE.toStack(), 100.0F, 0.0F);
        registerMainhand(AoATools.ENERGISTIC_AXE.toStack(), 50.0F, 1000.0F);
        registerMainhand(AoATools.JADE_AXE.toStack(), 500.0F, 0.0F);
        registerMainhand(AoATools.LIMONITE_AXE.toStack(), 500.0F, 0.0F);
        registerMainhand(AoATools.OCCULT_AXE.toStack(), 75.0F, 500.0F);
        registerMainhand(AoATools.SKELETAL_AXE.toStack(), 90.0F, 400.0F);
        registerMainhand(AoATools.SOULSTONE_AXE.toStack(), 75.0F, 500.0F);

        registerMainhand(AoATools.EMBERSTONE_HOE.toStack(), 100.0F, 0.0F);
        registerMainhand(AoATools.JADE_HOE.toStack(), 500.0F, 0.0F);
        registerMainhand(AoATools.LIMONITE_HOE.toStack(), 500.0F, 0.0F);

        registerMainhand(AoAWeapons.BARON_SWORD.toStack(), 50.0F, 1000.0F);
        registerMainhand(AoAWeapons.BLOODSTONE_SWORD.toStack(), 20.0F, 5000.0F);
        registerMainhand(AoAWeapons.CANDLEFIRE_SWORD.toStack(), 10.0F, 10000.0F);
        registerMainhand(AoAWeapons.CARAMEL_CARVER.toStack(), 15.0F, 7500.0F);
        registerMainhand(AoAWeapons.CORALSTORM_SWORD.toStack(), 60.0F, 900.0F);
        registerMainhand(AoAWeapons.CREEPIFIED_SWORD.toStack(), 30.0F, 7000.0F);
        registerMainhand(AoAWeapons.CRYSTALLITE_SWORD.toStack(), 30.0F, 7000.0F);
        registerMainhand(AoAWeapons.EMBERSTONE_SWORD.toStack(), 100.0F, 500.0F);
        registerMainhand(AoAWeapons.EXPLOCHRON_SWORD.toStack(), 20.0F, 5000.0F);
        registerMainhand(AoAWeapons.FIREBORNE_SWORD.toStack(), 25.0F, 4500.0F);
        registerMainhand(AoAWeapons.GUARDIANS_SWORD.toStack(), 20.0F, 5000.0F);
        registerMainhand(AoAWeapons.HARVESTER_SWORD.toStack(), 5.0F, 50000.0F);
        registerMainhand(AoAWeapons.HOLY_SWORD.toStack(), 0.00001F, 1000000.0F);
        registerMainhand(AoAWeapons.ILLUSION_SWORD.toStack(), 30.0F, 7000.0F);
        registerMainhand(AoAWeapons.JADE_SWORD.toStack(), 50.0F, 1000.0F);
        registerMainhand(AoAWeapons.LEGBONE_SWORD.toStack(), 75.0F, 500.0F);
        registerMainhand(AoAWeapons.LIGHTS_WAY.toStack(), 100.0F, 500.0F);
        registerMainhand(AoAWeapons.LIMONITE_SWORD.toStack(), 100.0F, 500.0F);
        registerMainhand(AoAWeapons.NETHENGEIC_SLUGGER.toStack(), 30.0F, 7000.0F);
        registerMainhand(AoAWeapons.PRIMAL_SWORD.toStack(), 60.0F, 900.0F);
        registerMainhand(AoAWeapons.ROCKBASHER_SWORD.toStack(), 15.0F, 7500.0F);
        registerMainhand(AoAWeapons.ROCK_PICK_SWORD.toStack(), 50.0F, 1000.0F);
        registerMainhand(AoAWeapons.ROSIDIAN_SWORD.toStack(), 45.0F, 1250.0F);
        registerMainhand(AoAWeapons.RUNIC_SWORD.toStack(), 20.0F, 5000.0F);
        registerMainhand(AoAWeapons.SHROOMUS_SWORD.toStack(), 30.0F, 7000.0F);
        registerMainhand(AoAWeapons.SKELETAL_SWORD.toStack(), 50.0F, 1000.0F);
        registerMainhand(AoAWeapons.SWEET_SWORD.toStack(), 30.0F, 7000.0F);
        registerMainhand(AoAWeapons.TROLL_BASHER_AXE.toStack(), 45.0F, 1250.0F);
        registerMainhand(AoAWeapons.ULTRAFLAME.toStack(), 20.0F, 5000.0F);
        registerMainhand(AoAWeapons.VOID_SWORD.toStack(), 50.0F, 1000.0F);

        registerMainhand(AoAWeapons.BARON_GREATBLADE.toStack(), 0.02F, 50000.0F);
        registerMainhand(AoAWeapons.BLOODLURKER.toStack(), 0.01F, 100000.0F);
        registerMainhand(AoAWeapons.CANDY_BLADE.toStack(), 0.005F, 120000.0F);
        registerMainhand(AoAWeapons.COTTON_CRUSHER.toStack(), 0.005F, 120000.0F);
        registerMainhand(AoAWeapons.CRYSTAL_GREATBLADE.toStack(), 0.01F, 100000.0F);
        registerMainhand(AoAWeapons.EREBON_SCYTHE.toStack(), 0.02F, 50000.0F);
        registerMainhand(AoAWeapons.GODS_GREATBLADE.toStack(), 0.000001F, 1000000.0F); // World's End Dancehall
        registerMainhand(AoAWeapons.HAUNTED_GREATBLADE.toStack(), 0.005F, 120000.0F);
        registerMainhand(AoAWeapons.KNIGHTS_GUARD.toStack(), 0.005F, 120000.0F);
        registerMainhand(AoAWeapons.LUNAR_GREATBLADE.toStack(), 0.00001F, 500000.0F);
        registerMainhand(AoAWeapons.LUXON_SCYTHE.toStack(), 0.005F, 120000.0F);
        registerMainhand(AoAWeapons.LYONIC_BARDICHE.toStack(), 0.02F, 50000.0F);
        registerMainhand(AoAWeapons.MILLENNIUM_GREATBLADE.toStack(), 0.000005F, 120000.0F); // Arisu!
        registerMainhand(AoAWeapons.NOXIOUS_PARTIZAN.toStack(), 0.01F, 100000.0F);
        registerMainhand(AoAWeapons.PLUTON_SCYTHE.toStack(), 0.02F, 50000.0F);
        registerMainhand(AoAWeapons.PRIMORDIAL_GLAIVE.toStack(), 0.005F, 120000.0F);
        registerMainhand(AoAWeapons.ROSIDIAN_GREATBLADE.toStack(), 0.01F, 100000.0F);
        registerMainhand(AoAWeapons.ROYAL_GREATBLADE.toStack(), 0.02F, 50000.0F);
        registerMainhand(AoAWeapons.SELYAN_SCYTHE.toStack(), 0.02F, 50000.0F);
        registerMainhand(AoAWeapons.SHYRE_SWORD.toStack(), 0.005F, 100000.0F);
        registerMainhand(AoAWeapons.SUBTERRANEAN_GREATBLADE.toStack(), 0.005F, 120000.0F);
        registerMainhand(AoAWeapons.TIDAL_GREATBLADE.toStack(),  0.005F, 120000.0F);

        registerMainhand(AoAWeapons.CORAL_CROSSBOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.PYRO_CROSSBOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.ROSIDIAN_CROSSBOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.SKELETAL_SWORD.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.TROLLS_CROSSBOW.toStack(), 20.0F, 10000.0F);

        registerMainhand(AoAWeapons.ALACRITY_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.ANCIENT_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.ATLANTIC_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.BARON_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.BOREIC_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.DAYBREAKER_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.DEEP_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.EXPLOSIVE_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.ICE_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.INFERNAL_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.JUSTICE_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.POISON_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.PREDATIOUS_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.PRIMORDIAL_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.RUNIC_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.SHYREGEM_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.SKYDRIVER_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.SLINGSHOT.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.SUNSHINE_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.VOID_BOW.toStack(), 20.0F, 10000.0F);
        registerMainhand(AoAWeapons.WITHER_BOW.toStack(), 20.0F, 10000.0F);
    }

    public static void activeIfCataclysmInstalled()
    {

    }

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

    public static void registerSet(
            AoAArmour.ArmourSet set,
            float weight,
            float minDiff
    )
    {
        registerHead(set.helmet.toStack(), weight, minDiff);
        registerChest(set.chestplate.toStack(), weight, minDiff);
        registerLegs(set.leggings.toStack(), weight, minDiff);
        registerFeet(set.boots.toStack(), weight, minDiff);
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