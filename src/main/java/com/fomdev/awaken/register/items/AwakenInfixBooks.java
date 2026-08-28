package com.fomdev.awaken.register.items;

import com.fomdev.awaken.entries.raw.affix.AwakenInfix;
import com.fomdev.awaken.particle.AwakenParticlePlayer;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class AwakenInfixBooks extends Item
{
    public AwakenInfixBooks(Properties properties)
    {
        super(properties.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            @NotNull Level level,
            @NotNull Player player,
            @NotNull InteractionHand usedHand
    )
    {
        if (usedHand != InteractionHand.OFF_HAND)
            return InteractionResultHolder.fail(player.getItemInHand(usedHand));

        if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty())
            return InteractionResultHolder.fail(player.getItemInHand(usedHand));

        player.startUsingItem(usedHand);
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(
            @NotNull ItemStack stack,
            @NotNull Level level,
            @NotNull LivingEntity entity
    )
    {
        if (!(entity instanceof ServerPlayer player))
            return stack;

        AwakenInfix.InfixContainer infix = NBTUtil.deserializeInfix(stack);
        ItemStack target = entity.getMainHandItem();

        NBTUtil.modifyInfix(target, infix);
        if (level instanceof ServerLevel serverLevel)
            serverLevel.players().forEach(p -> serverLevel.sendParticles(p, ParticleTypes.EXPLOSION, true, player.getX(), player.getY(), player.getZ(), 100, 1.0F, 1.0F, 1.0F, 0));

        player.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("bar.set_infix.info").withStyle(ChatFormatting.GREEN)));
        stack.copyAndClear();
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(
            @NotNull ItemStack stack
    )
    {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(
            @NotNull ItemStack stack,
            @NotNull LivingEntity entity
    )
    {
        return 150;
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
        if (level instanceof ServerLevel serverLevel)
            serverLevel.players().forEach(p -> AwakenParticlePlayer.playBookConsumeParticle(serverLevel, p, player.getX(), player.getY(), player.getZ(), getUseDuration(stack, entity) - duration));

        if (duration % 10 != 0)
            return;

        player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE);
    }
}