package com.fomdev.awaken.entries.raw.affix.suffix.weapon;

import com.fomdev.awaken.entries.raw.affix.AwakenSuffix;
import com.fomdev.awaken.entries.raw.affix.ServingTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoubleCriticalSuffix extends AwakenSuffix
{
    public DoubleCriticalSuffix(
            String id
    )
    {
        super(
                id,
                List.of(ServingTypes.WEAPON_TOOL)
        );
    }

    @Override
    public void executeAsWeapon(
            ItemStack stack,
            Map<String, String> args,
            LivingIncomingDamageEvent event
    )
    {
        float chance = Float.parseFloat(args.get("criticalChance"));
        float amount = Float.parseFloat(args.get("criticalAmount"));

        Entity entity = event.getSource().getEntity();
        Entity target = event.getEntity();
        if (entity == null)
            return;

        RandomSource random = entity.getRandom();
        if (random.nextFloat() % 100.0F < chance)
            event.setAmount(event.getAmount() + amount);
    }

    @Override
    public Component getDescription(Map<String, String> args)
    {
        return Component.translatable("suffix.double_critical.tooltip", args.get("criticalChance"), args.get("criticalAmount"));
    }

    @Override
    public Map<String, String> randomize(float diff, float factor, RandomSource random)
    {
        Map<String, String> map = new HashMap<>();
        map.put("criticalChance", "" + random.nextFloat() % factor);
        map.put("criticalAmount", "" + random.nextFloat() % (factor / 2));
        return map;
    }
}