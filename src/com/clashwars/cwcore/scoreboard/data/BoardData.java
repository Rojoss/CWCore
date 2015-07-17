package com.clashwars.cwcore.scoreboard.data;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.utils.CWUtil;

import java.util.*;

public class BoardData {

    private String ID = "";
    private boolean global = false;
    private boolean visible = false;
    private List<String> players = new ArrayList<String>();

    private String SIDEBAR = "";
    private String BELOW_NAME = "";
    private String PLAYER_LIST = "";

    private HashMap<String, String> teams = new HashMap<String, String>();
    private HashMap<String, String> objectives = new HashMap<String, String>();

    public BoardData() {
        //-
    }

    public BoardData(String ID) {
        this.ID = ID;
    }


    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isGlobal() {
        return global;
    }
    public void setGlobal(boolean global) {
        this.global = global;
    }


    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    public boolean hasTeam(String team) {
        return teams.containsKey(team);
    }
    public TeamData getTeam(String team) {
        if (hasTeam(team)) {
            return CWCore.inst().getGson().fromJson(teams.get(team), TeamData.class);
        }
        return null;
    }
    public void setTeam(String team, TeamData teamData) {
        teams.put(team, CWCore.inst().getGson().toJson(teamData, TeamData.class));
    }
    public void removeTeam(String team) {
        if (hasTeam(team)) {
            teams.remove(team);
        }
    }
    public HashMap<String, TeamData> getTeams() {
        HashMap<String, TeamData> teamList = new HashMap<String, TeamData>();
        for (Map.Entry<String, String> team : teams.entrySet()) {
            teamList.put(team.getKey(), CWCore.inst().getGson().fromJson(team.getValue(), TeamData.class));
        }
        return teamList;
    }
    public void setTeams(HashMap<String, TeamData> teams) {
        HashMap<String, String> teamList = new HashMap<String, String>();
        for (Map.Entry<String, TeamData> team : teams.entrySet()) {
            teamList.put(team.getKey(), CWCore.inst().getGson().toJson(team.getValue(), TeamData.class));
        }
        this.teams = teamList;
    }


    public boolean hasObjective(String objective) {
        return objectives.containsKey(objective);
    }
    public ObjectiveData getObjective(String objective) {
        if (hasObjective(objective)) {
            return CWCore.inst().getGson().fromJson(objectives.get(objective), ObjectiveData.class);
        }
        return null;
    }
    public void setObjective(String objective, ObjectiveData objectiveData) {
        objectives.put(objective, CWCore.inst().getGson().toJson(objectiveData, ObjectiveData.class));
    }
    public void removeObjective(String objective) {
        if (hasObjective(objective)) {
            objectives.remove(objective);
        }
    }
    public HashMap<String, ObjectiveData> getObjectives() {
        HashMap<String, ObjectiveData> objectiveList = new HashMap<String, ObjectiveData>();
        for (Map.Entry<String, String> objective : objectives.entrySet()) {
            objectiveList.put(objective.getKey(), CWCore.inst().getGson().fromJson(objective.getValue(), ObjectiveData.class));
        }
        return objectiveList;
    }
    public void setObjectives(HashMap<String, ObjectiveData> objectives) {
        HashMap<String, String> objectiveList = new HashMap<String, String>();
        for (Map.Entry<String, ObjectiveData> objective : objectives.entrySet()) {
            objectiveList.put(objective.getKey(), CWCore.inst().getGson().toJson(objective.getValue(), ObjectiveData.class));
        }
        this.objectives = objectiveList;
    }


    public void setSidebarObjective(String objective) {
        SIDEBAR = objective;
    }
    public String getSidebarObjective() {
        return SIDEBAR;
    }

    public void setNameObjective(String objective) {
        BELOW_NAME = objective;
    }
    public String getNameObjective() {
        return BELOW_NAME;
    }

    public void setTablistObjective(String objective) {
        PLAYER_LIST = objective;
    }
    public String getTablistObjective() {
        return PLAYER_LIST;
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


}
