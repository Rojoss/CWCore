package com.clashwars.cwcore.events;

import com.clashwars.cwcore.debug.Debug;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class DelayedPlayerInteractEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    protected ItemStack item;
    protected Action action;
    protected Block blockClicked;
    protected BlockFace blockFace;
    protected  boolean cancelled;

    public DelayedPlayerInteractEvent(Player who, Action action, ItemStack item, Block clickedBlock, BlockFace clickedFace) {
        super(who);
        this.action = action;
        this.item = item;
        this.blockClicked = clickedBlock;
        this.blockFace = clickedFace;
    }

    public Action getAction() {
        return this.action;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public Material getMaterial() {
        return !this.hasItem()?Material.AIR:this.item.getType();
    }

    public boolean hasBlock() {
        return this.blockClicked != null;
    }

    public boolean hasItem() {
        return this.item != null;
    }

    public boolean isBlockInHand() {
        return !this.hasItem()?false:this.item.getType().isBlock();
    }

    public Block getClickedBlock() {
        return this.blockClicked;
    }

    public BlockFace getBlockFace() {
        return this.blockFace;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
