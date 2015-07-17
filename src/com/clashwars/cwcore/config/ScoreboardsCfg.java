package com.clashwars.cwcore.config;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.config.internal.EasyConfig;
import com.clashwars.cwcore.scoreboard.data.BoardData;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardsCfg extends EasyConfig {

    public HashMap<String, String> BOARDS = new HashMap<String, String>();

    public ScoreboardsCfg(String fileName) {
        this.setFile(fileName);
    }

    public boolean hasBoard(String boardID) {
        return BOARDS.containsKey(boardID);
    }

    public BoardData getBoard(String boardID) {
        if (hasBoard(boardID)) {
            return CWCore.inst().getGson().fromJson(BOARDS.get(boardID), BoardData.class);
        }
        return null;
    }

    public void setBoard(String boardID, BoardData data) {
        BOARDS.put(boardID, CWCore.inst().getGson().toJson(data, BoardData.class));
        save();
    }

    public HashMap<String, BoardData> getBoards() {
        HashMap<String, BoardData> resultMap = new HashMap<String, BoardData>();
        for (Map.Entry<String, String> entry : BOARDS.entrySet()) {
            resultMap.put(entry.getKey(), CWCore.inst().getGson().fromJson(BOARDS.get(entry.getValue()), BoardData.class));
        }
        return resultMap;
    }

    public void setBoards(HashMap<String, BoardData> boards) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        for (Map.Entry<String, BoardData> entry : boards.entrySet()) {
            resultMap.put(entry.getKey(), CWCore.inst().getGson().toJson(BOARDS.get(entry.getValue()), BoardData.class));
        }
        BOARDS = resultMap;
        save();
    }

    public void removeBoard(String boardID) {
        if (hasBoard(boardID)) {
            BOARDS.remove(boardID);
            save();
        }
    }
}
