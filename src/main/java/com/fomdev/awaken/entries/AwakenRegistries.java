package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.FreezingRegistry;
import com.fomdev.awaken.init.Awaken;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;

public class AwakenRegistries
{
    public static final String SIG_AWAKEN_ASPECT = "awaken_aspect";
    public static final String SIG_AWAKEN_INFIX = "awaken_infix";
    public static final String SIG_AWAKEN_LEVEL = "awaken_level";
    public static final String SIG_AWAKEN_POLLINATE = "awaken_pollinate";
    public static final String SIG_AWAKEN_PREFIX = "awaken_prefix";
    public static final String SIG_AWAKEN_QUALITY = "awaken_quality";
    public static final String SIG_AWAKEN_SPIRIT = "awaken_spiritual";
    public static final String SIG_AWAKEN_SPORE = "awaken_spore";
    public static final String SIG_AWAKEN_SUFFIX = "awaken_suffix";

    public static final ResourceKey<Registry<AwakenAspect>> RES_AWAKEN_ASPECT = createKey(SIG_AWAKEN_ASPECT);
    public static final ResourceKey<Registry<AwakenInfix>> RES_AWAKEN_INFIX = createKey(SIG_AWAKEN_INFIX);
    public static final ResourceKey<Registry<AwakenLevel>> RES_AWAKEN_LEVEL = createKey(SIG_AWAKEN_LEVEL);
    public static final ResourceKey<Registry<AwakenPollinate>> RES_AWAKEN_POLLINATE = createKey(SIG_AWAKEN_POLLINATE);
    public static final ResourceKey<Registry<AwakenPrefix>> RES_AWAKEN_PREFIX = createKey(SIG_AWAKEN_PREFIX);
    public static final ResourceKey<Registry<AwakenQuality>> RES_AWAKEN_QUALITY = createKey(SIG_AWAKEN_QUALITY);
    public static final ResourceKey<Registry<AwakenSpiritual>> RES_AWAKEN_SPIRIT = createKey(SIG_AWAKEN_SPIRIT);
    public static final ResourceKey<Registry<AwakenSpore>> RES_AWAKEN_SPORE = createKey(SIG_AWAKEN_SPORE);
    public static final ResourceKey<Registry<AwakenSuffix>> RES_AWAKEN_SUFFIX = createKey(SIG_AWAKEN_SUFFIX);

    public static final FreezingRegistry<AwakenAspect> AWAKEN_ASPECT = new FreezingRegistry<>(RES_AWAKEN_ASPECT);
    public static final FreezingRegistry<AwakenInfix> AWAKEN_INFIX = new FreezingRegistry<>(RES_AWAKEN_INFIX);
    public static final FreezingRegistry<AwakenLevel> AWAKEN_LEVEL = new FreezingRegistry<>(RES_AWAKEN_LEVEL);
    public static final FreezingRegistry<AwakenPollinate> AWAKEN_POLLINATE = new FreezingRegistry<>(RES_AWAKEN_POLLINATE);
    public static final FreezingRegistry<AwakenPrefix> AWAKEN_PREFIX = new FreezingRegistry<>(RES_AWAKEN_PREFIX);
    public static final FreezingRegistry<AwakenQuality> AWAKEN_QUALITY = new FreezingRegistry<>(RES_AWAKEN_QUALITY);
    public static final FreezingRegistry<AwakenSpiritual> AWAKEN_SPIRIT = new FreezingRegistry<>(RES_AWAKEN_SPIRIT);
    public static final FreezingRegistry<AwakenSpore> AWAKEN_SPORE = new FreezingRegistry<>(RES_AWAKEN_SPORE);
    public static final FreezingRegistry<AwakenSuffix> AWAKEN_SUFFIX = new FreezingRegistry<>(RES_AWAKEN_SUFFIX);

    public static void register(
            IEventBus bus
    )
    {
        AWAKEN_ASPECT.attach(bus);
        AWAKEN_INFIX.attach(bus);
        AWAKEN_LEVEL.attach(bus);
        AWAKEN_POLLINATE.attach(bus);
        AWAKEN_PREFIX.attach(bus);
        AWAKEN_QUALITY.attach(bus);
        AWAKEN_SPIRIT.attach(bus);
        AWAKEN_SPORE.attach(bus);
        AWAKEN_SUFFIX.attach(bus);
    }

    private static <T> ResourceKey<Registry<T>> createKey(
            String key
    )
    {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Awaken.MODID, key));
    }
}