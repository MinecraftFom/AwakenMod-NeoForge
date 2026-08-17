package com.fomdev.awaken.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AnvilScreen.class)
public abstract class MixinAnvilScreen extends ItemCombinerScreen<AnvilMenu>
{
    @Shadow
    @Final
    private Player player;

    public MixinAnvilScreen(AnvilMenu menu, Inventory playerInventory, Component title, ResourceLocation menuResource)
    {
        super(menu, playerInventory, title, menuResource);
    }

    /**
     * @author Fom477
     * @reason Change display limits
     */
    @Overwrite
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        int i = this.menu.getCost();
        if (i <= 0)
            return;

        int j = 8453920;
        Component component;

        if (!this.menu.getSlot(2).hasItem())
            component = null;
        else
        {
            component = Component.translatable("container.repair.cost", new Object[]{i});
            if (!this.menu.getSlot(2).mayPickup(this.player))
                j = 16736352;
        }

        if (component != null)
        {
            int k = this.imageWidth - 8 - this.font.width(component) - 2;
            int l = 69;
            guiGraphics.fill(k - 2, 67, this.imageWidth - 8, 79, 1325400064);
            guiGraphics.drawString(this.font, component, k, 69, j);
        }
    }
}