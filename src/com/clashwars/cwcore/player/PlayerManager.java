package com.clashwars.cwcore.player;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.config.PlayerConfig;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.*;

public class PlayerManager {

    private CWCore cwc;
    private PlayerConfig pcfg;

    private Map<UUID, CWPlayer> players = new HashMap<UUID, CWPlayer>();

    public PlayerManager(CWCore cwc) {
        this.cwc = cwc;
        this.pcfg = this.cwc.getPlayerCfg();
        populate();
    }


    private void populate() {
        Long t = System.currentTimeMillis();
        Map<UUID, PlayerData> cfgPlayers = pcfg.getPlayers();
        for (UUID uuid : cfgPlayers.keySet()) {
            players.put(uuid, new CWPlayer(uuid, cfgPlayers.get(uuid)));
        }
    }


    public CWPlayer getPlayer(OfflinePlayer p) {
        return getPlayer(p.getUniqueId());
    }

    public CWPlayer getPlayer(String name) {
        return getPlayer(cwc.getServer().getOfflinePlayer(name));
    }

    public CWPlayer getPlayer(UUID uuid) {
        if (players.containsKey(uuid)) {
            return players.get(uuid);
        } else if (pcfg.PLAYERS.containsKey(uuid.toString())) {;
            CWPlayer cwp = new CWPlayer(uuid, pcfg.getPlayer(uuid));
            players.put(uuid, cwp);
            return cwp;
        } else {
            CWPlayer cwp = new CWPlayer(uuid, new PlayerData());
            players.put(uuid, cwp);
            return cwp;
        }
    }

    public Map<UUID, CWPlayer> getPlayers() {
        return players;
    }

    public List<CWPlayer> getPlayers(boolean onlineOnly) {
        List<CWPlayer> playerList = new ArrayList<CWPlayer>();
        for (CWPlayer cwp : players.values()) {
            if (cwp.isOnline()) {
                playerList.add(cwp);
            }
        }
        return playerList;
    }

    public void savePlayers() {
        for (CWPlayer cwp : players.values()) {
            cwp.savePlayer();
        }
    }

    public void removePlayer(UUID uuid, boolean fromConfig) {
        players.remove(uuid);
        if (fromConfig == true) {
            pcfg.removePlayer(uuid);
            pcfg.save();
        }
    }

    public void removePlayer(CWPlayer cwp, boolean fromConfig) {
        removePlayer(cwp.getUUID(), fromConfig);
    }

    public void removePlayer(Player p, boolean fromConfig) {
        removePlayer(p.getPlayer(), fromConfig);
    }


    public void removePlayers(boolean fromConfig) {
        players.clear();
        if (fromConfig == true) {
            pcfg.PLAYERS.clear();
            pcfg.save();
        }
    }
}
