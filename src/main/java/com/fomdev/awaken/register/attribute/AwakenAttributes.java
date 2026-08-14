package com.fomdev.awaken.register.attribute;

import com.fomdev.awaken.init.Awaken;
import com.fomdev.flame.annotation.AutoRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

@AutoRegister
public class AwakenAttributes
{
    @AutoRegister.Registrable
    public static final DeferredRegister<Attribute> REGISTER =
            DeferredRegister.create(
                    Registries.ATTRIBUTE,
                    Awaken.MODID
            );

    public static final Holder<Attribute> ENCHANTMENT =
            REGISTER.register(
                    "enchant_ability",
                    loc ->
                            new RangedAttribute(
                                    "attribute.awaken.enchant_ability.name",
                                    0,
                                    0,
                                    32767
                            )
            );
}