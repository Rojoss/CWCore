package com.clashwars.cwcore.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Projectile;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public class ProjectileHitBlockEvent extends BlockEvent {
    private static final HandlerList handlers = new HandlerList();

    private Projectile projectile;

    public ProjectileHitBlockEvent(Projectile projectile, Block block) {
        super(block);
        this.projectile = projectile;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
