package com.fomdev.awaken.mixin;

import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.spawn.EquipmentManager;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;

@Mixin(IItemStackExtension.class)
public interface MixinIItemStackExtension
{
    @Inject(method = "getAttributeModifiers", at = @At("RETURN"), cancellable = true)
    private void getAttributeModifiers(
            CallbackInfoReturnable<ItemAttributeModifiers> cir
    )
    {
        IItemStackExtension ext = (IItemStackExtension) this;
        if (!(ext instanceof ItemStack stack))
            return;

        if (stack.is(Items.AIR))
            return;

        ItemAttributeModifiers modifiers = cir.getReturnValue();

        AwakenMoods mood = NBTUtil.deserializeMood(stack);

        AwakenPrefix prefix = NBTUtil.deserializePrefix(stack);
        AwakenSuffix suffix = NBTUtil.deserializeSuffix(stack);
        AwakenInfix infix = NBTUtil.deserializeInfix(stack);
        AwakenQuality quality = NBTUtil.deserializeQuality(stack);

        double factor = quality == null? 1D: quality.getFactor();

        if (prefix != null && suffix != null && infix != null)
        {
            Holder<Attribute> attribute = infix.getAttribute().attr();
            double amount = (suffix.should(attribute)? infix.getAttribute().amount() * suffix.factor(): infix.getAttribute().amount()) * factor;
            AttributeModifier.Operation operation = infix.getAttribute().operation();
            EquipmentSlot[] slots = infix.getAttribute().slot();
            EquipmentSlot slot = EquipmentManager.forSlot(stack);
            if (Arrays.asList(slots).contains(slot))
                modifiers = modifiers.withModifierAdded(
                        attribute,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(
                                        infix.id(),
                                        attribute.unwrapKey().orElseThrow().location().getPath() + "_" + slot.getName()
                                ),
                                amount,
                                operation
                        ),
                        EquipmentSlotGroup.bySlot(slot)
                );
        }

        if (mood != null)
        {
            Holder<Attribute> reinforce = mood.getReinforce();
            Holder<Attribute> weaken = mood.getWeaken();

            modifiers = modifiers.withModifierAdded(
                    reinforce,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(
                                    mood.id(),
                                    reinforce.unwrapKey().orElseThrow().location().getPath() + "_reinforce"
                            ),
                            mood.getReinforceAmount(),
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                    ),
                    EquipmentSlotGroup.bySlot(EquipmentManager.forSlot(stack))
            );

            modifiers = modifiers.withModifierAdded(
                    weaken,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(
                                    mood.id(),
                                    weaken.unwrapKey().orElseThrow().location().getPath() + "_weaken"
                            ),
                            -mood.getWeakenAmount(),
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                    ),
                    EquipmentSlotGroup.bySlot(EquipmentManager.forSlot(stack))
            );
        }

        cir.setReturnValue(modifiers);
    }
}