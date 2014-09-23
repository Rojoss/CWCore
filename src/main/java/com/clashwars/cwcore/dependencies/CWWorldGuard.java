package com.clashwars.cwcore.dependencies;

import com.clashwars.cwcore.CWCore;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CWWorldGuard {

    /**
     * Get a worldgaurd region by name.
     * @param world The world where to look for the region.
     * @param name The name of the region.
     * @return ProtectedRegion
     */
    public static ProtectedRegion getRegion(World world, String name) {
        return WGBukkit.getRegionManager(world).getRegion(name);
    }

    /**
     * Set flag for a specific region.
     * @param world The world where to look for the region.
     * @param regionID The identifying name of the region.
     * @param flag The StateFlag flag.
     * @param value String value like 'deny'
     * @return true if set false if invalid region/flag.
     */
    public static boolean setFlag(World world, String regionID, StateFlag flag, String value) {
        ProtectedRegion region = WGBukkit.getRegionManager(world).getRegion(regionID);
        try {
            region.setFlag(flag, flag.parseInput(WGBukkit.getPlugin(), CWCore.inst().getServer().getConsoleSender(), value));
        } catch (InvalidFlagFormat e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Check if a player is allowed for the specified flag at his location.
     * @param player The player to check.
     * @param flag The StateFlag flag.
     * @return true if allowed false if denied.
     */
    public static boolean getFlag(Player player, StateFlag flag) {
        ApplicableRegionSet set = WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
        LocalPlayer localPlayer = WGBukkit.getPlugin().wrapPlayer(player);
        return set.allows(flag, localPlayer);
    }

    /**
     * Check if a player can PvP at his location.
     * @param player The player to check.
     * @return true if pvp is allowed false if denied.
     */
    public static boolean canPvP(Player player) {
        ApplicableRegionSet set = WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
        LocalPlayer localPlayer = WGBukkit.getPlugin().wrapPlayer(player);
        return set.allows(DefaultFlag.PVP, localPlayer);
    }


    /**
     * Fill a specific region with the specified block by using WorldEdit methods.
     * This will work for both Cuboid and Poly regions/selections.
     * @param world The world where to look for the region.
     * @param region The region
     * @param blockID The block ID used for filling the region.
     * @return true if filled false if it failed.
     */
    public static boolean regionFill(World world, ProtectedRegion region, int blockID) {
        LocalWorld localWorld = BukkitUtil.getLocalWorld(world);
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(localWorld, -1);

        Region weRegion = null;
        if (region.getTypeName().equalsIgnoreCase("cuboid")) {
            weRegion = new CuboidRegion(localWorld, region.getMinimumPoint(), region.getMaximumPoint().toBlockPoint());
        } else {
            weRegion = new Polygonal2DRegion(localWorld, region.getPoints(), region.getMinimumPoint().getBlockY(), region.getMaximumPoint().getBlockY());
        }

        try {
            editSession.setBlocks(weRegion, new BaseBlock(blockID));
        } catch (MaxChangedBlocksException e) {
            return false;
        }
        return true;
    }

    /**
     * Replace blocks in a specific region by using WorldEdit methods.
     * This will work for both Cuboid and Poly regions/selections.
     * @param world The world where to look for the region.
     * @param region The region
     * @param fromBlock The block ID that will be replaced.
     * @param toBlock The block ID used to replace the fromBlock with.
     * @return true if replaced false if it failed.
     */
    public static boolean regionReplace(World world, ProtectedRegion region, int fromBlock, int toBlock) {
        LocalWorld localWorld = BukkitUtil.getLocalWorld(world);
        Set<BaseBlock> fromBlocks = new HashSet<BaseBlock>();
        fromBlocks.add(new BaseBlock(fromBlock));
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(localWorld, -1);

        Region weRegion = null;
        if (region.getTypeName().equalsIgnoreCase("cuboid")) {
            weRegion = new CuboidRegion(localWorld, region.getMinimumPoint(), region.getMaximumPoint());

        } else {
            weRegion = new Polygonal2DRegion(localWorld, region.getPoints(), region.getMinimumPoint().getBlockY(), region.getMaximumPoint().getBlockY());

        }

        try {
            editSession.replaceBlocks(weRegion, fromBlocks, new BaseBlock(toBlock));
        } catch (MaxChangedBlocksException e) {
            return false;
        }
        return true;
    }


    /**
     * Paste a schematic at a Bukkit Location.
     * @param world The world to paste the schematic in.
     * @param file The schematic file.
     * @param location The location where to paste it.
     * @param noAir If true it wont set air blocks.
     * @param rotation The rotation to paste it in (0,90,180,270)
     * @throws MaxChangedBlocksException
     * @throws DataException
     * @throws IOException
     */
    public static void pasteSchematic(World world, File file, Location location, boolean noAir, int rotation) throws MaxChangedBlocksException, DataException, IOException {
        pasteSchematic(world, file, BukkitUtil.toVector(location), noAir, rotation);
    }

    /**
     * @see {@link : pasteSchematic}
     * @param world The world to paste the schematic in.
     * @param file The schematic file.
     * @param origin The vector where to paste it.
     * @param noAir If true it wont set air blocks.
     * @param rotation The rotation to paste it in (0,90,180,270)
     * @throws DataException
     * @throws IOException
     * @throws MaxChangedBlocksException
     */
    public static void pasteSchematic(World world, File file, Vector origin, boolean noAir, int rotation) throws DataException, IOException, MaxChangedBlocksException {
        if (rotation != 90 && rotation != 180 && rotation != 270 && rotation != 360 && rotation != 0) {
            rotation = 0;
        }
        EditSession es = new EditSession(new BukkitWorld(world), 999999999);
        CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
        cc.rotate2D(rotation);
        cc.paste(es, origin, noAir);
    }

}
