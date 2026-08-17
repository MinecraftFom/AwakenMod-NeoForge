package com.fomdev.awaken.ai;

import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;

public class UseShieldGoal extends Goal
{
    private final Mob mob;
    private float use;

    public UseShieldGoal(
            PathfinderMob mob
    )
    {
        this.mob = mob;
        this.use = 0;
    }

    @Override
    public boolean canContinueToUse()
    {
        return mob.getTarget() != null && use <= 0 && (mob.getTarget().getMainHandItem().is(ItemTags.AXES) || mob.getTarget().getMainHandItem().is(ItemTags.SWORDS));
    }

    @Override
    public boolean canUse()
    {
        return
                mob != null
                        && mob.getTarget() != null
                        && mob.getItemBySlot(EquipmentSlot.OFFHAND).is(Items.SHIELD)
                        && mob.getTarget().distanceTo(mob) < 3
                        && (mob.getTarget().getMainHandItem().is(ItemTags.AXES) || mob.getTarget().getMainHandItem().is(ItemTags.SWORDS));
    }

    @Override
    public void start()
    {
        ItemStack shield = mob.getItemBySlot(EquipmentSlot.OFFHAND);
        if (!(shield.getItem() instanceof ShieldItem))
            return;

        mob.startUsingItem(InteractionHand.OFF_HAND);
        use = 100;
        mob.getTarget().playSound(SoundEvents.SHIELD_BLOCK);
        super.start();
    }


    @Override
    public void stop()
    {
        if (!mob.isUsingItem())
        {
            use = 0;
            return;
        }

        if (use > 0)
            return;

        mob.stopUsingItem();
    }

    @Override
    public void tick()
    {
        use--;
        super.tick();
    }
}