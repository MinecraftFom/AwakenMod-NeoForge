package com.fomdev.awaken.mixin;

import com.fomdev.awaken.enchant.EnchantManager;
import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper
{
    /**
     * @author Fom477
     * @reason why not?
     */
    @Overwrite
    public static List<EnchantmentInstance> getAvailableEnchantmentResults(
            int cost,
            ItemStack stack,
            Stream<Holder<Enchantment>> enchantment
    )
    {
        List<EnchantmentInstance> list = new ArrayList<>();

        Objects.requireNonNull(stack);

        enchantment
                .filter(stack::isPrimaryItemFor)
                .forEach(enchant -> {
                    Enchantment ench = enchant.value();


                    for(int i = ench.getMaxLevel(); i >= ench.getMinLevel(); i--)
                    {
                        if (ench.getMinCost(i) <= cost && cost <= ench.getMaxCost(i))
                        {
                            if (
                                !EnchantManager.meetsRequirements(
                                        NBTUtil.deserializeAspects(stack),
                                        EnchantManager.get(Objects.requireNonNull(enchant.getKey()).location(), i)
                                )
                            )
                                return;

                            list.add(new EnchantmentInstance(enchant, i));
                            break;
                        }
                    }
        });

        return list;
    }

    /**
     * @author Fom477
     * @reason Change the limit
     */
    @Overwrite
    public static int getEnchantmentCost(
            RandomSource random,
            int enchantNum,
            int power,
            ItemStack stack
    )
    {
        int i = stack.getEnchantmentValue();
        if (i <= 0)
            return 0;

        if (power > AwakenCommon.CONFIG.MAX_ENCHANT_ABILITY.get())
            power = AwakenCommon.CONFIG.MAX_ENCHANT_ABILITY.get();

        int j = random.nextInt(8) + 1 + (power >> 1) + random.nextInt(power + 1);
        return enchantNum == 0? Math.max(j / 3, 1): (enchantNum == 1? j * 2 / 3 + 1: Math.max(j, power * 2));
    }

    @Redirect(method = "getTagEnchantmentLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getOrDefault(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static Object getTagEnchantmentLevel(
            ItemStack instance,
            DataComponentType<?> dataComponentType,
            Object o
    )
    {
        ItemEnchantments ie = (ItemEnchantments) o;
        ItemEnchantments ie2 = (ItemEnchantments) instance.getOrDefault(dataComponentType, ie);
        return NBTUtil.deserializeEnchantments(instance, ie2);
    }

    @Redirect(method = "hasAnyEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getOrDefault(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static Object hasAnyEnchantments(
            ItemStack instance,
            DataComponentType<?> dataComponentType,
            Object o
    )
    {
        ItemEnchantments ie = (ItemEnchantments) o;
        ItemEnchantments ie2 = (ItemEnchantments) instance.getOrDefault(dataComponentType, ie);
        return NBTUtil.deserializeEnchantments(instance, ie2);
    }

    @Redirect(method = "runIterationOnItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/enchantment/EnchantmentHelper$EnchantmentVisitor;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getOrDefault(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static Object runIterationOnItem(
            ItemStack instance,
            DataComponentType<?> dataComponentType,
            Object o
    )
    {
        ItemEnchantments ie = (ItemEnchantments) o;
        ItemEnchantments ie2 = (ItemEnchantments) instance.getOrDefault(dataComponentType, ie);
        return NBTUtil.deserializeEnchantments(instance, ie2);
    }

    @Redirect(method = "runIterationOnItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/enchantment/EnchantmentHelper$EnchantmentInSlotVisitor;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;"))
    private static Object runIterationOnItem(
            ItemStack instance,
            DataComponentType<?> dataComponentType
    )
    {
        ItemEnchantments ie = (ItemEnchantments) instance.get(dataComponentType);
        return NBTUtil.deserializeEnchantments(instance, ie);
    }

    @Redirect(method = "hasTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getOrDefault(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static Object hasTag(
            ItemStack instance,
            DataComponentType<?> dataComponentType,
            Object o
    )
    {
        ItemEnchantments ie = (ItemEnchantments) o;
        ItemEnchantments ie2 = (ItemEnchantments) instance.getOrDefault(dataComponentType, ie);
        return NBTUtil.deserializeEnchantments(instance, ie2);
    }

    @Redirect(method = "getRandomItemWith", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getOrDefault(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static Object getRandomItemWith(
            ItemStack instance,
            DataComponentType<?> dataComponentType,
            Object o
    )
    {
        ItemEnchantments ie = (ItemEnchantments) o;
        ItemEnchantments ie2 = (ItemEnchantments) instance.getOrDefault(dataComponentType, ie);
        return NBTUtil.deserializeEnchantments(instance, ie2);
    }
}