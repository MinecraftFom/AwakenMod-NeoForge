package com.fomdev.awaken.util;

import com.fomdev.awaken.entries.raw.*;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.fomdev.awaken.register.data.AwakenDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NBTUtil
{
    public static void addAwakenLevel(
            Player player,
            float amount
    )
    {
        serializeAwakenLevel(player, deserializeAwakenLevel(player) + amount);
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

        List<CompoundTag> component = stack.get(AwakenDataComponents.AWAKEN_ASPECT_STORAGE.get());
        if (component == null)
            return List.of();

        List<AwakenAspect.AspectInstance> instances = new ArrayList<>();
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
            Player player
    )
    {
        Records.AwakenLevelComponent data = player.getExistingDataOrNull(AwakenAttachmentTypes.PLAYER_AWAKEN_LEVEL_ATTACHMENT);
        if (data == null)
            serializeAwakenLevel(player, 0.0F);

        data = player.getExistingDataOrNull(AwakenAttachmentTypes.PLAYER_AWAKEN_LEVEL_ATTACHMENT);
        assert data != null;
        return data.level();
    }

    public static AwakenInfix deserializeInfix(
            ItemStack stack
    )
    {
        Records.AwakenDescriberComponent desc = deserializeDescriber(stack);
        if (desc == null)
            return null;

        if (desc.infix() == null)
            return null;

        return AwakenRegistries.AWAKEN_INFIX.getRegistry(ResourceLocation.parse(desc.infix()));
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

    public static AwakenPrefix deserializePrefix(
            ItemStack stack
    )
    {
        Records.AwakenDescriberComponent desc = deserializeDescriber(stack);
        if (desc == null)
            return null;

        if (desc.prefix() == null)
            return null;

        return AwakenRegistries.AWAKEN_PREFIX.getRegistry(ResourceLocation.parse(desc.prefix()));
    }

    public static AwakenSuffix deserializeSuffix(
            ItemStack stack
    )
    {
        Records.AwakenDescriberComponent desc = deserializeDescriber(stack);
        if (desc == null)
            return null;

        if (desc.suffix() == null)
            return null;

        return AwakenRegistries.AWAKEN_SUFFIX.getRegistry(ResourceLocation.parse(desc.suffix()));
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
            Player player,
            float level
    )
    {
        player.setData(AwakenAttachmentTypes.PLAYER_AWAKEN_LEVEL_ATTACHMENT, new Records.AwakenLevelComponent(level));
    }

    public static void serializeDescriber(
            ItemStack stack,
            AwakenInfix infix,
            AwakenPrefix prefix,
            AwakenSuffix suffix
    )
    {
        Records.AwakenDescriberComponent component = new Records.AwakenDescriberComponent(
                infix.getLocation().toString(),
                prefix.getLocation().toString(),
                suffix.getLocation().toString()
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