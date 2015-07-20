package com.clashwars.cwcore.player;

import com.clashwars.cwcore.CWCore;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Freeze implements Listener {

    private static CWCore cwc = CWCore.inst();

    static {
        new BukkitRunnable() {
            @Override
            public void run() {
                Collection<Player> onlinePlayers = (Collection<Player>)cwc.getServer().getOnlinePlayers();
                for (Player player : onlinePlayers) {
                    if (player == null) {
                        continue;
                    }
                    CWPlayer cwp = cwc.getPM().getPlayer(player);
                    if (cwp.isFrozen()) {
                        Location freezeLoc = cwp.getFreezeLoc();
                        if (player.getLocation().getBlockX() != freezeLoc.getBlockX() || player.getLocation().getBlockY() != freezeLoc.getBlockY() || player.getLocation().getBlockZ() != freezeLoc.getBlockZ()) {
                            freezeLoc.setYaw(player.getLocation().getYaw());
                            freezeLoc.setPitch(player.getLocation().getPitch());
                            player.teleport(freezeLoc);
                        }
                    }
                }
            }
        }.runTaskTimer(cwc, 0, 5);
    }

    public static void freeze(UUID playerUUID, Location location) {
        CWPlayer cwp = cwc.getPM().getPlayer(playerUUID);
        cwp.setFrozen(true);
        cwp.setFreezeLoc(location);
    }

    public static void unfreeze(UUID playerUUID) {
        CWPlayer cwp = cwc.getPM().getPlayer(playerUUID);
        cwp.setFrozen(false);
        cwp.setFreezeLoc(null);
    }

    public static boolean isFrozen(UUID playerUUID) {
        return cwc.getPM().getPlayer(playerUUID).isFrozen();
    }

    public static Location getFreezeLocation(UUID playerUUID) {
        return cwc.getPM().getPlayer(playerUUID).getFreezeLoc();
    }

    @EventHandler
    private void playerJoin(PlayerJoinEvent event) {
        //Set freeze location if a offline player was frozen.
        CWPlayer cwp = cwc.getPM().getPlayer(event.getPlayer());
        if (cwp.getData().isFrozen() && cwp.getFreezeLoc() == null) {
            cwp.setFreezeLoc(event.getPlayer().getLocation());
        }
    }

}
