package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public abstract class AwakenPollinate extends Registry
{
    private final MobEffectInstance effect;
    private final EquipmentSlot[] slots;
    private final TriggerTarget target;
    private final TriggerType type;

    public AwakenPollinate(
            String id,
            MobEffectInstance effect,
            EquipmentSlot[] slots,
            TriggerTarget target,
            TriggerType type
    )
    {
        super(id);

        this.effect = effect;
        this.slots = slots;
        this.target = target;
        this.type = type;
    }

    public abstract double getAmount(
            int level
    );

    public final MobEffectInstance getEffect()
    {
        return this.effect;
    }

    public final EquipmentSlot[] getSlots()
    {
        return this.slots;
    }

    public TriggerTarget getTarget()
    {
        return this.target;
    }

    public TriggerType getType()
    {
        return this.type;
    }

    public static class PollinateInstance implements INBTSerializable<CompoundTag>
    {
        private AwakenPollinate pollinate;
        private int level;

        public PollinateInstance(
                AwakenPollinate pollinate,
                int level
        )
        {
            this.pollinate = pollinate;
            this.level = level;
        }

        @Override
        public CompoundTag serializeNBT(@NotNull HolderLookup.Provider provider)
        {
            CompoundTag self = new CompoundTag();
            self.putString("id", pollinate.getLocation().toString());
            self.putInt("level", level);

            return self;
        }

        @Override
        public void deserializeNBT(@NotNull HolderLookup.Provider provider, @NotNull CompoundTag tag)
        {
            this.pollinate = AwakenRegistries.AWAKEN_POLLINATE.getRegistry(ResourceLocation.parse(tag.getString("id")));
            this.level = tag.getInt("level");
        }

        public AwakenPollinate getPollinate()
        {
            return this.pollinate;
        }

        public int getLevel()
        {
            return this.level;
        }
    }

    public enum TriggerTarget
    {
        DAMAGER,
        SELF,
        TARGET
    }

    public enum TriggerType
    {
        CONTINUE,
        DAMAGE,
        HURT
    }
}