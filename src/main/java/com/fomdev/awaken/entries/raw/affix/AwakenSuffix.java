package com.fomdev.awaken.entries.raw.affix;

import com.fomdev.awaken.entries.raw.affix.suffix.NoneSuffix;
import com.fomdev.flame.register.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;

import java.util.List;

public abstract class AwakenSuffix<T extends Event> extends Registry
{
    private final int durability;
    private final List<EquipmentSlot> slot;
    private final Component description;

    public AwakenSuffix(
            String id,
            int durability,
            Component description,
            List<EquipmentSlot> slot
    )
    {
        super(id);

        this.description = description;
        this.durability = durability;
        this.slot = slot;
    }

    public int addition()
    {
        return this.durability;
    }

    public List<EquipmentSlot> getSlot()
    {
        return this.slot;
    }

    public void register(
            IEventBus bus
    )
    {
        bus.addListener(this::onEvent);
    }

    public abstract void onEvent(
            T event
    );

    public boolean isEmpty()
    {
        return this.getLocation().equals(NoneSuffix.NONE.getLocation());
    }
}