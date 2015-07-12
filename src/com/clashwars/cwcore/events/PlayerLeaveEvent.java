package com.clashwars.cwcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerLeaveEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private String quitMessage;
    private boolean kicked;
    private String kickReason;

    public PlayerLeaveEvent(Player who, String quitMessage, boolean kicked, String kickReason) {
        super(who);
        this.quitMessage = quitMessage;
        this.kicked = kicked;
        this.kickReason = kickReason;
    }

    public String getQuitMessage() {
        return this.quitMessage;
    }

    public void setQuitMessage(String quitMessage) {
        this.quitMessage = quitMessage;
    }

    public boolean isKicked() {
        return kicked;
    }

    public String getKickReason() {
        return kickReason;
    }

    public void setKickReason(String kickReason) {
        this.kickReason = kickReason;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
