package com.clashwars.cwcore.commands;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.helpers.CWEntity;
import com.clashwars.cwcore.helpers.CWItem;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;

public class Commands {
    private CWCore cwc;

    public Commands(CWCore cwc) {
        this.cwc = cwc;
    }


    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //TEST COMMAND
        if (label.equalsIgnoreCase("cwtest")) {
            //Console check
            if (!(sender instanceof Player)) {
                sender.sendMessage(CWUtil.formatMsg("&cThis is a player command only."));
                return true;
            }
            Player player = (Player) sender;

            //Permission check.
            if (!player.isOp() && !player.hasPermission("cwcore.testcmd")) {
                player.sendMessage(CWUtil.formatMsg("&cInsuficient permissions."));
                return true;
            }

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("item1")) {
                    new CWItem(Material.SKULL_ITEM, 3).setSkullOwner("worstboy32").setName("&9TEST1").addLore("&5Added lore...").setLore(5, "&2Line 5 lore.").giveToPlayer(player);
                    return true;
                }
                if (args[0].equalsIgnoreCase("item2")) {
                    new CWItem(Material.LEATHER_HELMET, 100).setName("&9TEST2").setSkullOwner("worstboy32").setLeatherColor(50, 100, 250).addLore("&5Added lore...").setLore(5, "&2Line 5 lore.").giveToPlayer(player);
                    return true;
                }
                if (args[0].equalsIgnoreCase("item3")) {
                    new CWItem(Material.DIRT, 1).setName("&9TEST1").makeGlowing().giveToPlayer(player);
                    return true;
                }
                if (args[0].equalsIgnoreCase("rename")) {
                    player.setItemInHand(new CWItem(player.getItemInHand()).setName("&6Renamed..."));
                    return true;
                }
                if (args[0].equalsIgnoreCase("e1")) {
                    CWEntity.create(EntityType.SHEEP, player.getLocation()).setDyeColor(DyeColor.LIME).setBaby(true).setHand(new CWItem(Material.DIAMOND_PICKAXE));
                    return true;
                }
                if (args[0].equalsIgnoreCase("e2")) {
                    CWEntity entity = CWEntity.create(EntityType.OCELOT, player.getLocation()).setCatType(Ocelot.Type.RED_CAT).setBaby(true).setHand(new CWItem(Material.DIAMOND_PICKAXE)).setSitting(true);
                    entity.entity().setPassenger(player);
                    return true;
                }
                if (args[0].equalsIgnoreCase("e3")) {
                    CWEntity.create(EntityType.WITHER_SKULL, player.getLocation()).setPowered(true).setVelocity(player.getVelocity());
                    return true;
                }
            }
            player.sendMessage(CWUtil.integrateColor("&6Test command aliases&8: &5item1, item2, item3, rename"));
            return true;
        }
        return false;
    }
}
