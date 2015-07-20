package com.clashwars.cwcore.damage.types;

import com.clashwars.cwcore.damage.BaseDmg;
import com.clashwars.cwcore.damage.DmgType;
import com.clashwars.cwcore.damage.Iattacker;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class MeleeDmg extends BaseDmg implements Iattacker {

    private OfflinePlayer attacker;

    public MeleeDmg(Player player, double damage, OfflinePlayer attacker) {
        super(player, damage);

        this.attacker = attacker;
        type = DmgType.MELEE;

        damage();
    }

    @Override
    public String getDeathMsg() {
        String deathMsg = "{0} was killed by {1}";
        return deathMsg.replace("{0}", player.getName()).replace("{1}", attacker.getName());
    }

    @Override
    public String getDmgMsg(boolean damageTaken) {
        if (damageTaken) {
            return "hit by " + attacker.getName();
        }
        return "hit " + player.getName();
    }

    public OfflinePlayer getAttacker() {
        return attacker;
    }

    public boolean hasAttacker() {
        return attacker != null;
    }
}