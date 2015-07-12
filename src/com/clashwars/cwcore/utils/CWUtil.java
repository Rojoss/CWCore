package com.clashwars.cwcore.utils;

import com.clashwars.cwcore.helpers.CWItem;
import net.minecraft.server.v1_8_R2.*;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class CWUtil {

    private static Random random;
    private static final int MSEC_IN_DAY = 86400000;
    private static final int MSEC_IN_HOUR = 3600000;
    private static final int MSEC_IN_MIN = 60000;
    private static final int MSEC_IN_SEC = 1000;

    static {
        random = new Random();
    }



    //##########################################################################################
    //###############################  STRING COLOR UTILITIES  #################################
    //##########################################################################################

    /**
     * Format a message with [CW] Prefix
     * @param msg
     * @return formatted message
     */
    public static String formatCWMsg(String msg) {
        return integrateColor("&8[&4CW&8] &6" + msg);
    }

    /**
     * Integrate ChatColor in a string based on color codes.
     * @param str
     * @return formatted string
     */
    public static String integrateColor(String str) {
        for (ChatColor c : ChatColor.values()) {
            str = str.replaceAll("&" + c.getChar() + "|&" + Character.toUpperCase(c.getChar()), c.toString());
        }
        return str;
    }

    /**
     * Integrate ChatColor in a array of strings based on color codes.
     * @param str Array of strings
     * @return formatted string array
     */
    public static String[] integrateColor(String[] str) {
        for (int i = 0; i < str.length; i++) {
            str[i] = integrateColor(str[i]);
        }
        return str;
    }

    /**
     * Integrate ChatColor in a list of strings based on color codes.
     * @param str List of strings
     * @return formatted string list
     */
    public static List<String> integrateColor(List<String> str) {
        for (int i = 0; i < str.size(); i++) {
            str.set(i, integrateColor(str.get(i)));
        }
        return str;
    }

    /**
     * Integrate colors in a string but you can set what you want to format.
     * @param str The string to format.
     * @param format Format colors?
     * @param magic Format magic symbol?
     * @return Formatted string,
     */
    public static String integrateColor(String str, boolean format, boolean magic) {
        char ch;
        for (ChatColor c : ChatColor.values()) {
            ch = c.getChar();

            if (!format && (ch == 'k' || ch == 'l' || ch == 'n' || ch == 'o' || ch == 'm' || ch == 'K' || ch == 'L' || ch == 'N' || ch == 'O' || ch == 'M')) {
                continue;
            }

            if (!magic && (ch == 'k' || ch == 'K')) {
                continue;
            }

            str = str.replaceAll("&" + ch + "|&" + Character.toUpperCase(ch), c.toString());
        }
        return str;
    }

    /**
     * Strip all color from a string.
     * @param str
     * @return formatted string
     */
    public static String stripAllColor(String str) {
        return ChatColor.stripColor(integrateColor(str));
    }

    /**
     * Remove all color and put colors as the formatting codes like &1.
     * @param str
     * @return formatted string
     */
    public static String removeColour(String str) {
        for (ChatColor c : ChatColor.values()) {
            str = str.replace(c.toString(), "&" + c.getChar());
        }
        return str;
    }




    //##########################################################################################
    //##################################  STRING UTILITIES  ####################################
    //##########################################################################################


    public static void sendActionBar(Player player, String prefixClr, String message){
        sendActionBar(player, CWUtil.integrateColor(prefixClr + ">> " + message + prefixClr + " <<"));
    }

    /**
     * Send a message the player his actionbar. (above hotbar)
     * @param player The palyer to send the message to.
     * @param message The message can have chat colors and stuff.
     */
    public static void sendActionBar(Player player, String message){
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        p.getHandle().playerConnection.sendPacket(ppoc);
    }

    /**
     * Capitalize the first character of a string.
     * @param str
     * @return Capitalized string
     */
    public static String capitalize(String str) {
        if (str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Remove Convert a string to a List<String> mostly useful for lores.
     * @param str The string to split.
     * @param seperateChar The string used for seperation.
     * @param color Should ChatColors be integrated in all strings?
     * @return formatted string
     */
    public static List<String> splitToList(String str, String seperateChar, boolean color) {
        List<String> list = null;
        if (color) {
            str = integrateColor(str);
        }
        String[] split = str.split(seperateChar);
        list = Arrays.asList(split);
        return list;
    }

    /**
     * Trim first string from string array
     * @param arr The array of strings to modify
     * @return String[] with the first string removed.
     */
    public static String[] trimFirst(String[] arr) {
        String[] ret = new String[arr.length - 1];
        for (int i = 1; i < arr.length; i++) {
            ret[i - 1] = arr[i];
        }
        return ret;
    }

    /**
     * Trim first string from string list
     * @param list The list of strings to modify
     * @return List<String> with the first string removed.
     */
    public static List<String> trimFirst(List<String> list) {
        return Arrays.asList(trimFirst(list.toArray(new String[list.size() - 1])));
    }

    public static String implode(String[] arr, String glue, int start, int end) {
        String ret = "";

        if (arr == null || arr.length <= 0)
            return ret;

        for (int i = start; i <= end && i < arr.length; i++) {
            ret += arr[i] + glue;
        }

        return ret.substring(0, ret.length() - glue.length());
    }

    public static String implode(String[] arr, String glue, int start) {
        return implode(arr, glue, start, arr.length - 1);
    }

    public static String implode(String[] arr, String glue) {
        return implode(arr, glue, 0);
    }

    public static String implode(String[] arr) {
        return implode(arr, " ");
    }

    public static String implode(Collection<String> args, String glue) {
        if (args.isEmpty())
            return "";
        return implode(args.toArray(new String[args.size()]), glue);
    }




    //##########################################################################################
    //###################################  TIME UTILITIES  #####################################
    //##########################################################################################

    public static String formatTime(long time, String syntax) {
        return formatTime(time, syntax, false);
    }

    /**
     * Format a timestamp to a string with days/hours/mins/secs/ms.
     * For example: '%Dd %H:%M:%S' will be replaced with something like '1d 23:12:52'
     * The possible options in the syntax are:
     * %D = Days
     * %H = Hours
     * %M = Minutes
     * %S = Seconds
     * %MS = MilliSeconds
     * %% Remainder percentage of seconds with 1 decimal like '%S.%%s' could be '34.1s'
     * %%% Remainder percentage of seconds with 2 decimals like '%S.%%%s' could be '34.13s'
     * @param time The time to convert to min:sec
     * @param syntax The string with the above options which will be replaced with the time.
     * @return Formatted time string.
     */
    public static String formatTime(long time, String syntax, boolean extraZeros) {
        //time = time / 1000;

        int days = (int) time / MSEC_IN_DAY;
        time = time - days * MSEC_IN_DAY;

        int hours = (int) time / MSEC_IN_HOUR;
        time = time - hours * MSEC_IN_HOUR;

        int mins = (int) time / MSEC_IN_MIN;
        time = time - mins * MSEC_IN_MIN;

        int secs = (int) time / MSEC_IN_SEC;
        time = time - secs * MSEC_IN_SEC;

        int ms = (int) time;
        int ds = (int) time / 100;
        int fs = (int) time / 10;

        syntax = syntax.replace("%D", "" + days);
        syntax = syntax.replace("%H", "" + (hours < 10 && extraZeros ? "0"+hours : hours));
        syntax = syntax.replace("%M", "" + (mins < 10 && extraZeros ? "0"+mins : mins));
        syntax = syntax.replace("%S", "" + (secs < 10 && extraZeros ? "0"+secs : secs));
        syntax = syntax.replace("%MS", "" + ms);
        syntax = syntax.replace("%%%", "" + fs);
        syntax = syntax.replace("%%", "" + ds);
        return syntax;
    }

    public static String getTimeStamp() {
        return getTimeStamp("[dd-MM HH:mm:ss]");
    }

    public static String getTimeStamp(String syntax) {
        return new SimpleDateFormat(syntax).format(Calendar.getInstance().getTime());
    }

    public static String timeStampToDateString(Timestamp timestamp) {
        return new SimpleDateFormat("dd MMM yyyy").format(timestamp);
    }





    //##########################################################################################
    //##############################  RANDOMIZATION UTILITIES  #################################
    //##########################################################################################

    /**
     * Get a random number between start and end.
     * @param start
     * @param end
     * @return random int
     */
    public static int random(int start, int end) {
        return start + random.nextInt(end - start + 1);
    }

    /**
     * Get a random float (Same as Random.nextFloat())
     * @return random float between 0-1
     */
    public static float randomFloat() {
        return random.nextFloat();
    }

    /**
     * Get a random value out of a Array.
     * @param array The array like String[] or int[]
     * @return Random value out of array.
     */
    public static <T> T random(T[] array) {
        return array[random(0, array.length-1)];
    }

    /**
     * Get a random value out of a List.
     * @param list The list like List<String>
     * @return Random value out of list.
     */
    public static <T> T random(List<T> list) {
        return list.get(random(0, list.size() - 1));
    }

    /**
     * Get a random value out of a enum
     * @param clazz The enum class like Material.class
     * @return Random value out of enum
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    /**
     * Get a random {@link org.bukkit.Color}
     * @return random color
     */
    public static Color getRandomColor() {
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);

        return Color.fromRGB(r, g, b);
    }




    //##########################################################################################
    //#################################  NUMBER UTILITIES  #####################################
    //##########################################################################################

    /**
     * Convert a string like '1' to a int. Returns -1 if it's invalid.
     * @param str
     * @return int
     */
    public static int getInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
        }
        return -1;
    }

    /**
     * Convert a string like '1.5' to a double. Returns -1 if it's invalid.
     * @param str
     * @return double
     */
    public static double getDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
        }
        return -1;
    }

    /**
     * Convert a string like '1.12' to a float. Returns -1 if it's invalid.
     * @param str
     * @return float
     */
    public static float getFloat(String str) {
        if (str != null && str != "") {
            try {
                return Float.parseFloat(str);
            } catch (NumberFormatException e) {
            }
        }
        return -1;
    }

    /**
     * Round a double value.
     * @param val
     * @return rounded double
     */
    public static double roundDouble(double val) {
        val = val * 100;
        val = Math.round(val);
        val = val / 100;
        return val;
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /**
     * Get percentage based on 2 ints.
     * For example small=10 big = 50 will return 10/50*100 = 20.0%
     * @param smallInt
     * @param bigInt
     * @return Percentage as double
     */
    public static double getPercentage(int smallInt, int bigInt) {
        return ((double) (smallInt) / bigInt) * 100;
    }

    /**
     * Get a entry from a map with the lowest value.
     * It works for Integer, Double and Float.
     * @param map the map to check.
     * @return Entry<K, V> with the lowest value.
     */
    public static <K, V> Map.Entry<K, V> getMinByValue(Map<K, V> map) {
        Map.Entry<K, V> min = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (min.getValue() instanceof Integer && entry.getValue() instanceof Integer) {
                if (min == null || getInt(min.getValue().toString()) > getInt(min.getValue().toString())) {
                    min = entry;
                }
            } else if (min.getValue() instanceof Double && entry.getValue() instanceof Double) {
                if (min == null || getDouble(min.getValue().toString()) > getDouble(min.getValue().toString())) {
                    min = entry;
                }
            } else if (min.getValue() instanceof Float && entry.getValue() instanceof Float) {
                if (min == null || getFloat(min.getValue().toString()) > getFloat(min.getValue().toString())) {
                    min = entry;
                }
            }
            if (min == null) {
                min = entry;
            }
        }
        return min;
    }


    /**
     * Get a entry from a map with the lowest key.
     * It works for Integer, Double and Float.
     * @param map the map to check.
     * @return Entry<K, V> with the lowest key.
     */
    public static <K, V> Map.Entry<K, V> getMinByKey(Map<K, V> map) {
        Map.Entry<K, V> min = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (min.getKey() instanceof Integer && entry.getKey() instanceof Integer) {
                if (min == null || getInt(min.getKey().toString()) > getInt(min.getKey().toString())) {
                    min = entry;
                }
            } else if (min.getKey() instanceof Double && entry.getKey() instanceof Double) {
                if (min == null || getDouble(min.getKey().toString()) > getDouble(min.getKey().toString())) {
                    min = entry;
                }
            } else if (min.getKey() instanceof Float && entry.getKey() instanceof Float) {
                if (min == null || getFloat(min.getKey().toString()) > getFloat(min.getKey().toString())) {
                    min = entry;
                }
            }
            if (min == null) {
                min = entry;
            }
        }
        return min;
    }

    //##########################################################################################
    //###################################  FILE UTILITIES  #####################################
    //##########################################################################################

    /**
     * Delete a directory at specified path.
     * @param path The path were to delete the directory.
     * @return True if deleted and false if not deleted.
     */
    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }

    /**
     * Get a list of files with all directories found at the given path.
     * If the path doesn't exist it will return null and if no directories are found a empty list.
     * @param path The src path/file were to look for directories.
     * @return List with Files of directory paths or null if path specified doesn't exist.
     */
    public static List<File> getDirectories(File path) {
        List<File> directories = new ArrayList<File>();
        if (path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    directories.add(files[i]);
                }
            }
            return directories;
        } else {
            return null;
        }
    }

    /**
     * Copy a directory and all directories/files in it to another place.
     * It will throw an exception if it failed.
     * @param fromFile The directory to copy.
     * @param toFile The destination.
     * @throws IOException
     */
    public static void copyDirectory(File fromFile, File toFile) throws IOException {
        if (fromFile.isDirectory()) {
            if (!toFile.exists()) {
                toFile.mkdir();
            }
            String files[] = fromFile.list();
            for (String file : files) {
                File srcFile = new File(fromFile, file);
                File destFile = new File(toFile, file);
                copyDirectory(srcFile, destFile);
            }
        } else {
            OutputStream out;
            InputStream in = new FileInputStream(fromFile);
            out = new FileOutputStream(toFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.close();
        }
    }

    /**
     * Delete a folder. (using appache FileUtils)
     * Credits to multiverse.
     * @param file The folder to delete.
     * @return true if the folder was successfully deleted.
     */
    public static boolean deleteFolder(File file) {
        try {
            FileUtils.deleteDirectory(file);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean deleteFolder2(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteFolder2(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }


    //##########################################################################################
    //#################################  LOCATION UTILITIES  ###################################
    //##########################################################################################

    /**
     * Teleport a player to a location.
     * It will unmount any entity and teleport player and entity and then remount.
     * @param entity The Entity to teleport.
     * @param loc The location to teleport to.
     */
    public static void teleport(Entity entity, Location loc) {
        if (entity.getVehicle() == null) {
            entity.teleport(loc);
        }
        Entity vehicle = entity.getVehicle();
        entity.leaveVehicle();
        vehicle.teleport(loc);
        entity.teleport(loc);
        vehicle.setPassenger(entity);
    }


    /**
     * @see #isLocWithin(org.bukkit.Location, org.bukkit.Location, org.bukkit.Location, org.bukkit.util.Vector)
     */
    public static boolean isLocWithin(Location loc, Location pos1, Location pos2) {
        return isLocWithin(loc, pos1, pos2, new Vector(0,0,0));
    }

    /**
     * Test if a location is within a cuboid of the 2 specified locations.
     * If a offset is specified you can add a XX amount of blocks offset in any direction this can also be negative.
     * For example if you have a 10*10*10 area and the offset is 2 then it'll be a 14*14*14 area because 2 blocks are added to each axis.
     * @param loc The location to check.
     * @param pos1 First location of cuboid
     * @param pos2 Second location of cuboid
     * @param offset Vector with offset for each axis. (It will add this offset to each side so 1 offset means 1 extra block on both sides.)
     * @return True if loc is within and false if not.
     */
    public static boolean isLocWithin(Location loc, Location pos1, Location pos2, Vector offset) {
        Vector min = new Vector(Math.min(pos1.getBlockX(), pos2.getBlockX()), Math.min(pos1.getBlockY(), pos2.getBlockY()), Math.min(pos1.getBlockZ(), pos2.getBlockZ()));
        Vector max = new Vector(Math.max(pos1.getBlockX(), pos2.getBlockX()), Math.max(pos1.getBlockY(), pos2.getBlockY()), Math.max(pos1.getBlockZ(), pos2.getBlockZ()));

        min.subtract(offset);
        max.add(offset);

        if (loc.getX() >= min.getX() && loc.getX() <= max.getX() && loc.getY() >= min.getY() && loc.getY() <= max.getY() && loc.getZ() >= min.getZ() && loc.getZ() <= max.getZ()) {
            return true;
        }
        return false;
    }


    /**
     * Get the center of the given blockface at the given block.
     * @param blockLoc The location of the block.
     * @param face The block face to get the center at.
     * @return Modified location with offset towards center of blockface.
     */
    public static Location getBlockCenterByBlockface(Location blockLoc, BlockFace face) {
        if (face == BlockFace.DOWN) {
            return blockLoc.add(0.5f, 0.0f, 0.5f);
        } else if (face == BlockFace.UP) {
            return blockLoc.add(0.5f, 1.0f, 0.5f);
        } else if (face == BlockFace.NORTH) {
            return blockLoc.add(0.5f, 0.5f, 0.0f);
        } else if (face == BlockFace.EAST) {
            return blockLoc.add(1.0f, 0.5f, 0.5f);
        } else if (face == BlockFace.SOUTH) {
            return blockLoc.add(0.5f, 0.5f, 1.0f);
        } else if (face == BlockFace.WEST) {
            return blockLoc.add(0.0f, 0.5f, 0.5f);
        }
        return blockLoc.add(0.5f, 0.5f, 0.5f);
    }


    /**
     * Check if a player is looking at the specified location.
     * The width and height are to set the bounding box the larger the size the less accurate the check will be.
     * @param player The player to do the check with.
     * @param loc The target location to check.
     * @param width Width margin/offset
     * @param height Height margin/offset
     * @return True if player is looking at it else False.
     */
    public static boolean isPlayerLookingAtHitBox(Player player, Location loc, int width, int height) {
        boolean isLooking = false;
        Location loc1 = lookAt(player.getLocation(), loc);
        Location loc2 = player.getLocation();
        int yaw1 = (int)Math.abs(loc1.getYaw());
        int yaw2 = (int)Math.abs(loc2.getYaw());
        int pitch1 = (int)Math.abs(loc1.getPitch());
        int pitch2 = (int)Math.abs(loc2.getPitch());
        if (yaw1 + width > yaw2 || yaw1 - width < yaw2 && pitch1 + height > pitch2 || pitch1 - height < pitch2) {
            isLooking = true;
        }
        return isLooking;
    }

    /**
     * Modify a location to look at another location.
     * It will modify the pitch/yaw only.
     * @param loc The location to modify
     * @param target The location to make loc look at.
     * @return Modified location looking at the target location
     */
    public static Location lookAt(Location loc, Location target) {
        loc = loc.clone();
        double dx = target.getX() - loc.getX();
        double dy = target.getY() - loc.getY();
        double dz = target.getZ() - loc.getZ();

        if (dx != 0) {
            if (dx < 0) {
                loc.setYaw((float)(1.5 * Math.PI));
            } else {
                loc.setYaw((float)(0.5 * Math.PI));
            }
            loc.setYaw((float)loc.getYaw() - (float)Math.atan(dz / dx));
        } else if (dz < 0) {
            loc.setYaw((float)Math.PI);
        }

        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

        loc.setPitch((float)-Math.atan(dy / dxz));
        loc.setYaw(-loc.getYaw() * 180f / (float)Math.PI);
        loc.setPitch(loc.getPitch() * 180f / (float)Math.PI);
        return loc;
    }

    /**
     * Get a location from string (string generated by locToString())
     * If the location string is invalid it will return null.
     * @param locStr The string with location data.
     * @return Location if valid else null.
     */
    public static Location locFromString(String locStr) {
        Location loc = null;
        if (locStr == null || locStr.isEmpty()) {
            return loc;
        }
        HashMap<String, String> dataMap = new HashMap<String, String>();
        String[] dataArray = locStr.split("\\$");
        for (String data : dataArray) {
            String[] data2 = null;
            if (data.contains("|")) {
                data2 = data.split("\\|");
            }
            if (data2 != null && data2.length >= 2) {
                dataMap.put(data2[0], data2[1]);
            }
        }
        if (dataMap.containsKey("x") && dataMap.containsKey("y") && dataMap.containsKey("z") && dataMap.containsKey("world")) {
            loc = new Location(Bukkit.getWorld(dataMap.get("world")), getDouble(dataMap.get("x")), getDouble(dataMap.get("y")), getDouble(dataMap.get("z")));
            if (dataMap.containsKey("yaw")) {
                loc.setYaw(getFloat(dataMap.get("yaw")));
            }
            if (dataMap.containsKey("pitch")) {
                loc.setPitch(getFloat(dataMap.get("pitch")));
            }
        }
        return loc;
    }

    /**
     * Convert a location to string.
     * Example: 'world=world|x=1|y=12|z=-123|yaw=0.0|pitch=0.0'
     * @param loc The location to convert.
     * @return String with location data.
     */
    public static String locToString(Location loc) {
        if (loc == null) {
            return null;
        }
        String locStr = "";
        locStr += "world|" + loc.getWorld().getName();
        locStr += "$x|" + String.valueOf(loc.getX());
        locStr += "$y|" + String.valueOf(loc.getY());
        locStr += "$z|" + String.valueOf(loc.getZ());
        locStr += "$pitch|" + String.valueOf(loc.getPitch());
        locStr += "$yaw|" + String.valueOf(loc.getYaw());

        return locStr;
    }


    public static Location locFromStringSimple(String locStr) {
        Location loc = null;
        if (locStr == null || locStr.isEmpty()) {
            return null;
        }
        HashMap<String, String> dataMap = new HashMap<String, String>();
        String[] dataArray = locStr.split(",");
        if (dataArray.length >= 4 ) {
            loc = new Location(Bukkit.getWorld(dataArray[0]), getDouble(dataArray[1]), getDouble(dataArray[2]), getDouble(dataArray[3]));
        }
        if (dataArray.length >= 5) {
            loc.setPitch(getFloat(dataArray[4]));
        }
        if (dataArray.length >= 6) {
            loc.setPitch(getFloat(dataArray[5]));
        }
        return loc;
    }

    public static String locToStringSimple(Location loc) {
        if (loc == null) {
            return null;
        }
        String locStr = "";
        locStr += loc.getWorld().getName();
        locStr += "," + String.valueOf(loc.getX());
        locStr += "," + String.valueOf(loc.getY());
        locStr += "," + String.valueOf(loc.getZ());
        locStr += "," + String.valueOf(loc.getPitch());
        locStr += "," + String.valueOf(loc.getYaw());

        return locStr;
    }



    //##########################################################################################
    //#################################  ITEM/INV UTILITIES  ###################################
    //##########################################################################################

    /**
     * Remove a certain amount of items from the items the player is holding.
     * If there is more amt or equal to the item it will remove the item.
     * If there is a remainder what didn't get removed it will be returned.
     * @param player The player to remove items from.
     * @param amt The amount of items to try remove.
     * @return Amount of items that didn't get removed. (remainder)
     */
    public static int removeItemsFromHand(Player player, int amt) {
        ItemStack item = player.getItemInHand();
        if (item == null) {
            return amt;
        }
        int stackAmt = item.getAmount();
        if (stackAmt > amt) {
            item.setAmount(stackAmt - amt);
            return 0;
        }
        player.setItemInHand(new ItemStack(Material.AIR));
        if (stackAmt == amt) {
            return 0;
        }
        return (amt - stackAmt);
    }

    /**
     * Remove a certain amount of items from the specified slot in the specified inventory.
     * If there is more amt or equal to the item it will remove the item.
     * If there is a remainder what didn't get removed it will be returned.
     * @param inv The inventory to remove the item(s) from.
     * @param slot The slot ID to remove the item(s) from.
     * @param amt The amount of items to try remove.
     * @return Amount of items that didn't get removed. (remainder)
     */
    public static int removeItemsFromSlot(Inventory inv, int slot, int amt) {
        ItemStack item = inv.getItem(slot);
        if (item == null) {
            return amt;
        }
        int stackAmt = item.getAmount();
        if (stackAmt > amt) {
            item.setAmount(stackAmt - amt);
            return 0;
        }
        inv.setItem(slot, new ItemStack(Material.AIR));
        if (stackAmt == amt) {
            return 0;
        }
        return (amt - stackAmt);
    }


    /**
     * @see #removeItems(org.bukkit.inventory.Inventory, org.bukkit.inventory.ItemStack, int, boolean, boolean)
     */
    public static int removeItems(Inventory inv, ItemStack item) {
        return removeItems(inv, item, item.getAmount(), false, false);
    }

    /**
     * @see #removeItems(org.bukkit.inventory.Inventory, org.bukkit.inventory.ItemStack, int, boolean, boolean)
     */
    public static int removeItems(Inventory inv, ItemStack item, int amt) {
        return removeItems(inv, item, amt, false, false);
    }

    /**
     * @see #removeItems(org.bukkit.inventory.Inventory, org.bukkit.inventory.ItemStack, int, boolean, boolean)
     */
    public static int removeItems(Inventory inv, ItemStack item, boolean checkName) {
        return removeItems(inv, item, item.getAmount(), checkName, false);
    }

    /**
     * @see #removeItems(org.bukkit.inventory.Inventory, org.bukkit.inventory.ItemStack, int, boolean, boolean)
     */
    public static int removeItems(Inventory inv, ItemStack item, int amt, boolean checkName) {
        return removeItems(inv, item, amt, checkName, false);
    }

    /**
     * Remove items from a Inventory.
     * It'll check for items of the ItemStack you specified and it will try and remove the specified amt.
     * If you set checkName to true it will only remove items with the exact same name and the same goes for durability.
     * If there is a remainder what didn't get removed it will be returned.
     * @param inv The inventory to remove the items from.
     * @param item The item to remove (used to check type, durability, name etc)
     * @param amt The amount of items to try and remove.
     * @param checkName If set to true only items matching the name will be removed.
     * @param checkDurability If set to true only items matching durability will be removed.
     * @return Amount of items that didn't get removed. (remainder)
     */
    public static int removeItems(Inventory inv, ItemStack item, int amt, boolean checkName, boolean checkDurability) {
        int stackAmt;
        for (int i = 0; i <= inv.getSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack == null) {
                continue;
            }
            //Check if items match
            if (!compareItems(stack, item, checkName, checkDurability)) {
                continue;
            }

            //Remove the items
            stackAmt = stack.getAmount();
            if (stackAmt > amt) {
                stack.setAmount(stackAmt - amt);
                return 0;
            }
            inv.setItem(i, new ItemStack(Material.AIR));
            if (stackAmt == amt) {
                return 0;
            }
            amt -= stackAmt;
        }
        return amt;
    }


    /**
     * @see #compareItems(org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack, boolean, boolean)
     */
    public static boolean compareItems(ItemStack stack1, ItemStack stack2) {
        return compareItems(stack1, stack2, false, true);
    }

    /**
     * Compare 2 ItemStack's with each other.
     * If you set checkName to true it will only be called equal when both items have the same name and the same goes for durability.
     * @param stack1 ItemStack 1 to compare with stack 2.
     * @param stack2 ItemStack 2 to compare with stack 1.
     * @param checkName If set to true only items matching the name will be removed.
     * @param checkDurability If set to true only items matching durability will be removed.
     * @return
     */
    public static boolean compareItems(ItemStack stack1, ItemStack stack2, boolean checkName, boolean checkDurability) {
        if (stack1.getType() != stack2.getType()) {
            return false;
        }
        if (checkDurability && stack1.getDurability() != stack2.getDurability()) {
            return false;
        }
        if (checkName && stack2.hasItemMeta()) {
            if (!stack1.hasItemMeta() || !stack1.getItemMeta().getDisplayName().equalsIgnoreCase(stack2.getItemMeta().getDisplayName())) {
                return false;
            }
        }
        return true;
    }

    public static void dropItemStack(Location loc, ItemStack item) {
        loc.getWorld().dropItem(loc, item);
    }

    public static void dropItemStack(Location loc, ItemStack item, JavaPlugin plugin, Player owner) {
        loc.add(0.5f, 0.5f, 0.5f);
        Item itemDrop = loc.getWorld().dropItem(loc, item);
        itemDrop.setMetadata("owner", new FixedMetadataValue(plugin, owner.getName()));
        itemDrop.setVelocity(itemDrop.getVelocity().multiply(0.2f));
    }




    //##########################################################################################
    //###################################  MISC UTILITIES  #####################################
    //##########################################################################################


    /**
     * Get a player in line of sight within right.
     * This is a heavy method so make sure the player can't spam it and that it's not called every tick for example.
     * @param player The player
     * @param range The range how far to check.
     * @return Targeted player if one is found else return null.
     */
    public static Player getTargetedPlayer(Player player, int range) {
        Player target = null;

        //Get list of nearby players.
        List<Entity> ne = player.getNearbyEntities(range, range, range);
        ArrayList<Player> players = new ArrayList<Player>();
        for (Entity e : ne) {
            if (e instanceof Player) {
                players.add((Player)e);
            }
        }

        //Create a block iterator for all blocks in range.
        //This will get all blocks in a line were the player is looking till range.
        BlockIterator bi;
        try {
            bi = new BlockIterator(player, range);
        } catch (IllegalStateException e) {
            return null;
        }

        Block b;
        Location l;
        int bx, by, bz;
        double ex, ey, ez;
        //Loop through all blocks in the itterator.
        while (bi.hasNext()) {
            b = bi.next();
            bx = b.getX();
            by = b.getY();
            bz = b.getZ();
            //At each block location loop through the players.
            for (Player p : players) {
                l = p.getLocation();
                ex = l.getX();
                ey = l.getY();
                ez = l.getZ();
                //Check if the location of the player is the same as the block with offsets for the hitbox.
                if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75) && (by - 1 <= ey && ey <= by + 2.5)) {
                    target = p;
                    return target;
                }
            }
        }
        return null;
    }

    public static List<Chunk> getNearbyChunks(Location l, int range) {
        List<Chunk> chunkList = new ArrayList<Chunk>();
        World world = l.getWorld();
        int chunks = range / 16 + 1;

        Chunk chunk;
        for (int x = l.getChunk().getX() - chunks; x < l.getChunk().getX() + chunks; x++) {
            for (int z = l.getChunk().getZ() - chunks; z < l.getChunk().getZ() + chunks; z++) {
                chunk = world.getChunkAt(x, z);
                if (chunk != null && chunk.isLoaded()) {
                   chunkList.add(chunk);
                }
            }
        }

        return chunkList;
    }

    public static List<Entity> getEntitiesInNearbyChunks(Location l, int range, List<EntityType> entityTypes) {
        List<Entity> entities = new ArrayList<Entity>();

        for(Chunk chunk : getNearbyChunks(l, range)) {
            if (entityTypes == null || entityTypes.size() <= 0) {
                entities.addAll(Arrays.asList(chunk.getEntities()));
            } else {
                for (Entity e : chunk.getEntities()) {
                    if (entityTypes.contains(e.getType())) {
                        entities.add(e);
                    }
                }
            }
        }

        return entities;
    }

    public static List<Player> getPlayersInNearbyChunks(Location l, int range) {
        List<Player> players = new ArrayList<Player>();

        for(Chunk chunk : getNearbyChunks(l, range)) {
            for (Entity e : chunk.getEntities()) {
                if (e instanceof  Player) {
                    players.add((Player)e);
                }
            }
        }

        return players;
    }

    public static List<Entity> getNearbyEntities(Location l, float range, List<EntityType> entityTypes) {
        List<Entity> entities = new ArrayList<Entity>();
        for (Entity e : getEntitiesInNearbyChunks(l, (int) range, entityTypes)) {
            if (e.getLocation().distance(l) <= range) {
                entities.add(e);
            }
        }
        return entities;
    }

    public static List<Player> getNearbyPlayers(Location l, float range) {
        List<Player> players = new ArrayList<Player>();

        for (Player player : getPlayersInNearbyChunks(l, (int) range)) {
            if (player.getLocation().distance(l) <= range) {
                players.add(player);
            }
        }

        return players;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, final boolean descending) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                if (descending) {
                    return (o2.getValue()).compareTo(o1.getValue());
                } else {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Get a Set<Block> with all blocks found between the 2 locations.
     * You can specify a list of materials that have to match.
     * So if you want to find all chests within a area you add 'new Material[] {Material.CHEST}'
     * If material is null it will just all add blocks in the area.
     * @param pos1 The first position
     * @param pos2 The second position
     * @param filter Array with block materials.
     * @return Set with blocks found in the area.
     */
    public static Set<Block> findBlocksInArea(Location pos1, Location pos2, Material[] filter) {
        Set<Block> blocks = new HashSet<Block>();
        List<Material> filterList = filter == null ? new ArrayList<Material>() : Arrays.asList(filter);
        Vector min = new Vector(Math.min(pos1.getBlockX(), pos2.getBlockX()), Math.min(pos1.getBlockY(), pos2.getBlockY()), Math.min(pos1.getBlockZ(), pos2.getBlockZ()));
        Vector max = new Vector(Math.max(pos1.getBlockX(), pos2.getBlockX()), Math.max(pos1.getBlockY(), pos2.getBlockY()), Math.max(pos1.getBlockZ(), pos2.getBlockZ()));
        int xDiff = max.getBlockX() - min.getBlockX();
        int yDiff = max.getBlockY() - min.getBlockY();
        int zDiff = max.getBlockZ() - min.getBlockZ();

        Block block;
        for (int x = 0; x <= xDiff; x++) {
            for (int z = 0; z <= zDiff; z++) {
                for (int y = 0; y <= yDiff; y++) {
                    block = pos1.getWorld().getBlockAt(min.getBlockX() + x, min.getBlockY() + y, min.getBlockZ() + z);
                    if (filterList == null) {
                        blocks.add(block);
                    } else if (filterList.contains(block.getType())) {
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }

    public static void createSphere(Location center, Material mat, byte data, float radius, boolean overrideBlocks, boolean hollow) {
        //Vector centerVec = center.toVector();
        Vector zeroVec = new Vector(0,0,0);
        Vector blockVec;
        Location blockLoc;
        for (float x = -radius; x <= radius; x++) {
            for (float y = -radius; y <= radius; y++) {
                for (float z = -radius; z <= radius; z++) {
                    blockVec = new Vector(x, y, z);

                    double distance = blockVec.distance(zeroVec);
                    if (distance > radius) {
                        continue;
                    }

                    if (hollow && distance < radius - 1) {
                        continue;
                    }

                    blockLoc = center.clone().add(blockVec);
                    if (!overrideBlocks && blockLoc.getBlock().getType() != Material.AIR) {
                        continue;
                    }

                    blockLoc.getBlock().setTypeIdAndData(mat.getId(), data, false);
                }
            }
        }
    }

    /**
     * Set the tab header/footer.
     * Call this method on login to send it to the player.
     * @param player The player to send the header/footer to.
     * @param header The header text
     * @param footer The footer text
     */
    public static void setTab(Player player, String header, String footer) {
        if (header == null) {
            header = "";
        }
        header = ChatColor.translateAlternateColorCodes('&', header);

        if (footer == null) {
            footer = "";
        }
        footer = ChatColor.translateAlternateColorCodes('&', footer);

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

        try {
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, tabFoot);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.sendPacket(headerPacket);
        }
    }

    public static double lerp(double start, double end, double perc) {
        if (Double.isNaN(perc) || perc > 1) {
            return end;
        } else if (perc < 0) {
            return start;
        } else {
            return start * (1 - perc) + end * perc;
        }
    }

    public static Vector lerp(Vector start, Vector end, double perc) {
        Vector newvec = new Vector();
        newvec.setX(lerp(start.getX(), end.getX(), perc));
        newvec.setY(lerp(start.getY(), end.getY(), perc));
        newvec.setZ(lerp(start.getZ(), end.getZ(), perc));
        return newvec;
    }

    public static Location lerp(Location start, Location end, double perc) {
        Location newloc = new Location(start.getWorld(), 0, 0, 0);
        newloc.setX(lerp(start.getX(), end.getX(), perc));
        newloc.setY(lerp(start.getY(), end.getY(), perc));
        newloc.setZ(lerp(start.getZ(), end.getZ(), perc));
        newloc.setYaw((float) lerp(start.getYaw(), end.getYaw(), perc));
        newloc.setPitch((float) lerp(start.getPitch(), end.getPitch(), perc));
        return newloc;
    }

    public static String getName(UUID playerUUID) {
        if (playerUUID != null) {
            OfflinePlayer oplayer = Bukkit.getOfflinePlayer(playerUUID);
            if (oplayer != null) {
                return oplayer.getName();
            }
        }
        return null;
    }

    public static void resetPlayer(Player player, GameMode gameMode) {
        clearPlayerInv(player, true);
        removePotionEffects(player);

        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setSaturation(10);

        player.setFlying(false);
        player.setAllowFlight(false);
        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.1f);

        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0);

        player.setGameMode(gameMode);

        player.updateInventory();
    }

    public static void clearPlayerInv(Player player, boolean armorSlots) {
        player.getInventory().clear();
        if (armorSlots) {
            player.getInventory().setHelmet(new CWItem(Material.AIR));
            player.getInventory().setChestplate(new CWItem(Material.AIR));
            player.getInventory().setLeggings(new CWItem(Material.AIR));
            player.getInventory().setBoots(new CWItem(Material.AIR));
        }
    }

    public static void removePotionEffects(Player player) {
        Collection<PotionEffect> effects = player.getActivePotionEffects();
        for (PotionEffect pe : effects) {
            player.removePotionEffect(pe.getType());
        }
    }
}
