package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;

public class TooltipUtil
{
    public static List<Component> castInfixTooltip(
            TooltipFlag flag,
            AwakenInfix infix
    )
    {
        List<Component> components = new ArrayList<>();
        components.add(Component.empty().append(Component.translatable("tooltip.infix.info")).append(": ").append(LocaleUtil.localizeInfix(infix)));

        if (flag.hasShiftDown())
        {
            components.add(Component.empty().append(Component.translatable("tooltip.infix.attribute.info")).append(": ").append(infix.getAttribute().attr().getDescriptionId()));
            components.add(Component.empty().append(translateOperation(infix.getAttribute().operation())).append("" + infix.getAttribute().amount()));
            components.addAll(translateSlots(infix.getAttribute().slot()));
        } else
            components.add(Component.translatable("tooltip.hold_shift.info"));

        return components;
    }

    public static List<Component> castPrefixTooltip(
            TooltipFlag flag,
            AwakenPrefix prefix
    )
    {
        List<Component> components = new ArrayList<>();
        components.add(Component.empty().append(Component.translatable("tooltip.prefix.info")).append(": ").append(LocaleUtil.localizePrefix(prefix)));

        if (flag.hasShiftDown())
        {
            components.add(Component.empty().append(Component.translatable("tooltip.prefix.effect.info")).append(": "));
            components.addAll(translateEffects(prefix.effects()));
            components.add(Component.empty().append(Component.translatable("tooltip.durability.additional.info", prefix.addition())));
        } else
            components.add(Component.translatable("tooltip.hold_shift.info"));

        return components;
    }

    public static List<Component> castQualityTooltip(
            TooltipFlag flag,
            AwakenQuality quality
    )
    {
        List<Component> components = new ArrayList<>();
        components.add(Component.empty().append(Component.translatable("tooltip.quality.info")).append(": ").append(LocaleUtil.localizeQuality(quality)));

        if (flag.hasShiftDown())
            components.add(Component.empty().append(Component.translatable("tooltip.quality.factor.info")).append(": ").append("" + quality.getFactor()));
        else
            components.add(Component.translatable("tooltip.hold_shift.info"));

        return components;
    }

    public static List<Component> castSuffixTooltip(
            TooltipFlag flag,
            AwakenSuffix suffix
    )
    {
        List<Component> components = new ArrayList<>();
        components.add(Component.empty().append(Component.translatable("tooltip.suffix.info")).append(": ").append(LocaleUtil.localizeSuffix(suffix)));

        if (flag.hasShiftDown())
        {
            components.add(Component.translatable("tooltip.suffix.attribute.info", suffix.getTarget().getDescriptionId(), "" + suffix.factor()));
            components.add(Component.translatable("tooltip.durability.additional.info", suffix.addition()));
        } else
            components.add(Component.translatable("tooltip.hold_shift.info"));

        return components;
    }

    private static List<Component> translateEffects(
            MobEffectInstance[] instances
    )
    {
        List<Component> components = new ArrayList<>();
        for (MobEffectInstance instance: instances)
            components.add(Component.empty().append(Component.translatable("tooltip.effect.hold.info", instance.getDescriptionId(), instance.getAmplifier())));

        return components;
    }

    private static String translateOperation(
            AttributeModifier.Operation operation
    )
    {
        return switch (operation) {
            case ADD_VALUE -> "+ ";
            case ADD_MULTIPLIED_BASE -> "*= ";
            case ADD_MULTIPLIED_TOTAL -> "* ";
        };
    }

    private static List<Component> translateSlots(
            EquipmentSlot[] slots
    )
    {
        List<Component> components = new ArrayList<>();
        for (EquipmentSlot slot: slots)
            components.add(Component.empty().append(Component.translatable("tooltip.slot.avaiable.info")).append(Component.translatable(slot.getName())));

        return components;
    }

    private static Component translateTarget(
            AwakenPollinate.TriggerTarget target
    )
    {
        return Component.translatable(target.getUnlocalized());
    }

    private static Component translateTrigger(
            AwakenPollinate.TriggerType type
    )
    {
        return Component.translatable(type.getUnlocalized());
    }
}