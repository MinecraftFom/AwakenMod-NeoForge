package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public abstract class AwakenSpore extends Registry
{
    private final Attribute attribute;
    private final EquipmentSlot[] suitable;

    public AwakenSpore(
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

    public static class SporeInstance implements INBTSerializable<CompoundTag>
    {
        private AwakenSpore spore;
        private int level;

        public SporeInstance(
                AwakenSpore spore,
                int level
        )
        {
            this.spore = spore;
            this.level = level;
        }

        @Override
        public CompoundTag serializeNBT(@NotNull HolderLookup.Provider provider)
        {
            CompoundTag self = new CompoundTag();
            self.putString("id", spore.getLocation().toString());
            self.putInt("level", level);

            return self;
        }

        @Override
        public void deserializeNBT(@NotNull HolderLookup.Provider provider, @NotNull CompoundTag tag)
        {
            this.spore = AwakenRegistries.AWAKEN_SPORE.getRegistry(ResourceLocation.parse(tag.getString("id")));
            this.level = tag.getInt("level");
        }

        public AwakenSpore getSpore()
        {
            return this.spore;
        }

        public int getLevel()
        {
            return this.level;
        }
    }
}