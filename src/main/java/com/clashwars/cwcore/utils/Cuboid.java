package com.clashwars.cwcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * Cuboid area
 * Has a min/max position and a world.
 *
 * <b>Important note: </b>Using any of the methods will modify the original region the same as with Locations in bukkit.
 * So you want to modify it without changing the original first clone it and then modify it.
 */
public class Cuboid implements Iterable<Block>, Cloneable {

    private String worldName;

    private int minX;
    private int minY;
    private int minZ;

    private int maxX;
    private int maxY;
    private int maxZ;


    /**
     * Create a cuboid based on another cuboid.
     * This wont return a new cuboid so make sure you do Cuboid cuboid = new Cuboid(prevCuboid); Or just use prevCuboid.clone();
     * @param cuboid A cuboid to clone the properties from.
     */
    public Cuboid(Cuboid cuboid) {
        this(cuboid.getWorld(), cuboid.minX, cuboid.minY, cuboid.minZ, cuboid.maxX, cuboid.maxY, cuboid.maxZ);
    }

    /**
     * Create a cuboid based on 2 locations.
     * @param loc1 A Location
     * @param loc2 A Location
     */
    public Cuboid(Location loc1, Location loc2) {
        this(loc1.getWorld(), loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
    }

    /**
     * Create a cuboid based on a world and 6 integers. [x1,y2,z3 - x2,y2,z2]
     * @param world The World
     * @param x1 X pos of Location 1
     * @param y1 Y pos of Location 1
     * @param z1 Z pos of Location 1
     * @param x2 X pos of Location 2
     * @param y2 Y pos of Location 2
     * @param z2 Z pos of Location 2
     */
    public Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this(world == null ? "" : world.getName(), x1, y1, z1, x2, y2, z2);
    }

    /**
     * Create a cuboid based on a world and 2 vectors.
     * @param world The World
     * @param loc1 A Vector
     * @param loc2 A Vector
     */
    public Cuboid(World world, Vector loc1, Vector loc2) {
        this(world == null ? "" : world.getName(), loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
    }

    /**
     * Create a cuboid based on a world, min vector and width/height/length.
     * The width/height/length will be added to the loc vector.
     * @param world The World
     * @param loc The location to add the width/height/length to. (MIN loc)
     * @param width The width to add. (X)
     * @param height The height to add. (Y)
     * @param length The length to add. (Z)
     */
    public Cuboid(World world, Vector loc, int width, int height, int length) {
        this(world == null ? "" : world.getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getBlockX() + width, loc.getBlockY() + height, loc.getBlockZ() + length);
    }

    /**
     * Create a cuboid based on a min Location and width/height/length.
     * @param loc The location to add the width/height/length to. (MIN loc)
     * @param width The width to add. (X)
     * @param height The height to add. (Y)
     * @param length The length to add. (Z)
     */
    public Cuboid(Location loc, int width, int height, int length) {
        this(loc.getWorld() == null ? "" : loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getBlockX() + width, loc.getBlockY() + height, loc.getBlockZ() + length);
    }

    /**
     * Create a cuboid based on a worldname and 6 integers. [x1,y2,z3 - x2,y2,z2]
     * @param worldName The name of the World
     * @param x1 X pos of Location 1
     * @param y1 Y pos of Location 1
     * @param z1 Z pos of Location 1
     * @param x2 X pos of Location 2
     * @param y2 Y pos of Location 2
     * @param z2 Z pos of Location 2
     */
    public Cuboid(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.worldName = worldName;
        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);
        minZ = Math.min(z1, z2);
        maxX = Math.max(x1, x2);
        maxY = Math.max(y1, y2);
        maxZ = Math.max(z1, z2);
    }

    /**
     * Create a cuboid from a map like:
     * [world:{worldname}, minX:{int}, minY:{int}, minZ:{int}, maxX:{int}, maxY:{int}, maxZ:{int}]
     * @param map The map with all data
     */
    public Cuboid(Map<String, Object> map) {
        worldName = (String)map.get("world");
        minX = (Integer)map.get("minX");
        minY = (Integer)map.get("minY");
        minZ = (Integer)map.get("minZ");
        maxX = (Integer)map.get("maxX");
        maxY = (Integer)map.get("maxY");
        maxZ = (Integer)map.get("maxZ");
    }


    /**
     * Check if the cuboid contains the given block.
     * @param block This block that needs to be checked.
     * @return true if it does and false if not.
     */
    public boolean contains(Block block) {
        return contains(block.getLocation());
    }

