package com.clashwars.cwcore.config;

import com.clashwars.cwcore.config.internal.EasyConfig;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Location;

import java.util.*;

public class PlayerConfig extends EasyConfig {

    public List<String> VANISH = new ArrayList<String>();
    public HashMap<String, String> FREEZE = new HashMap<String, String>();

    public PlayerConfig(String fileName) {
        this.setFile(fileName);
    }


    public List<UUID> getVanishedPlayers() {
        return stringListToUUIDList(VANISH);
    }

    public boolean isVanished(UUID uuid) {
        return VANISH.contains(uuid.toString());
    }

    public void vanish(UUID uuid) {
        if (!isVanished(uuid)) {
            VANISH.add(uuid.toString());
        }
    }

    public void unvanish(UUID uuid) {
        if (isVanished(uuid)) {
            VANISH.remove(uuid.toString());
        }
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
    }

    public void unfreeze(UUID uuid) {
        if (isFrozen(uuid)) {
            FREEZE.remove(uuid.toString());
        }
    }



    private List<UUID> stringListToUUIDList(List<String> stringList) {
        List<UUID> uuidList = new ArrayList<UUID>();
        for (String key : stringList) {
            uuidList.add(UUID.fromString(key));
        }
        return uuidList;
    }
}
