package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.entries.raw.AwakenSpore;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import com.fomdev.awaken.util.HealthUtil;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
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
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

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
            BigDecimal factor = DifficultyManager.getLevelDifficulty(level).divide(new BigDecimal("20"), RoundingMode.HALF_UP);
            BigDecimal factor2 = factor.max(new BigDecimal("1.0"));
            BigDecimal factor3 = new BigDecimal(this.getExperienceReward(level, entity));
            BigDecimal factor4 = factor2.multiply(factor3).sqrt(new MathContext(2)).sqrt(new MathContext(2));
            int reward = EventHooks.getExperienceDrop(self, this.lastHurtByPlayer, factor4.intValue());
            ExperienceOrb.award(level, self.position(), reward);
        }
    }

    @Inject(method = "getAttributeValue", at = @At("RETURN"), cancellable = true)
    private void getAttributeValue(
            Holder<Attribute> attribute,
            CallbackInfoReturnable<Double> cir
    )
    {
        LivingEntity self = (LivingEntity) (Object) this;
        double original = cir.getReturnValue();

        if (self.level().isClientSide() && self instanceof LocalPlayer player && player.connection == null)
            return;
        else if (!self.level().isClientSide && self instanceof ServerPlayer player && player.connection == null)
            return;

        List<AwakenSpore.SporeInstance> spores = NBTUtil.deserializeSpores(self);
        for (AwakenSpore.SporeInstance instance: spores)
        {
            if (instance.getSpore().getAttribute().is(attribute))
                original += instance.getSpore().getAmount(instance.getLevel());
        }

        cir.setReturnValue(original);
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
            cir.setReturnValue(HealthUtil.calculateMobHealth(self, new BigDecimal(cir.getReturnValue())).floatValue());
        else if (!player.level().isClientSide())
            cir.setReturnValue(cir.getReturnValue() + HealthUtil.deserializeAdditionalHealthPersistent(player));
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