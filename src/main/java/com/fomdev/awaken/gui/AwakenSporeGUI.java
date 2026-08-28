package com.fomdev.awaken.gui;

import com.fomdev.awaken.entries.raw.spore.AwakenSpore;
import com.fomdev.awaken.util.LocaleUtil;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AwakenSporeGUI implements LayeredDraw.Layer
{
    public static final LayeredDraw.Layer INSTANCE = new AwakenSporeGUI();
    public static final int DIST = 8;

    @Override
    public void render(
            @NotNull GuiGraphics graphics,
            @Nullable DeltaTracker tracker
    )
    {
        Player player = Minecraft.getInstance().player;
        if (player == null)
            return;

        List<AwakenSpore.SporeInstance> spores = NBTUtil.deserializeSpores(player).spores();
        for (int i = 0; i < spores.size(); i++)
        {
            int posx = 10;
            int posy = 10 + DIST * i;

            AwakenSpore.SporeInstance spore = spores.get(i);
            Component component = Component.empty().append(LocaleUtil.localizeSpore(spore.getRepresentation())).append(": " + spore.getLevel());
            graphics.drawString(Minecraft.getInstance().font, component.getString(), posx, posy, 0xFFFFFF);
        }
    }
}