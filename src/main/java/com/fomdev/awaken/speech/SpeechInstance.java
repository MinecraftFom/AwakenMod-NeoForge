package com.fomdev.awaken.speech;

import com.fomdev.awaken.packet.SpeechSyncPacketPayload;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class SpeechInstance
{
    private final Queue<SpeechComponent> queue;
    private int delay;

    public SpeechInstance(
            List<SpeechComponent> speeches,
            int initialDelay
    )
    {
        this.queue = new ArrayDeque<>();
        this.delay = initialDelay;

        if (speeches.isEmpty())
            return;

        for (int i = speeches.size() - 1; i >= 0; i--)
            this.queue.add(speeches.get(i));
    }

    public boolean available()
    {
        return delay <= 0;
    }

    public void tick()
    {
        delay--;
    }

    public void tick(
            Player player,
            GuiGraphics gui,
            int maxX,
            int maxY,
            boolean newTick
    )
    {
        if (!available())
        {
            if (newTick)
                tick();

            return;
        }

        SpeechComponent speech = queue.peek();
        int xStart = maxX / 2 - 20;
        int yStart = maxY / 2 - 10;

        if (speech == null)
            return;

        if (newTick)
            speech.tick();

        speech.render(gui, xStart, yStart);

        if (speech.obsolete())
        {
            if (player instanceof LocalPlayer local)
                local.connection.send(new SpeechSyncPacketPayload(this));

            queue.poll();
            this.delay = 100;
        }
    }

    public void clear()
    {
        this.queue.clear();
    }

    public int getRemainingDelay()
    {
        return this.delay;
    }

    public List<SpeechComponent> getSpeech()
    {
        return this.queue.stream().toList();
    }

    public SpeechInstance push(Component component)
    {
        String value = component.getString();
        float h = new Random().nextFloat(360.0F);
        Color color = Color.getHSBColor(h, 1.0F, 1.0F);
        this.queue.add(new SpeechComponent(component, value.length() * 10, 0, color));
        return this;
    }

    public static class SpeechComponent
    {
        private final String speech;
        private final int ticks;
        private final Color color;
        private int elapsed;

        public SpeechComponent(
                String speech,
                int ticks,
                int elapsed,
                int color
        )
        {
            this.speech = speech;
            this.ticks = ticks;
            this.elapsed = elapsed;
            this.color = new Color(color);
        }

        public SpeechComponent(
                Component speech,
                int ticks,
                int elapsed,
                Color color
        )
        {
            this(speech.getString(), ticks, elapsed, color.getRGB());
        }

        public String getSpeech()
        {
            return this.speech;
        }

        public int getTicks()
        {
            return this.ticks;
        }

        public int getElapsed()
        {
            return this.elapsed;
        }

        public int getColor()
        {
            return this.color.getRGB();
        }

        public void tick()
        {
            elapsed++;
        }

        public boolean obsolete()
        {
            return elapsed >= ticks;
        }

        public void render(
                GuiGraphics graphics,
                int x,
                int y
        )
        {
            graphics.drawString(
                    Minecraft.getInstance().font,
                    this.speech,
                    x,
                    y,
                    this.color.getRGB()
            );
        }
    }

    public static final Codec<SpeechComponent> COMPONENT_CODEC =
            RecordCodecBuilder.create(
                    inst ->
                            inst
                                    .group(
                                            Codec.STRING
                                                    .fieldOf("speech")
                                                    .forGetter(SpeechComponent::getSpeech)
                                    )
                                    .and(
                                            Codec.INT
                                                    .fieldOf("ticks")
                                                    .forGetter(SpeechComponent::getTicks)
                                    )
                                    .and(
                                            Codec.INT
                                                    .fieldOf("elapsed")
                                                    .forGetter(SpeechComponent::getElapsed)
                                    )
                                    .and(
                                            Codec.INT
                                                    .fieldOf("color")
                                                    .forGetter(SpeechComponent::getColor)
                                    )
                                    .apply(
                                            inst,
                                            SpeechComponent::new
                                    )
            );

    public static final StreamCodec<ByteBuf, SpeechComponent> COMPONENT_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    SpeechComponent::getSpeech,
                    ByteBufCodecs.INT,
                    SpeechComponent::getTicks,
                    ByteBufCodecs.INT,
                    SpeechComponent::getElapsed,
                    ByteBufCodecs.INT,
                    SpeechComponent::getColor,
                    SpeechComponent::new
            );
}