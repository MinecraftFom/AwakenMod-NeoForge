package com.fomdev.awaken.mixin;

import com.fomdev.awaken.ai.UseMaceGoal;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MaceItem.class)
public class MixinMaceItem
{
    @Inject(method = "hurtEnemy", at = @At("HEAD"), cancellable = true)
    private void hurtEnemy(
            ItemStack stack,
            LivingEntity target,
            LivingEntity attacker,
            CallbackInfoReturnable<Boolean> cir
    )
    {
        if (attacker instanceof ServerPlayer)
            return;

        cir.cancel();
        if (attacker.fallDistance > 1.5F)
        {
            ServerLevel level = (ServerLevel) attacker.level();

            attacker.setDeltaMovement(attacker.getDeltaMovement().with(Direction.Axis.Y, 0.01F));
            SoundEvent soundevent = attacker.fallDistance > 5.0F ? SoundEvents.MACE_SMASH_GROUND_HEAVY : SoundEvents.MACE_SMASH_GROUND;
            level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), soundevent, attacker.getSoundSource(), 1.0F, 1.0F);

            UseMaceGoal.knockback(level, attacker, target);
        }

        cir.setReturnValue(true);
    }
}