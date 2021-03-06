package com.clashwars.cwcore.helpers;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CWItem extends ItemStack {

    private int slot = -1;

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
     * Create a CWItem from a string.
     * It uses the same formatting as essentials does.
     * http://ess.khhq.net/wiki/Item_Meta
     * Firework and book data is not yet supported.
     * @param itemStr The string with item data/meta.
     */
    public CWItem(String itemStr) {
        super(Material.AIR, 1);

        String[] split = itemStr.split(" ");
        if (itemStr.isEmpty() || split.length < 0) {
            return;
        }

        //Material
        Material mat = CWCore.inst().getMaterials().getMaterial(split[0]);
        if (mat != null) {
            setType(mat);
        }

        //Data
        int data = CWCore.inst().getMaterials().getMaterialData(split[0]);
        if (data >= 0) {
            setDurability((short)data);
        }

        //Amount
        if (split.length > 1) {
            int amt = CWUtil.getInt(split[1]);
            if (amt > 0) {
                setAmount(amt);
            }
        }

        //Extra data
        for (String str : split) {
            String[] split2 = str.split(":");
            if (split.length < 2) {
                continue;
            }

            String key = split[0].toLowerCase();
            String value = split[1];

            if (key.equals("name")) {
                setName(value);
                continue;
            }

            if (key.equals("lore")) {
                value.replace("_", " ");
                value.replace("__", "_");
                value.replace("\n", "|");
                setLore(value.split("/|"));
                continue;
            }

            if (key.equals("player")) {
                setSkullOwner(value);
            }

            if (key.equals("color")) {
                setLeatherColor(value);
            }

            Enchantment enchant = CWCore.inst().getEnchants().getenchant(key);
            if (enchant != null) {
                addEnchantment(enchant, Math.max(CWUtil.getInt(value), 0));
            }
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

    /**
     * Create a new Potion itemstack with multiple effects.
     * @param potion The potion type used as main effect.
     * @param splash Is it a splash potion?
     * @param amount Amount for the itemstack.
     */
    public CWItem(PotionType potion, boolean splash, int amount) {
        super(Material.POTION, amount);
        Potion pot = new Potion(potion);
        pot.setSplash(splash);
        pot.apply(this);
    }



    // SLOT

    /**
     * Set a slot number where this item needs to be set when giving it to the player.
     * It will override any item already in the specified slot.
     * Set the slot ID to -1 (default) so it's given normally.
     * @param slot The slot Id where the item should be set. -1 to disable.
     * @return CWItem
     */
    public CWItem setSlot(int slot) {
        this.slot = slot;
        return this;
    }

    /**
     * Get the slot ID specified with setSlot.
     * @return The slot ID. -1 if no slot has been specified.
     */
    public int getSlot() {
        return slot;
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



    // ENCHANTS

    /**
     * Add the specified enchantment to the item.
     * @param enchantment The Enchantment type.
     * @param level The level to enchant. (can be unsafe)
     * @return CWItem
     */
    public CWItem addEnchant(Enchantment enchantment, int level) {
        super.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Add all the specified enchantments in the hashmap to the item.
     * @param enchantments HashMap with enchantments
     * @return CWItem
     */
    public CWItem addEnchants(HashMap<Enchantment, Integer> enchantments) {
        super.addUnsafeEnchantments(enchantments);
        return this;
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

    public Color getLeatherColor() {
        if (getType() == Material.LEATHER_BOOTS || getType() == Material.LEATHER_LEGGINGS || getType() == Material.LEATHER_CHESTPLATE || getType() == Material.LEATHER_HELMET) {
            LeatherArmorMeta lmeta = (LeatherArmorMeta) getItemMeta();
            return lmeta.getColor();
        }
        return Color.WHITE;
    }


    // Banner stuff

    /**
     * Set the base color of the banner.
     * @param color The color.
     * @return CWItem
     */
    public CWItem setBaseColor(DyeColor color) {
        if (getType() == Material.BANNER) {
            BannerMeta bmeta = (BannerMeta) getItemMeta();
            bmeta.setBaseColor(color);
            setItemMeta(bmeta);
        }
        return this;
    }

    public CWItem setPattern(int index, Pattern pattern) {
        if (getType() == Material.BANNER) {
            BannerMeta bmeta = (BannerMeta) getItemMeta();
            if (bmeta.getPatterns().size() > index) {
                bmeta.setPattern(index, pattern);
            } else {
                bmeta.addPattern(pattern);
            }
            setItemMeta(bmeta);
        }
        return this;
    }

    public Pattern getPattern(int index) {
        if (getType() == Material.BANNER) {
            BannerMeta bmeta = (BannerMeta) getItemMeta();
            return bmeta.getPattern(index);
        }
        return null;
    }

    public CWItem setPatterns(List<Pattern> patterns) {
        if (getType() == Material.BANNER) {
            BannerMeta bmeta = (BannerMeta) getItemMeta();
            bmeta.setPatterns(patterns);
            setItemMeta(bmeta);
        }
        return this;
    }

    public List<Pattern> getPatterns() {
        if (getType() == Material.BANNER) {
            BannerMeta bmeta = (BannerMeta) getItemMeta();
            return bmeta.getPatterns();
        }
        return null;
    }




    //POTION EFFECTS
    public CWItem addPotionEffect(PotionEffectType effect, int amplifier, int duration) {
        if (getType() == Material.POTION) {
            PotionMeta pmeta = (PotionMeta)getItemMeta();
            pmeta.addCustomEffect(new PotionEffect(effect, duration, amplifier), true);
            setItemMeta(pmeta);
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
        if (slot >= 0) {
            player.getInventory().setItem(slot, this);
            return;
        }
        CWItem itemClone = clone();
        itemClone.setAmount(1);
        for (int i = 0; i < getAmount(); i++) {
            //Add item to inventory and if it can't be added drop it.
            if (!player.getInventory().addItem(itemClone).isEmpty()) {
                player.getWorld().dropItem(player.getLocation(), itemClone);
            }
        }
    }

    public CWItem hideTooltips() {
        ItemMeta meta = getItemMeta();
        for (ItemFlag itemFlag : ItemFlag.values()) {
            meta.addItemFlags(itemFlag);
        }
        setItemMeta(meta);
        return this;
    }

    public CWItem makeGlowing() {
        ItemMeta meta = getItemMeta();
        if (!meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        setItemMeta(meta);
        addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 0);
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
                loreList.addAll(CWUtil.splitToList(lore, "\\|", false));
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
