package com.clashwars.cwcore.config.aliases;

import com.clashwars.cwcore.config.internal.EasyConfig;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Enchantments extends EasyConfig {

    public Map<String, ArrayList<String>> enchants = new HashMap<String, ArrayList<String>>();

    public Enchantments(String fileName) {
        this.setFile(fileName);
    }

    /**
     * Update the enchants list with all available bukkit enchantments.
     */
    public void update() {
        for (Enchantment enchant : Enchantment.values()) {
            String enchantName = enchant.toString();
            if (!enchants.containsKey(enchantName)) {
                ArrayList<String> aliases = new ArrayList<String>();

                String name = "";
                String[] split = enchantName.split("_");
                for (String str : split) {
                    name += CWUtil.capitalize(str);
                }
                aliases.add(name);

                enchants.put(enchantName, aliases);
            }

        }
        save();
    }

    /**
     * Get a enchant by alias.
     * @param alias
     * @return Enchantment
     */
    public Enchantment getenchant(String alias) {
        for (String enchantName : enchants.keySet()) {
            if (alias.equalsIgnoreCase(enchantName) || alias.equalsIgnoreCase(enchantName.replace("_", ""))) {
                return Enchantment.getByName(enchantName);
            }

            for (String s : enchants.get(enchantName)) {
                if (s.equalsIgnoreCase(alias)) {
                    return Enchantment.getByName(enchantName);
                }
            }
        }
        return null;
    }

    /**
     * Get a display name for the specified enchant.
     * It will use the first value in the aliases config.
     * @param enchant
     * @return Display name for enchant
     */
    public String getDisplayName(Enchantment enchant) {
        String enchantName = enchant.toString();
        if (!enchants.containsKey(enchantName) || enchants.get(enchantName).size() < 1) {
            String name = "";
            String[] split = enchant.toString().split("_");
            for (String str : split) {
                name += CWUtil.capitalize(str);
            }
            return name;
        }
        return enchants.get(enchantName).get(0);
    }
}
