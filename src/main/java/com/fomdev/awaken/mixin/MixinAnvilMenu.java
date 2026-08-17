package com.fomdev.awaken.mixin;

import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class MixinAnvilMenu extends ItemCombinerMenu
{
    @Shadow
    public int repairItemCountCost;

    @Shadow
    @Final
    private DataSlot cost;

    public MixinAnvilMenu(@Nullable MenuType<?> type, int containerId, Inventory playerInventory, ContainerLevelAccess access)
    {
        super(type, containerId, playerInventory, access);
    }

    @Redirect(method = "createResult", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"))
    private int createResult(
            int a,
            int b
    )
    {
        int max = b * 4;
        return Math.min((int) Math.sqrt(a), max); // Not lower than 1
    }

    @Redirect(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/DataSlot;get()I"))
    private int createResult(
            DataSlot instance
    )
    {
        return 39; // Thank you
    }

    @Redirect(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/AnvilMenu;calculateIncreasedRepairCost(I)I"))
    private int createResult(
            int original
    )
    {
        float proficiency = NBTUtil.deserializeKnowledge(this.player).proficiency();
        float factor = (float) Math.pow(proficiency, 1.0 / 3.0);
        float factor1 = Math.max(factor, 1);
        return (int) (original / factor1);
    }

    @Redirect(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V"))
    private void createResult(
            ItemStack instance,
            int damage
    )
    {
        float proficiency = NBTUtil.deserializeKnowledge(this.player).proficiency();
        float factor = proficiency <= 1? 1: proficiency;
        int result = (int) (damage / factor);
        instance.setDamageValue(result);
    }

    @Inject(method = "createResult", at = @At("RETURN"))
    private void createResult(
            CallbackInfo ci
    )
    {
        int count = this.repairItemCountCost;
        int cost = this.cost.get();

        if (count <= 0)
            return;

        float skill = NBTUtil.deserializeKnowledge(this.player).skill();
        float factor = (float) Math.pow(skill <= 0? 1: skill, 1.0 / 5.0);
        int result = (int) (count / factor);
        this.repairItemCountCost = Math.clamp(result, 1, 64);

        if (cost > 0)
        {
            float experience = NBTUtil.deserializeKnowledge(this.player).experience();
            float factor2 = (float) Math.pow(experience, 1.0 / 5.0);
            this.cost.set(Math.max((int) (cost / factor2), 1));
        }
    }
}