    /**
     * Check if the given player is within the cuboid.
     * @param player The player that needs to be checked.
     * @return true if he is and false if not.
     */
    public boolean contains(Player player) {
        return contains(player.getLocation());
    }

    /**
     * Check if the cuboid contains the given location.
     * @param location The location that needs to be checked.
     * @return true if it does and false if not.
     */
    public boolean contains(Location location) {
        return contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    /**
     * Check if the cuboid contains the given coordinates. x/y/z int.
     * @param locX The X position to check
     * @param locY The Y position to check
     * @param locZ The Z position to check
     * @return true if it does and false if not.
     */
    public boolean contains(int locX, int locY, int locZ) {
        return locX >= minX && locX <= maxX && locY >= minY && locY <= maxY && locZ >= minZ && locZ <= maxZ;
    }


    /**
     * Get the X coordinate of the minimum location
     * @return minX
     */
    public int getMinX() {
        return minX;
    }

    /**
     * Set the X coordinate of the minimum location.
     * It will be set to max X if it's higher then the maxX.
     */
    public void setMinX(int minX) {
        this.minX = Math.min(minX, maxX);
    }

    /**
     * Get the Y coordinate of the minimum location
     * @return minY
     */
    public int getMinY() {
        return minY;
    }

    /**
     * Set the Y coordinate of the minimum location.
     * It will be set to max Y if it's higher then the maxY.
     */
    public void setMinY(int minY) {
        this.minY = Math.min(minY, maxY);
    }

    /**
     * Get the Z coordinate of the minimum location
     * @return minZ
     */
    public int getMinZ() {
        return minZ;
    }

    /**
     * Set the Z coordinate of the minimum location.
     * It will be set to max Z if it's higher then the maxZ.
     */
    public void setMinZ(int minZ) {
        this.minZ = Math.min(minZ, maxZ);
    }

    /**
     * Get the X coordinate of the maximum location
     * @return maxX
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * Set the X coordinate of the maximum location.
     * It will be set to min X if it's lower then the maxZ.
     */
    public void setMaxX(int maxX) {
        this.maxX = Math.max(maxX, minX);
    }

    /**
     * Get the Y coordinate of the maximum location
     * @return maxY
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Set the Y coordinate of the maximum location.
     * It will be set to min Y if it's lower then the maxY.
     */
    public void setMaxY(int maxY) {
        this.maxY = Math.max(maxY, minY);
    }

    /**
     * Get the Z coordinate of the maximum location
     * @return maxZ
     */
    public int getMaxZ() {
        return maxZ;
    }

    /**
     * Set the Z coordinate of the maximum location.
     * It will be set to min Z if it's lower then the maxZ.
     */
    public void setMaxZ(int maxZ) {
        this.maxZ = Math.max(maxZ, minZ);
    }



    /**
     * Set the minimum location based on a bukkit Location.
     * If one of the axis is higher then the max it will be set to the max of that axis.
     * @param min A location used as min.
     */
    public void setMin(Location min) {
        setMin(min.getBlockX(), min.getBlockY(), min.getBlockZ());
    }

    /**
     * Set the minimum location based on a vector.
     * If one of the axis is higher then the max it will be set to the max of that axis.
     * @param min A vector used as min.
     */
    public void setMin(Vector min) {
        setMin(min.getBlockX(), min.getBlockY(), min.getBlockZ());
    }

    /**
     * Set the minimum location based on x/y/z coordinates.
     * If one of the axis is higher then the max it will be set to the max of that axis.
     * @param minX The X coordinate used as min.
     * @param minY The Y coordinate used as min.
     * @param minZ The Z coordinate used as min.
     */
    public void setMin(int minX, int minY, int minZ) {
        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);
        this.minZ = Math.min(minZ, maxZ);
    }

    /**
     * Set the maximum location based on a bukkit Location.
     * If one of the axis is lower then the min it will be set to the min of that axis.
     * @param max A location used as max.
     */
    public void setMax(Location max) {
        setMin(max.getBlockX(), max.getBlockY(), max.getBlockZ());
    }

    /**
     * Set the maximum location based on a vector.
     * If one of the axis is lower then the min it will be set to the min of that axis.
     * @param max A vector used as max.
     */
    public void setMax(Vector max) {
        setMin(max.getBlockX(), max.getBlockY(), max.getBlockZ());
    }

