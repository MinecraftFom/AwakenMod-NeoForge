package com.fomdev.awaken.mixin;

import com.fomdev.awaken.difficulty.DifficultyManager;
import com.fomdev.awaken.init.config.AwakenCommon;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigDecimal;
import java.math.MathContext;

@Mixin(Raid.class)
public abstract class MixinRaid
{
    @Shadow
    @Final
    @Mutable
    private int numGroups;

    @Shadow
    @Final
    private ServerBossEvent raidEvent;

    @Shadow
    private int groupsSpawned;

    @Shadow
    public abstract int getTotalRaidersAlive();

    @Shadow
    @Final
    private ServerLevel level;

    @Shadow
    private long ticksActive;

    @Shadow
    private int raidCooldownTicks;

    @Inject(method = "<init>(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)V", at = @At("RETURN"))
    private void onInit(
            int id,
            ServerLevel level,
            BlockPos center,
            CallbackInfo ci
    )
    {
        this.raidCooldownTicks = 20;
        this.numGroups = this.numGroups * DifficultyManager.getLevelDifficulty(level).sqrt(new MathContext(2)).sqrt(new MathContext(2)).intValue();
    }

    @Inject(method = "getMaxRaidOmenLevel", at = @At("RETURN"), cancellable = true)
    private void getMaxRaidOmenLevel(
            CallbackInfoReturnable<Integer> cir
    )
    {
        cir.setReturnValue(AwakenCommon.CONFIG.MAX_RAID_LEVEL.get());
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void tick(
            CallbackInfo ci
    )
    {
        this.ticksActive = 0;
        float factor = (float) this.groupsSpawned / (float) this.numGroups;
        float factor2 = factor * BossEvent.BossBarColor.values().length;
        int factor3 = Math.min((int) factor2, BossEvent.BossBarColor.values().length);

        BossEvent.BossBarColor color1 = BossEvent.BossBarColor.values()[factor3];
        MutableComponent component = Component.empty().append(this.raidEvent.getName());
        component.append(" " + this.groupsSpawned + " / " + this.numGroups);
        component.append(", " + this.getTotalRaidersAlive());

        this.raidEvent.setColor(color1);
        this.raidEvent.getPlayers().forEach(p -> p.connection.send(new ClientboundSetActionBarTextPacket(component)));
    }

    @Inject(method = "getDefaultNumSpawns", at = @At("HEAD"), cancellable = true)
    private void getDefaultNumSpawns(
            Raid.RaiderType raiderType,
            int wave,
            boolean shouldSpawnBonusGroup,
            CallbackInfoReturnable<Integer> cir
    )
    {
        Level level = this.level;
        if (!(level instanceof ServerLevel serverLevel))
            return;

        int value = switch (raiderType)
        {
            case VINDICATOR -> 5;
            case EVOKER, PILLAGER, RAVAGER -> 2;
            case WITCH -> 1;
        };

        BigDecimal diff = DifficultyManager.getLevelDifficulty(serverLevel);
        BigDecimal factor = diff.sqrt(new MathContext(2)).sqrt(new MathContext(2)).sqrt(new MathContext(2));
        cir.setReturnValue(value * wave * factor.intValue());
        cir.cancel();
    }
}