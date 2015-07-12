package com.clashwars.cwcore.cuboid;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.helpers.CWItem;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

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
        if (item  == null || item.getType() != wand.getType()) {
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
            if (!loc.equals(sel.getPos1(player))) {
                sel.setPos1(player, loc);
                player.sendMessage(CWUtil.formatCWMsg("&5Pos1 &6selected! &8(&4" + loc.getBlockX() + "&8, &4" + loc.getBlockY() + "&8, &4" + loc.getBlockZ() + " &8- &7"
                        + event.getClickedBlock().getType().toString() + "&8:&e" + event.getClickedBlock().getData() + "&8)"));

                Vector halfBlock = new Vector(0.5f, 0.5f, 0.5f);
                if (sel.getStatus(player) == SelectionStatus.BOTH) {
                    Cuboid cuboid = new Cuboid(sel.getPos1(player), sel.getPos2(player));
                    for (org.bukkit.util.Vector vector : cuboid.getEdgeVectors()) {
                        ParticleEffect.CLOUD.display(0.2f, 0.2f, 0.2f, 0f, 5, vector.add(halfBlock).toLocation(player.getWorld()), 300);
                    }
                }
            }
        } else {
            if (!loc.equals(sel.getPos2(player))) {
                sel.setPos2(player, loc);
                player.sendMessage(CWUtil.formatCWMsg("&5Pos2 &6selected! &8(&4" + loc.getBlockX() + "&8, &4" + loc.getBlockY() + "&8, &4" + loc.getBlockZ() + " &8- &7"
                        + event.getClickedBlock().getType().toString() + "&8:&e" + event.getClickedBlock().getData() + "&8)"));

                Vector halfBlock = new Vector(0.5f, 0.5f, 0.5f);
                if (sel.getStatus(player) == SelectionStatus.BOTH) {
                    Cuboid cuboid = new Cuboid(sel.getPos1(player), sel.getPos2(player));
                    for (org.bukkit.util.Vector vector : cuboid.getEdgeVectors()) {
                        ParticleEffect.CLOUD.display(0.2f, 0.2f, 0.2f, 0f, 5, vector.add(halfBlock).toLocation(player.getWorld()), 300);
                    }
                }
            }
        }
    }

}
