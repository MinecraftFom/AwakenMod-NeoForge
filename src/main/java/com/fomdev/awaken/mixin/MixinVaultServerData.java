package com.fomdev.awaken.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.vault.VaultServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;
import java.util.UUID;

@Mixin(VaultServerData.class)
public class MixinVaultServerData
{
    @Inject(method = "hasRewardedPlayer", at = @At("RETURN"), cancellable = true)
    private void hasRewardedPlayer(
            Player player,
            CallbackInfoReturnable<Boolean> cir
    )
    {
        cir.setReturnValue(false);
    }

    @Inject(method = "getRewardedPlayers", at = @At("RETURN"), cancellable = true)
    private void getRewardedPlayers(
            CallbackInfoReturnable<Set<UUID>> cir
    )
    {
        cir.setReturnValue(Set.of());
    }
}