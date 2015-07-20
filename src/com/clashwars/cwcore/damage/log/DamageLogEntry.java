package com.clashwars.cwcore.damage.log;

import com.clashwars.cwcore.damage.BaseDmg;

public class DamageLogEntry {

    public BaseDmg dmgClass;
    public double health;
    public boolean dmgTaken = false;
    public Long timestamp;

    public DamageLogEntry(BaseDmg dmgClass, double health, boolean dmgTaken) {
        this.dmgClass = dmgClass;
        this.health = health;
        this.dmgTaken = dmgTaken;
        timestamp = System.currentTimeMillis();
    }

}
