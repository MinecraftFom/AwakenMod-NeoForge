package com.fomdev.awaken.mixin;

import com.fomdev.awaken.init.config.AwakenCommon;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEnchantments.Mutable.class)
public class MixinMutable
{
    @Redirect(method = "set", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"))
    private int set(
            int a, int b
    )
    {
        return Math.min(a, AwakenCommon.CONFIG.MAX_ACCEPTABLE_ENCHANT.get());
    }

    @Redirect(method = "upgrade", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"))
    private int upgrade(
            int a,
            int b
    )
    {
        return Math.min(a, AwakenCommon.CONFIG.MAX_ACCEPTABLE_ENCHANT.get());
    }
}