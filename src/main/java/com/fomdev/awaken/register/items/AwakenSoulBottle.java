package com.fomdev.awaken.register.items;

import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import com.fomdev.awaken.util.HealthUtil;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class AwakenSoulBottle extends Item
{
    public AwakenSoulBottle(
            Properties properties
    )
    {
        super(
                properties
                        .stacksTo(1)
                        .component(
                                AwakenDataComponents.AWAKEN_SOUL_STORAGE,
                                new Records.AwakenSoulComponent(0.0F, 100.0F)
                        )
                        .component(
                                DataComponents.UNBREAKABLE,
                                new Unbreakable(false)
                        )
                        .durability(
                                1
                        )
                        .rarity(
                                Rarity.EPIC
                        )
        );
    }

    @Override
    public boolean canContinueUsing(
            @NotNull ItemStack oldStack,
            @NotNull ItemStack newStack
    )
    {
        Records.AwakenSoulComponent soul = NBTUtil.deserializeSoul(newStack);
        return soul.current() >= soul.maximum();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            @NotNull Level level,
            @NotNull Player player,
            @NotNull InteractionHand usedHand
    )
    {
        ItemStack stack = player.getItemInHand(usedHand);
        Records.AwakenSoulComponent soul = NBTUtil.deserializeSoul(stack);
        if (soul.current() < soul.maximum())
        {
            if (!(player instanceof ServerPlayer serverPlayer))
                return InteractionResultHolder.fail(stack);

            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("bar.not_enough.info", soul.current(), soul.maximum()).withStyle(ChatFormatting.RED)));
            player.playSound(SoundEvents.ITEM_BREAK);
            return InteractionResultHolder.fail(stack);
        }

        player.startUsingItem(usedHand);
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(
            @NotNull ItemStack stack,
            @NotNull Level level,
            @NotNull LivingEntity entity
    )
    {
        if (!(entity instanceof Player player))
            return stack;

        Records.AwakenSoulComponent soul = NBTUtil.deserializeSoul(stack);
        if (soul.current() < soul.maximum())
            return stack;

        int heart = player.getRandom().nextInt(4) + 1;
        HealthUtil.addAdditionalHealthPersistent(
                player,
                heart
        );

        NBTUtil.serializeSoul(stack, new Records.AwakenSoulComponent(0.0F, AwakenCommon.CONFIG.DEFAULT_SOUL_FACTOR.get().floatValue() * soul.maximum()));
        player.playSound(SoundEvents.PLAYER_LEVELUP);
        if (!(player instanceof ServerPlayer serverPlayer))
            return stack;

        serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("bar.increased_health.info", heart).withStyle(ChatFormatting.GREEN)));
        player.getCooldowns().addCooldown(this, 40);
        return stack;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(
            @NotNull ItemStack stack
    )
    {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(
            @NotNull ItemStack stack,
            @NotNull LivingEntity entity
    )
    {
        return 40;
    }

    @Override
    public void onUseTick(
            @NotNull Level level,
            @NotNull LivingEntity entity,
            @NotNull ItemStack stack,
            int duration
    )
    {
        if (!(entity instanceof Player player))
            return;

        if (duration % 10 != 0)
            return;

        player.playSound(SoundEvents.GENERIC_DRINK);
    }

    @Override
    public int getBarWidth(
            @NotNull ItemStack stack
    )
    {
        Records.AwakenSoulComponent soul = NBTUtil.deserializeSoul(stack);
        return (int) (soul.current() / soul.maximum());
    }

    @Override
    public int getBarColor(
            @NotNull ItemStack stack
    )
    {
        return Color.MAGENTA.getRGB();
    }

    @Override
    public int getDamage(
            @NotNull ItemStack stack
    )
    {
        Records.AwakenSoulComponent soul = NBTUtil.deserializeSoul(stack);
        return (int) soul.current();
    }

    @Override
    public int getMaxDamage(
            @NotNull ItemStack stack
    )
    {
        Records.AwakenSoulComponent soul = NBTUtil.deserializeSoul(stack);
        return (int) soul.maximum();
    }
}