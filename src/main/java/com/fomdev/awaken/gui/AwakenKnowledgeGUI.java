package com.fomdev.awaken.gui;

import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class AwakenKnowledgeGUI implements LayeredDraw.Layer
{
    private static final float size = 0.75F;

    @Override
    public void render(
            @NotNull GuiGraphics graphics,
            @NotNull DeltaTracker tracker
    )
    {
        Minecraft minecraft = Minecraft.getInstance();
        PoseStack pose = graphics.pose();
        Player player = minecraft.player;
        if (player == null) return;

        Records.AwakenKnowledgeComponent knowledge = NBTUtil.deserializeKnowledge(player);

        int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        pose.scale(size, size, 1.0F);

        String expText = Component.translatable("gui.knowledge.experience.info").getString() + ": " + knowledge.experience();
        String insightText = Component.translatable("gui.knowledge.insight.info").getString() + ": " + knowledge.insight();
        String proficiencyText = Component.translatable("gui.knowledge.proficiency.info").getString() + ": " + knowledge.proficiency();
        String skillText = Component.translatable("gui.knowledge.skill.info").getString() + ": " + knowledge.skill();

        graphics.drawString(minecraft.font, expText, 10 / size, screenHeight / size - 50, Color.MAGENTA.getRGB(), true);
        graphics.drawString(minecraft.font, insightText, 10 / size, screenHeight / size - 40, Color.MAGENTA.getRGB(), true);
        graphics.drawString(minecraft.font, proficiencyText, 10 / size, screenHeight / size - 30, Color.MAGENTA.getRGB(), true);
        graphics.drawString(minecraft.font, skillText, 10 / size, screenHeight / size - 20, Color.MAGENTA.getRGB(), true);

        pose.scale(1.0F / size, 1.0F / size, 1.0F);
    }
}