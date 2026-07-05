package com.fomdev.awaken.mixin;

import com.fomdev.awaken.enchant.EnchantManager;
import com.fomdev.awaken.util.ColorUtil;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(Enchantment.class)
public class MixinEnchantment
{
    @Inject(method = "getFullname", at = @At("RETURN"), cancellable = true)
    private static void getDescription(Holder<Enchantment> enchantment, int level, CallbackInfoReturnable<Component> cir)
    {
        MutableComponent component = enchantment.value().description().copy();

        if (enchantment.is(EnchantmentTags.CURSE))
            component.setStyle(ColorUtil.colorStyle(Color.RED));
        else if (enchantment.value().getMaxLevel() == 1)
            component.setStyle(ColorUtil.colorStyle(Color.MAGENTA));
        else
            component.setStyle(ColorUtil.colorStyle(EnchantManager.colors.get(level - 1)));

        if (enchantment.value().getMaxLevel() != 1)
            component.append(" ").append(Component.translatable("tooltip.enchantment.level." + level));

        cir.setReturnValue(component);
    }

    @Inject(method = "getMaxLevel", at = @At("RETURN"), cancellable = true)
    private void getMaxLevel(CallbackInfoReturnable<Integer> cir)
    {
        if (cir.getReturnValue() == 1)
            return;

        cir.setReturnValue(20);
    }
}