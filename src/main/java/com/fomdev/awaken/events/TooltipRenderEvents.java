package com.fomdev.awaken.events;

import com.fomdev.awaken.enchant.EnchantManager;
import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.entries.raw.affix.AwakenInfix;
import com.fomdev.awaken.entries.raw.affix.AwakenPrefix;
import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.TooltipUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EventBusSubscriber(modid = Awaken.MODID)
public class TooltipRenderEvents
{
    @SubscribeEvent
    public static void onAddTooltipForItem(
            ItemTooltipEvent event
    )
    {
        // moved from mixin to here
        ItemStack stack = event.getItemStack();
        TooltipFlag flag = event.getFlags();
        List<Component> list = event.getToolTip();

        if (stack.is(Items.ENCHANTED_BOOK))
        {
            List<AwakenAspect.AspectInstance> aspects = new ArrayList<>();
            ItemEnchantments enchantments = stack.get(DataComponents.STORED_ENCHANTMENTS);

            if (enchantments != null && !enchantments.isEmpty())
            {
                for (Object2IntMap.Entry<Holder<Enchantment>> enchantment : enchantments.entrySet())
                {
                    List<AwakenAspect.AspectInstance> aspect = EnchantManager.get(Objects.requireNonNull(enchantment.getKey().getKey()).location(), enchantment.getIntValue());
                    aspects.addAll(aspect);
                    aspects = new ArrayList<>(aspects);
                }

                list.add(1, Component.empty());
                list.addAll(1, TooltipUtil.castAspectTooltip(flag, aspects));
                return;
            }
        }

        if (stack.is(AwakenItems.SOUL_BOTTLE))
            list.addAll(1, TooltipUtil.castSoulTooltip(NBTUtil.deserializeSoul(stack)));

        AwakenMoods mood = NBTUtil.deserializeMood(stack);
        AwakenInfix.InfixContainer infix = NBTUtil.deserializeAffix$Infix(stack);
        AwakenPrefix.PrefixInstance prefix = NBTUtil.deserializeAffix$Prefix(stack);
        AwakenSuffix.SuffixContainer suffix = NBTUtil.deserializeAffix$Suffix(stack);
        AwakenQuality quality = NBTUtil.deserializeQuality(stack);
        AwakenAspect.AspectContainer aspects = NBTUtil.deserializeAspects(stack);

        if (aspects != null && !aspects.aspects().isEmpty() && !stack.is(AwakenItems.ASPECT_STONE))
        {
            list.addAll(1, TooltipUtil.castAspectTooltip(flag, aspects.aspects()));
            list.add(1, Component.empty());
        }

        if (!prefix.isEmpty())
        {
            list.addAll(1, TooltipUtil.castSuffixTooltip(flag, suffix));
            list.addAll(1, TooltipUtil.castInfixTooltip(flag, infix));
            list.addAll(1, TooltipUtil.castPrefixTooltip(flag, prefix));
            list.add(1, Component.empty());
        }

        if (quality != null)
            list.addAll(1, TooltipUtil.castQualityTooltip(flag, quality));

        if (mood != null)
            list.addAll(1, TooltipUtil.castMoodTooltip(flag, mood));
    }
}