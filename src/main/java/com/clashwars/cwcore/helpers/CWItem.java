package com.clashwars.cwcore.helpers;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.utils.CWUtil;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CWItem extends ItemStack {

    /**
     * Create a CWItem from a bukkit ItemStack.
     * @param itemStack The bukkit itemstack to use.
     */
    public CWItem(ItemStack itemStack) {
        setType(itemStack.getType());
        setAmount(itemStack.getAmount());
        setData(itemStack.getData());
        setDurability(itemStack.getDurability());
        addUnsafeEnchantments(itemStack.getEnchantments());
        if (itemStack.hasItemMeta()) {
            setItemMeta(itemStack.getItemMeta());
        }
    }

    /**
     * Create new Item by ID
     * Should try use Material instead of this.
     * @param ID The item ID
     */
    public CWItem(int ID) {
        super(ID);
    }

    /**
     * Create new Item by Material
     * @param material The item material
     */
    public CWItem(Material material) {
        super(material);
    }

    /**
     * Create new Item by ID
     * Should try use Material instead of this.
     * @param ID The item ID.
     * @param amount The amount of items.
     */
    public CWItem(int ID, int amount) {
        super(ID, amount);
    }

    /**
     * Create new Item by Material
     * @param material The item material
     * @param amount The amount of items.
     */
    public CWItem(Material material, int amount) {
        super(material, amount);
    }

    /**
     * Create new Item by ID
     * Should try use Material instead of this.
     * @param ID The item ID.
     * @param amount The amount of items.
     * @param durability The item durability/data.
     */
    public CWItem(int ID, int amount, short durability) {
        super(ID, amount, durability);
    }

    /**
     * Create new Item by Material
     * @param material The item material
     * @param amount The amount of items.
     * @param durability The item durability/data.
     */
    public CWItem(Material material, int amount, short durability) {
        super(material, amount, durability);
    }

    /**
     * Create new Item by ID
     * Should try use Material instead of this.
     * @param ID The item ID.
     * @param amount The amount of items.
     * @param durability The item durability/data.
     * @param name The item display name.
     */
    public CWItem(int ID, int amount, short durability, String name) {
        super(ID, amount, durability);
        setName(name);
    }

    /**
     * Create new Item by Material
     * @param material The item material
     * @param amount The amount of items.
     * @param durability The item durability/data.
     * @param name The item display name.
     */
    public CWItem(Material material, int amount, short durability, String name) {
        super(material, amount, durability);
        setName(name);
    }

    /**
     * Create new Item by ID
     * Should try use Material instead of this.
     * @param ID The item ID.
     * @param amount The amount of items.
     * @param durability The item durability/data.
     * @param name The item display name.
     * @param lore The item lore.
     */
    public CWItem(int ID, int amount, short durability, String name, String[] lore) {
        super(ID, amount, durability);
        setName(name);
        setLore(lore);
    }

    /**
     * Create new Item by Material
     * @param material The item material
     * @param amount The amount of items.
     * @param durability The item durability/data.
     * @param name The item display name.
     * @param lore The item lore.
     */
    public CWItem(Material material, int amount, short durability, String name, String[] lore) {
        super(material, amount, durability);
        setName(name);
        setLore(lore);
    }


    // NAME

    /**
     * Set the name of the item.
     * @param name The name to set.
     * @return CWItem
     */
    public CWItem setName(String name) {
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(CWUtil.integrateColor(name));
        setItemMeta(meta);
        return this;
    }

    /**
     * Get the name of the name.
     * If no displayname is set it will get the name from material aliases.
     * @return The name.
     */
    public String getName() {
        if (hasItemMeta() && getItemMeta().hasDisplayName()) {
            return getItemMeta().getDisplayName();
        } else {
            return CWCore.inst().getMaterials().getDisplayName(getType(), getDurability());
        }
    }


    // LORE

    /**
     * Set the lore of the the item.
     * @param lore List of lore strings.
     * @return CWItem
     */
    public CWItem setLore(List<String> lore) {
        if (hasItemMeta()) {
            for (int i = 0; i < lore.size(); i++) {
                lore.set(i,  CWUtil.integrateColor(lore.get(i)));
            }

            ItemMeta meta = getItemMeta();
            meta.setLore(lore);
            setItemMeta(meta);
        }
        return this;
    }

    /**
     * Get the lore of the item.
     * @return The lore
     */
    public List<String> getLore() {
        if (hasItemMeta() && getItemMeta().hasLore()) {
            return getItemMeta().getLore();
        }
        return null;
    }

    /**
     * Set the lore of the the item.
     * @param alore Array of lore strings.
     * @return CWItem
     */
    public CWItem setLore(String[] alore) {
        List<String> lore = new ArrayList<String>();
        Collections.addAll(lore, alore);
        return setLore(lore);
    }

    /**
     * Get the lore of the item.
     * @return The lore
     */
    public String[] getLoreArray() {
        if (hasItemMeta() && getItemMeta().hasLore()) {
            return getItemMeta().getLore().toArray(new String[getItemMeta().getLore().size()]);
        }
        return null;
    }

    /**
     * Set a specific line in the lore.
     * All lines above the specified line will be set blank if they don't exist.
     * It will override existing lore at the specified line number.
     * @param lineNr The line to set the loreTxt at.
     * @param loreTxt The text to set in the lore.
     * @return CWItem
     */
    public CWItem setLore(int lineNr, String loreTxt) {
        if (hasItemMeta()) {
            ItemMeta meta = getItemMeta();
            List<String> lore = meta.getLore();
            int lines = Math.max(lineNr + 1, lore.size());
            for (int i = 0; i < lines; i++) {
                if (i >= lore.size()) {
                    lore.add("");
                }
                if (i == lineNr) {
                    lore.set(i, CWUtil.integrateColor(loreTxt));
                }
            }
            meta.setLore(lore);
            setItemMeta(meta);
        }
        return this;
    }

    /**
     * Get the lore text at the specified line.
     * @param lineNr The line to get the text at.
     * @return Lore text or null if it has no lore.
     */
    public String getLore(int lineNr) {
        List<String> lore = getLore();
        if (lore == null || lineNr >= lore.size()) {
            return null;
        } else {
            return lore.get(lineNr);
        }
    }

    /**
     * Add a line of lore to the current lore.
     * If it has no lore it will be created.
     * @param loreTxt The lore text to add.
     * @return CWItem
     */
    public CWItem addLore(String loreTxt) {
        if (hasItemMeta()) {
            ItemMeta meta = getItemMeta();

            List<String> lore = meta.getLore();
            if (!meta.hasLore() || lore == null) {
                lore = new ArrayList<String>();
            }
            lore.add(CWUtil.integrateColor(loreTxt));

            meta.setLore(lore);
            setItemMeta(meta);
        }
        return this;
    }


    // SKULL

    /**
     * Set the owner of a skull.
     * The item must be a SKULL_ITEM material.
     * The durability/data will be set to 3 so it will work on any skull.
     * @param playerName The name of the player to set as skull.
     * @return CWItem
     */
    public CWItem setSkullOwner(String playerName) {
        if (getType() == Material.SKULL_ITEM) {
            setDurability((short) 3);
            SkullMeta smeta = (SkullMeta)getItemMeta();
            smeta.setOwner(playerName);
            setItemMeta(smeta);
        }
        return this;
    }

    /**
     * Get the skull owner if it's a skull and has a owner.
     * @return The skull owner or null if it has no owner or is no skull.
     */
    public String getSkullOwner() {
        if (getType() == Material.SKULL_ITEM && hasItemMeta() && getItemMeta() instanceof SkullMeta) {
            return ((SkullMeta) getItemMeta()).getOwner();
        }
        return null;
    }



    // LEATHER COLOR

    /**
     * Set the color of leather armor by a hex value.
     * The hex value can be with or without the '#'
     * @param hexColor The color like 'BD19AA' or '#ffffff'
     * @return CWItem
     */
    public CWItem setLeatherColor(String hexColor) {
        int c = 0;
        if (hexColor.contains("#")) {
            String[] str = hexColor.split("#");
            if (str[1].matches("[0-9A-Fa-f]+")) {
                c = Integer.parseInt(str[1], 16);
            }
        } else {
            if (hexColor.matches("[0-9A-Fa-f]+")) {
                c = Integer.parseInt(hexColor, 16);
            }
        }
        return setLeatherColor(Color.fromRGB(c));
    }

    /**
     * Set the color of leather armor by a RGB value.
     * @param r The red value 0-255
     * @param g The green value 0-255
     * @param b The blue value 0-255
     * @return CWItem
     */
    public CWItem setLeatherColor(int r, int g, int b) {
        return setLeatherColor(Color.fromRGB(r, g, b));
    }

    /**
     * Set the color of leather armor by a Color.
     * @param color The color.
     * @return CWItem
     */
    public CWItem setLeatherColor(Color color) {
        if (getType() == Material.LEATHER_BOOTS || getType() == Material.LEATHER_LEGGINGS || getType() == Material.LEATHER_CHESTPLATE || getType() == Material.LEATHER_HELMET) {
            LeatherArmorMeta lmeta = (LeatherArmorMeta) getItemMeta();
            lmeta.setColor(color);
            setItemMeta(lmeta);
        }
        return this;
    }



    //UTILS


    /**
     * Give the item(s) to the specified player.
     * It will unstack items if they can't be stacked.
     * And it will drop the items on the ground if the inventory is full.
     * @param player The player to give the items to.
     */
    public void giveToPlayer(Player player) {
        CWItem itemClone = clone();
        itemClone.setAmount(1);
        for (int i = 0; i < getAmount(); i++) {
            //Add item to inventory and if it can't be added drop it.
            if (!player.getInventory().addItem(itemClone).isEmpty()) {
                player.getWorld().dropItem(player.getLocation(), itemClone);
            }
        }
    }

    public CWItem makeGlowing() {
        //TODO: Make this with protocol stuff...
        return this;
    }

    /**
     * Replace all \n symbols in the lore with new lines in the lore.
     * @return CWItem
     */
    public CWItem replaceLoreNewLines() {
        if (getLore() != null && getLore().size() > 0) {
            List<String> loreList = new ArrayList<String>();
            List<String> loreClone = getLore();
            for (String lore : loreClone) {
                loreList.addAll(CWUtil.splitToList(lore, "\\|\\|", false));
        }
            setLore(loreList);
        }
        return this;
    }

    /**
     * Clone the CWItem item.
     * @return A new CWItem
     */
    public CWItem clone() {
        return (CWItem)super.clone();
    }
}
