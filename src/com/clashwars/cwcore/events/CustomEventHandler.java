package com.clashwars.cwcore.events;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.CooldownManager;
import com.clashwars.cwcore.debug.Debug;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class CustomEventHandler implements Listener {

    private CWCore cwc;
    private long interactDelay = 200l;

    public CustomEventHandler(CWCore cwc) {
        this.cwc = cwc;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onInteract(PlayerInteractEvent event) {
        if (CooldownManager.interactCooldown(event.getPlayer().getUniqueId(), interactDelay)) {
            return;
        }

        DelayedPlayerInteractEvent newEvent = new DelayedPlayerInteractEvent(event.getPlayer(), event.getAction(), event.getItem(), event.getClickedBlock(), event.getBlockFace());
        cwc.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onEntityInteract(PlayerInteractEntityEvent event) {
        if (CooldownManager.interactCooldown(event.getPlayer().getUniqueId(), interactDelay)) {
            return;
        }

        DelayedEntityInteractEvent newEvent = new DelayedEntityInteractEvent(event.getPlayer(), event.getRightClicked());
        cwc.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    private void playerLeave(PlayerQuitEvent event) {
        PlayerLeaveEvent newEvent = new PlayerLeaveEvent(event.getPlayer(), event.getQuitMessage(), false, "");
        cwc.getServer().getPluginManager().callEvent(newEvent);
    }

    @EventHandler
    private void playerKick(PlayerKickEvent event) {
        PlayerLeaveEvent newEvent = new PlayerLeaveEvent(event.getPlayer(), event.getLeaveMessage(), true, event.getReason());
        cwc.getServer().getPluginManager().callEvent(newEvent);
    }


}
