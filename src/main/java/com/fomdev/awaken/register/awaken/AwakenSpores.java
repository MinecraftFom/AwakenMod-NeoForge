package com.fomdev.awaken.register.awaken;

import com.fomdev.awaken.entries.raw.AwakenRegistries;
import com.fomdev.awaken.entries.raw.AwakenSpore;
import com.fomdev.awaken.init.Awaken;
import com.fomdev.flame.annotation.AutoProxy;
import com.fomdev.flame.register.RegistryTable;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

@AutoProxy
public class AwakenSpores
{
    public static final RegistryTable<AwakenSpore> REGISTRY =
            new RegistryTable<>(
                    Awaken.MODID,
                    AwakenRegistries.AWAKEN_SPORE
            );

    public static void init()
    {
        register("aspergillus_fumigatus", Attributes.OXYGEN_BONUS, 0.2F);
        register("aspergillus_flavus", Attributes.ATTACK_DAMAGE, 0.5F);
        register("aspergillus_nidulans", Attributes.MAX_HEALTH, 0.3F);
        register("aspergillus_clavatus", Attributes.MAX_HEALTH, 0.3F);
        // It was going to be the fortieth spore. But due to some special reasons, I banned it. DO NOT submit these dangerous names to me
//        register("aspergillus_niger", Attributes.MAX_HEALTH, 0.3F); // HINT: This kinda spore actually exists. Search it online if you don't believe. All of those spores are advised by friends. None of my business.
        register("candida_albicans", Attributes.MOVEMENT_SPEED, 0.5F);
        register("candida_auris", Attributes.MAX_HEALTH, 0.5F);
        register("candida_glabrata", Attributes.MAX_ABSORPTION, 0.3F);
        register("candida_tropicalls", Attributes.MAX_ABSORPTION, 0.3F);
        register("candida_krusei", Attributes.MAX_ABSORPTION, 0.5F);
        register("candida_parapsilosis", Attributes.MAX_ABSORPTION, 0.2F);
        register("cryptococcus_neoformans", Attributes.ARMOR, 0.7F);
        register("cryptococcus_gattii", Attributes.OXYGEN_BONUS, 0.4F);
        register("histoplasma_capsulatum", Attributes.OXYGEN_BONUS, 0.5F);
        register("talaromyces_marneffei", Attributes.MOVEMENT_SPEED, 0.2F);
        register("pneumocystis_jirovecii", Attributes.OXYGEN_BONUS, 0.5F);
        register("sporothrix_schenckii", Attributes.MAX_ABSORPTION, 0.4F);
        register("coccidioides", Attributes.BURNING_TIME, 0.3F);
        register("paracoccidioides", Attributes.MAX_ABSORPTION, 0.4F);
        register("mucorales", Attributes.GRAVITY, -0.6F);
        register("rhizopus", Attributes.OXYGEN_BONUS, 0.3F);
        register("mucor", Attributes.LUCK, 0.4F);
        register("fusarium", Attributes.SAFE_FALL_DISTANCE, 0.6F);
        register("scedosporium", Attributes.MAX_HEALTH, 0.3F);
        register("lomentospora_prolificans", Attributes.SWEEPING_DAMAGE_RATIO, 0.2F);
        register("acremonium", Attributes.MOVEMENT_EFFICIENCY, 0.5F);
        register("alternaria", Attributes.JUMP_STRENGTH, 0.4F);
        register("cladosporium", Attributes.JUMP_STRENGTH, 0.5F);
        register("ustilago", Attributes.MAX_HEALTH, 0.4F);
        register("enterocytozoon_bieneusi", Attributes.ARMOR_TOUGHNESS, 0.3F);
        register("encephalitozoon_intestinais", Attributes.OXYGEN_BONUS, 0.7F);
        register("encephalitozoon_cuniculi", Attributes.MAX_HEALTH, 0.5F);
        register("bacillus_anthracis", Attributes.MAX_HEALTH, 1.5F);
        register("bacillus_cereus", Attributes.WATER_MOVEMENT_EFFICIENCY, 0.8F);
        register("clostridium_botulinum", Attributes.ENTITY_INTERACTION_RANGE, 0.6F);
        register("clostridium_tetani", Attributes.BLOCK_INTERACTION_RANGE, 0.6F);
        register("clostridium_perfringens", Attributes.BLOCK_BREAK_SPEED, 0.5F);
        register("clostridium_difficile", Attributes.STEP_HEIGHT, 0.5F);
        register("fern_spores", Attributes.GRAVITY, -0.4F);
    }

    public static void register(
            String id,
            Holder<Attribute> target,
            float factor
    )
    {
        register(
                new AwakenSpore(
                        id,
                        target
                )
                {
                    @Override
                    public double getAmount(int level)
                    {
                        return -(level * factor);
                    }
                }
        );
    }

    public static AwakenSpore register(
            AwakenSpore spore
    )
    {
        return REGISTRY.register(spore);
    }

    @AutoProxy.Proxied(AutoProxy.ProxyProtocol.MOD_INIT)
    public static void register()
    {
        init();
        REGISTRY.register();
    }
}