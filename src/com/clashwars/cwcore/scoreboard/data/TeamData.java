package com.clashwars.cwcore.scoreboard.data;

import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamData {

    private String name = "";
    private String displayName = "";
    private String prefix = "";
    private String suffix = "";
    private boolean friendlyFire = false;
    private boolean seeInvis = true;
    private NameTagVisibility nameTagVisibility = NameTagVisibility.ALWAYS;
    private List<String> players = new ArrayList<String>();

    public TeamData() {
        //-
    }

    public TeamData(String name) {
        this.name = name;
    }

    public TeamData(String name, String displayName, String prefix, String suffix, boolean friendlyFire, boolean seeInvis, NameTagVisibility nameTagVisibility, List<UUID> players) {
        this.name = name;
        this.displayName = displayName;
        this.prefix = prefix;
        this.suffix = suffix;
        this.friendlyFire = friendlyFire;
        this.seeInvis = seeInvis;
        this.nameTagVisibility = nameTagVisibility;
        setPlayers(players);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    public String getSuffix() {
        return suffix;
    }
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }


    public boolean isFriendlyFire() {
        return friendlyFire;
    }
    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }


    public boolean isSeeInvis() {
        return seeInvis;
    }
    public void setSeeInvis(boolean seeInvis) {
        this.seeInvis = seeInvis;
    }


    public boolean hasPlayer(UUID uuid) {
        return players.contains(uuid.toString());
    }
    public void addPlayer(UUID uuid) {
        if (!hasPlayer(uuid)) {
            players.add(uuid.toString());
        }
    }
    public void removePlayer(UUID uuid) {
        if (hasPlayer(uuid)) {
            players.remove(uuid.toString());
        }
    }
    public List<UUID> getPlayers() {
        return CWUtil.stringListToUUID(players);
    }
    public void setPlayers(List<UUID> players) {
        this.players = CWUtil.uuidListToString(players);
    }



    public Team getTeam(Scoreboard scoreboard) {
        Team team = scoreboard.getTeam(name);
        if (team == null) {
            team = scoreboard.registerNewTeam(name);
        }
        team.setDisplayName(CWUtil.integrateColor(displayName));
        team.setPrefix(CWUtil.integrateColor(prefix));
        team.setSuffix(CWUtil.integrateColor(suffix));
        team.setAllowFriendlyFire(friendlyFire);
        team.setCanSeeFriendlyInvisibles(seeInvis);
        team.setNameTagVisibility(nameTagVisibility);
        for (String uuidString : players) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuidString));
            if (offlinePlayer != null) {
                team.addPlayer(offlinePlayer);
            }
        }
        return team;
    }
}
