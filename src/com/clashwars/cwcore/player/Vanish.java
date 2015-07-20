package com.clashwars.cwcore.player;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.events.PlayerLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.UUID;

public class Vanish implements Listener {

    private static CWCore cwc = CWCore.inst();

    public static void vanish(UUID playerUUID) {
        CWPlayer cwp = cwc.getPM().getPlayer(playerUUID);
        cwp.setVanished(true);
        Player player = cwp.getPlayer();
        if (player != null) {
            updateVanishedPlayers(player);
        }
    }

    public static void unvanish(UUID playerUUID) {
        CWPlayer cwp = cwc.getPM().getPlayer(playerUUID);
        cwp.setVanished(false);
        Player player = cwp.getPlayer();
        if (player != null) {
            updateVanishedPlayers(player);
        }
    }

    public static boolean isVanished(UUID playerUUID) {
        CWPlayer cwp = cwc.getPM().getPlayer(playerUUID);
        return cwp.isVanished();
    }

    @EventHandler
    private void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        updateVanishedPlayers(player);
    }

    @EventHandler
    private void onEnable(PluginEnableEvent event) {
        Collection<Player> onlinePlayers = (Collection<Player>) Bukkit.getOnlinePlayers();
        for (Player p : onlinePlayers) {
            updateVanishedPlayers(p);
        }
    }

    private static void updateVanishedPlayers(Player player) {
        boolean vanished = isVanished(player.getUniqueId());
        if (vanished) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 1), true);
        } else {
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
        Collection<Player> onlinePlayers = (Collection<Player>) Bukkit.getOnlinePlayers();
        for (Player p : onlinePlayers) {
            if (vanished && !isVanished(p.getUniqueId())) {
                p.hidePlayer(player);
            } else {
                p.showPlayer(player);
            }
            if (isVanished(p.getUniqueId()) && !vanished) {
                player.hidePlayer(p);
            } else {
                player.showPlayer(p);
            }
        }
    }
}
