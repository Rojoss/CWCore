package com.clashwars.cwcore.utils;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.material.Directional;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

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
    public static String formatTime(long time, String syntax) {
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
        syntax = syntax.replace("%H", "" + hours);
        syntax = syntax.replace("%M", "" + mins);
        syntax = syntax.replace("%S", "" + secs);
        syntax = syntax.replace("%MS", "" + ms);
        syntax = syntax.replace("%%%", "" + fs);
        syntax = syntax.replace("%%", "" + ds);
        return syntax;
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




    //##########################################################################################
    //###################################  MISC UTILITIES  #####################################
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
}
