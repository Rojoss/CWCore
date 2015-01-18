package com.clashwars.cwcore.scoreboard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class ScoreboardListener implements Listener {

    @EventHandler
    private void login(PlayerLoginEvent event) {
        CWBoard board = CWBoard.activeBoard;
        if (board != null && board.isVisible()) {
            if (board.isGlobal()) {
                CWBoard.prevBoards.put(event.getPlayer().getUniqueId(), event.getPlayer().getScoreboard());
                event.getPlayer().setScoreboard(board.getBoard());
            }
        }
        for (CWBoard cwb : CWBoard.boards.values()) {
            if (!cwb.isGlobal()) {
                if (cwb.canSee(event.getPlayer())) {
                    CWBoard.prevBoards.put(event.getPlayer().getUniqueId(), event.getPlayer().getScoreboard());
                    event.getPlayer().setScoreboard(cwb.getBoard());
                }
            }
        }
    }


}
