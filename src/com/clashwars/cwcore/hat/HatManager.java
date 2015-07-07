package com.clashwars.cwcore.hat;

import com.clashwars.cwcore.CWCore;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class HatManager implements Listener {

    private static HashMap<UUID, Hat> hats = new HashMap<UUID, Hat>();


    public HatManager() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Hat hat : hats.values()) {
                    if (hat.isEquipped()) {
                        hat.getStand().eject();
                        Location playerLoc = hat.getOwner().getLocation().clone();
                        hat.getStand().teleport(playerLoc.add(hat.getOffset()));
                        hat.getStand().setVelocity(hat.getOwner().getVelocity());
                        if (hat.getHatItem() != null) {
                            hat.getStand().setPassenger(hat.getHatItem());
                        } else if (hat.getEntity() != null) {
                            hat.getStand().setPassenger(hat.getEntity().entity());
                        }
                    }
                }
            }
        }.runTaskTimer(CWCore.inst(), 0, 1);
    }

    public static boolean hasHat(Player player) {
        if (hats.containsKey(player.getUniqueId())) {
            return true;
        }
        return false;
    }

    public static Hat getHat(Player player) {
        if (hats.containsKey(player.getUniqueId())) {
            return hats.get(player.getUniqueId());
        }
        return null;
    }

    public static HashMap<UUID, Hat> getHats() {
        return hats;
    }

    public static void addHat(Player player, Hat hat) {
        hats.put(player.getUniqueId(), hat);
    }

    public static void removeHat(Player player) {
        if (hats.containsKey(player.getUniqueId())) {
            hats.get(player.getUniqueId()).unequip();
            hats.remove(player.getUniqueId());
        }
    }



    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        if (hats.containsKey(event.getPlayer().getUniqueId())) {
            hats.get(event.getPlayer().getUniqueId()).equip();
        }
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent event) {
        if (hats.containsKey(event.getPlayer().getUniqueId())) {
            hats.get(event.getPlayer().getUniqueId()).unequip();
        }
    }

    @EventHandler
    private void onKick(PlayerKickEvent event) {
        if (hats.containsKey(event.getPlayer().getUniqueId())) {
            hats.get(event.getPlayer().getUniqueId()).unequip();
        }
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        if (hats.containsKey(event.getEntity().getUniqueId())) {
            hats.get(event.getEntity().getUniqueId()).unequip();
        }
    }

    @EventHandler
    private void onRespawn(final PlayerRespawnEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (hats.containsKey(event.getPlayer().getUniqueId())) {
                    hats.get(event.getPlayer().getUniqueId()).equip();
                }
            }
        }.runTaskLater(CWCore.inst(), 10);
    }

    @EventHandler
    private void itemDespawn(ItemDespawnEvent event) {
        if (event.getEntity().hasMetadata("status-icon")) {
            event.setCancelled(true);
        }
    }

}
