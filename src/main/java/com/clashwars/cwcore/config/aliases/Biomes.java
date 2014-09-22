package com.clashwars.cwcore.config.aliases;

import com.clashwars.cwcore.config.internal.EasyConfig;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Biomes extends EasyConfig {

    public Map<String, ArrayList<String>> biomes = new HashMap<String, ArrayList<String>>();

    public Biomes(String fileName) {
        this.setFile(fileName);
    }

    /**
     * Update the biomes list with all available bukkit Biomes.
     */
    public void update() {
        for (Biome biome : Biome.values()) {
            String biomeName = biome.toString();
            if (!biomes.containsKey(biomeName)) {
                ArrayList<String> aliases = new ArrayList<String>();

                String name = "";
                String[] split = biomeName.split("_");
                for (String str : split) {
                    name += CWUtil.capitalize(str);
                }
                aliases.add(name);

                biomes.put(biomeName, aliases);
            }

        }
        save();
    }

    /**
     * Get a biome by alias.
     * @param alias
     * @return Biome
     */
    public Biome getBiome(String alias) {
        for (String biomeName : biomes.keySet()) {
            if (alias.equalsIgnoreCase(biomeName) || alias.equalsIgnoreCase(biomeName.replace("_", ""))) {
                return Biome.valueOf(biomeName);
            }

            for (String s : biomes.get(biomeName)) {
                if (s.equalsIgnoreCase(alias)) {
                    return Biome.valueOf(biomeName);
                }
            }
        }
        return null;
    }

    /**
     * Get a display name for the specified biome.
     * It will use the first value in the aliases config.
     * @param biome
     * @return Display name for biome
     */
    public String getDisplayName(Biome biome) {
        String biomeName = biome.toString();
        if (!biomes.containsKey(biomeName) || biomes.get(biomeName).size() < 1) {
            String name = "";
            String[] split = biome.toString().split("_");
            for (String str : split) {
                name += CWUtil.capitalize(str);
            }
            return name;
        }
        return biomes.get(biomeName).get(0);
    }
}
