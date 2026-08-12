package com.fomdev.awaken.ai;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class UseMaceGoal extends Goal
{
    private final Mob self;

    public UseMaceGoal(
            Mob mob
    )
    {
        this.self = mob;
    }

    @Override
    public boolean canUse()
    {
        return self.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.MACE) && self.getTarget() instanceof Player && self.getTarget().distanceTo(self) < 5;
    }

    @Override
    public void start()
    {
        ServerLevel level = (ServerLevel) self.level();
        ItemStack stack = self.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!stack.is(Items.MACE))
            return;

        LivingEntity target = self.getTarget();
        if (!(target instanceof Player player))
            return;

        self.fallDistance = 10;
        MaceItem mace = (MaceItem) self.getItemBySlot(EquipmentSlot.MAINHAND).getItem();
        mace.hurtEnemy(stack, player, self);
        DamageSource source = level.damageSources().mobAttack(self);
        player.hurt(source, mace.getAttackDamageBonus(player, 1.0F, source));
        mace.postHurtEnemy(stack, player, self);

        super.start();
    }

    public static void knockback(
            Level level,
            LivingEntity attacker,
            Entity entity
    )
    {
        level.levelEvent(2013, entity.getOnPos(), 750);
        level.getEntitiesOfClass(
                LivingEntity.class,
                entity.getBoundingBox().inflate(3.5F),
                knockbackPredicate(
                        attacker,
                        entity
                )
        )
                .forEach(living -> {
                    Vec3 vec3 = living.position().subtract(entity.position());
                    double d0 = getKnockbackPower(attacker, living, vec3);
                    Vec3 vec31 = vec3.normalize().scale(d0);
                    if (d0 > 0.0F)
                    {
                        living.push(vec31.x, 0.7F, vec31.z);
                        if (living instanceof ServerPlayer player)
                            player.connection.send(new ClientboundSetEntityMotionPacket(player));
                    }
                });
    }

    private static Predicate<LivingEntity> knockbackPredicate(
            LivingEntity player,
            Entity entity
    )
    {
        return living -> {
            boolean flag;
            boolean flag1;
            boolean flag2;
            boolean flag6;
            label62: {
                flag = !living.isSpectator();
                flag1 = living != player && living != entity;
                flag2 = !player.isAlliedTo(living);
                if (living instanceof TamableAnimal animal)
                {
                    if (animal.isTame() && player.getUUID().equals(animal.getOwnerUUID()))
                    {
                        flag6 = true;
                        break label62;
                    }
                }

                flag6 = false;
            }

            boolean flag3;
            label55:
            {
                flag3 = !flag6;
                if (living instanceof ArmorStand armorstand)
                    if (armorstand.isMarker())
                        break label55;

                flag6 = true;
            }

            boolean flag5 = entity.distanceToSqr(living) <= Math.pow(3.5F, 2.0F);
            return flag && flag1 && flag2 && flag3 && flag6 && flag5;
        };
    }

    private static double getKnockbackPower(LivingEntity attacker, LivingEntity entity, Vec3 entityPos)
    {
        return ((double)3.5F - entityPos.length()) * (double)0.7F * (double)(attacker.fallDistance > 5.0F ? 2 : 1) * ((double)1.0F - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
    }
}