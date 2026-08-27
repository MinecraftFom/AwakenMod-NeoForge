package com.fomdev.awaken.gui;

import com.fomdev.awaken.difficulty.ClientDifficultyManager;
import com.fomdev.awaken.entries.raw.level.AwakenLevel;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.rank.RankHelper;
import com.fomdev.awaken.util.LocaleUtil;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.math.BigDecimal;

public class AwakenDataGUI implements LayeredDraw.Layer
{
    @Override
    public void render(@NotNull GuiGraphics graphics, @NotNull DeltaTracker tracker)
    {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) return;

        String diffText = Component.translatable("gui.difficulty.info").getString() + ": " + ClientDifficultyManager.getDifficulty().toPlainString();
        graphics.drawString(minecraft.font, diffText, 10, 10, Color.RED.getRGB(), true);

        BigDecimal data = NBTUtil.deserializeAwakenLevel(player);
        AwakenLevel level = AwakenRegistries.AWAKEN_LEVEL.getLevel(data);

        String levelText = Component.translatable("gui.level.info").getString() + ": " + (level == null? "None": LocaleUtil.localizeAwakenLevel(level).getString() + " (" + data.toPlainString() + ")");
        graphics.drawString(minecraft.font, levelText, 10, 20, level == null? Color.CYAN.getRGB(): level.getColor().getRGB(), true);

        String rankText = Component.translatable("gui.rank.info").getString() + ": " + RankHelper.getRank(player).toPlainString();
        graphics.drawString(minecraft.font, rankText, 10, 30, Color.MAGENTA.getRGB(), true);
    }
}