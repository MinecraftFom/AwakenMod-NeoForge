package com.fomdev.awaken.mixin;

import com.fomdev.awaken.enchant.EnchantManager;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper
{
    /**
     * @author Fom477 (Fomdev)
     * @reason using '@Inject' will waste too much resource
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

                    if (
                            EnchantManager.aspects.containsKey(enchant) &&
                            !EnchantManager.meetsRequirements(
                                    NBTUtil.deserializeAspects(stack),
                                    EnchantManager.aspects.get(enchant)
                            )
                    )
                        return;

                    for(int i = ench.getMaxLevel(); i >= ench.getMinLevel(); i--)
                    {
                        if (ench.getMinCost(i) <= cost && cost <= ench.getMaxCost(i))
                        {
                            list.add(new EnchantmentInstance(enchant, i));
                            break;
                        }
                    }
        });

        return list;
    }
}