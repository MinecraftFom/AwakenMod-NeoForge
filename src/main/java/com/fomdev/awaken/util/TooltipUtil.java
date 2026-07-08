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
            AwakenInfix infix,
            float factor
    )
    {
        List<Component> components = new ArrayList<>();
        components
                .add(
                        Component
                                .empty()
                                .append(
                                        Component
                                                .translatable(
                                                        "tooltip.infix.info"
                                                )
                                )
                                .append(
                                        ": "
                                )
                                .append(
                                        LocaleUtil.localizeInfix(infix)
                                )
                );

        if (flag.hasShiftDown())
        {
            components
                    .add(
                            Component
                                    .empty()
                                    .append(
                                            Component
                                                    .translatable(
                                                            "tooltip.infix.attribute.info"
                                                    )
                                    )
                                    .append(
                                            ": "
                                    )
                                    .append(
                                            infix.getAttribute().attr().getRegisteredName()
                                    )
                    );
            components
                    .add(
                            Component
                                    .empty()
                                    .append(
                                            LocaleUtil.localizeOperation(
                                                    infix.getAttribute().operation()
                                            )
                                    )
                                    .append(
                                            factor <= 0
                                                    ? "" + infix.getAttribute().amount()
                                                    : infix.getAttribute().amount() * factor + " [" + infix.getAttribute().amount() + " * " + factor + "]"
                                    )
                    );
            components
                    .addAll(
                            LocaleUtil.localizeSlots(infix.getAttribute().slot())
                    );
        } else
            components
                    .add(
                            Component
                                    .translatable(
                                            "tooltip.hold_shift.info"
                                    )
                    );

        return components;
    }

    public static List<Component> castPrefixTooltip(
            TooltipFlag flag,
            AwakenPrefix prefix
    )
    {
        List<Component> components = new ArrayList<>();
        components
                .add(
                        Component
                                .empty()
                                .append(
                                        Component
                                                .translatable(
                                                        "tooltip.prefix.info"
                                                )
                                )
                                .append(
                                        ": "
                                )
                                .append(
                                        LocaleUtil.localizePrefix(prefix)
                                )
                );

        if (flag.hasShiftDown())
        {
            components
                    .add(
                            Component
                                    .empty()
                                    .append(
                                            Component
                                                    .translatable(
                                                            "tooltip.prefix.effect.info"
                                                    )
                                    )
                                    .append(
                                            ": "
                                    )
                    );
            components
                    .addAll(
                            translateEffects(prefix.effects()
                            )
                    );
            components
                    .add(
                            Component
                                    .empty()
                                    .append(
                                            Component
                                                    .translatable(
                                                            "tooltip.durability.additional.info",
                                                            prefix.addition()
                                                    )
                                    )
                    );
        } else
            components
                    .add(
                            Component
                                    .translatable(
                                            "tooltip.hold_shift.info"
                                    )
                    );

        return components;
    }

    public static List<Component> castQualityTooltip(
            TooltipFlag flag,
            AwakenQuality quality
    )
    {
        List<Component> components = new ArrayList<>();
        components
                .add(
                        Component
                                .empty()
                                .append(
                                        Component
                                                .translatable(
                                                        "tooltip.quality.info"
                                                )
                                )
                                .append(
                                        ": "
                                )
                                .append(
                                        LocaleUtil.localizeQuality(quality)
                                )
                );

        if (flag.hasShiftDown())
            components
                    .add(
                            Component
                                    .empty()
                                    .append(
                                            Component
                                                    .translatable(
                                                            "tooltip.quality.factor.info"
                                                    )
                                    )
                                    .append(
                                            ": "
                                    )
                                    .append(
                                            "" + quality.getFactor()
                                    )
                    );
        else
            components
                    .add(
                            Component
                                    .translatable(
                                            "tooltip.hold_shift.info"
                                    )
                    );

        return components;
    }

    public static List<Component> castSuffixTooltip(
            TooltipFlag flag,
            AwakenSuffix suffix
    )
    {
        List<Component> components = new ArrayList<>();
        components
                .add(
                        Component
                                .empty()
                                .append(
                                        Component
                                                .translatable(
                                                        "tooltip.suffix.info"
                                                )
                                )
                                .append(
                                        ": "
                                )
                                .append(
                                        LocaleUtil.localizeSuffix(suffix)
                                )
                );

        if (flag.hasShiftDown())
        {
            components
                    .add(
                            Component
                                    .translatable(
                                            "tooltip.suffix.attribute.info",
                                            suffix.getTarget().getRegisteredName(),
                                            "" + suffix.factor()
                                    )
                    );
            components
                    .add(
                            Component
                                    .translatable(
                                            "tooltip.durability.additional.info",
                                            suffix.addition()
                                    )
                    );
        } else
            components
                    .add(
                            Component
                                    .translatable(
                                            "tooltip.hold_shift.info"
                                    )
                    );

        return components;
    }

    private static List<Component> translateEffects(
            MobEffectInstance[] instances
    )
    {
        List<Component> components = new ArrayList<>();
        for (MobEffectInstance instance: instances)
            components.add(Component.empty().append(Component.translatable("tooltip.effect.whenhold.info", instance.getDescriptionId(), instance.getAmplifier())));

        return components;
    }
}