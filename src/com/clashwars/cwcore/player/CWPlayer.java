package com.clashwars.cwcore.player;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.config.PlayerConfig;
import com.clashwars.cwcore.damage.log.DamageLog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CWPlayer {

    private CWCore cwc;
    private PlayerConfig pcfg;

    private UUID uuid;
    private PlayerData data;

    public List<DamageLog> damageLogs = new ArrayList<DamageLog>();


    public CWPlayer(UUID uuid, PlayerData data) {
        this.uuid = uuid;
        this.data = data;
        this.cwc = CWCore.inst();
        this.pcfg = cwc.getPlayerCfg();

        damageLogs.add(new DamageLog(uuid));
    }


    public void savePlayer() {
        pcfg.setPlayer(uuid, data);
    }



    public void setVanished(boolean vanished) {
        data.setVanished(vanished);
        savePlayer();
    }

    public boolean isVanished() {
        return data.isVanished();
    }


    public void setFrozen(boolean frozen) {
        data.setFrozen(frozen);
        savePlayer();
    }

    public boolean isFrozen() {
        return data.isFrozen() && data.getFreezeLocation() != null;
    }

    public void setFreezeLoc(Location loc) {
        data.setFreezeLocation(loc);
        savePlayer();
    }

    public Location getFreezeLoc() {
        return data.getFreezeLocation();
    }


    public String getScoreboard() {
        return data.getScoreboard();
    }

    public void setScoreboard(String scoreboard) {
        data.setScoreboard(scoreboard);
        savePlayer();
    }


    public PlayerData getData() {
        return data;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return getOfflinePlayer().getName();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public boolean isOnline() {
        return getPlayer() == null ? false : getPlayer().isOnline();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CWPlayer) {
            CWPlayer other = (CWPlayer)obj;

            return other.getUUID() == getUUID();
        }
        return false;
    }
}
