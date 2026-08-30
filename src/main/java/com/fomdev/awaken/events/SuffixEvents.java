package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.raw.affix.AwakenSuffix.*;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.Constants;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class SuffixEvents
{
    @SubscribeEvent
    public static void onUseAsDefend(
            ArmorHurtEvent event
    )
    {
        for (EquipmentSlot slot: Constants.BODY_SLOTS)
        {
            ItemStack stack = event.getArmorItemStack(slot);
            SuffixContainer container = NBTUtil.deserializeAffix$Suffix(stack);

            for (SuffixSlot suffixSlot: container.slots().values())
            {
                if (!suffixSlot.isPresent())
                    continue;

                SuffixInstance suffix = suffixSlot.getSuffix();
                suffix.suffix().executeAsDefend(stack, suffix.args(), event);
            }
        }
    }

    @SubscribeEvent
    public static void onUseAsDigger(
            BlockEvent.BreakEvent event
    )
    {
        ItemStack stack = event.getPlayer().getMainHandItem();
        SuffixContainer container = NBTUtil.deserializeAffix$Suffix(stack);

        for (SuffixSlot suffixSlot: container.slots().values())
        {
            if (!suffixSlot.isPresent())
                continue;

            SuffixInstance suffix = suffixSlot.getSuffix();
            suffix.suffix().executeAsDigger(stack, suffix.args(), event);
        }
    }

    @SubscribeEvent
    public static void onUseAsShield(
            LivingShieldBlockEvent event
    )
    {
        ItemStack stack = event.getEntity().getUseItem();
        SuffixContainer container = NBTUtil.deserializeAffix$Suffix(stack);

        for (SuffixSlot suffixSlot: container.slots().values())
        {
            if (!suffixSlot.isPresent())
                continue;

            SuffixInstance suffix = suffixSlot.getSuffix();
            suffix.suffix().executeAsShield(stack, suffix.args(), event);
        }
    }

    @SubscribeEvent
    public static void onUseAsWeapon(
            LivingIncomingDamageEvent event
    )
    {
        ItemStack stack = event.getSource().getWeaponItem();
        if (stack == null || stack.isEmpty())
            return;

        SuffixContainer container = NBTUtil.deserializeAffix$Suffix(stack);

        for (SuffixSlot suffixSlot: container.slots().values())
        {
            if (!suffixSlot.isPresent())
                continue;

            SuffixInstance suffix = suffixSlot.getSuffix();
            suffix.suffix().executeAsWeapon(stack, suffix.args(), event);
        }
    }
}