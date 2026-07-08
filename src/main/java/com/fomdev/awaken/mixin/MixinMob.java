package com.fomdev.awaken.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MixinMob
{
    @Shadow
    public abstract void setItemSlot(EquipmentSlot p_21416_, ItemStack p_21417_);

    @Inject(method = "dropCustomDeathLoot", at = @At("HEAD"))
    private void dropEquipment(
            ServerLevel level,
            DamageSource source,
            boolean p,
            CallbackInfo ci
    )
    {
        Mob self = (Mob) (Object) this;

        for (EquipmentSlot slot: EquipmentSlot.values())
        {
            ItemStack stack = self.getItemBySlot(slot);
            if (stack.is(Items.AIR))
                continue;

            self.spawnAtLocation(stack);
            setItemSlot(slot, ItemStack.EMPTY);
        }
    }
}