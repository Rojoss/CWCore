package com.clashwars.cwcore.scoreboard;

import com.clashwars.cwcore.CWCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardListener implements Listener {

    @EventHandler
    private void login(final PlayerLoginEvent event) {
        final String scoreboard = CWCore.inst().getPM().getPlayer(event.getPlayer()).getScoreboard();
        if (scoreboard.isEmpty()) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (CWBoard.hasBoard(scoreboard, true)) {
                    CWBoard cwBoard = CWBoard.get(scoreboard);
                    if (cwBoard.isVisible() && cwBoard.hasPlayer(event.getPlayer().getUniqueId())) {
                        event.getPlayer().setScoreboard(CWBoard.get(scoreboard).getBukkitBoard());
                    } else {
                        event.getPlayer().setScoreboard(CWBoard.getEmptyBoard());
                    }
                } else {
                    event.getPlayer().setScoreboard(CWBoard.getEmptyBoard());
                }
            }
        }.runTaskLater(CWCore.inst(), 20);
    }


}
