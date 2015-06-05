package com.clashwars.cwcore.helpers;

public enum EntityTag {
    NO_AI("NoAI"),
    SILENT("Silent"),
    INVISIBLE("Invisible"),
    INVULNERABLE("Invulnerable"),
    MARKER("Marker");

    public String tag;

    EntityTag(String tag) {
        this.tag = tag;
    }
}
