package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.entries.raw.affix.AwakenInfix;
import com.fomdev.awaken.entries.raw.affix.AwakenPrefix;
import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.entries.raw.spore.AwakenPollinate;
import com.fomdev.awaken.entries.raw.spore.AwakenSpore;
import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class NBTUtil
{
    public static void addAspect(
            ItemStack stack,
            AwakenAspect.AspectInstance instance
    )
    {
        AwakenAspect.AspectContainer container = deserializeAspects(stack);
        container.merge(instance);
        serializeAspects(stack, container);
    }

    public static void addAwakenLevel(
            Player player,
            BigDecimal amount
    )
    {
        serializeAwakenLevel(player, deserializeAwakenLevel(player).add(amount).setScale(2, RoundingMode.HALF_UP));
    }

    public static void addSoul(
            ItemStack stack,
            float soul
    )
    {
        Records.AwakenSoulComponent original = deserializeSoul(stack);
        float value = original.current() + soul;
        serializeSoul(stack, new Records.AwakenSoulComponent((int) (value * 100) / 100.0F, original.maximum()));
    }

    public static void addSpore(
            Entity entity,
            AwakenSpore.SporeInstance instance
    )
    {
        AwakenSpore.SporeContainer spores = NBTUtil.deserializeSpores(entity);
        spores.merge(instance);
        NBTUtil.serializeSpores(entity, spores);
    }

    public static AwakenAspect.AspectContainer deserializeAspects(
            ItemStack stack
    )
    {
        AwakenAspect.AspectContainer container = new AwakenAspect.AspectContainer();
        if (stack.is(Items.AIR) || !stack.has(AwakenDataComponents.AWAKEN_ASPECT_STORAGE))
            return container;

        container = stack.get(AwakenDataComponents.AWAKEN_ASPECT_STORAGE);
        return container;
    }

    public static Records.AwakenEpochComponent deserializeEpoch(
            ItemStack stack
    )
    {
        return stack.get(AwakenDataComponents.AWAKEN_EPOCH_STORAGE);
    }

    public static Records.AwakenKnowledgeComponent deserializeKnowledge(
            Entity entity
    )
    {
        if (!entity.hasData(AwakenAttachmentTypes.PLAYER_AWAKEN_KNOWLEDGE_ATTACHMENT))
            serializeKnowledge(entity, new Records.AwakenKnowledgeComponent(0.0F, 0.0F, 0.0F, 0.0F));

        return entity.getData(AwakenAttachmentTypes.PLAYER_AWAKEN_KNOWLEDGE_ATTACHMENT);
    }

    public static Records.AwakenMedicineComponent deserializeMedicine(
            ItemStack stack
    )
    {
        return stack.get(AwakenDataComponents.AWAKEN_MEDICINE_STORAGE);
    }

    public static AwakenMoods deserializeMood(
            ItemStack stack
    )
    {
        if (!stack.has(AwakenDataComponents.AWAKEN_MOOD_STORAGE))
            return null;

        return AwakenRegistries.AWAKEN_MOOD.getRegistry(stack.get(AwakenDataComponents.AWAKEN_MOOD_STORAGE));
    }

    public static BigDecimal deserializeAwakenLevel(
            Entity entity
    )
    {
        Records.AwakenLevelComponent data = entity.getExistingDataOrNull(AwakenAttachmentTypes.PLAYER_AWAKEN_LEVEL_ATTACHMENT);
        if (data == null)
            serializeAwakenLevel(entity, new BigDecimal("0.0"));

        data = entity.getExistingDataOrNull(AwakenAttachmentTypes.PLAYER_AWAKEN_LEVEL_ATTACHMENT);
        assert data != null;
        return data.level();
    }

    public static AwakenInfix.InfixContainer deserializeInfix(
            ItemStack stack
    )
    {
        AwakenInfix.InfixContainer container;
        if (!stack.has(AwakenDataComponents.AWAKEN_AFFIX_STORAGE) || (container = Objects.requireNonNull(deserializeAffix(stack)).infix()) == null)
            return new AwakenInfix.InfixContainer(0);

        return container;
    }

    public static List<AwakenPollinate.PollinateInstance> deserializePollinates(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return List.of();

        List<CompoundTag> component = stack.get(AwakenDataComponents.AWAKEN_POLLINATE_STORAGE.get());
        if (component == null)
            return List.of();

        if (component.isEmpty())
            return List.of();

        List<AwakenPollinate.PollinateInstance> pollinates = new ArrayList<>();

        for (CompoundTag tag: component)
        {
            if (!tag.contains("id") || !tag.contains("level"))
                continue;

            AwakenPollinate pollinate = AwakenRegistries.AWAKEN_POLLINATE.getRegistry(ResourceLocation.parse(tag.getString("id")));
            int level = tag.getInt("level");

            if (pollinate == null || level <= 0)
                continue;

            pollinates.add(new AwakenPollinate.PollinateInstance(pollinate, level));
        }

        return pollinates;
    }

    public static AwakenPrefix.PrefixInstance deserializePrefix(
            ItemStack stack
    )
    {
        AwakenPrefix.PrefixInstance instance;
        if (!stack.has(AwakenDataComponents.AWAKEN_AFFIX_STORAGE) || (instance = Objects.requireNonNull(deserializeAffix(stack)).prefix()) == null)
            return null;

        return instance;
    }

    public static Records.AwakenSoulComponent deserializeSoul(
            ItemStack stack
    )
    {
        if (!stack.has(AwakenDataComponents.AWAKEN_SOUL_STORAGE))
            stack.set(AwakenDataComponents.AWAKEN_SOUL_STORAGE, new Records.AwakenSoulComponent(0.0F, AwakenCommon.CONFIG.DEFAULT_SOUL.get().floatValue()));

        return stack.get(AwakenDataComponents.AWAKEN_SOUL_STORAGE);
    }

    public static AwakenSuffix.SuffixInstance deserializeSuffix(
            ItemStack stack
    )
    {
        Records.AwakenAffixComponent desc = deserializeAffix(stack);
        if (desc == null)
            return null;

        if (desc.suffix() == null)
            return null;

        String id = desc.suffix().getString("id");
        int lvl = desc.suffix().getInt("level");
        ResourceLocation path = ResourceLocation.parse(id);
        AwakenSuffix suffix = AwakenRegistries.AWAKEN_SUFFIX.getRegistry(path);
        if (suffix == null)
            return null;

        return new AwakenSuffix.SuffixInstance(suffix, lvl);
    }

    public static AwakenQuality deserializeQuality(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return null;

        String component = stack.get(AwakenDataComponents.AWAKEN_QUALITY_STORAGE.get());
        if (component == null)
            return null;

        return AwakenRegistries.AWAKEN_QUALITY.getRegistry(ResourceLocation.parse(component));
    }

    public static AwakenSpiritual deserializeSpiritual(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return null;

        String component = stack.get(AwakenDataComponents.AWAKEN_SPIRITUAL_STORAGE.get());
        if (component == null)
            return null;

        return AwakenRegistries.AWAKEN_SPIRIT.getRegistry(ResourceLocation.parse(component));
    }

    public static AwakenSpore.SporeContainer deserializeSpores(
            Entity entity
    )
    {
        return entity.getData(AwakenAttachmentTypes.SPORE_ATTACHMENT.get());
    }

    public static List<AwakenSpore.SporeInstance> mergeSpores(
            List<AwakenSpore.SporeInstance> spores
    )
    {
        Map<AwakenSpore, Integer> merged = new HashMap<>();
        List<AwakenSpore.SporeInstance> insts = new ArrayList<>();

        for (AwakenSpore.SporeInstance inst : spores)
        {
            int lvl = merged.getOrDefault(inst, 0);
            merged.put(inst, lvl + inst.getLevel());
        }

        for (Map.Entry<AwakenSpore, Integer> entry: merged.entrySet())
            insts.add(new AwakenSpore.SporeInstance(entry.getKey(), entry.getValue()));

        return List.copyOf(insts);
    }

    public static void serializeAwakenLevel(
            Entity entity,
            BigDecimal level
    )
    {
        entity.setData(AwakenAttachmentTypes.PLAYER_AWAKEN_LEVEL_ATTACHMENT, new Records.AwakenLevelComponent(level.abs()));
    }

    public static void serializeAspects(
            ItemStack stack,
            AwakenAspect.AspectContainer instances
    )
    {
        if (stack.isEmpty())
            return;

        stack.set(AwakenDataComponents.AWAKEN_ASPECT_STORAGE, instances);
    }

    public static void serializeDescriber(
            ItemStack stack,
            @Nullable AwakenInfix.InfixContainer infix,
            @Nullable AwakenPrefix.PrefixInstance prefix,
            @Nullable AwakenSuffix.SuffixInstance suffix
    )
    {
        CompoundTag suffixTag = new CompoundTag();

        if (suffix != null)
        {
            suffixTag.putString("id", suffix.getLocation().toString());
            suffixTag.putInt("level", Math.abs(suffix.getLevel()));
        }

        Records.AwakenAffixComponent component = new Records.AwakenAffixComponent(
                infix,
                prefix,
                suffixTag
        );

        stack.set(AwakenDataComponents.AWAKEN_AFFIX_STORAGE, component);
    }

    public static void serializeEpoch(
            ItemStack stack,
            Records.AwakenEpochComponent epoch
    )
    {
        stack.set(AwakenDataComponents.AWAKEN_EPOCH_STORAGE, epoch);
    }

    public static void serializeKnowledge(
            Entity entity,
            Records.AwakenKnowledgeComponent knowledge
    )
    {
        entity.setData(AwakenAttachmentTypes.PLAYER_AWAKEN_KNOWLEDGE_ATTACHMENT, knowledge);
    }

    public static void serializeMedicine(
            ItemStack stack,
            Records.AwakenMedicineComponent medicine
    )
    {
        stack.set(AwakenDataComponents.AWAKEN_MEDICINE_STORAGE, medicine);
    }

    public static void serializeMood(
            ItemStack stack,
            AwakenMoods mood
    )
    {
        stack.set(AwakenDataComponents.AWAKEN_MOOD_STORAGE, mood.getLocation());
    }

    public static void serializeQuality(
            ItemStack stack,
            AwakenQuality quality
    )
    {
        stack.set(AwakenDataComponents.AWAKEN_QUALITY_STORAGE, quality.getLocation().toString());
    }

    public static void serializeSoul(
            ItemStack stack,
            Records.AwakenSoulComponent soul
    )
    {
        stack.set(AwakenDataComponents.AWAKEN_SOUL_STORAGE, soul);
    }

    public static void serializeSpores(
            Entity entity,
            AwakenSpore.SporeContainer spores
    )
    {
        entity.setData(AwakenAttachmentTypes.SPORE_ATTACHMENT, spores);
    }

    public static void setMaxDurability(
            ItemStack stack,
            int target
    )
    {
        stack.set(DataComponents.MAX_DAMAGE, target);
    }

    private static Records.AwakenAffixComponent deserializeAffix(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return null;

        return stack.get(AwakenDataComponents.AWAKEN_AFFIX_STORAGE);
    }
}