    /**
     * Set the maximum location based on x/y/z coordinates.
     * If one of the axis is lower then the min it will be set to the min of that axis.
     * @param maxX The X coordinate used as max.
     * @param maxY The Y coordinate used as max.
     * @param maxZ The Z coordinate used as max.
     */
    public void setMax(int maxX, int maxY, int maxZ) {
        this.maxX = Math.max(maxX, minX);
        this.maxY = Math.max(maxY, minY);
        this.maxZ = Math.max(maxZ, minZ);
    }


    /**
     * Calculate the volume of the cuboid. (width*length*height)
     * @return The volume in blocks. (int)
     */
    public int getVolume() {
        return getWidth() * getHeight() * getLength();
    }

    /**
     * Calculate the width (X) of the cuboid. (max - min + 1)
     * @return The width in blocks. (int)
     */
    public int getWidth() {
        return this.maxX - this.minX + 1;
    }

    /**
     * Calculate the height (Y) of the cuboid. (max - min + 1)
     * @return The height in blocks. (int)
     */
    public int getHeight() {
        return this.maxY - this.minY + 1;
    }

    /**
     * Calculate the length (Z) of the cuboid. (max - min + 1)
     * @return The length in blocks. (int)
     */
    public int getLength() {
        return this.maxZ - this.minZ + 1;
    }



    /**
     * Get the minimum location as a bukkit Location.
     * @return The min Location
     */
    public Location getMinLoc() {
        return new Location(getWorld(), minX, minY, minZ);
    }

    /**
     * Get the maximum location as a bukkit Location.
     * @return The max Location
     */
    public Location getMaxLoc() {
        return new Location(getWorld(), maxX, maxY, maxZ);
    }

    /**
     * Get the center location as a bukkit Location.
     * @return The center Location
     */
    public Location getCenterLoc() {
        Vector centerVector = getCenterVector();
        return new Location(getWorld(), centerVector.getX(), centerVector.getY(), centerVector.getZ());
    }


    /**
     * Get the minimum location as a Vector.
     * @return The min Vector
     */
    public Vector getMinVector() {
        return new Vector(minX, minY, minZ);
    }

    /**
     * Get the maximum location as a Vector.
     * @return The max Vector
     */
    public Vector getMaxVector() {
        return new Vector(maxX, maxY, maxZ);
    }

    /**
     * Get the center location as a Vector.
     * @return The center Vector
     */
    public Vector getCenterVector() {
        return new Vector(minX + (getWidth() / 2), minY + (getHeight() /2), minZ + (getLength() / 2));
    }


    /**
     * Add a offset of the specified vector to the cuboid.
     * This offset will be added to ALL sides so with a offset of Y 1 the height increases with 2 blocks.
     * @param offset Vector with x,y,z offset.
     */
    public void offset(Vector offset) {
        offset(offset.getBlockX(), offset.getBlockY(), offset.getBlockZ());
    }

    /**
     * Add a offset of the specified amounts to the cuboid.
     * This offset will be added to ALL sides so with a offset of height 1 the height increases with 2 blocks.
     * @param width The X offset
     * @param height The Y offset
     * @param length The Z offset
     */
    public void offset(int width, int height, int length) {
        minX -= width;
        minY -= height;
        minZ -= length;
        maxX += width;
        maxY += height;
        maxZ += length;
    }

    /**
     * Add a inset of the specified vector to the cuboid.
     * This inset will be added to ALL sides so with a inset of Y 1 the height decreases with 2 blocks.
     * @param inset Vector with x,y,z inset.
     */
    public void inset(Vector inset) {
        offset(-inset.getBlockX(), -inset.getBlockY(), -inset.getBlockZ());
    }

    /**
     * Add a inset of the specified amounts to the cuboid.
     * This inset will be added to ALL sides so with a inset of height 1 the height decreases with 2 blocks.
     * @param width The X inset
     * @param height The Y inset
     * @param length The Z inset
     */
    public void inset(int width, int height, int length) {
        offset(-width, -height, -length);
    }


    /**
     * Expand the region by the specified amount of blocks in the specified direction.
     * If you specify for example Dir.X it will expand in both directions.
     * To only expand in 1 direction use for example Dir.NORTH
     * @param dir The direction {@link com.clashwars.cwcore.utils.Cuboid.Dir}
     * @param amt The amount of blocks to expand.
     */
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

