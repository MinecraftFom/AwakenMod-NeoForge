package com.fomdev.awaken.mixin;

import com.fomdev.awaken.init.config.AwakenCommon;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;

@Mixin(ItemEnchantments.class)
public abstract class MixinItemEnchantments
{
    @Shadow
    @Final
    private Object2IntOpenHashMap<Holder<Enchantment>> enchantments;

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z"))
    private boolean hasNext(Iterator<?> instance)
    {
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : this.enchantments.object2IntEntrySet())
        {
            int i = entry.getIntValue();
            if (i < 0 || i > AwakenCommon.CONFIG.MAX_ACCEPTABLE_ENCHANT.get())
            {
                String var10002 = String.valueOf(entry.getKey());
                throw new IllegalArgumentException("Enchantment " + var10002 + " has invalid level " + i);
            }
        }

        return false;
    }
}