package com.clashwars.cwcore.commands;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.cuboid.Cuboid;
import com.clashwars.cwcore.cuboid.CuboidEditor;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Commands {
    private CWCore cwc;

    public Commands(CWCore cwc) {
        this.cwc = cwc;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //Particles
        if (label.equalsIgnoreCase("pe")) {
            //Console check
            if (!(sender instanceof Player)) {
                sender.sendMessage(CWUtil.formatCWMsg("&cThis is a player command only."));
                return true;
            }
            Player player = (Player) sender;

            //Permission check.
            if (!player.isOp() && !player.hasPermission("cwcore.pe")) {
                player.sendMessage(CWUtil.formatCWMsg("&cInsuficient permissions."));
                return true;
            }

            if (args.length > 0 && args[0].equalsIgnoreCase("list")) {
                List<String> effectNames = new ArrayList<String>();
                for (ParticleEffect effect : ParticleEffect.values()) {
                    effectNames.add(effect.toString().toLowerCase().replace("_", ""));
                }
                player.sendMessage(CWUtil.formatCWMsg("&6&lEffect List&8&l: &7" + CWUtil.implode(effectNames, "&8, &7")));
                return true;
            }

            if (args.length < 6) {
                player.sendMessage(CWUtil.formatCWMsg("&cInvalid usage: &4/pe {particle|list} {xo} {yo} {zo} {speed} {amt}"));
                return true;
            }

            ParticleEffect effect = ParticleEffect.fromName(args[0]);
            if (effect == null) {
                player.sendMessage(CWUtil.formatCWMsg("&cInvalid effect specified. See &4/pe list &cfor effects."));
                return true;
            }

            float xo = CWUtil.getFloat(args[1]);
            float yo = CWUtil.getFloat(args[2]);
            float zo = CWUtil.getFloat(args[3]);

            float speed = CWUtil.getFloat(args[4]);
            int amt = CWUtil.getInt(args[5]);

            effect.display(xo, yo, zo, speed, amt, player.getLocation());
            return true;
        }



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


        if (label.equalsIgnoreCase("sel")) {
            //Console check
            if (!(sender instanceof Player)) {
                sender.sendMessage(CWUtil.formatCWMsg("&cThis is a player command only."));
                return true;
            }
            Player player = (Player) sender;

            //Permission check.
            if (!player.isOp() && !player.hasPermission("cwcore.sel")) {
                player.sendMessage(CWUtil.formatCWMsg("&cInsuficient permissions."));
                return true;
            }

            if (args.length < 1) {
                player.sendMessage(CWUtil.formatCWMsg("&cInvalid command usage."));
                player.sendMessage(CWUtil.formatCWMsg("&4Edit options: &cfill&8, &creplace&8, &cwall&8, &creplacewall"));
                return true;
            }

            Cuboid cuboid = cwc.getSel().getSelection(player);
            if (cuboid == null) {
                player.sendMessage(CWUtil.formatCWMsg("&cNo selection has been made."));
                return true;
            }
            CuboidEditor ce = new CuboidEditor(cuboid);


            if (args[0].equalsIgnoreCase("fill") || args[0].equalsIgnoreCase("set")) {
                if (args.length < 2) {
                    player.sendMessage(CWUtil.formatCWMsg("&cSpecify a material."));
                    return true;
                }
                Material mat = cwc.getMaterials().getMaterial(args[1]);
                int matData = cwc.getMaterials().getMaterialData(args[1]);
                if (mat == null) {
                    player.sendMessage("&cInvalid material specified.");
                }
                ce.fill(mat, (byte)matData);
                return true;
            }

            if (args[0].equalsIgnoreCase("replace")) {
                if (args.length < 3) {
                    player.sendMessage(CWUtil.formatCWMsg("&cSpecify 2 materials."));
                    return true;
                }
                Material mat1 = cwc.getMaterials().getMaterial(args[1]);
                Material mat2 = cwc.getMaterials().getMaterial(args[2]);
                int matData2 = cwc.getMaterials().getMaterialData(args[2]);
                if (mat1 == null || mat2 == null) {
                    player.sendMessage("&cInvalid material(s) specified.");
                }
                ce.replace(mat1, mat2, (byte)matData2);
                return true;
            }

            if (args[0].equalsIgnoreCase("walls")) {
                if (args.length < 2) {
                    player.sendMessage(CWUtil.formatCWMsg("&cSpecify a material."));
                    return true;
                }
                Material mat = cwc.getMaterials().getMaterial(args[1]);
                int matData = cwc.getMaterials().getMaterialData(args[1]);
                if (mat == null) {
                    player.sendMessage("&cInvalid material specified.");
                }
                ce.walls(mat, (byte)matData, args.length > 2 ? true : false);
                return true;
            }

            if (args[0].equalsIgnoreCase("replacewalls")) {
                if (args.length < 3) {
                    player.sendMessage(CWUtil.formatCWMsg("&cSpecify 2 materials."));
                    return true;
                }
                Material mat1 = cwc.getMaterials().getMaterial(args[1]);
                Material mat2 = cwc.getMaterials().getMaterial(args[2]);
                int matData2 = cwc.getMaterials().getMaterialData(args[2]);
                if (mat1 == null || mat2 == null) {
                    player.sendMessage("&cInvalid material(s) specified.");
                }
                ce.replacewalls(mat1, mat2, (byte)matData2, args.length > 3 ? true : false);
                return true;
            }
            return true;
        }
        return false;
    }
}
