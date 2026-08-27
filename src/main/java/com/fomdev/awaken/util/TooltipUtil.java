package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.entries.raw.affix.AwakenInfix;
import com.fomdev.awaken.entries.raw.affix.AwakenPrefix;
import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.entries.raw.spore.AwakenPollinate;
import com.fomdev.awaken.entries.raw.spore.AwakenSpore;
import com.fomdev.flame.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TooltipUtil
{
    public static List<Component> castAspectTooltip(
            TooltipFlag flag,
            List<AwakenAspect.AspectInstance> aspects
    )
    {
        if (aspects.isEmpty())
            return List.of();

        List<Component> components = new ArrayList<>();
        components
                .add(
                        Component
                                .empty()
                                .append(
                                        Component
                                                .translatable(
                                                        "tooltip.aspect.info"
                                                )
                                )
                                .withStyle(ChatFormatting.GRAY)
                );

        if (flag.hasShiftDown())
            for (AwakenAspect.AspectInstance instance: aspects)
                components.add(
                        Component
                                .empty()
                                .append(
                                        LocaleUtil.localizeAspect(instance)
                                )
                                .append(
                                        ": "
                                )
                                .append(
                                        "" + instance.amount()
                                )
                                .withStyle(ChatFormatting.GRAY)
                );
        else
            components.add(Component.translatable("tooltip.hold_shift.info").withStyle(ChatFormatting.GRAY));

        return components;
    }

    public static List<Component> castEpochTooltip(
            TooltipFlag flag,
            Records.AwakenEpochComponent epoch,
            BigDecimal currentLevel,
            BigDecimal currentDiff
    )
    {
        List<Component> components = new ArrayList<>();
        components.add(
                Component
                        .empty()
                        .append(
                                Component
                                        .translatable(
                                                "tooltip.unawaken.info"
                                        )
                                        .withStyle(ChatFormatting.GRAY)
                        )
        );

        components.add(
                Component
                        .empty()
                        .append(
                                Component
                                        .translatable(
                                                "tooltip.unawaken.required.info"
                                        )
                                        .withStyle(ChatFormatting.GRAY)
                        )
        );

        if (flag.hasShiftDown())
        {
            components.add(
                    Component
                            .empty()
                            .append(
                                    Component
                                            .translatable(
                                                    "tooltip.unawaken.diff.info",
                                                    currentDiff.toPlainString(),
                                                    epoch.requiredMinDifficulty().toPlainString()
                                            )
                            )
                            .withStyle(ChatFormatting.GRAY)
            );

            components.add(
                    Component
                            .empty()
                            .append(
                                    Component
                                            .translatable(
                                                    "tooltip.unawaken.level.info",
                                                    currentLevel.toPlainString(),
                                                    epoch.requiredAwakenLevel().toPlainString()
                                            )
                            )
                            .withStyle(ChatFormatting.GRAY)
            );
        } else
            components.add(
                    Component
                            .translatable(
                                    "tooltip.hold_shift.info"
                            )
                            .withStyle(ChatFormatting.GRAY)
            );

        return components;
    }

    public static List<Component> castInfixTooltip(
            TooltipFlag flag,
            AwakenInfix.InfixContainer infix
    )
    {
        // TODO: complete this
        return List.of();
    }

    public static List<Component> castMoodTooltip(
            TooltipFlag flag,
            AwakenMoods mood
    )
    {
        List<Component> components = new ArrayList<>();
        components.add(Component.translatable("tooltip." + mood.id() + ".info"));
        components.add(Component.empty());
        components.add(Component.empty());
        return components;
    }

    public static List<Component> castPollinateTooltip(
            TooltipFlag flag,
            AwakenPollinate.PollinateInstance pollinate
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
                                                        "tooltip.pollinate.info"
                                                )
                                )
                                .append(
                                        ": "
                                )
                                .append(
                                        LocaleUtil.localizePollinate(pollinate.getPollinate())
                                )
                                .append(
                                        " (" + pollinate.getLevel() + ")"
                                )
                                .withStyle(ChatFormatting.GRAY)
                );

        if (flag.hasShiftDown())
            components.add(
                    Component
                            .empty()
                            .append(
                                    Component
                                            .translatable(
                                                    "tooltip.pollinate.when.info",
                                                    LocaleUtil.localizeTrigger(pollinate.getPollinate().getType()),
                                                    LocaleUtil.localizeTarget(pollinate.getPollinate().getTarget()),
                                                    Component.translatable(pollinate.getPollinate().getEffect(pollinate.getLevel()).getDescriptionId()).getString(),
                                                    Util.castTickToString(pollinate.getPollinate().getEffect(pollinate.getLevel()).getDuration())
                                            )
                            )
                            .withStyle(ChatFormatting.GRAY)
            );
        else
            components.add(
                    Component
                            .translatable("tooltip.hold_shift.info")
                            .withStyle(ChatFormatting.GRAY)
            );

        return components;
    }

    public static List<Component> castPrefixTooltip(
            TooltipFlag flag,
            AwakenPrefix.PrefixInstance prefix
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
                                .withStyle(ChatFormatting.GRAY)
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
                                    .withStyle(ChatFormatting.GRAY)
                    );
            components
                    .add(
                            Component
                                    .empty()
                                    .append(
                                            Component
                                                    .translatable(
                                                            "tooltip.durability.additional.info",
                                                            prefix.getDurability()
                                                    )
                                    )
                                    .withStyle(ChatFormatting.GRAY)
                    );
        } else
            components
                    .add(
                            Component
                                    .translatable(
                                            "tooltip.hold_shift.info"
                                    )
                                    .withStyle(ChatFormatting.GRAY)
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
                                .withStyle(ChatFormatting.GRAY)
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
                                    .withStyle(ChatFormatting.GRAY)
                    );
        else
            components
                    .add(
                            Component
                                    .translatable(
                                            "tooltip.hold_shift.info"
                                    )
                                    .withStyle(ChatFormatting.GRAY)
                    );

        return components;
    }

    public static List<Component> castSoulTooltip(
            Records.AwakenSoulComponent soul
    )
    {
        return List.of(
                Component
                        .translatable("tooltip.soul_current.info")
                        .append(": ")
                        .append("" + soul.current())
                        .withStyle(ChatFormatting.GRAY),
                Component
                        .translatable("tooltip.soul_max.info")
                        .append(": ")
                        .append("" + soul.maximum())
                        .withStyle(ChatFormatting.GRAY)
        );
    }

    public static List<Component> castSuffixTooltip(
            TooltipFlag flag,
            AwakenSuffix.SuffixInstance suffix
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
                                .withStyle(ChatFormatting.GRAY)
                );

        if (flag.hasShiftDown())
        {
            components
                    .add(
                            Component
                                    .translatable(
                                            "tooltip.suffix.attribute.info",
                                            Component.translatable(suffix.getTarget().value().getDescriptionId()).getString(),
                                            "" + suffix.factor()
                                    )
                                    .withStyle(ChatFormatting.GRAY)
                    );
            components
                    .add(
                            Component
                                    .translatable(
                                            "tooltip.durability.additional.info",
                                            suffix.addition()
                                    )
                                    .withStyle(ChatFormatting.GRAY)
                    );
        } else
            components
                    .add(
                            Component
                                    .translatable(
                                            "tooltip.hold_shift.info"
                                    )
                                    .withStyle(ChatFormatting.GRAY)
                    );

        return components;
    }

    private static List<Component> translateEffects(
            MobEffectInstance[] instances
    )
    {
        List<Component> components = new ArrayList<>();
        for (MobEffectInstance instance: instances)
            components.add(Component.empty().append(Component.translatable("tooltip.effect.whenhold.info", Component.translatable(instance.getDescriptionId()).getString(), Util.castTickToString(instance.getDuration()))).withStyle(ChatFormatting.GRAY));

        return components;
    }
}