package com.clashwars.cwcore.damage.types;

import com.clashwars.cwcore.damage.BaseDmg;
import com.clashwars.cwcore.damage.DmgType;
import com.clashwars.cwcore.damage.Iattacker;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CustomDmg extends BaseDmg implements Iattacker {

    private OfflinePlayer damageSource;
    private String deathMsg;
    private String dmgMsg;

    public CustomDmg(Player player, double damage, String deathMessage, String dmgMsg) {
        super(player, damage);

        type = DmgType.CUSTOM;
        deathMsg = deathMessage;
        this.dmgMsg = dmgMsg;

        damage();
    }

    public CustomDmg(OfflinePlayer player, double damage, String deathMessage, String dmgMsg, OfflinePlayer damageSource) {
        super(player, damage);

        this.damageSource = damageSource;

        type = DmgType.CUSTOM;
        deathMsg = deathMessage;
        this.dmgMsg = dmgMsg;
        damage();
    }

    @Override
    public String getDeathMsg() {
        String msg = deathMsg;
        msg = msg.replace("{0}", player.getName());
        if (damageSource != null) {
            msg = msg.replace("{1}", damageSource.getName());
        }
        return msg;
    }

    @Override
    public String getDmgMsg(boolean damageTaken) {
        if (damageSource != null) {
            return dmgMsg.replace("{1}", damageSource.getName());
        }
        return dmgMsg;
    }

    public OfflinePlayer getAttacker() {
        return damageSource;
    }

    public boolean hasAttacker() {
        return damageSource != null;
    }
}