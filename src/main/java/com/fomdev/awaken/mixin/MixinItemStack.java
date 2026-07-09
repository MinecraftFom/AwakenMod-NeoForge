package com.fomdev.awaken.mixin;

import com.fomdev.awaken.entries.*;
import com.fomdev.awaken.util.ColorUtil;
import com.fomdev.awaken.util.LocaleUtil;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.TooltipUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class MixinItemStack implements DataComponentHolder
{
    @Shadow
    public abstract Component getHoverName();

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract boolean is(Item p_150931_);

    @Shadow
    public abstract int getMaxDamage();

    @Shadow
    public abstract int getDamageValue();

    @Shadow
    @Final
    PatchedDataComponentMap components;

    @Shadow
    @Final
    private static Component DISABLED_ITEM_TOOLTIP;

    @Inject(method = "getMaxDamage", at = @At("RETURN"), cancellable = true)
    private void customDurability(CallbackInfoReturnable<Integer> cir)
    {
        ItemStack self = (ItemStack) (Object) this;
        cir.setReturnValue(self.getItem().getMaxDamage(self));
    }

    @Inject(method = "getHoverName", at = @At("RETURN"), cancellable = true)
    private void fancyName(CallbackInfoReturnable<Component> cir)
    {
        ItemStack self = (ItemStack) (Object) this;
        AwakenQuality quality = NBTUtil.deserializeQuality(self);
        AwakenInfix infix = NBTUtil.deserializeInfix(self);
        AwakenPrefix prefix = NBTUtil.deserializePrefix(self);
        AwakenSuffix suffix = NBTUtil.deserializeSuffix(self);
        MutableComponent component = Component.empty();

        if (infix != null && prefix != null && suffix != null)
            component.append(LocaleUtil.localizePrefix(prefix)).append(" ").append(LocaleUtil.localizeInfix(infix)).append("-").append(cir.getReturnValue()).append(" (").append(LocaleUtil.localizeSuffix(suffix)).append(")");
        else
            component.append(cir.getReturnValue());

        if (quality != null)
            component.withStyle(ColorUtil.colorStyle(ColorUtil.render(quality.getColors(), quality.getPattern()).backEnd()));

        cir.setReturnValue(component);
    }

    @Inject(method = "getTooltipLines", at = @At("RETURN"), cancellable = true)
    private void fancyTooltip(Item.TooltipContext context, Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir)
    {
        ItemStack self = (ItemStack) (Object) this;
        List<Component> list = new ArrayList<>();
        Consumer<Component> consumer = list::add;

        if (player == null || self.is(Items.AIR))
            return;

        if (!player.isCreative() && this.has(DataComponents.HIDE_TOOLTIP))
        {
            cir.setReturnValue(list);
            return;
        }

        if (!this.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP))
            this.getItem().appendHoverText(self, context, list, flag);

        MutableComponent hover = Component.empty().append(this.getHoverName());

        if (this.has(DataComponents.CUSTOM_NAME))
            hover.withStyle(ChatFormatting.ITALIC);

        list.addFirst(hover);

        AwakenInfix infix = NBTUtil.deserializeInfix(self);
        AwakenPrefix prefix = NBTUtil.deserializePrefix(self);
        AwakenSuffix suffix = NBTUtil.deserializeSuffix(self);
        AwakenQuality quality = NBTUtil.deserializeQuality(self);
        List<AwakenAspect.AspectInstance> aspects = NBTUtil.deserializeAspects(self);
        List<AwakenPollinate.PollinateInstance> pollinates = NBTUtil.deserializePollinates(self);
        List<AwakenSpore.SporeInstance> spores = NBTUtil.deserializeSpores(self);

        for (AwakenPollinate.PollinateInstance instance: pollinates)
            list.addAll(TooltipUtil.castPollinateTooltip(flag, instance));

        for (AwakenSpore.SporeInstance instance: spores)
            list.addAll(TooltipUtil.castSporeTooltip(flag, instance));

        if (infix != null && prefix != null && suffix != null)
        {
            list.addAll(TooltipUtil.castInfixTooltip(flag, infix, (float) (quality != null? quality.getFactor() * suffix.factor(): suffix.factor())));
            list.addAll(TooltipUtil.castPrefixTooltip(flag, prefix));
            list.addAll(TooltipUtil.castSuffixTooltip(flag, suffix));
        }

        if (quality != null)
            list.addAll(TooltipUtil.castQualityTooltip(flag, quality));

        if (!flag.isAdvanced() && !this.has(DataComponents.CUSTOM_NAME) && this.is(Items.FILLED_MAP))
        {
            MapId mapid = this.get(DataComponents.MAP_ID);
            if (mapid != null)
                list.add(MapItem.getTooltipForId(mapid));
        }

        this.addToTooltip(DataComponents.JUKEBOX_PLAYABLE, context, consumer, flag);
        this.addToTooltip(DataComponents.TRIM, context, consumer, flag);
        this.addToTooltip(DataComponents.STORED_ENCHANTMENTS, context, consumer, flag);
        this.addToTooltip(DataComponents.ENCHANTMENTS, context, consumer, flag);
        this.addToTooltip(DataComponents.DYED_COLOR, context, consumer, flag);
        this.addToTooltip(DataComponents.LORE, context, consumer, flag);

        AttributeUtil.addAttributeTooltips(self, consumer, AttributeTooltipContext.of(player, context, flag));
        this.addToTooltip(DataComponents.UNBREAKABLE, context, consumer, flag);

        AdventureModePredicate can_break = this.get(DataComponents.CAN_BREAK);
        if (can_break != null && can_break.showInTooltip())
        {
            consumer.accept(CommonComponents.EMPTY);
            consumer.accept(AdventureModePredicate.CAN_BREAK_HEADER);
            can_break.addToTooltip(consumer);
        }

        AdventureModePredicate can_place = this.get(DataComponents.CAN_PLACE_ON);
        if (can_place != null && can_place.showInTooltip())
        {
            consumer.accept(CommonComponents.EMPTY);
            consumer.accept(AdventureModePredicate.CAN_PLACE_HEADER);
            can_place.addToTooltip(consumer);
        }

        list.addAll(TooltipUtil.castAspectTooltip(flag, aspects));

        if (flag.isAdvanced())
        {
            list.add(Component.translatable("item.durability", this.getMaxDamage() - this.getDamageValue(), this.getMaxDamage()));

            list.add(Component.literal(BuiltInRegistries.ITEM.getKey(this.getItem()).toString()).withStyle(ChatFormatting.DARK_GRAY));
            int i = this.components.size();
            if (i > 0)
                list.add(Component.translatable("item.components", i).withStyle(ChatFormatting.DARK_GRAY));

            if (player != null && !this.getItem().isEnabled(player.level().enabledFeatures()))
                list.add(DISABLED_ITEM_TOOLTIP);
        }

        cir.setReturnValue(list);
    }
}