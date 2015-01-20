package com.clashwars.cwcore.commands;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.cuboid.Cuboid;
import com.clashwars.cwcore.cuboid.CuboidEditor;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
                sender.sendMessage(CWUtil.formatARMsg("&cThis is a player command only."));
                return true;
            }
            Player player = (Player) sender;

            //Permission check.
            if (!player.isOp() && !player.hasPermission("cwcore.pe")) {
                player.sendMessage(CWUtil.formatARMsg("&cInsuficient permissions."));
                return true;
            }

            if (args.length > 0 && args[0].equalsIgnoreCase("list")) {
                List<String> effectNames = new ArrayList<String>();
                for (ParticleEffect effect : ParticleEffect.values()) {
                    effectNames.add(effect.toString().toLowerCase().replace("_", ""));
                }
                player.sendMessage(CWUtil.formatARMsg("&6&lEffect List&8&l: &7" + CWUtil.implode(effectNames, "&8, &7")));
                return true;
            }

            if (args.length < 6) {
                player.sendMessage(CWUtil.formatARMsg("&cInvalid usage: &4/pe {particle|list} {xo} {yo} {zo} {speed} {amt}"));
                return true;
            }

            ParticleEffect effect = ParticleEffect.fromName(args[0]);
            if (effect == null) {
                player.sendMessage(CWUtil.formatARMsg("&cInvalid effect specified. See &4/pe list &cfor effects."));
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

        //Sounds
        if (label.equalsIgnoreCase("sound") || label.equalsIgnoreCase("so")) {
            //Console check
            if (!(sender instanceof Player)) {
                sender.sendMessage(CWUtil.formatARMsg("&cThis is a player command only."));
                return true;
            }
            Player player = (Player)sender;

            //Permission check.
            if (!player.isOp() && !player.hasPermission("cwcore.sound")) {
                player.sendMessage(CWUtil.formatARMsg("&cInsuficient permissions."));
                return true;
            }

            if (args.length > 0 && args[0].equalsIgnoreCase("list")) {
                List<String> soundNames = new ArrayList<String>();
                for (Sound sound : Sound.values()) {
                    soundNames.add(sound.toString().toLowerCase().replace("_", ""));
                }
                player.sendMessage(CWUtil.formatARMsg("&6&lSound List&8&l: &7" + CWUtil.implode(soundNames, "&8, &7")));
                return true;
            }

            if (args.length < 1) {
                player.sendMessage(CWUtil.formatARMsg("&cInvalid usage: &4/sound {sound|list} [volume] [pitch]"));
                return true;
            }

            Sound sound = cwc.getSounds().getSound(args[0]);
            if (sound == null) {
                player.sendMessage(CWUtil.formatARMsg("&cInvalid sound specified. See &4/sound list &cfor all sounds."));
                return true;
            }

            float volume = 1;
            if (args.length > 1) {
                volume = CWUtil.getInt(args[1]);
                if (volume < 0) {
                    volume = 1;
                }
            }
            final float volumeFinal = volume;

            float pitch = 1;
            if (args.length > 2) {
                pitch = CWUtil.getInt(args[2]);
                if (pitch < 0) {
                    volume = 1;
                }
            }

            player.playSound(player.getLocation(), sound, volume, pitch);
            return true;
        }

        //Soundcheck (test in all 3 pitches)
        if (label.equalsIgnoreCase("soundcheck") || label.equalsIgnoreCase("sc") || label.equalsIgnoreCase("soc")) {
            //Console check
            if (!(sender instanceof Player)) {
                sender.sendMessage(CWUtil.formatARMsg("&cThis is a player command only."));
                return true;
            }
            final Player player = (Player)sender;

            //Permission check.
            if (!player.isOp() && !player.hasPermission("cwcore.sound")) {
                player.sendMessage(CWUtil.formatARMsg("&cInsuficient permissions."));
                return true;
            }

            if (args.length > 0 && args[0].equalsIgnoreCase("list")) {
                List<String> soundNames = new ArrayList<String>();
                for (Sound sound : Sound.values()) {
                    soundNames.add(sound.toString().toLowerCase().replace("_", ""));
                }
                player.sendMessage(CWUtil.formatARMsg("&6&lSound List&8&l: &7" + CWUtil.implode(soundNames, "&8, &7")));
                return true;
            }

            if (args.length < 1) {
                player.sendMessage(CWUtil.formatARMsg("&cInvalid usage: &4/soundcheck {sound|list} [delay]"));
                return true;
            }

            final Sound sound = cwc.getSounds().getSound(args[0]);
            if (sound == null) {
                player.sendMessage(CWUtil.formatARMsg("&cInvalid sound specified. See &4/soundcheck list &cfor all sounds."));
                return true;
            }

            final int delay = args.length > 1 ? CWUtil.getInt(args[1]) : 20;
            player.playSound(player.getLocation(), sound, 1, 0);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.playSound(player.getLocation(), sound, 1, 1);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.playSound(player.getLocation(), sound, 1, 2);
                        }
                    }.runTaskLater(cwc, delay);
                }
            }.runTaskLater(cwc, delay);
            return true;
        }



            // Wand command.
        if (label.equalsIgnoreCase("cww") || label.equalsIgnoreCase("cwwand") || label.equalsIgnoreCase("arw") || label.equalsIgnoreCase("arwand")) {
            //Console check
            if (!(sender instanceof Player)) {
                sender.sendMessage(CWUtil.formatARMsg("&cThis is a player command only."));
                return true;
            }
            Player player = (Player) sender;

            //Permission check.
            if (!player.isOp() && !player.hasPermission("cwcore.wand")) {
                player.sendMessage(CWUtil.formatARMsg("&cInsuficient permissions."));
                return true;
            }

            cwc.getSel().getWand().giveToPlayer(player);
            player.sendMessage(CWUtil.formatARMsg("&6Wand given."));
            return true;
        }

        // P1 / P2 Command
        if (label.equalsIgnoreCase("p1") || label.equalsIgnoreCase("l1") || label.equalsIgnoreCase("p2") || label.equalsIgnoreCase("l2")) {
            //Console check
            if (!(sender instanceof Player)) {
                sender.sendMessage(CWUtil.formatARMsg("&cThis is a player command only."));
                return true;
            }
            Player player = (Player) sender;

            //Permission check.
            if (!player.isOp() && !player.hasPermission("cwcore.wand")) {
                player.sendMessage(CWUtil.formatARMsg("&cInsuficient permissions."));
                return true;
            }

            Location loc = player.getLocation();
            if (label.equalsIgnoreCase("p1") || label.equalsIgnoreCase("l1")) {
                cwc.getSel().setPos1(player, loc);
                player.sendMessage(CWUtil.formatARMsg("&5Pos1 &6selected! &8(&7" + loc.getBlockX() + "&8, &7" + loc.getBlockY() + "&8, &7" + loc.getBlockZ() + "&8)"));
            } else {
                cwc.getSel().setPos2(player, loc);
                player.sendMessage(CWUtil.formatARMsg("&5Pos2 &6selected! &8(&7" + loc.getBlockX() + "&8, &7" + loc.getBlockY() + "&8, &7" + loc.getBlockZ() + "&8)"));
            }
        }


        if (label.equalsIgnoreCase("sel")) {
            //Console check
            if (!(sender instanceof Player)) {
                sender.sendMessage(CWUtil.formatARMsg("&cThis is a player command only."));
                return true;
            }
            Player player = (Player) sender;

            //Permission check.
            if (!player.isOp() && !player.hasPermission("cwcore.sel")) {
                player.sendMessage(CWUtil.formatARMsg("&cInsuficient permissions."));
                return true;
            }

            if (args.length < 1) {
                player.sendMessage(CWUtil.formatARMsg("&cInvalid command usage."));
                player.sendMessage(CWUtil.formatARMsg("&4Edit options: &cfill&8, &creplace&8, &cwall&8, &creplacewall"));
                return true;
            }

            Cuboid cuboid = cwc.getSel().getSelection(player);
            if (cuboid == null) {
                player.sendMessage(CWUtil.formatARMsg("&cNo selection has been made."));
                return true;
            }
            CuboidEditor ce = new CuboidEditor(cuboid);


            if (args[0].equalsIgnoreCase("fill") || args[0].equalsIgnoreCase("set")) {
                if (args.length < 2) {
                    player.sendMessage(CWUtil.formatARMsg("&cSpecify a material."));
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
                    player.sendMessage(CWUtil.formatARMsg("&cSpecify 2 materials."));
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
                    player.sendMessage(CWUtil.formatARMsg("&cSpecify a material."));
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
                    player.sendMessage(CWUtil.formatARMsg("&cSpecify 2 materials."));
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
