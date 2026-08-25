package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.raw.AwakenMoods;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.fomdev.awaken.speech.SpeechInstance;
import com.fomdev.awaken.util.LocaleUtil;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = Awaken.MODID)
public class AwakenMoodEvents
{
    @SubscribeEvent
    public static void onSpeech(
            EntityTickEvent.Post event
    )
    {
        if (!(event.getEntity() instanceof Player player))
            return;

        for (EquipmentSlot slot: EquipmentSlot.values())
        {
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.isEmpty())
                continue;

            AwakenMoods mood = NBTUtil.deserializeMood(stack);
            if (mood == null)
                continue;

            if (player.getRandom().nextInt(100) < 25)
            {
                SpeechInstance speech = player.getData(AwakenAttachmentTypes.PLAYER_SPEECH_QUEUE);
                speech.push(LocaleUtil.localizeMood(mood, player.getRandom().nextInt(mood.getQuotes())));
            }
        }
    }
}