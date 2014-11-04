package com.clashwars.cwcore.commands;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands {
    private CWCore cwc;

    public Commands(CWCore cwc) {
        this.cwc = cwc;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Wand command.
        if (label.equalsIgnoreCase("cww") || label.equalsIgnoreCase("cwwand")) {
            //Console check
            if (!(sender instanceof Player)) {
                sender.sendMessage(CWUtil.formatCWMsg("&cThis is a player command only."));
                return true;
            }
            Player player = (Player) sender;

            //Permission check.
            if (!player.isOp() && !player.hasPermission("cwcore.wand")) {
                player.sendMessage(CWUtil.formatCWMsg("&cInsuficient permissions."));
                return true;
            }

            cwc.getSel().getWand().giveToPlayer(player);
            player.sendMessage(CWUtil.formatCWMsg("&6Wand given."));
            return true;
        }

        // P1 / P2 Command
        if (label.equalsIgnoreCase("p1") || label.equalsIgnoreCase("l1") || label.equalsIgnoreCase("p2") || label.equalsIgnoreCase("l2")) {
            //Console check
            if (!(sender instanceof Player)) {
                sender.sendMessage(CWUtil.formatCWMsg("&cThis is a player command only."));
                return true;
            }
            Player player = (Player) sender;

            //Permission check.
            if (!player.isOp() && !player.hasPermission("cwcore.wand")) {
                player.sendMessage(CWUtil.formatCWMsg("&cInsuficient permissions."));
                return true;
            }

            Location loc = player.getLocation();
            if (label.equalsIgnoreCase("p1") || label.equalsIgnoreCase("l1")) {
                cwc.getSel().setPos1(player, loc);
                player.sendMessage(CWUtil.formatCWMsg("&5Pos1 &6selected! &8(&7" + loc.getBlockX() + "&8, &7" + loc.getBlockY() + "&8, &7" + loc.getBlockZ() + "&8)"));
            } else {
                cwc.getSel().setPos2(player, loc);
                player.sendMessage(CWUtil.formatCWMsg("&5Pos2 &6selected! &8(&7" + loc.getBlockX() + "&8, &7" + loc.getBlockY() + "&8, &7" + loc.getBlockZ() + "&8)"));
            }
        }
        return false;
    }
}
