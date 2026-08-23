package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.AwakenSpore;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

@EventBusSubscriber(modid = Awaken.MODID)
public class MedicineEvents
{
    @SubscribeEvent
    public static void onSearch(
            PlayerInteractEvent.RightClickBlock event
    )
    {
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        Level level = player.level();
        BlockState block = level.getBlockState(pos);
        ItemStack stack = event.getItemStack();
        RandomSource random = player.getRandom();

        if (!block.getBlockHolder().is(BlockTags.FLOWERS) || !stack.is(AwakenItems.BOTANY_CUT))
            return;

        if (random.nextInt(100) < 25) // 25%
            return;

        List<AwakenSpore> spores = AwakenRegistries.AWAKEN_SPORE.getRegistries();
        if (spores.isEmpty())
            return;

        AwakenSpore spore = spores.get(random.nextInt(spores.size()));
        Records.AwakenKnowledgeComponent knowledge = NBTUtil.deserializeKnowledge(player);
        float proficiency = knowledge.proficiency();
        int value = (int) Math.pow(proficiency, 1.0 / 8.0);
        int lvl = Math.max(value, 1);
        int count = random.nextInt(8);

        Records.AwakenMedicineComponent medicine = new Records.AwakenMedicineComponent(spore.getLocation().toString(), lvl);
        ItemStack result = new ItemStack(AwakenItems.PLANT_MEDICINE.get(), count);
        NBTUtil.serializeMedicine(result, medicine);
        player.spawnAtLocation(result);
    }
}