package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public abstract class AwakenPollinate extends Registry
{
    private final Attribute attribute;
    private final EquipmentSlot[] suitable;

    public AwakenPollinate(
            String id,
            Attribute attribute,
            EquipmentSlot[] suitable
    )
    {
        super(id);

        this.attribute = attribute;
        this.suitable = suitable;
    }

    public abstract double getAmount(
            int level
    );

    public final Attribute getAttribute()
    {
        return this.attribute;
    }

    public final EquipmentSlot[] getSlots()
    {
        return this.suitable;
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
}