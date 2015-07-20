package com.clashwars.cwcore.damage.log;

import com.clashwars.cwcore.damage.Iattacker;
import com.clashwars.cwcore.utils.CWUtil;
import com.clashwars.cwcore.events.CustomDamageEvent;

import java.util.*;

public class DamageLog {

    public UUID logOwner;
    public String deathMsg;
    public Long deathTime;
    public List<DamageLogEntry> log = new ArrayList<DamageLogEntry>();

    public DamageLog(UUID logOwner) {
        this.logOwner = logOwner;
    }

    public void updateLog(CustomDamageEvent event) {
        if (event.getPlayer().getUniqueId().equals(logOwner)) {
            log.add(new DamageLogEntry(event.getDmgClass(), event.getPlayer().getHealth(), true));
        }

        if (event.getDmgClass() instanceof Iattacker) {
            if (((Iattacker)event.getDmgClass()).hasAttacker()) {
                if (((Iattacker)event.getDmgClass()).getAttacker().getUniqueId().equals(logOwner)) {
                    log.add(new DamageLogEntry(event.getDmgClass(), event.getPlayer().getHealth(), false));
                }
            }
        }
    }

    public List<String> getDmgMessages() {
        List<String> logMessages = new ArrayList<String>();
        for (DamageLogEntry logEntry : log) {
            if (logEntry.dmgClass.getDmg() == 0) {
                continue;
            }
            if (logEntry.dmgTaken) {
                logMessages.add("&4&l-" + CWUtil.round((float)logEntry.dmgClass.getDmg(), 2) + " &c" + logEntry.dmgClass.getDmgMsg(logEntry.dmgTaken) + " &8[&6" +
                        CWUtil.round((float)logEntry.health, 2) + "&7>&e" + CWUtil.round((float)(logEntry.health - logEntry.dmgClass.getDmg()), 2) + "&8]");
            } else {
                logMessages.add("&4&l-" + CWUtil.round((float)logEntry.dmgClass.getDmg(), 2) + " &a" + logEntry.dmgClass.getDmgMsg(logEntry.dmgTaken).replace(" by", "") + " &8[&6" +
                        CWUtil.round((float)logEntry.health, 2) + "&7>&e" + CWUtil.round((float)(logEntry.health - logEntry.dmgClass.getDmg()), 2) + "&8]");
            }
        }
        return logMessages;
    }

}
