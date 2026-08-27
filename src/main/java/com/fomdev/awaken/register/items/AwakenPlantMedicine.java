package com.fomdev.awaken.register.items;

import com.fomdev.awaken.entries.raw.spore.AwakenSpore;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AwakenPlantMedicine extends Item
{

    public AwakenPlantMedicine(
            Properties properties
    )
    {
        super(properties.stacksTo(8).food(new FoodProperties(5, 4, false, 2, Optional.empty(), List.of())));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            @NotNull Level level,
            @NotNull Player player,
            @NotNull InteractionHand usedHand
    )
    {
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(
            @NotNull ItemStack stack,
            @NotNull Level level,
            @NotNull LivingEntity livingEntity
    )
    {
        Records.AwakenMedicineComponent medicine = NBTUtil.deserializeMedicine(stack);
        List<AwakenSpore.SporeInstance> instances = NBTUtil.deserializeSpores(livingEntity);
        List<AwakenSpore.SporeInstance> result = new ArrayList<>();
        ResourceLocation target = ResourceLocation.parse(medicine.immuniseType());
        ItemStack res = stack;
        for (AwakenSpore.SporeInstance inst : instances)
        {
            if (inst.getSpore().getLocation().equals(target))
            {
                int nlvl = inst.getLevel() - medicine.value();
                if (nlvl > 0)
                    result.add(new AwakenSpore.SporeInstance(inst.getSpore(), nlvl));

                res = stack.consumeAndReturn(stack.getCount() - 1, livingEntity);
                break;
            }

            result.add(inst);
        }

        NBTUtil.serializeSpores(livingEntity, result);
        if (livingEntity instanceof Player player)
        {
            Records.AwakenKnowledgeComponent knowledge = NBTUtil.deserializeKnowledge(player);
            float proOriginal = knowledge.proficiency();
            proOriginal += player.getRandom().nextInt(8) + 1;
            NBTUtil.serializeKnowledge(player, new Records.AwakenKnowledgeComponent(knowledge.experience(), knowledge.insight(), proOriginal, knowledge.skill()));
        }

        return res;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(
            @NotNull ItemStack stack
    )
    {
        return UseAnim.EAT;
    }

    @Override
    public @NotNull SoundEvent getEatingSound()
    {
        return SoundEvents.GENERIC_EAT;
    }
}