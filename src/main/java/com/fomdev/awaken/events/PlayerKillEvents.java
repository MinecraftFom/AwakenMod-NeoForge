package com.fomdev.awaken.events;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.AwakenSpore;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.register.items.AwakenItems;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@EventBusSubscriber(modid = Awaken.MODID)
public class PlayerKillEvents
{
    @SubscribeEvent
    public static void onPlayerKill(
            LivingDeathEvent event
    )
    {
        Entity sin = event.getSource().getEntity();
        Entity vic = event.getEntity();

        if (!(vic instanceof Monster) || !(sin instanceof Player player))
            return;

        RandomSource random = player.getRandom();
        BigDecimal awaken = NBTUtil.deserializeAwakenLevel(player);
        BigDecimal factor = awaken.sqrt(new MathContext(2)).sqrt(new MathContext(2));
        BigDecimal factor2 = factor.compareTo(new BigDecimal(0)) <= 0? new BigDecimal("1"): factor;

        ItemStack mainhand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);
        processSoulAdd(player.level(), player, mainhand, factor2.intValue(), random);
        processSoulAdd(player.level(), player, offhand, factor2.intValue(), random);

        NBTUtil.addAwakenLevel(player, NBTUtil.deserializeAwakenLevel(vic).sqrt(new MathContext(2)).sqrt(new MathContext(2)).sqrt(new MathContext(2)).sqrt(new MathContext(2)));

        if (random.nextInt(100) < 2) // %2
        {
            List<AwakenSpore> spores = AwakenRegistries.AWAKEN_SPORE.getRegistries();
            if (spores.isEmpty())
                return;

            AwakenSpore spore = spores.get(random.nextInt(spores.size()));
            int level = random.nextInt(8) * (MobSpawnEvents.isAwaken(vic)? random.nextInt(5) + 1: 1);
            int lvl = Math.max(level, 1);
            NBTUtil.addSpore(player, new AwakenSpore.SporeInstance(spore, lvl));
        }
    }

    private static void processSoulAdd(
            Level level,
            Player player,
            ItemStack stack,
            float factor,
            RandomSource random
    )
    {
        if (!stack.is(AwakenItems.SOUL_BOTTLE))
            return;

        float max = NBTUtil.deserializeSoul(stack).maximum();
        float factor2 = (float) Math.sqrt(max);
        float soul = random.nextFloat() % (factor * factor2);
        NBTUtil.addSoul(stack, soul);
        if (level instanceof ServerLevel serverLevel)
            serverLevel.players().forEach(p -> serverLevel.sendParticles(p, ParticleTypes.SCULK_SOUL, false, player.getX(), player.getY() + 1, player.getZ(), (int) (20 * soul), 1.0, 1.0, 1.0, 0.0));
    }
}