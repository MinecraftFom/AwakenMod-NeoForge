package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.ArrayList;
import java.util.List;

public class LocaleUtil
{
    public static Component localizeAspect(
            AwakenAspect aspect
    )
    {
        return Component.translatable("aspect." + aspect.id() + ".name");
    }

    public static Component localizeAwakenLevel(
            AwakenLevel level
    )
    {
        return Component.translatable("level." + level.id() + ".name");
    }

    public static Component localizeInfix(
            AwakenInfix infix
    )
    {
        return Component.translatable("infix." + infix.id() + ".name");
    }

    public static String localizeOperation(
            AttributeModifier.Operation operation
    )
    {
        return switch (operation) {
            case ADD_VALUE -> "+ ";
            case ADD_MULTIPLIED_BASE -> "*= ";
            case ADD_MULTIPLIED_TOTAL -> "* ";
        };
    }

    public static Component localizePollinate(
            AwakenPollinate pollinate
    )
    {
        return Component.translatable("pollinate." + pollinate.id() + ".name");
    }

    public static Component localizePrefix(
            AwakenPrefix prefix
    )
    {
        return Component.translatable("prefix." + prefix.id() + ".name");
    }

    public static Component localizeQuality(
            AwakenQuality quality
    )
    {
        return Component.translatable("quality." + quality.id() + ".name");
    }

    public static List<Component> localizeSlots(
            EquipmentSlot[] slots
    )
    {
        List<Component> components = new ArrayList<>();
        for (EquipmentSlot slot: slots)
            components.add(Component.empty().append(Component.translatable("tooltip.slot.avaiable.info")).append(Component.translatable(slot.getName())));

        return components;
    }

    public static Component localizeSpore(
            AwakenSpore spore

    )
    {
        return Component.translatable("spore." + spore.id() + ".name");
    }

    public static Component localizeSuffix(
            AwakenSuffix suffix
    )
    {
        return Component.translatable("suffix." + suffix.id() + ".name");
    }

    public static Component localizeTarget(
            AwakenPollinate.TriggerTarget target
    )
    {
        return Component.translatable(target.getUnlocalized());
    }

    public static Component localizeTrigger(
            AwakenPollinate.TriggerType type
    )
    {
        return Component.translatable(type.getUnlocalized());
    }
}