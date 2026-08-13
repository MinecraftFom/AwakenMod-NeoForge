package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.enchant.EnchantManager;
import com.fomdev.awaken.entries.raw.AwakenLevel;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import com.fomdev.awaken.util.ColorUtil;
import com.fomdev.awaken.util.LocaleUtil;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Player.class)
public abstract class MixinPlayer
{
    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot1);

    @Shadow
    public abstract boolean isCreative();

    @Shadow
    @Nullable
    public abstract ItemEntity drop(ItemStack itemStack, boolean includeThrowerName);

    @Shadow
    public abstract void setItemSlot(EquipmentSlot slot, ItemStack stack);

    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void fancyName(CallbackInfoReturnable<Component> cir)
    {
        Player self = (Player) (Object) this;
        MutableComponent original = Component.empty();
        float level = NBTUtil.deserializeAwakenLevel(self);
        AwakenLevel awakenLevel = AwakenRegistries.AWAKEN_LEVEL.getLevel(level);

        if (awakenLevel == null)
            original.append(cir.getReturnValue());
        else
            original.append("[").append(LocaleUtil.localizeAwakenLevel(awakenLevel)).append("] ").append(cir.getReturnValue()).withStyle(ColorUtil.colorStyle(awakenLevel.getColor()));

        cir.setReturnValue(original);
    }

    @Inject(method = "getXpNeededForNextLevel", at = @At("RETURN"), cancellable = true)
    private void getXpNeededForNextLevel(
            CallbackInfoReturnable<Integer> cir
    )
    {
        cir.setReturnValue(EnchantManager.xpLevel);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(
            CallbackInfo ci
    )
    {
        Player player = (Player) (Object) this;
        if (!(player instanceof ServerPlayer serverPlayer))
            return;

        if (isCreative())
            return;

        for (EquipmentSlot slot: EquipmentSlot.values())
        {
            ItemStack stack = getItemBySlot(slot);
            if (stack.isEmpty())
                continue;

            Records.AwakenEpochComponent epoch;
            if ((epoch = stack.get(AwakenDataComponents.AWAKEN_EPOCH_STORAGE)) == null)
                continue;

            float awakenLevel = NBTUtil.deserializeAwakenLevel(player);
            float difficulty = DifficultyManager.getLevelDifficulty(serverPlayer.serverLevel());
            if (epoch.requiredAwakenLevel() > awakenLevel || epoch.requiredMinDifficulty() > difficulty)
            {
                drop(stack, true);
                setItemSlot(slot, Items.AIR.getDefaultInstance());
            }
        }
    }
}