package com.clashwars.cwcore.damage.types;

import com.clashwars.cwcore.damage.BaseDmg;
import com.clashwars.cwcore.damage.DmgType;
import com.clashwars.cwcore.damage.Iattacker;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;

public class RangedDmg extends BaseDmg implements Iattacker {

    private OfflinePlayer shooter;
    private EntityType projectileType = EntityType.ARROW;

    public RangedDmg(OfflinePlayer player, double damage, OfflinePlayer shooter) {
        super(player, damage);

        this.shooter = shooter;
        type = DmgType.RANGED;

        damage();
    }

    public RangedDmg(OfflinePlayer player, double damage, OfflinePlayer shooter, EntityType projectileType) {
        super(player, damage);

        this.shooter = shooter;
        this.projectileType = projectileType;
        type = DmgType.RANGED;
        damage();
    }

    @Override
    public String getDeathMsg() {
        String deathMsg = "{0} was shot by {1}'s {2}";
        return deathMsg.replace("{0}", player.getName()).replace("{1}", shooter.getName()).replace("{2}", projectileType.toString().toLowerCase().replace("_", " "));
    }

    @Override
    public String getDmgMsg(boolean damageTaken) {
        if (damageTaken) {
            return "shot by " + shooter.getName() + "'s " + projectileType.toString().toLowerCase().replace("_", " ");
        }
        return "shot " + player.getName() + " with an " + projectileType.toString().toLowerCase().replace("_", " ");
    }

    public OfflinePlayer getAttacker() {
        return shooter;
    }

    public boolean hasAttacker() {
        return shooter != null;
    }

    public EntityType getProjectileType() {
        return projectileType;
    }
}
