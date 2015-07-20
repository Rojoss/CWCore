package com.clashwars.cwcore.damage;

import org.bukkit.OfflinePlayer;

public interface Iattacker {
    boolean hasAttacker();
    OfflinePlayer getAttacker();
}
