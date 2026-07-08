package com.fomdev.awaken.mixin;

import com.fomdev.awaken.entries.AwakenLevel;
import com.fomdev.awaken.entries.AwakenRegistries;
import com.fomdev.awaken.util.ColorUtil;
import com.fomdev.awaken.util.LocaleUtil;
import com.fomdev.awaken.util.NBTUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class MixinPlayer
{
    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void fancyName(CallbackInfoReturnable<Component> cir)
    {
        Player self = (Player) (Object) this;
        MutableComponent original = Component.empty();
        float level = NBTUtil.deserializeAwakenLevel(self);
        AwakenLevel awakenLevel = AwakenRegistries.AWAKEN_LEVEL.getLevel(level);

        if (awakenLevel == null)
            original.append(cir.getReturnValue());
        else
            original.append("[").append(LocaleUtil.localizeAwakenLevel(awakenLevel)).append("] ").append(cir.getReturnValue()).withStyle(ColorUtil.colorStyle(awakenLevel.getColor()));

        cir.setReturnValue(original);
    }
}