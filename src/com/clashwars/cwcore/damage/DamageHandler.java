package com.clashwars.cwcore.damage;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.events.CustomDamageEvent;
import com.clashwars.cwcore.events.CustomDeathEvent;
import com.clashwars.cwcore.helpers.CWItem;
import com.clashwars.cwcore.player.CWPlayer;
import com.clashwars.cwcore.utils.CWUtil;
import com.clashwars.cwcore.damage.log.DamageLog;
import com.clashwars.cwcore.damage.log.DamageLogEntry;
import com.clashwars.cwcore.damage.types.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class DamageHandler implements Listener {

    private CWCore cwc;

    public DamageHandler(CWCore cwc) {
        this.cwc = cwc;
    }


    @EventHandler
    private void customDmgTake(CustomDamageEvent event) {
        Player player = event.getPlayer();
        CWPlayer cwp = cwc.getPM().getPlayer(player);

        //Update damage log
        List<DamageLog> dmgLogs = cwp.damageLogs;
        if (dmgLogs == null || dmgLogs.size() < 1) {
            cwp.damageLogs.add(new DamageLog(player.getUniqueId()));
        }
        dmgLogs.get(dmgLogs.size() -1).updateLog(event);

        BaseDmg dmg = event.getDmgClass();
        if (!(dmg instanceof MeleeDmg) && !(dmg instanceof RangedDmg) && !(dmg instanceof EnvironmentDmg)) {
            if (dmg.getDmg() > 0) {
                player.damage(0);
            }
        }

        OfflinePlayer damager = null;
        if (dmg instanceof Iattacker) {
            damager = ((Iattacker)dmg).getAttacker();
        }

        if (damager != null) {
            List<DamageLog> dmgLogsDmger = cwc.getPM().getPlayer(damager).damageLogs;
            if (dmgLogsDmger == null || dmgLogsDmger.size() < 1) {
                cwc.getPM().getPlayer(damager).damageLogs.add(new DamageLog(damager.getUniqueId()));
            }
            dmgLogsDmger.get(dmgLogsDmger.size() - 1).updateLog(event);
        }
    }


    /* Cancel all damage and transform it into custom damage */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void damageTake(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        //event.setDamage(EntityDamageEvent.DamageModifier.MAGIC, event.getDamage(EntityDamageEvent.DamageModifier.MAGIC) / 100 * 125);

        final double dmg = event.getDamage();
        final double finalDmg = event.getFinalDamage();

        final Player player = (Player)event.getEntity();
        EntityDamageEvent.DamageCause cause = event.getCause();

        event.setDamage(0);

        if (event instanceof  EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent entityDmgByEntityEvent = (EntityDamageByEntityEvent)event;

            if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                if (entityDmgByEntityEvent.getDamager() instanceof Player) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            new MeleeDmg(player, finalDmg, (Player)entityDmgByEntityEvent.getDamager());
                        }
                    }.runTaskLater(cwc, 1);
                    return;
                }
                //TODO: MobDmg type with option to get owner of mob.
                //It can say something like Worstboy was killed by Kadowster's endermite.
            }

            if (cause == EntityDamageEvent.DamageCause.PROJECTILE) {
                if (entityDmgByEntityEvent.getDamager() instanceof Projectile) {
                    final Projectile proj = (Projectile)entityDmgByEntityEvent.getDamager();
                    if (proj.getShooter() instanceof Player) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                new RangedDmg(player, finalDmg, (Player)proj.getShooter(), ((EntityDamageByEntityEvent) event).getDamager().getType());
                            }
                        }.runTaskLater(cwc, 1);
                    }
                    return;
                }
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    new EnvironmentDmg(player, finalDmg, event.getCause());
                } else {
                    new EnvironmentDmg(player, dmg, event.getCause());
                }
            }
        }.runTaskLater(cwc, 1);
    }

    @EventHandler
    private void death(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        CWPlayer cwp = cwc.getPM().getPlayer(player);

        List<DamageLog> dmgLogs = cwp.damageLogs;
        DamageLog dmgLog = null;
        if (dmgLogs != null && dmgLogs.size() > 0) {
            dmgLog = cwp.damageLogs.get(dmgLogs.size() - 1);
        }

        OfflinePlayer killer = null;
        DamageLogEntry finalDamage = null;
        Long currTime = System.currentTimeMillis();
        if (dmgLog != null) {
            //Try find a killer from the damage log from the last 10 seconds.
            for (int i = dmgLog.log.size()-1; i >= 0; i--) {
                DamageLogEntry entry = dmgLog.log.get(i);
                if (!entry.dmgTaken) {
                    continue;
                }

                if (currTime - entry.timestamp > 10000) {
                    break;
                }

                if (entry.dmgClass instanceof Iattacker) {
                    if (((Iattacker)entry.dmgClass).hasAttacker()) {
                        finalDamage = entry;
                        killer = ((Iattacker)entry.dmgClass).getAttacker();
                        break;
                    }
                }
            }
            //If no killer was found the last 10 seconds use the last damage type.
            if (finalDamage == null) {
                finalDamage = dmgLog.log.get(dmgLog.log.size() -1);
            }
        }

        String deathMsg = player.getName() + " died";
        if (finalDamage != null) {
            deathMsg = finalDamage.dmgClass.getDeathMsg();
        }

        CustomDeathEvent customEvent = new CustomDeathEvent(player, deathMsg, dmgLog, finalDamage, killer);
        cwc.getServer().getPluginManager().callEvent(customEvent);

        //Save damage log and create new one.
        dmgLog.deathMsg = deathMsg;
        dmgLog.deathTime = System.currentTimeMillis();
        cwp.damageLogs.set(cwp.damageLogs.size()-1, dmgLog);
        cwc.getPM().getPlayer(event.getEntity()).damageLogs.add(new DamageLog(event.getEntity().getUniqueId()));
    }

}
