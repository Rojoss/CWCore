package com.clashwars.cwcore.config;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.config.internal.EasyConfig;
import com.clashwars.cwcore.player.PlayerData;

import java.util.*;

public class PlayerConfig extends EasyConfig {

    public HashMap<String, String> PLAYERS = new HashMap<String, String>();

    public PlayerConfig(String fileName) {
        this.setFile(fileName);
    }

    public Map<UUID, PlayerData> getPlayers() {
        Map<UUID, PlayerData> players = new HashMap<UUID, PlayerData>();
        for (String key : PLAYERS.keySet()) {
            players.put(UUID.fromString(key), CWCore.inst().getGson().fromJson(PLAYERS.get(key), PlayerData.class));
        }
        return players;
    }

    public PlayerData getPlayer(UUID uuid) {
        return CWCore.inst().getGson().fromJson(PLAYERS.get(uuid.toString()), PlayerData.class);
    }

    public void setPlayer(UUID uuid, PlayerData pd) {
        PLAYERS.put(uuid.toString(), CWCore.inst().getGson().toJson(pd, PlayerData.class));
        save();
    }

    public void removePlayer(UUID uuid) {
        PLAYERS.remove(uuid.toString());
        save();
    }
}
