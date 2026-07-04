package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.AwakenPollinate;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Util;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentUser;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;

@EventBusSubscriber(modid = Awaken.MODID)
public class PollinateEffectEvents
{
    @SubscribeEvent
    public static void onLivingDamage(
            LivingDamageEvent.Post event
    )
    {
        if (!(event.getSource().getEntity() instanceof LivingEntity damager))
            return;

        if (event.getSource().getWeaponItem() == null || event.getSource().getWeaponItem().is(Items.AIR))
            return;

        for (AwakenPollinate.PollinateInstance pollinate: NBTUtil.deserializePollinates(event.getSource().getWeaponItem()))
        {
            AwakenPollinate p = pollinate.getPollinate();
            int level = pollinate.getLevel();

            if (p == null)
                continue;

            if (p.getType() != AwakenPollinate.TriggerType.DAMAGE)
                continue;

            MobEffectInstance instance = p.getEffect(level);

            switch (p.getTarget())
            {
                case SELF -> damager.addEffect(instance);
                case TARGET -> event.getEntity().addEffect(instance);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(
            ArmorHurtEvent event
    )
    {
        List<ItemStack> stacks = event.getArmorMap().values().stream().map(a -> a.armorItemStack).filter(s -> !s.is(Items.AIR)).toList();

        if (!(event.getDamageSource().getEntity() instanceof LivingEntity damager))
            return;

        for (ItemStack stack: stacks)
        {
            for (AwakenPollinate.PollinateInstance pollinate: NBTUtil.deserializePollinates(stack))
            {
                AwakenPollinate p = pollinate.getPollinate();
                int level = pollinate.getLevel();

                if (p == null)
                    continue;

                if (p.getType() != AwakenPollinate.TriggerType.HURT)
                    continue;

                MobEffectInstance instance = p.getEffect(level);

                switch (p.getTarget())
                {
                    case DAMAGER -> damager.addEffect(instance);
                    case SELF -> event.getEntity().addEffect(instance);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingShield(
            LivingShieldBlockEvent event
    )
    {
        if (!event.getBlocked())
            return;

        if (!(event.getDamageSource().getEntity() instanceof LivingEntity damager))
            return;

        for (AwakenPollinate.PollinateInstance pollinate: NBTUtil.deserializePollinates(event.getEntity().getUseItem()))
        {
            AwakenPollinate p = pollinate.getPollinate();
            int level = pollinate.getLevel();

            if (p == null)
                continue;

            if (p.getType() != AwakenPollinate.TriggerType.HURT)
                continue;

            MobEffectInstance instance = p.getEffect(level);

            switch (p.getTarget())
            {
                case DAMAGER -> damager.addEffect(instance);
                case SELF -> event.getEntity().addEffect(instance);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingTick(
            EntityTickEvent.Post event
    )
    {
        if (!(event.getEntity() instanceof EquipmentUser user))
            return;

        if (!(event.getEntity() instanceof LivingEntity living))
            return;

        for (ItemStack stack: Util.getStacks(user))
        {
            for (AwakenPollinate.PollinateInstance pollinate: NBTUtil.deserializePollinates(stack))
            {
                AwakenPollinate p = pollinate.getPollinate();
                int level = pollinate.getLevel();

                if (p == null)
                    continue;

                if (p.getType() != AwakenPollinate.TriggerType.CONTINUE)
                    continue;

                if (p.getTarget() != AwakenPollinate.TriggerTarget.SELF)
                    continue;

                MobEffectInstance instance = p.getEffect(level);
                living.addEffect(instance);
            }
        }
    }
}