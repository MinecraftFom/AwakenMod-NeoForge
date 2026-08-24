package com.fomdev.awaken.mixin;

import com.fomdev.awaken.gui.AwakenSporeGUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class MixinInventoryScreen
{
    @Inject(method = "render", at = @At("RETURN"))
    private void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick,
            CallbackInfo ci
    )
    {
        AwakenSporeGUI.INSTANCE.render(guiGraphics, null);
    }
}