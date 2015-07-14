package com.clashwars.cwcore.player;

import com.clashwars.cwcore.CWCore;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class Freeze {

    private static CWCore cwc = CWCore.inst();

    static {
        new BukkitRunnable() {
            @Override
            public void run() {
                List<UUID> frozenPlayers = cwc.getPlayerCfg().getFrozenPlayers();
                for (UUID uuid : frozenPlayers) {
                    Player player = cwc.getServer().getPlayer(uuid);
                    if (player == null) {
                        continue;
                    }
                    Location freezeLoc = getFreezeLocation(uuid);
                    if (player.getLocation().getBlockX() != freezeLoc.getBlockX() || player.getLocation().getBlockY() != freezeLoc.getBlockY() || player.getLocation().getBlockZ() != freezeLoc.getBlockZ()) {
                        freezeLoc.setYaw(player.getLocation().getYaw());
                        freezeLoc.setPitch(player.getLocation().getPitch());
                        player.teleport(freezeLoc);
                    }
                }
            }
        }.runTaskTimer(cwc, 0, 3);
    }

    public static void freeze(UUID playerUUID, Location location) {
        cwc.getPlayerCfg().freeze(playerUUID, location);
    }

    public static void unfreeze(UUID playerUUID) {
        cwc.getPlayerCfg().unfreeze(playerUUID);
    }

    public static boolean ifFrozen(UUID playerUUID) {
        return cwc.getPlayerCfg().isVanished(playerUUID);
    }

    public static Location getFreezeLocation(UUID playerUUID) {
        return cwc.getPlayerCfg().getFreezeLocation(playerUUID);
    }

}
