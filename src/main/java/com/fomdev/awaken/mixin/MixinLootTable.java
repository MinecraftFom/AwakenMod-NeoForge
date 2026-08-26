package com.fomdev.awaken.mixin;

import com.fomdev.awaken.spawn.EquipmentManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;
import java.math.BigDecimal;

@Mixin(LootTable.class)
public class MixinLootTable
{
    @Redirect(method = "fill", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Container;setItem(ILnet/minecraft/world/item/ItemStack;)V"))
    private void fill(
            Container instance,
            int i,
            ItemStack stack
    )
    {
        if (stack.isDamageableItem())
        {
            IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
            if (server == null)
                return;

            Level level = server.overworld();
            EquipmentManager.shuffleForItemStack(level, stack, EquipmentManager.forSlot(stack), new BigDecimal("100"), 5.0F, Color.WHITE, level.getRandom());
        }

        instance.setItem(i, stack);
    }
}