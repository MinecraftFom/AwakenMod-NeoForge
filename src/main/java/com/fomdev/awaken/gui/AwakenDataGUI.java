package com.fomdev.awaken.gui;

import com.fomdev.awaken.difficulty.ClientDifficultyManager;
import com.fomdev.awaken.entries.AwakenLevel;
import com.fomdev.awaken.entries.AwakenRegistries;
import com.fomdev.awaken.util.LocaleUtil;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class AwakenDataGUI implements LayeredDraw.Layer
{
    @Override
    public void render(@NotNull GuiGraphics graphics, @NotNull DeltaTracker tracker)
    {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) return;

        String diffText = Component.translatable("gui.difficulty.info").getString() + ": " + ClientDifficultyManager.getDifficulty();
        graphics.drawString(minecraft.font, diffText, 10, 10, 0xFFFFFF, true);

        float data = NBTUtil.deserializeAwakenLevel(player);
        AwakenLevel level = AwakenRegistries.AWAKEN_LEVEL.getLevel(data);

        String levelText = Component.translatable("gui.level.info").getString() + ": " + (level == null? "None": LocaleUtil.localizeAwakenLevel(level) + " (" + data + ")");
        graphics.drawString(minecraft.font, levelText, 10, 20, 0xFFFFFF, true);
    }
}