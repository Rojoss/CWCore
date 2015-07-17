package com.clashwars.cwcore.config;

import com.clashwars.cwcore.config.internal.EasyConfig;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Location;

import java.util.*;

public class PlayerConfig extends EasyConfig {

    public List<String> VANISH = new ArrayList<String>();
    public HashMap<String, String> FREEZE = new HashMap<String, String>();
    public HashMap<String, String> SCOREBOARD = new HashMap<String, String>();

    public PlayerConfig(String fileName) {
        this.setFile(fileName);
    }


    public List<UUID> getVanishedPlayers() {
        return CWUtil.stringListToUUID(VANISH);
    }

    public boolean isVanished(UUID uuid) {
        return VANISH.contains(uuid.toString());
    }

    public void vanish(UUID uuid) {
        if (!isVanished(uuid)) {
            VANISH.add(uuid.toString());
        }
        save();
    }

    public void unvanish(UUID uuid) {
        if (isVanished(uuid)) {
            VANISH.remove(uuid.toString());
        }
        save();
    }



    public List<UUID> getFrozenPlayers() {
        List<UUID> uuidList = new ArrayList<UUID>();
        for (String key : FREEZE.keySet()) {
            uuidList.add(UUID.fromString(key));
        }
        return uuidList;
    }

    public Location getFreezeLocation(UUID uuid) {
        if (isFrozen(uuid)) {
            return CWUtil.locFromStringSimple(FREEZE.get(uuid.toString()));
        }
        return null;
    }

    public boolean isFrozen(UUID uuid) {
        return FREEZE.containsKey(uuid.toString());
    }

    public void freeze(UUID uuid, Location location) {
        FREEZE.put(uuid.toString(), CWUtil.locToStringSimple(location));
        save();
    }

    public void unfreeze(UUID uuid) {
        if (isFrozen(uuid)) {
            FREEZE.remove(uuid.toString());
        }
        save();
    }


    public HashMap<UUID, String> getScoreboards() {
        HashMap<UUID, String> resultMap = new HashMap<UUID, String>();
        for (Map.Entry<String, String> entry : SCOREBOARD.entrySet()) {
            resultMap.put(UUID.fromString(entry.getKey()), entry.getValue());
        }
        return resultMap;
    }

    public boolean hasScoreboard(UUID uuid) {
        return SCOREBOARD.containsKey(uuid.toString()) && !SCOREBOARD.get(uuid.toString()).isEmpty();
    }

    public String getScoreboard(UUID uuid) {
        if (hasScoreboard(uuid)) {
            return SCOREBOARD.get(uuid.toString());
        }
        return "";
    }

    public void setScoreboard(UUID uuid, String scoreboardID) {
        SCOREBOARD.put(uuid.toString(), scoreboardID);
        save();
    }

    public void removeScoreboard(UUID uuid) {
        if (SCOREBOARD.containsKey(uuid.toString())) {
            SCOREBOARD.remove(uuid.toString());
        }
        save();
    }

}
