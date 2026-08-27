package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.spore.AwakenPollinate;
import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.awaken.util.Constants;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

@AutoProxy
public class AwakenPollinates
{
    public static final RegistryTable<AwakenPollinate> REGISTRY =
            new RegistryTable<>(
                    Awaken.MODID,
                    AwakenRegistries.AWAKEN_POLLINATE
            );

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        REGISTRY.register();
    }

    public static final AwakenPollinate POLLINATE_LUCK;

    static
    {
        POLLINATE_LUCK = REGISTRY.register(
                new AwakenPollinate(
                        "luck",
                        MobEffects.LUCK,
                        Constants.BODY_SLOTS,
                        AwakenPollinate.TriggerTarget.SELF,
                        AwakenPollinate.TriggerType.HURT
                )
                {
                    @Override
                    public MobEffectInstance getEffect(int level)
                    {
                        return new MobEffectInstance(
                                effect,
                                level * 100,
                                level / 5,
                                false,
                                false
                        );
                    }
                }
        );
    }
}