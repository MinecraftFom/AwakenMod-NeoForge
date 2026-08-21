package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NBTUtil
{
    public static void addAspect(
            ItemStack stack,
            AwakenAspect.AspectInstance instance
    )
    {
        List<AwakenAspect.AspectInstance> insts = deserializeAspects(stack);
        insts.add(instance);
        serializeAspects(stack, insts);
    }

    public static void addAwakenLevel(
            Player player,
            float amount
    )
    {
        serializeAwakenLevel(player, (int) (100 * (deserializeAwakenLevel(player) + amount)) / 100.0F);
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

    public static void clearPlayer(
            ItemStack stack
    )
    {
        stack.set(AwakenDataComponents.AWAKEN_OWNER, "");
    }

    public static List<AwakenAspect.AspectInstance> deserializeAspects(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return List.of();

        List<AwakenAspect.AspectInstance> instances = new ArrayList<>();
        List<CompoundTag> component = stack.get(AwakenDataComponents.AWAKEN_ASPECT_STORAGE.get());
        if (component == null)
            return instances;

        for (CompoundTag tag: component)
        {
            if (!tag.contains("id") || !tag.contains("level"))
                continue;

            AwakenAspect aspect = AwakenRegistries.AWAKEN_ASPECT.getRegistry(ResourceLocation.parse(tag.getString("id")));
            int level = tag.getInt("level");

            if (aspect == null)
                continue;

            instances.add(new AwakenAspect.AspectInstance(aspect, level));
        }


        return instances;
    }

    public static Records.AwakenEpochComponent deserializeEpoch(
            ItemStack stack
    )
    {
        return stack.get(AwakenDataComponents.AWAKEN_EPOCH_STORAGE);
    }

    public static Records.AwakenKnowledgeComponent deserializeKnowledge(
            Player player
    )
    {
        if (!player.hasData(AwakenAttachmentTypes.PLAYER_AWAKEN_KNOWLEDGE_ATTACHMENT))
            serializeKnowledge(player, new Records.AwakenKnowledgeComponent(0.0F, 0.0F, 0.0F, 0.0F));

        return player.getData(AwakenAttachmentTypes.PLAYER_AWAKEN_KNOWLEDGE_ATTACHMENT);
    }

    public static float deserializeAwakenLevel(
            Entity entity
    )
    {
        Records.AwakenLevelComponent data = entity.getExistingDataOrNull(AwakenAttachmentTypes.PLAYER_AWAKEN_LEVEL_ATTACHMENT);
        if (data == null)
            serializeAwakenLevel(entity, 0.0F);

        data = entity.getExistingDataOrNull(AwakenAttachmentTypes.PLAYER_AWAKEN_LEVEL_ATTACHMENT);
        assert data != null;
        return data.level();
    }

    public static AwakenInfix.InfixInstance deserializeInfix(
            ItemStack stack
    )
    {
        Records.AwakenDescriberComponent desc = deserializeDescriber(stack);
        if (desc == null)
            return null;

        if (desc.infix() == null)
            return null;

        String id = desc.infix().getString("id");
        int lvl = desc.infix().getInt("level");
        ResourceLocation path = ResourceLocation.parse(id);
        AwakenInfix infix = AwakenRegistries.AWAKEN_INFIX.getRegistry(path);
        if (infix == null)
            return null;

        return new AwakenInfix.InfixInstance(infix, lvl);
    }

    public static ServerPlayer deserializePlayer(
            ItemStack stack
    )
    {
        String id = stack.get(AwakenDataComponents.AWAKEN_OWNER);
        IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
        if (server == null || id == null || id.isBlank())
            return null;

        return server.getPlayerList().getPlayer(UUID.fromString(id));
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
        Records.AwakenDescriberComponent desc = deserializeDescriber(stack);
        if (desc == null)
            return null;

        if (desc.prefix() == null)
            return null;

        String id = desc.prefix().getString("id");
        int lvl = desc.prefix().getInt("level");
        ResourceLocation path = ResourceLocation.parse(id);
        AwakenPrefix prefix = AwakenRegistries.AWAKEN_PREFIX.getRegistry(path);
        if (prefix == null)
            return null;

        return new AwakenPrefix.PrefixInstance(prefix, lvl);
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
        Records.AwakenDescriberComponent desc = deserializeDescriber(stack);
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

    public static List<AwakenSpore.SporeInstance> deserializeSpores(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return List.of();

        List<CompoundTag> component = stack.get(AwakenDataComponents.AWAKEN_SPORE_STORAGE.get());
        if (component == null)
            return List.of();

        if (component.isEmpty())
            return List.of();

        List<AwakenSpore.SporeInstance> spores = new ArrayList<>();

        for (CompoundTag tag: component)
        {
            if (!tag.contains("id") || !tag.contains("level"))
                continue;

            AwakenSpore spore = AwakenRegistries.AWAKEN_SPORE.getRegistry(ResourceLocation.parse(tag.getString("id")));
            int level = tag.getInt("level");

            if (spore == null || level <= 0)
                continue;

            spores.add(new AwakenSpore.SporeInstance(spore, level));
        }

        return spores;
    }

    public static void serializeAwakenLevel(
            Entity entity,
            float level
    )
    {
        entity.setData(AwakenAttachmentTypes.PLAYER_AWAKEN_LEVEL_ATTACHMENT, new Records.AwakenLevelComponent(level));
    }

    public static void serializeAspects(
            ItemStack stack,
            List<AwakenAspect.AspectInstance> instances
    )
    {
        if (stack.is(Items.AIR))
            return;

        List<CompoundTag> tags = new ArrayList<>();
        for (AwakenAspect.AspectInstance aspect: instances)
        {
            CompoundTag tag = new CompoundTag();
            tag.putString("id", aspect.aspect().getLocation().toString());
            tag.putInt("level", aspect.amount());
            tags.add(tag);
        }

        stack.set(AwakenDataComponents.AWAKEN_ASPECT_STORAGE, tags);
    }

    public static void serializeDescriber(
            ItemStack stack,
            @Nullable AwakenInfix.InfixInstance infix,
            @Nullable AwakenPrefix.PrefixInstance prefix,
            @Nullable AwakenSuffix.SuffixInstance suffix
    )
    {
        CompoundTag infixTag = new CompoundTag();
        CompoundTag prefixTag = new CompoundTag();
        CompoundTag suffixTag = new CompoundTag();

        if (infix != null)
        {
            infixTag.putString("id", infix.getLocation().toString());
            infixTag.putInt("level", infix.getLevel());
        }

        if (prefix != null)
        {
            prefixTag.putString("id", prefix.getLocation().toString());
            prefixTag.putInt("level", prefix.getLevel());
        }

        if (suffix != null)
        {
            suffixTag.putString("id", suffix.getLocation().toString());
            suffixTag.putInt("level", suffix.getLevel());
        }

        Records.AwakenDescriberComponent component = new Records.AwakenDescriberComponent(
                infixTag,
                prefixTag,
                suffixTag
        );

        stack.set(AwakenDataComponents.AWAKEN_DESCRIBER_STORAGE, component);
    }

    public static void serializeEpoch(
            ItemStack stack,
            Records.AwakenEpochComponent epoch
    )
    {
        stack.set(AwakenDataComponents.AWAKEN_EPOCH_STORAGE, epoch);
    }

    public static void serializeKnowledge(
            Player player,
            Records.AwakenKnowledgeComponent knowledge
    )
    {
        player.setData(AwakenAttachmentTypes.PLAYER_AWAKEN_KNOWLEDGE_ATTACHMENT, knowledge);
    }

    public static void serializePlayer(
            ItemStack stack,
            ServerPlayer player
    )
    {
        stack.set(AwakenDataComponents.AWAKEN_OWNER, player.getUUID().toString());
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

    public static void setDurability(
            ItemStack stack,
            int target
    )
    {
        int max = stack.getMaxDamage();
        int toSet = Math.max(max, target);
        stack.set(DataComponents.DAMAGE, toSet);
    }

    public static void setMaxDurability(
            ItemStack stack,
            int target
    )
    {
        stack.set(DataComponents.MAX_DAMAGE, target);
    }

    private static Records.AwakenDescriberComponent deserializeDescriber(
            ItemStack stack
    )
    {
        if (stack.is(Items.AIR))
            return null;

        return stack.get(AwakenDataComponents.AWAKEN_DESCRIBER_STORAGE);
    }
}