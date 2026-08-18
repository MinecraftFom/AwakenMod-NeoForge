package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import com.fomdev.awaken.util.HealthUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

    @Shadow
    public abstract float getMaxHealth();

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
            float factor = DifficultyManager.getLevelDifficulty(level) / 20;
            int reward = EventHooks.getExperienceDrop(self, this.lastHurtByPlayer, this.getExperienceReward(level, entity) * (int) (Math.max(factor, 1.0F)));
            ExperienceOrb.award(level, self.position(), reward);
        }
    }

    @Inject(method = "getAttributeBaseValue", at = @At("RETURN"), cancellable = true)
    private void getAttributeBaseValue(
            Holder<Attribute> attribute,
            CallbackInfoReturnable<Double> cir
    )
    {
        LivingEntity self = (LivingEntity) (Object) this;

        if (!attribute.is(Attributes.MAX_HEALTH))
            return;

        if (self instanceof ServerPlayer player)
            cir.setReturnValue(cir.getReturnValue() + HealthUtil.deserializeAdditionalHealthPersistent(player));
        else if (self instanceof LocalPlayer player)
            cir.setReturnValue(cir.getReturnValue() + HealthUtil.deserializeAdditionalHealthPersistent(player));
    }

    @Inject(method = "getAttributeValue", at = @At("RETURN"), cancellable = true)
    private void getAttributeValue(
            Holder<Attribute> attribute,
            CallbackInfoReturnable<Double> cir
    )
    {
        LivingEntity self = (LivingEntity) (Object) this;

        if (!attribute.is(Attributes.MAX_HEALTH))
            return;

        if (self instanceof ServerPlayer player && player.connection != null)
            cir.setReturnValue(cir.getReturnValue() + HealthUtil.deserializeAdditionalHealthPersistent(player));
        else if (self instanceof LocalPlayer player && player.connection != null)
            cir.setReturnValue(cir.getReturnValue() + HealthUtil.deserializeAdditionalHealthPersistent(player));
    }

    @Inject(method = "getEquipmentSlotForItem", at = @At("RETURN"), cancellable = true)
    private void getEquipmentSlotForItem(
            ItemStack stack,
            CallbackInfoReturnable<EquipmentSlot> cir
    )
    {
        if (!stack.has(AwakenDataComponents.AWAKEN_SLOT_STORAGE))
            return;

        cir.setReturnValue(stack.get(AwakenDataComponents.AWAKEN_SLOT_STORAGE));
    }

    @Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
    private void getCustomHealth(CallbackInfoReturnable<Float> cir)
    {
        LivingEntity self = (LivingEntity) (Object) this;

        if (!(self instanceof Player player))
            cir.setReturnValue(HealthUtil.calculateMobHealth(self, cir.getReturnValue()));
    }

    @Inject(method = "onAttributeUpdated", at = @At("HEAD"), cancellable = true)
    private void onAttributeUpdated(
            Holder<Attribute> attribute,
            CallbackInfo ci
    )
    {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof Player player))
            return;

        if (player instanceof LocalPlayer player1 && player1.connection == null)
            ci.cancel();
        else if (player instanceof ServerPlayer player1 && player1.connection == null)
            ci.cancel();
    }
}