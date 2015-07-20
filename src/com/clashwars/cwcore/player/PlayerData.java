package com.clashwars.cwcore.player;

import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Location;

public class PlayerData {

    private boolean frozen = false;
    private boolean vanished = false;
    private String freezeLocation = "";
    private String scoreboard = "";

    public PlayerData() {
        //--
    }


    public boolean isFrozen() {
        return frozen;
    }
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public Location getFreezeLocation() {
        return CWUtil.locFromStringSimple(freezeLocation);
    }
    public void setFreezeLocation(Location freezeLocation) {
        this.freezeLocation = CWUtil.locToStringSimple(freezeLocation);
    }

    public boolean isVanished() {
        return vanished;
    }
    public void setVanished(boolean vanished) {
        this.vanished = vanished;
    }

    public String getScoreboard() {
        return scoreboard;
    }
    public void setScoreboard(String scoreboard) {
        this.scoreboard = scoreboard;
    }
}
