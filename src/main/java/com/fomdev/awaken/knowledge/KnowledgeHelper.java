package com.fomdev.awaken.knowledge;

import com.fomdev.awaken.init.config.AwakenCommon;
import com.fomdev.awaken.util.Records;
import net.minecraft.util.RandomSource;

public class KnowledgeHelper
{
    public static int calculateDurability(
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
        return (int) Math.clamp(result1, 0, original * AwakenCommon.CONFIG.MAX_DURABILITY_FACTOR.get());
    }
}