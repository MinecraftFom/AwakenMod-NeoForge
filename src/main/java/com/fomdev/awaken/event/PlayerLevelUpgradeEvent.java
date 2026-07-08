package com.fomdev.awaken.event;

import com.fomdev.awaken.entries.AwakenLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.Event;

public class PlayerLevelUpgradeEvent extends Event
{
    private final AwakenLevel current;
    private final AwakenLevel before;
    private final ServerPlayer player;

    public PlayerLevelUpgradeEvent(
            ServerPlayer player,
            AwakenLevel current,
            AwakenLevel before
    )
    {
        this.player = player;
        this.current = current;
        this.before = before;
    }

    public ServerPlayer getPlayer()
    {
        return this.player;
    }

    public AwakenLevel getCurrentLevel()
    {
        return this.current;
    }

    public AwakenLevel getPreviousLevel()
    {
        return this.before;
    }
}