package com.fomdev.awaken.knowledge;

import com.fomdev.awaken.entries.raw.AwakenQuality;
import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.spawn.shuffle.ShuffledRegistries;
import com.fomdev.awaken.util.NBTUtil;
import com.fomdev.awaken.util.Records;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.atomic.AtomicReference;

public class KnowledgeHelper
{
    public static int calculateDurability(
            int max,
            Records.AwakenKnowledgeComponent knowledge,
            RandomSource random
    )
    {
        float factor = knowledge.proficiency() * knowledge.skill();
        float factor1 = factor <= 0? 1: factor;
        float factor2 = random.nextFloat() % factor1;
        float result = (float) Math.pow(factor2, 1.0 / 10.0);
        int result1 = (int) (max * result);
        return Math.clamp(max - result1, 0, max);
    }

    public static int calculateMaxDurability(
            int original,
            Records.AwakenKnowledgeComponent knowledge,
            RandomSource random
    )
    {
        float factor = knowledge.experience() * knowledge.skill();
        float factor1 = factor <= 0? 1: factor;
        float factor2 = random.nextFloat() % factor1;
        float factor3 = factor2 * knowledge.proficiency();
        float factor4 = factor3 < 0? 0: factor;
        float result = (float) Math.pow(factor4, 1.0 / 3.0);
        int result1 = (int) (result * original);
        return (int) Math.clamp(result1, 1, original * AwakenCommon.CONFIG.MAX_DURABILITY_FACTOR.get()); // At least 1 durability
    }

    public static AwakenQuality shuffleQuality(
            Records.AwakenKnowledgeComponent knowledge,
            RandomSource random
    )
    {
        float total = knowledge.experience() * knowledge.insight() * knowledge.proficiency() * knowledge.skill();
        float factor = total <= 0? 1: total;
        return ShuffledRegistries.WEIGHTED_AWAKEN_QUALITY.calculate((float) Math.pow(factor, 3), random);
    }

    public static ItemStack getResult(
            Player player,
            ItemStack stack
    )
    {
        Records.AwakenKnowledgeComponent knowledge = NBTUtil.deserializeKnowledge(player);
        if (!stack.has(DataComponents.TOOL) || stack.getMaxStackSize() != 1)
            return stack;

        int originalDurability = stack.getMaxDamage();
        int changedDurability = KnowledgeHelper.calculateMaxDurability(
                originalDurability,
                knowledge,
                player.getRandom()
        );

        int durability = KnowledgeHelper.calculateDurability(
                changedDurability,
                knowledge,
                player.getRandom()
        );

        NBTUtil.setDurability(
                stack,
                durability
        );

        NBTUtil.setMaxDurability(
                stack,
                changedDurability
        );

        if (player.getRandom().nextFloat() % 100 < 0.1 * knowledge.proficiency() * knowledge.skill() && stack.isDamageableItem())
        {
            AwakenQuality quality = shuffleQuality(knowledge, player.getRandom());
            NBTUtil.serializeQuality(stack, quality);
        }

        float factor = (float) Math.pow((float) changedDurability / (float) durability, 1.0 / 3.0);
        int factor2 = (int) factor;
        int factor3 = factor2 <= 0? 1: factor2;
        float result = player.getRandom().nextInt(factor3);
        float skill = knowledge.skill() + result;

        NBTUtil.serializeKnowledge(player, new Records.AwakenKnowledgeComponent(knowledge.experience(), knowledge.insight(), knowledge.proficiency(), skill));

        return stack;
    }

    public static Records.AwakenKnowledgeComponent randomizeKnowledge(
            ServerLevel server,
            RandomSource random
    )
    {
        boolean shouldForExperience = random.nextBoolean();
        boolean shouldForInsight = random.nextBoolean();
        boolean shouldForProficiency = random.nextBoolean();
        boolean shouldForSkill = random.nextBoolean();

        float experience = 0.0F;
        float insight = 0.0F;
        float proficiency = 0.0F;
        float skill = 0.0F;
        AtomicReference<Float> experienceFactor = new AtomicReference<>(0.0F);
        AtomicReference<Float> insightFactor = new AtomicReference<>(0.0F);
        AtomicReference<Float> proficiencyFactor = new AtomicReference<>(0.0F);
        AtomicReference<Float> skillFactor = new AtomicReference<>(0.0F);

        server.players().forEach(p -> {
            Records.AwakenKnowledgeComponent knowledge = NBTUtil.deserializeKnowledge(p);
            experienceFactor.updateAndGet(v -> v + knowledge.experience());
            insightFactor.updateAndGet(v -> v + knowledge.insight());
            proficiencyFactor.updateAndGet(v -> v + knowledge.proficiency());
            skillFactor.updateAndGet(v -> v + knowledge.skill());
        });

        float efa = (float) Math.sqrt(experienceFactor.get());
        float ifa = (float) Math.sqrt(insightFactor.get());
        float pfa = (float) Math.sqrt(proficiencyFactor.get());
        float sfa = (float) Math.sqrt(skillFactor.get());

        efa = efa < 1? 1: efa;
        ifa = ifa < 1? 1: ifa;
        pfa = pfa < 1? 1: pfa;
        sfa = sfa < 1? 1: sfa;

        if (shouldForExperience)
            experience = random.nextFloat() % efa;
        if (shouldForInsight)
            insight = random.nextFloat() % ifa;
        if (shouldForProficiency)
            proficiency = random.nextFloat() % pfa;
        if (shouldForSkill)
            skill = random.nextFloat() % sfa;

        return  new Records.AwakenKnowledgeComponent(experience, insight, proficiency, skill);
    }
}