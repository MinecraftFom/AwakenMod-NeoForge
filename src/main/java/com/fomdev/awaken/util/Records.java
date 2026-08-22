package com.fomdev.awaken.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.awt.*;

public class Records
{
    public record AwakenLevelComponent
            (
                    Float level
            )
    {}

    public record AwakenDescriberComponent
            (
                    CompoundTag infix,
                    CompoundTag prefix,
                    CompoundTag suffix
            )
    {}

    public record AwakenEpochComponent
            (
                    double requiredAwakenLevel,
                    float requiredMinDifficulty
            )
    {}

    public record AwakenKnowledgeComponent
            (
                    float experience,
                    float insight,
                    float proficiency,
                    float skill
            )
    {}

    public record AwakenMedicineComponent
            (
                    String medicineType,
                    float value
            )
    {}

    public record AwakenSoulComponent
            (
                    float current,
                    float maximum
            )
    {}

    public record AttributeHolder
            (
                    Holder<Attribute> attr,
                    double amount,
                    AttributeModifier.Operation operation,
                    EquipmentSlot[] slot
            )
    {
        public AttributeHolder(
                Holder<Attribute> attr,
                double amount,
                EquipmentSlot slots
        )
        {
            this(attr, amount, AttributeModifier.Operation.ADD_VALUE, new EquipmentSlot[]{slots});
        }
    }

    public record ColorHolder
            (
                    Color frontStart,
                    Color frontEnd,
                    Color backStart,
                    Color backEnd
            )
    {
        public ColorHolder(
                Color front,
                Color end
        )
        {
            this(front, front, end, end);
        }
    }

    public record EnchantmentHolder
            (
                    ResourceKey<Enchantment> enchantment,
                    int level
            )
    {
        public EnchantmentHolder(
                ResourceKey<Enchantment> enchantment
        )
        {
            this(enchantment, 0);
        }

        public EnchantmentHolder of(
                int lvl
        )
        {
            return new EnchantmentHolder(this.enchantment, lvl);
        }

        public EnchantmentInstance toInstance(
                HolderLookup<Enchantment> registry
        )
        {
            Holder<Enchantment> ench = registry.getOrThrow(this.enchantment);
            return new EnchantmentInstance(ench, Math.max(this.level, 1));
        }

        /* USUALLY DANGEROUS, NOT USE */
        public EnchantmentInstance toInstance()
        {
            IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
            if (server == null)
                return null;

            RegistryAccess registry = server.overworld().registryAccess();
            Registry<Enchantment> enchantRegistry = registry.registryOrThrow(Registries.ENCHANTMENT);
            Holder<Enchantment> enchantment = enchantRegistry.getHolderOrThrow(this.enchantment);
            return new EnchantmentInstance(enchantment, this.level);
        }
    }
}