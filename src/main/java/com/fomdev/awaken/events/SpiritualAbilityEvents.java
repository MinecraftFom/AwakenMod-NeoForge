package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.AwakenSpiritual;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.PosUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

@EventBusSubscriber(modid = Awaken.MODID)
public class SpiritualAbilityEvents
{
    @SubscribeEvent
    public static void onTrigger(
            PlayerInteractEvent.RightClickBlock event
    )
    {
        Vec3 center = event.getEntity().position();
        Vec3 target = event.getHitVec().getLocation();
        trigger(event.getItemStack(), event.getEntity(), center, target, event.getLevel());
    }

    @SubscribeEvent
    public static void onTrigger(
            PlayerInteractEvent.RightClickEmpty event
    )
    {
        Vec3 center = event.getEntity().position();
        Vec3 target = PosUtil.calculatePlayerTarget(event.getEntity());
        trigger(event.getItemStack(), event.getEntity(), center, target, event.getLevel());
    }

    @SubscribeEvent
    public static void onTrigger(
            PlayerInteractEvent.RightClickItem event
    )
    {
        Vec3 center = event.getEntity().position();
        Vec3 target = PosUtil.calculatePlayerTarget(event.getEntity());
        trigger(event.getItemStack(), event.getEntity(), center, target, event.getLevel());
    }

    private static void trigger(
            ItemStack stack,
            Player player,
            Vec3 center,
            Vec3 target,
            Level level
    )
    {
        if (stack.is(Items.AIR))
            return;

        AwakenSpiritual spiritual = NBTUtil.deserializeSpiritual(stack);
        if (spiritual == null)
            return;

        double radius = spiritual.getRadius();

        List<LivingEntity> effected = PosUtil.calculateNearestEntities(
                target.x,
                target.y,
                target.z,
                radius,
                level
        );

        effected.remove(player);

        for (LivingEntity entity: effected)
        {
            entity.addEffect(
                    new MobEffectInstance(
                            MobEffects.GLOWING,
                            20,
                            255
                    )
            );

            spiritual.effectEntity(
                    center.x,
                    center.y,
                    center.z,
                    target.x,
                    target.y,
                    target.z,
                    player,
                    entity,
                    level
            );

            spiritual.renderEntity(
                    center.x,
                    center.y,
                    center.z,
                    target.x,
                    target.y,
                    target.z,
                    entity.getX(),
                    entity.getY(),
                    entity.getZ(),
                    level
            );
        }

        spiritual.effectAll(
                center.x,
                center.y,
                center.z,
                target.x,
                target.y,
                target.z,
                player,
                level
        );

        spiritual.renderAll(
                center.x,
                center.y,
                center.z,
                target.x,
                target.y,
                target.z,
                level
        );
    }
}