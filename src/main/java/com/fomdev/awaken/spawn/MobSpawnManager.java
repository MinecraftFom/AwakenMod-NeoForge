package com.fomdev.awaken.spawn;

import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.knowledge.KnowledgeHelper;
import com.fomdev.awaken.literature.Literature;
import com.fomdev.awaken.rank.RankHelper;
import com.fomdev.awaken.register.data.AwakenAttachmentTypes;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.awaken.util.ColorUtil;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.List;

public class MobSpawnManager
{
    /* Here, the resourcelocation should be the key of the level (dimension) the entities are generating in */
    public static final Map<ResourceLocation, List<ResourceLocation>> LEVELED_ENTITIES
            = new HashMap<>();

    public static final List<Holder.Reference<EntityType<?>>> RIDE_ENTITIES = new ArrayList<>();

    public static boolean shouldRide(
            BigDecimal diff,
            float factor,
            RandomSource random
    )
    {
        int n = random.nextInt(diff.multiply(new BigDecimal(factor)).intValue());
        BigDecimal n2 = diff.divide(new BigDecimal(factor * 10), RoundingMode.HALF_UP);
        BigDecimal n3 = new BigDecimal(AwakenCommon.CONFIG.CARRIER_GENERATION.get());
        return n2.compareTo(new BigDecimal(n)) < 0 && n3.compareTo(diff) <= 0;
    }

    public static Entity spawnRideEntity(
            Level level,
            Vector3f pos,
            BigDecimal diff,
            float factor,
            RandomSource random
    )
    {
        if (!shouldRide(diff, factor, random))
            return null;

        if (RIDE_ENTITIES.isEmpty())
            return null;

        Holder.Reference<EntityType<?>> entity = RIDE_ENTITIES.get(random.nextInt(RIDE_ENTITIES.size()));
        String id = Objects.requireNonNull(entity.getKey()).location().toString();
        CompoundTag idTag = new CompoundTag();
        idTag.putString("id", id);
        Entity ent = EntityType.loadEntityRecursive(idTag, level, e -> {
            e.moveTo(new Vec3(pos), e.getYRot(), e.getXRot());
            return e;
        });
        if (ent == null)
            return null;

        ent.setCustomName(Component.translatable("tile.rideble_entity.name"));
        level.addFreshEntity(ent);
        return ent;
    }

    public static void awakenSpawnLogic(
            LivingEntity original,
            float diff,
            int auraSize,
            Color color,
            Component title,
            Level level,
            RandomSource random
    )
    {
        /* TODO: ADD PARTICLE */
        Util.placeholder(diff, auraSize, color, title, level, random);
    }

    public static Color shuffleColor(
            RandomSource random
    )
    {
        float h = random.nextFloat() % 360.0F;
        float s = random.nextFloat() % 360.0F;

        return Color.getHSBColor(h, s, 1.0f);
    }

    public static Component shuffleTitle(
            RandomSource random
    )
    {
        return Literature.NAMES_FIRST_INSTANCE.get(random).copy().append("-").append(Literature.NAMES_LAST_INSTANCE.get(random));
    }

    public static void spawn(
            LivingEntity entity,
            BigDecimal diff,
            Level level,
            RandomSource random
    )
    {
        if (diff.compareTo(new BigDecimal("0")) <= 0)
            return;

        MobTier tier = ShuffledRegistries.WEIGHTED_AWAKEN_TIER.calculate(
                diff.floatValue(),
                random
        );

        if (tier == null)
            return;

        int auraSize = random.nextInt(20);
        Color color = shuffleColor(
                random
        );
        Component title = Component.empty().append(Component.translatable(tier.getDescriptionID()).append(" ").append(shuffleTitle(
                random
        )));

        if (!tier.shouldSpawn())
            return;

        normalGenerate(
                entity,
                diff,
                tier.factor(),
                color,
                title,
                level,
                random
        );

        tier.additionalSpawn().onSpawn(
                entity,
                diff.floatValue(),
                auraSize,
                color,
                title,
                level,
                random
        );
    }

    private static void normalGenerate(
            LivingEntity original,
            BigDecimal diff,
            float factor,
            Color color,
            Component title,
            Level level,
            RandomSource random
    )
    {
        if (factor <= 0)
            return;
        if (!(level instanceof ServerLevel server))
            return;
        if (!(original instanceof EquipmentUser user))
            return;

        if (factor >= 5)
            original.setData(AwakenAttachmentTypes.IS_AWAKEN, true);

        NBTUtil.serializeAwakenLevel(original, RankHelper.randomizeRank(server, factor, random).abs());
        NBTUtil.serializeKnowledge(original, KnowledgeHelper.randomizeKnowledge(server, random));

        original.setCustomName(
                Component.empty().append(title).withStyle(ColorUtil.colorStyle(color))
        );

        EquipmentSlot[] slots = EquipmentManager.shuffleSlots(
                diff,
                factor,
                random
        );

        for (EquipmentSlot slot: slots)
        {
            ItemStack item = EquipmentManager.shuffleItemStack(
                    slot,
                    diff,
                    factor,
                    random
            );

            if (item == null)
                continue;

            EquipmentManager.shuffleForItemStack(
                    level,
                    item,
                    slot,
                    diff,
                    factor,
                    color,
                    random
            );

            user.setItemSlot(
                    slot,
                    item
            );
        }

        List<MobEffectInstance> insts = EquipmentManager.shuffleEffects(level,diff, factor, random);
        insts.forEach(original::addEffect);
        Entity ride;
        if ((ride = spawnRideEntity(level, original.getEyePosition().toVector3f(), diff, factor, random)) != null)
            original.startRiding(ride);
    }

    @FunctionalInterface
    public interface ISpawningLogic
    {
        void onSpawn(
                LivingEntity original,
                float diff,
                int auraSize,
                Color color,
                Component title,
                Level level,
                RandomSource random
        );
    }

    public static void loadFromConfig()
    {
        AwakenCommon.CONFIG.ENTITIES.get()
                .stream()
                .map(
                        MobSpawnManager::loadFromString
                )
                .forEach(
                        data ->
                                data.getB()
                                        .forEach(
                                                loc -> LEVELED_ENTITIES.computeIfAbsent(
                                                        loc,
                                                        l -> new ArrayList<>()
                                                ).add(data.getA())
                                        )
                );

        AwakenCommon.CONFIG.RIDE_ENTITIES.get()
                .stream()
                .map(
                        MobSpawnManager::loadFromString$1
                )
                .forEach(
                        RIDE_ENTITIES::add
                );
    }

    public static Tuple<ResourceLocation, List<ResourceLocation>> loadFromString(
            String raw
    )
    {
        String[] components = raw.split("\\|");
        if (components.length != 2)
            throw new IllegalArgumentException("Invalid config structure");

        String entity = components[0].strip();
        String[] levels = components[1].strip().split("&");

        return new Tuple<>(
                ResourceLocation.parse(entity),
                Arrays.stream(levels).map(String::strip).map(ResourceLocation::parse).toList()
        );
    }

    public static Holder.Reference<EntityType<?>> loadFromString$1(
            String raw
    )
    {
        ResourceLocation location = ResourceLocation.parse(raw);
        return BuiltInRegistries.ENTITY_TYPE.getHolder(location).orElseThrow();
    }
}