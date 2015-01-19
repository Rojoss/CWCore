package com.clashwars.cwcore.scoreboard;

import com.clashwars.cwcore.CWCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardListener implements Listener {

    @EventHandler
    private void login(final PlayerLoginEvent event) {
        final CWBoard board = CWBoard.activeBoard;
        if (board != null && board.isVisible()) {
            if (board.isGlobal()) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        CWBoard.prevBoards.put(event.getPlayer().getUniqueId(), event.getPlayer().getScoreboard());
                        event.getPlayer().setScoreboard(board.getBoard());
                    }
                }.runTaskLater(CWCore.inst(), 40);
            }
        }
        for (final CWBoard cwb : CWBoard.boards.values()) {
            if (!cwb.isGlobal()) {
                if (cwb.canSee(event.getPlayer())) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            CWBoard.prevBoards.put(event.getPlayer().getUniqueId(), event.getPlayer().getScoreboard());
                            event.getPlayer().setScoreboard(cwb.getBoard());
                        }
                    }.runTaskLater(CWCore.inst(), 40);
                }
            }
        }
    }


}
