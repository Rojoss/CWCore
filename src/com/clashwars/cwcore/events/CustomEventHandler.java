package com.clashwars.cwcore.events;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.CooldownManager;
import com.clashwars.cwcore.debug.Debug;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R2.entity.AbstractProjectile;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftArrow;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftProjectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.BlockIterator;

import java.lang.reflect.Field;

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


    @EventHandler
    private void onProjectileHit(final ProjectileHitEvent event) {
        BlockIterator iterator = new BlockIterator(event.getEntity().getWorld(), event.getEntity().getLocation().toVector(), event.getEntity().getVelocity().normalize(), 0.0D, 4);
        Block hitBlock = null;

        while (iterator.hasNext()) {
            hitBlock = iterator.next();

            if (hitBlock.getTypeId() != 0) {
                break;
            }
        }
        Bukkit.getServer().getPluginManager().callEvent(new ProjectileHitBlockEvent(event.getEntity(), hitBlock));
    }
}
