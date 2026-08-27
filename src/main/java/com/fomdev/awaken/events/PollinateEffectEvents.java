package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.raw.spore.AwakenPollinate;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

@EventBusSubscriber(modid = Awaken.MODID)
public class PollinateEffectEvents
{
    @SubscribeEvent
    public static void onLivingDamage(
            LivingDamageEvent.Post event
    )
    {
        if (!(event.getSource().getEntity() instanceof Player damager))
            return;

        CuriosApi.getCuriosInventory(damager).ifPresent(
                handler ->
                {
                    ICurioStacksHandler sh = handler.getCurios().get("amulet");
                    if (sh == null)
                        return;

                    for (int i = 0; i < sh.getStacks().getSlots(); i++)
                    {
                        ItemStack stack = sh.getStacks().getStackInSlot(i);
                        for (AwakenPollinate.PollinateInstance pollinate: NBTUtil.deserializePollinates(stack))
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
                }
        );
    }

    @SubscribeEvent
    public static void onLivingHurt(
            ArmorHurtEvent event
    )
    {
        if (!(event.getDamageSource().getEntity() instanceof Player damager))
            return;

        CuriosApi.getCuriosInventory(damager).ifPresent(
                handler ->
                {
                    ICurioStacksHandler sh = handler.getCurios().get("amulet");
                    if (sh == null)
                        return;

                    for (int i = 0; i < sh.getStacks().getSlots(); i++)
                    {
                        ItemStack stack = sh.getStacks().getStackInSlot(i);
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
        );
    }

    @SubscribeEvent
    public static void onLivingShield(
            LivingShieldBlockEvent event
    )
    {
        if (!event.getBlocked())
            return;

        if (!(event.getDamageSource().getEntity() instanceof Player damager))
            return;

        CuriosApi.getCuriosInventory(damager).ifPresent(
                handler ->
                {
                    ICurioStacksHandler sh = handler.getCurios().get("amulet");
                    if (sh == null)
                        return;

                    for (int i = 0; i < sh.getStacks().getSlots(); i++)
                    {
                        ItemStack stack = sh.getStacks().getStackInSlot(i);
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
        );
    }

    @SubscribeEvent
    public static void onLivingTick(
            EntityTickEvent.Post event
    )
    {
        if (!(event.getEntity() instanceof Player living))
            return;

        CuriosApi.getCuriosInventory(living).ifPresent(
                handler ->
                {
                    ICurioStacksHandler sh = handler.getCurios().get("amulet");
                    if (sh == null)
                        return;

                    for (int i = 0; i < sh.getStacks().getSlots(); i++)
                    {
                        ItemStack stack = sh.getStacks().getStackInSlot(i);
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
                                case DAMAGER, SELF -> living.addEffect(instance);
                            }
                        }
                    }
                }
        );
    }
}