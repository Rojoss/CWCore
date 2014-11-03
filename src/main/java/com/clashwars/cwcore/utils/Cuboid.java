package com.clashwars.cwcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class Cuboid implements Iterable<Block>, Cloneable {

    private String worldName;

    private int minX;
    private int minY;
    private int minZ;

    private int maxX;
    private int maxY;
    private int maxZ;


    public Cuboid(Cuboid cuboid) {
        this(cuboid.getWorld(), cuboid.minX, cuboid.minY, cuboid.minZ, cuboid.maxX, cuboid.maxY, cuboid.maxZ);
    }

    public Cuboid(Location loc1, Location loc2) {
        this(loc1.getWorld(), loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
    }

    public Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this(world == null ? "" : world.getName(), x1, y1, z1, x2, y2, z2);
    }

    public Cuboid(World world, Vector loc1, Vector loc2) {
        this(world == null ? "" : world.getName(), loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
    }

    public Cuboid(World world, Vector loc, int width, int height, int length) {
        this(world == null ? "" : world.getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getBlockX() + width, loc.getBlockY() + height, loc.getBlockZ() + length);
    }

    public Cuboid(Location loc, int width, int height, int length) {
        this(loc.getWorld() == null ? "" : loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getBlockX() + width, loc.getBlockY() + height, loc.getBlockZ() + length);
    }

    public Cuboid(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.worldName = worldName;
        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);
        minZ = Math.min(z1, z2);
        maxX = Math.max(x1, x2);
        maxY = Math.max(y1, y2);
        maxZ = Math.max(z1, z2);
    }

    public Cuboid(Map<String, Object> map) {
        worldName = (String)map.get("world");
        minX = (Integer)map.get("minX");
        minY = (Integer)map.get("minY");
        minZ = (Integer)map.get("minZ");
        maxX = (Integer)map.get("maxX");
        maxY = (Integer)map.get("maxY");
        maxZ = (Integer)map.get("maxZ");
    }



    public boolean contains(Block block) {
        return contains(block.getLocation());
    }

    public boolean contains(Player player) {
        return contains(player.getLocation());
    }

    public boolean contains(Location location) {
        return contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public boolean contains(int locX, int locY, int locZ) {
        return locX >= minX && locX <= maxX && locY >= minY && locY <= maxY && locZ >= minZ && locZ <= maxZ;
    }



    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMinZ() {
        return minZ;
    }

    public void setMinZ(int minZ) {
        this.minZ = minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(int maxZ) {
        this.maxZ = maxZ;
    }



    public void setMin(Location min) {
        setMin(min.getBlockX(), min.getBlockY(), min.getBlockZ());
    }

    public void setMin(Vector min) {
        setMin(min.getBlockX(), min.getBlockY(), min.getBlockZ());
    }

    public void setMin(int minX, int minY, int minZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
    }

    public void setMax(Location max) {
        setMin(max.getBlockX(), max.getBlockY(), max.getBlockZ());
    }

    public void setMax(Vector max) {
        setMin(max.getBlockX(), max.getBlockY(), max.getBlockZ());
    }

    public void setMax(int maxX, int maxY, int maxZ) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }



    public int getVolume() {
        return getWidth() * getHeight() * getLength();
    }

    public int getWidth() {
        return this.maxX - this.minX + 1;
    }

    public int getHeight() {
        return this.maxY - this.minY + 1;
    }

    public int getLength() {
        return this.maxZ - this.minZ + 1;
    }



    public Location getMinLoc() {
        return new Location(getWorld(), minX, minY, minZ);
    }

    public Location getMaxLoc() {
        return new Location(getWorld(), maxY, maxY, maxZ);
    }

    public Location getCenterLoc() {
        Vector centerVector = getCenterVector();
        return new Location(getWorld(), centerVector.getX(), centerVector.getY(), centerVector.getZ());
    }


    public Vector getMinVector() {
        return new Vector(minX, minY, minZ);
    }

    public Vector getMaxVector() {
        return new Vector(maxY, maxY, maxZ);
    }

    public Vector getCenterVector() {
        return new Vector(minX + (getWidth() / 2), minY + (getHeight() /2), minZ + (getLength() / 2));
    }



    public void offset(Vector offset) {
        offset(offset.getBlockX(), offset.getBlockY(), offset.getBlockZ());
    }

    public void offset(int width, int height, int length) {
        minX -= width;
        minY -= height;
        minZ -= length;
        maxX += width;
        maxY += height;
        maxZ += length;
    }

    public void inset(Vector inset) {
        offset(-inset.getBlockX(), -inset.getBlockY(), -inset.getBlockZ());
    }

    public void inset(int width, int height, int length) {
        offset(-width, -height, -length);
    }



    public void expand(Dir dir, int amt) {
        switch (dir) {
            case X:
                minX -= amt;
                maxX += amt;
                break;
            case Y:
                minY -= amt;
                maxY += amt;
                break;
            case Z:
                minZ -= amt;
                maxZ += amt;
                break;
            case UP:
                maxY += amt;
                break;
            case DOWN:
                minY -= amt;
                break;
            case NORTH:
                minX += amt;
                break;
            case EAST:
                minZ -= amt;
                break;
            case SOUTH:
                maxX += amt;
                break;
            case WEST:
                maxZ += amt;
                break;
        }
    }

    public void contract(Dir dir, int amt) {
        expand(dir, -amt);
    }



    public Block getRelativeBlock(int x, int y, int z) {
        return getWorld().getBlockAt(minX + x, minY + y, minZ + z);
    }

    public Block getRelativeBlock(Vector offset) {
        return getWorld().getBlockAt(minX + offset.getBlockX(), minY + offset.getBlockY(), minZ + offset.getBlockZ());
    }



    public List<Block> getBlocks() {
        return getBlocks(new Material[] {});
    }

    public List<Block> getBlocks(Material[] filter) {
        List<Material> blockFilter = Arrays.asList(filter);
        List<Block> blockList = new ArrayList<Block>();
        World world = getWorld();
        if (world != null) {
            Block block;
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= this.maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        block = world.getBlockAt(x, y, z);
                        if (blockFilter.isEmpty() || blockFilter.contains(block.getType())) {
                            blockList.add(block);
                        }
                    }
                }
            }
        }
        return blockList;
    }

    public List<Vector> getVectors() {
        return getVectors(new Material[]{});
    }

    public List<Vector> getVectors(Material[] filter) {
        List<Material> blockFilter = Arrays.asList(filter);
        List<Vector> vectorList = new ArrayList<Vector>();
        World world = getWorld();
        if (world != null) {
            Block block;
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= this.maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        block = world.getBlockAt(x, y, z);
                        if (blockFilter.isEmpty() || blockFilter.contains(block.getType())) {
                            vectorList.add(new Vector(x, y, z));
                        }
                    }
                }
            }
        }
        return vectorList;
    }

    public List<Player> getPlayers() {
        List<Player> playerList = new ArrayList<Player>();
        World world = getWorld();
        if (world != null) {
            for (Player player : world.getPlayers()) {
                if (contains(player.getLocation())) {
                    playerList.add(player);
                }
            }
        }
        return playerList;
    }



    public World getWorld() {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            throw new IllegalStateException("'" + worldName + "' is not loaded or not a valid world.");
        }
        return world;
    }

    public void setWorld(World world) {
        if (world != null) {
            worldName = world.getName();
        }
    }



    @Override
    public Cuboid clone() {
        return new Cuboid(this);
    }

    @Override
    public Iterator<Block> iterator() {
        return this.getBlocks().listIterator();
    }

    @Override
    public String toString() {
        return worldName + "," + minX + "," + minY + "," + minZ + "," + maxX + "," + maxY + "," + maxZ;
    }



    public static Cuboid deserialize(String cuboidString) {
        try {
            String[] cuboidSplit = cuboidString.split(",");

            String worldName = cuboidSplit[0];
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                return null;
            }

            int x1 = Integer.parseInt(cuboidSplit[1]), y1 = Integer.parseInt(cuboidSplit[2]), z1 = Integer.parseInt(cuboidSplit[3]);
            int x2 = Integer.parseInt(cuboidSplit[4]), y2 = Integer.parseInt(cuboidSplit[5]), z2 = Integer.parseInt(cuboidSplit[6]);

            Location loc1 = new Location(world, x1, y1, y2);
            Location loc2 = new Location(world, x2, y2, z2);

            return new Cuboid(loc1, loc2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public enum Dir {
        UP, DOWN, NORTH, EAST, SOUTH, WEST, X, Y, Z, XZ;
    }

}
