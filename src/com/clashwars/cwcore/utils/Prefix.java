package com.clashwars.cwcore.utils;

public enum Prefix {
    DARK_BLUE("&1", "DarkBlue"),
    DARK_GREEN("&2", "DarkGreen"),
    DARK_AQUA("&3", "Teal"),
    DARK_RED("&4", "DarkRed"),
    PURPLE("&5", "Purple"),
    GOLD("&6", "Gold"),
    LIGHT_GRAY("&7", "LightGray"),
    DARK_GRAY("&8", "DarkGray"),
    BLUE("&9", "Blue"),
    BLACK("&0", "Black"),
    GREEN("&a", "Green"),
    AQUA("&b", "Aqua"),
    RED("&c", "Red"),
    PINK("&d", "Pink"),
    YELLOW("&e", "Yellow"),
    WHITE("&f", "White");

    public String color;
    public String name;

    Prefix(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public static Prefix fromString(String name) {
        name = name.toLowerCase().replace("_","");
        for (Prefix p : values()) {
            if (p.toString().toLowerCase().replace("_","").equals(name)) {
                return p;
            }
        }

        name = name.toLowerCase();
        for (Prefix p : values()) {
            if (p.name.toLowerCase().equals(name)) {
                return p;
            }
            if (name.contains(p.color)) {
                return p;
            }
        }
        return null;
    }



}