    /**
     * Contract the region by the specified amount of blocks in the specified direction.
     * If you specify for example Dir.X it will contract in both directions.
     * To only contract in 1 direction use for example Dir.NORTH
     * @param dir The direction {@link com.clashwars.cwcore.utils.Cuboid.Dir}
     * @param amt The amount of blocks to contract.
     */
    public void contract(Dir dir, int amt) {
        expand(dir, -amt);
    }


    /**
     * Get a Block relative from the minimum location.
     * It does not check if the block is within the region so you can specify relative coords further then the width/height/length.
     * @param x The amount of blocks to add on the X axis.
     * @param y The amount of blocks to add on the Y axis.
     * @param z The amount of blocks to add on the Z axis.
     * @return The block at the relative location.
     */
    public Block getRelativeBlock(int x, int y, int z) {
        return getWorld().getBlockAt(minX + x, minY + y, minZ + z);
    }

    /**
     * Get a Block relative from the minimum location.
     * It does not check if the block is within the region so you can specify relative coords further then the width/height/length.
     * @param offset A vector with the amount of blocks to add on the X,Y,Z axis.
     * @return The block at the relative location.
     */
    public Block getRelativeBlock(Vector offset) {
        return getWorld().getBlockAt(minX + offset.getBlockX(), minY + offset.getBlockY(), minZ + offset.getBlockZ());
    }



    /**
     * Get a List<Block> of all blocks within this cuboid.
     * @return List with all blocks or empty list if none found.
     */
    public List<Block> getBlocks() {
        return getBlocks(new Material[] {});
    }

    /**
     * Get a List<Block> of all blocks within this cuboid that match any of the filter materials.
     * For example if you specify {Material.CHEST} it will return all chests inside the cuboid.
     * If you want to get a lot blocks separate don't just call this many times but call getBlocks() and then do the loop your self.
     * @return List with all blocks or empty list if none found.
     */
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

    /**
     * Get a List<Vector> of all block vectors within this cuboid.
     * @return List with all vectors or empty list if none found.
     */
    public List<Vector> getVectors() {
        return getVectors(new Material[]{});
    }

    /**
     * Get a List<Vector> of all block vectors within this cuboid that match any of the filter materials.
     * For example if you specify {Material.CHEST} it will return all locations of chests inside the cuboid.
     * If you want to get a lot blocks separate don't just call this many times but call getVectors() and then do the loop your self.
     * @return List with all vectors or empty list if none found.
     */
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

    /**
     * Get a List<Player> of all players found within this cuboid.
     * @return List of players within this cuboid.
     */
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


    /**
     * Try and get the world of this cuboid.
     * @throws java.lang.IllegalStateException if the world is null.
     * @return World of this cuboid if it's not null.
     */
    public World getWorld() {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            throw new IllegalStateException("'" + worldName + "' is not loaded or not a valid world.");
        }
        return world;
    }

    /**
     * Set the world of this cuboid.
     * @param world The world to set it at.
     */
    public void setWorld(World world) {
        if (world != null) {
            worldName = world.getName();
        }
    }


    /**
     * Clone this cuboid by returning a NEW copy of this cuboid.
     * @return a new Cuboid with the same properties.
     */
    @Override
    public Cuboid clone() {
        return new Cuboid(this);
    }

    /**
     * Get a block iterator to iterate through all blocks in this region.
     * @return block iterator.
     */
    @Override
    public Iterator<Block> iterator() {
        return this.getBlocks().listIterator();
    }

    /**
     * Convert the region to a string.
     * String syntax: 'worldName,minX,minY,minZ,maxZ,maxY,maxZ'
     * @return String with all region data.
     */
    @Override
    public String toString() {
        return worldName + "," + minX + "," + minY + "," + minZ + "," + maxX + "," + maxY + "," + maxZ;
    }


    /**
     * Deserialize a string containing cuboid data to a Cuboid.
     * @param cuboidString The string containing all cuboid data. (fetched from toString())
     * @return A new Cuboid if the data could be loaded properly otherwise it'll return null.
     */
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

            Location loc1 = new Location(world, x1, y1, z1);
            Location loc2 = new Location(world, x2, y2, z2);

            return new Cuboid(loc1, loc2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * A enum with directions used for modifying a cuboid.
     * UP: Y+
     * DOWN: Y-
     * NORTH: X+
     * EAST: Z+
     * SOUTH: X-
     * WEST: Z-
     * X: Both NORTH and SOUTH
     * Z: Both EAST and WEST
     */
    public enum Dir {
        UP, DOWN, NORTH, EAST, SOUTH, WEST, X, Y, Z;
    }

}
