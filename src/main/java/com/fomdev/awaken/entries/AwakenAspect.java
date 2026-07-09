package com.fomdev.awaken.entries;

import com.fomdev.awaken.api.Registry;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class AwakenAspect extends Registry
{
    private final Color color;

    public AwakenAspect(
            String id,
            Color color
    )
    {
        super(id);
        this.color = color;
    }

    public Color getColor()
    {
        return this.color;
    }

    public static class AspectInstance implements INBTSerializable<CompoundTag>
    {
        private AwakenAspect aspect;
        private int amount;

        public AspectInstance(
                AwakenAspect aspect,
                int amount
        )
        {
            this.aspect = aspect;
            this.amount = amount;
        }

        @Override
        public CompoundTag serializeNBT(@NotNull HolderLookup.Provider provider)
        {
            CompoundTag self = new CompoundTag();
            self.putString("id", aspect.getLocation().toString());
            self.putInt("level", amount);

            return self;
        }

        @Override
        public void deserializeNBT(@NotNull HolderLookup.Provider provider, @NotNull CompoundTag tag)
        {
            this.aspect = AwakenRegistries.AWAKEN_ASPECT.getRegistry(ResourceLocation.parse(tag.getString("id")));
            this.amount = tag.getInt("level");
        }

        public AwakenAspect aspect()
        {
            return this.aspect;
        }

        public int amount()
        {
            return this.amount;
        }
    }
}