package com.clashwars.cwcore.cuboid;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.helpers.CWItem;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SelectionListener implements Listener {

    private Selection sel;
    private CWItem wand;

    public SelectionListener(CWCore cwc) {
        sel = cwc.getSel();
        wand = sel.getWand();
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()  && !player.hasPermission("cwcore.sel")) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        //Compare item with wands.
        ItemStack item = event.getItem();
        if (item.getType() != wand.getType()) {
            return;
        }
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
        }
        if (!item.getItemMeta().getDisplayName().equalsIgnoreCase(wand.getItemMeta().getDisplayName())) {
            return;
        }

        //Select
        event.setCancelled(true);
        Location loc = event.getClickedBlock().getLocation();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            sel.setPos1(player, loc);
            player.sendMessage(CWUtil.formatCWMsg("&5Pos1 &6selected! &8(&7" + loc.getBlockX() + "&8, &7" + loc.getBlockY() + "&8, &7" + loc.getBlockZ() + "&8)"));
        } else {
            sel.setPos2(player, loc);
            player.sendMessage(CWUtil.formatCWMsg("&5Pos2 &6selected! &8(&7" + loc.getBlockX() + "&8, &7" + loc.getBlockY() + "&8, &7" + loc.getBlockZ() + "&8)"));
        }
    }

}
