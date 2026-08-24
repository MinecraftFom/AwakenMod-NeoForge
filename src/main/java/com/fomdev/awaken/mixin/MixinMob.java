package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import net.minecraft.core.component.DataComponents;
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

import java.util.Random;

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

            if (stack.isDamageableItem())
            {
                int max = stack.getMaxDamage();
                float diff = DifficultyManager.getLevelDifficulty(level).floatValue();
                int factor = (int) (Math.abs(diff) / max);
                int factor2 = factor <= 0? 1: factor;
                int d = new Random().nextInt(factor2);
                int d2 = max - d;
                stack.setDamageValue(d2);
            }

            self.spawnAtLocation(stack);
            setItemSlot(slot, ItemStack.EMPTY);
        }

        if (self.hasData(AwakenAttachmentTypes.IS_AWAKEN))
        {
            ItemStack stack = new ItemStack(AwakenItems.SOUL_FRAGMENT.asItem());
            NBTUtil.serializeSoul(stack, new Records.AwakenSoulComponent(self.getRandom().nextInt(100) + 1, 100));
            self.spawnAtLocation(stack);
        }
    }
}