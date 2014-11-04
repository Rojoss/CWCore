package com.clashwars.cwcore.cuboid;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.helpers.CWItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Cuboid selection
 * Has 2 locations that can be set with '/cww'
 * Can be used to get a Cuboid.
 */
public class Selection {

    private CWItem basicWand = new CWItem(Material.STONE_AXE, 1, (byte)0, "&6Basic Wand", new String[] {"&7Select CW cuboids."});
    private CWItem multiWand = new CWItem(Material.IRON_AXE, 1, (byte)0, "&6Multi Wand", new String[] {"&7Select both CW cuboids and WorldEdit."});

    Map<UUID, SelectionData> selections = new HashMap<UUID, SelectionData>();


    /**
     * Get a Cuboid from the selection.
     * If only one of the locations is set a 1*1*1 cuboid will be returned at that location.
     * @return Cuboid or null if both locations aren't set.
     */
    public Cuboid getSelection(Player player) {
        UUID uuid = player.getUniqueId();
        if (!selections.containsKey(uuid)) {
            selections.put(uuid, new SelectionData());
        }
        SelectionData sd = selections.get(uuid);
        if (sd.pos1 != null && sd.pos2 != null) {
            return new Cuboid(sd.pos1, sd.pos2);
        } else if (sd.pos1 != null) {
            return new Cuboid(sd.pos1, sd.pos1);
        } else if (sd.pos2 != null) {
            return new Cuboid(sd.pos2, sd.pos2);
        } else {
            return null;
        }
    }


    /**
     * Get the first selected position.
     * If the position isn't set it'll return null.
     * @return Location of pos1 or null
     */
    public Location getPos1(Player player) {
        UUID uuid = player.getUniqueId();
        if (!selections.containsKey(uuid)) {
            selections.put(uuid, new SelectionData());
        }
        return selections.get(uuid).pos1;
    }

    /**
     * Set the first position to the specified location.
     * @param location The location used to be set as pos1.
     */
    public void setPos1(Player player, Location location) {
        UUID uuid = player.getUniqueId();
        if (!selections.containsKey(uuid)) {
            selections.put(uuid, new SelectionData());
        }
        selections.get(uuid).pos1 = location;
    }


    /**
     * Get the second selected position.
     * If the position isn't set it'll return null.
     * @return Location of pos2 or null
     */
    public Location getPos2(Player player) {
        UUID uuid = player.getUniqueId();
        if (!selections.containsKey(uuid)) {
            selections.put(uuid, new SelectionData());
        }
        return selections.get(uuid).pos2;
    }

    /**
     * Set the second position to the specified location.
     * @param location The location used to be set as pos2.
     */
    public void setPos2(Player player, Location location) {
        UUID uuid = player.getUniqueId();
        if (!selections.containsKey(uuid)) {
            selections.put(uuid, new SelectionData());
        }
        selections.get(uuid).pos2 = location;
    }


    /**
     * Get a enum value that shows which points have been set.
     * It'll return BOTH, NONE, POS1 or POS2.
     * @return SetPoints enum value based on points set.
     */
    public SetPoints getSetPoints(Player player) {
        UUID uuid = player.getUniqueId();
        if (!selections.containsKey(uuid)) {
            selections.put(uuid, new SelectionData());
        }
        SelectionData sd = selections.get(uuid);
        if (sd.pos1 != null && sd.pos2 != null) {
            return SetPoints.BOTH;
        } else if (sd.pos1 != null) {
            return SetPoints.POS1;
        } else if (sd.pos2 != null) {
            return SetPoints.POS2;
        } else {
            return SetPoints.NONE;
        }
    }


    /**
     * Get the item used for basic selection.
     * @return CWItem
     */
    public CWItem getWand() {
        return basicWand;
    }


    public enum SetPoints {
        BOTH, NONE, POS1, POS2;
    }
}
