package com.clashwars.cwcore.scoreboard.data;

import com.clashwars.cwcore.scoreboard.Criteria;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;

public class ObjectiveData {

    private String name = "";
    private String displayName = "";
    private Criteria criteria = Criteria.DUMMY;
    private HashMap<String, String> scores = new HashMap<String, String>();

    public ObjectiveData() {
        //-
    }

    public ObjectiveData(String name, String displayName, Criteria criteria) {
        this.name = name;
        this.displayName = displayName;
        this.criteria = criteria;
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


    public Criteria getCriteria() {
        return criteria;
    }
    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }


    public boolean hasScore(String entry) {
        return scores.containsKey(entry);
    }

    public void setScore(String entry, int value) {
        scores.put(entry, Integer.toString(value));
    }

    public int getScore(String entry) {
        if (hasScore(entry)) {
            return getScore(entry);
        }
        return -1;
    }

    public void removeScore(String entry) {
        if (hasScore(entry)) {
            scores.remove(entry);
        }
    }

    public HashMap<String, Integer> getScores() {
        HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
        for (Map.Entry<String, String> entry : scores.entrySet()) {
            resultMap.put(entry.getKey(), CWUtil.getInt(entry.getValue()));
        }
        return resultMap;
    }

    public void setScores(HashMap<String, Integer> scores) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            resultMap.put(entry.getKey(), entry.getValue().toString());
        }
        this.scores = resultMap;
    }


    public Objective getObjective(Scoreboard scoreboard, BoardData data) {
        Objective objective = scoreboard.getObjective(name);
        if (objective == null) {
            objective = scoreboard.registerNewObjective(name, criteria.getID());
        }
        objective.setDisplayName(CWUtil.integrateColor(displayName));
        HashMap<String, Integer> entries = getScores();
        for (Map.Entry<String, Integer> entry : entries.entrySet()) {
            objective.getScore(entry.getKey()).setScore(entry.getValue());
        }
        if (data.getSidebarObjective().equals(name)) {
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        if (data.getNameObjective().equals(name)) {
            scoreboard.clearSlot(DisplaySlot.BELOW_NAME);
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }
        if (data.getTablistObjective().equals(name)) {
            scoreboard.clearSlot(DisplaySlot.PLAYER_LIST);
            objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        }
        return objective;
    }
}
