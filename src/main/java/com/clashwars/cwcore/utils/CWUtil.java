package com.clashwars.cwcore.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CWUtil {

    private static Random random;

    static {
        random = new Random();
    }

    /**
     * Format a message with [CW] Prefix
     * @param msg
     * @return formatted message
     */
    public static String formatMsg(String msg) {
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
     * Integrate ChatColor in a list of strings based on color codes.
     * @param str
     * @return formatted string list
     */
    public static String[] integrateColor(String[] str) {
        for (int i = 0; i < str.length; i++) {
            for (ChatColor c : ChatColor.values()) {
                str[i] = str[i].replaceAll("&" + c.getChar() + "|&" + Character.toUpperCase(c.getChar()), c.toString());
            }
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


    /**
     * Remove Convert a string to a List<String> for lore based on \n symbols and add in colors etc.
     * @param loreStr
     * @return formatted string
     */
    public static List<String> loreFromString(String loreStr) {
        List<String> lore = null;
        loreStr = integrateColor(loreStr);
        String[] split = loreStr.split("\n");
        lore = Arrays.asList(split);
        return lore;
    }

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
     * Capitalize the first character of a string.
     * @param str
     * @return Capitalized string
     */
    public static String capitalize(String str) {
        if (str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

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

    public static Color getRandomColor() {
        //TODO: Implement
        return null;
    }
}
