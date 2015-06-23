package com.clashwars.cwcore.hat;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.helpers.CWEntity;
import com.clashwars.cwcore.helpers.CWItem;
import com.clashwars.cwcore.helpers.EntityTag;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class Hat {

    private Player owner;
    private CWItem item;
    private boolean equipped = false;

    private ArmorStand stand;
    private CWEntity entity;
    private EntityType entityType;
    private Item hatItem;
    private Vector offset = new Vector(0,2f,0);


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

        stand = owner.getWorld().spawn(owner.getLocation().add(offset), ArmorStand.class);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setSmall(true);
        stand.setBasePlate(false);
        stand.setArms(false);
        CWEntity cwStand = new CWEntity(stand);
        cwStand.setTag(EntityTag.MARKER, 1);

        if (item != null) {
            hatItem = owner.getWorld().dropItem(owner.getLocation(), item);
            hatItem.setPickupDelay(Integer.MAX_VALUE);
            hatItem.setVelocity(new Vector(0, 0, 0));
            CWCore.inst().getEntityHider().hideEntity(owner, hatItem);
            stand.setPassenger(hatItem);
        }

        if (entityType != null) {
            entity = CWEntity.create(entityType == null ? EntityType.BAT : entityType, owner.getLocation());
            entity.setSize(-1);
            entity.setAge(-1);
            entity.setTag(EntityTag.NO_AI, 1);
            entity.setTag(EntityTag.SILENT, 1);
            entity.setTag(EntityTag.INVULNERABLE, 1);
            stand.setPassenger(entity.entity());
        }

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
        if (stand != null) {
            stand.remove();
        }
        equipped = false;
    }

    public void remove() {
        unequip();
        HatManager.removeHat(owner);
    }

    public void setOffset(Vector offset) {
        this.offset = offset;
    }

    public void setOffset(double x, double y, double z) {
        offset.setX(x);
        offset.setY(y);
        offset.setZ(z);
    }

    public Vector getOffset() {
        return offset;
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

    public ArmorStand getStand() {
        return stand;
    }
}
