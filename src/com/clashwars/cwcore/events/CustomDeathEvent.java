package com.clashwars.cwcore.events;

import com.clashwars.cwcore.damage.log.DamageLog;
import com.clashwars.cwcore.damage.log.DamageLogEntry;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomDeathEvent extends Event {

    private Player player;
    private String deathMessage;
    private DamageLog damageLog;
    private DamageLogEntry finalDamage;
    private OfflinePlayer killer;

    public CustomDeathEvent(Player player, String deathMessage, DamageLog damageLog, DamageLogEntry finalDamage, OfflinePlayer killer) {
        this.player = player;
        this.deathMessage = deathMessage;
        this.damageLog = damageLog;
        this.finalDamage = finalDamage;
        this.killer = killer;
    }


    public Player getPlayer() {
        return player;
    }

    public String getDeathMessage() {
        return deathMessage;
    }

    public DamageLog getDamageLog() {
        return damageLog;
    }

    public DamageLogEntry getFinalDamageLog() {
        return finalDamage;
    }

    public OfflinePlayer getKiller() {
        return killer;
    }


    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


}
