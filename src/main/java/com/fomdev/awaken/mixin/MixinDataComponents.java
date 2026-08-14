package com.fomdev.awaken.mixin;

import com.fomdev.awaken.enchant.EnchantManager;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.UnaryOperator;

@Mixin(DataComponents.class)
public class MixinDataComponents
{
    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static <T> void onRegister(
            String name,
            UnaryOperator<DataComponentType.Builder<T>> builder,
            CallbackInfoReturnable<DataComponentType<T>> cir
    )
    {
        if (!name.equals("enchantments") && !name.equals("stored_enchantments"))
            return;

        if (name.equals("enchantments"))
        {
            UnaryOperator<DataComponentType.Builder<ItemEnchantments>> b =
                    (bd) -> bd.persistent(EnchantManager.CODEC).networkSynchronized(EnchantManager.STREAM_CODEC).cacheEncoding();

            cir.setReturnValue(
                    (DataComponentType<T>) Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, name, (b.apply(DataComponentType.builder())).build())
            );
        } else if (name.equals("stored_enchantments"))
        {
            UnaryOperator<DataComponentType.Builder<ItemEnchantments>> b =
                    (bd) -> bd.persistent(EnchantManager.CODEC).networkSynchronized(EnchantManager.STREAM_CODEC).cacheEncoding();

            cir.setReturnValue(
                    (DataComponentType<T>) Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, name, (b.apply(DataComponentType.builder()).build()))
            );
        }
    }
}