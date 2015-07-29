package com.clashwars.cwcore.utils;

public enum Prefix {
    DARK_BLUE("&1", "DarkBlue", (byte)12),
    DARK_GREEN("&2", "DarkGreen", (byte)13),
    DARK_AQUA("&3", "Teal", (byte)9),
    DARK_RED("&4", "DarkRed", (byte)14),
    PURPLE("&5", "Purple", (byte)10),
    GOLD("&6", "Gold", (byte)1),
    LIGHT_GRAY("&7", "LightGray", (byte)8),
    DARK_GRAY("&8", "DarkGray", (byte)7),
    BLUE("&9", "Blue", (byte)11),
    BLACK("&0", "Black", (byte)15),
    GREEN("&a", "Green", (byte)5),
    AQUA("&b", "Aqua", (byte)3),
    RED("&c", "Red", (byte)6),
    PINK("&d", "Pink", (byte)2),
    YELLOW("&e", "Yellow", (byte)4),
    WHITE("&f", "White", (byte)0);

    public String color;
    public String name;
    public byte colorData;

    Prefix(String color, String name, byte colorData) {
        this.color = color;
        this.name = name;
        this.colorData = colorData;
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

    public static Prefix fromColor(byte colorData) {
        for (Prefix p : values()) {
            if (p.colorData == colorData) {
                return p;
            }
        }
        return null;
    }

}
