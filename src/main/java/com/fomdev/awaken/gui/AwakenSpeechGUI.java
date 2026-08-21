package com.fomdev.awaken.gui;

import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.fomdev.awaken.speech.SpeechInstance;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class AwakenSpeechGUI implements LayeredDraw.Layer
{
    private long lastTick = 0;

    @Override
    public void render(
            @NotNull GuiGraphics graphics,
            @NotNull DeltaTracker tracker
    )
    {
        Player player = Minecraft.getInstance().player;
        if (player == null)
            return;


        boolean newTick = player.level().getGameTime() != lastTick;
        lastTick = player.level().getGameTime();
        SpeechInstance instance = player.getData(AwakenAttachmentTypes.PLAYER_SPEECH_QUEUE);

        instance.tick(player, graphics, Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight(), newTick);
    }
}