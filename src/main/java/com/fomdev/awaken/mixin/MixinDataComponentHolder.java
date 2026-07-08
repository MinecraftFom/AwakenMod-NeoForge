package com.fomdev.awaken.mixin;

import com.fomdev.awaken.entries.*;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DataComponentHolder.class)
public interface MixinDataComponentHolder
{
    @Inject(method = "getOrDefault", at = @At("RETURN"), cancellable = true)
    private <T> void changeAttributes(DataComponentType<? extends T> type, T defaultValue, CallbackInfoReturnable<T> cir)
    {
        if (type != DataComponents.ATTRIBUTE_MODIFIERS)
            return;

        if (!((DataComponentHolder) this instanceof ItemStack stack))
            return;

        if (stack.is(Items.AIR))
            return;

        ItemAttributeModifiers modifiers = (ItemAttributeModifiers) cir.getReturnValue();

        AwakenPrefix prefix = NBTUtil.deserializePrefix(stack);
        AwakenSuffix suffix = NBTUtil.deserializeSuffix(stack);
        AwakenInfix infix = NBTUtil.deserializeInfix(stack);
        AwakenQuality quality = NBTUtil.deserializeQuality(stack);
        List<AwakenSpore.SporeInstance> spore = NBTUtil.deserializeSpores(stack);

        double factor = quality == null? 1D: quality.getFactor();

        if (prefix != null && suffix != null && infix != null)
        {
            Holder<Attribute> attribute = infix.getAttribute().attr();
            double amount = (suffix.should(attribute)? infix.getAttribute().amount() * suffix.factor(): infix.getAttribute().amount()) * factor;
            AttributeModifier.Operation operation = infix.getAttribute().operation();
            EquipmentSlot[] slots = infix.getAttribute().slot();

            for (EquipmentSlot slot: slots)
            {
               modifiers.modifiers().add(new ItemAttributeModifiers.Entry(
                        attribute,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(
                                        infix.id(),
                                        attribute.getRegisteredName() + "_" + slot.getName()
                                ),
                                amount,
                                operation
                        ),
                        EquipmentSlotGroup.bySlot(slot)
                ));
            }
        }

        for (AwakenSpore.SporeInstance instance: spore)
        {
            AwakenSpore value = instance.getSpore();
            int level = instance.getLevel();
            double amount = value.getAmount(level) * factor;
            Holder<Attribute> attribute = value.getAttribute();

            for (EquipmentSlot slot: value.getSlots())
            {
                modifiers.modifiers().add(new ItemAttributeModifiers.Entry(
                        attribute,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(
                                        value.id(),
                                        attribute.getRegisteredName() + "_" + slot.getName()
                                ),
                                amount,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.bySlot(slot)
                ));
            }
        }

        cir.setReturnValue((T) modifiers);
    }
}