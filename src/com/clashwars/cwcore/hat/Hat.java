package com.clashwars.cwcore.hat;

import com.clashwars.cwcore.helpers.CWEntity;
import com.clashwars.cwcore.helpers.CWItem;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class Hat {

    private Player owner;
    private CWItem item;
    private boolean equipped = false;

    private CWEntity entity;
    private EntityType entityType;
    private Item hatItem;


    public Hat(Player owner, CWItem item) {
        //Register the hat and remove old one if player had one.
        if (HatManager.hasHat(owner)) {
            Hat prevHat = HatManager.getHat(owner);
            prevHat.remove();
        }
        HatManager.addHat(owner, this);

        this.owner = owner;
        this.item = item;
        equip();
    }

    public Hat(Player owner, EntityType entityType) {
        //Register the hat and remove old one if player had one.
        if (HatManager.hasHat(owner)) {
            Hat prevHat = HatManager.getHat(owner);
            prevHat.remove();
        }
        HatManager.addHat(owner, this);

        this.owner = owner;
        this.entityType = entityType;
        equip();
    }


    public void equip() {
        if (equipped) {
            return;
        }

        if (item != null) {
            hatItem = owner.getWorld().dropItem(owner.getLocation(), item);
            hatItem.setPickupDelay(Integer.MAX_VALUE);
            hatItem.setVelocity(new Vector(0, 0, 0));
        }

        entity = CWEntity.create(entityType == null ? EntityType.RABBIT : entityType, owner.getLocation());
        if (entityType == null) {
            entity.addPotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true, false);
        }
        entity.setAI(false);
        entity.setSilent(true);
        entity.setInvulnerable(true);

        owner.eject();
        owner.setPassenger(entity.entity());
        entity.entity().setPassenger(hatItem);

        equipped = true;
    }

    public void unequip() {
        if (!equipped) {
            return;
        }
        if (hatItem != null) {
            hatItem.remove();
        }
        if (entity != null) {
            entity.entity().remove();
        }
        equipped = false;
    }

    public void remove() {
        unequip();
        HatManager.removeHat(owner);
    }


    public Player getOwner() {
        return owner;
    }

    public CWItem getItem() {
        return item;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public Item getHatItem() {
        return hatItem;
    }

    public CWEntity getEntity() {
        return entity;
    }
}
