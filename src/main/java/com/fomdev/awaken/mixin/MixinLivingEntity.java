package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.util.HealthUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity
{
    @Shadow
    protected abstract boolean isAlwaysExperienceDropper();

    @Shadow
    protected int lastHurtByPlayerTime;

    @Shadow
    @Nullable
    protected Player lastHurtByPlayer;

    @Shadow
    public abstract int getExperienceReward(ServerLevel p_345212_, @org.jetbrains.annotations.Nullable Entity p_345512_);

    @Inject(method = "dropExperience", at = @At("HEAD"), cancellable = true)
    private void getCustomExp(Entity entity, CallbackInfo ci)
    {
        LivingEntity self = (LivingEntity) (Object) this;
        ci.cancel();

        if (!(self.level() instanceof ServerLevel level))
            return;

        if (
                !self.wasExperienceConsumed() &&
                        (
                                this.isAlwaysExperienceDropper() ||
                                        this.lastHurtByPlayerTime > 0 &&
                                                self.shouldDropExperience() &&
                                                level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)
                        )
        ) {
            float factor = DifficultyManager.getDimensionFactor(level) / 20;
            int reward = EventHooks.getExperienceDrop(self, this.lastHurtByPlayer, this.getExperienceReward(level, entity) * (int) (Math.max(factor, 1.0F)));
            ExperienceOrb.award(level, self.position(), reward);
        }
    }


    @Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
    private void getCustomHealth(CallbackInfoReturnable<Float> cir)
    {
        LivingEntity self = (LivingEntity) (Object) this;

        if (!(self instanceof Player player))
            cir.setReturnValue(HealthUtil.calculateMobHealth(self, cir.getReturnValue()));
        else
            cir.setReturnValue(cir.getReturnValue() + HealthUtil.deserializeAdditionalHealthPersistent(player));
    }
}