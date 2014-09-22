package com.clashwars.cwcore.config.aliases;

import com.clashwars.cwcore.config.internal.EasyConfig;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Entities extends EasyConfig {

    public Map<String, ArrayList<String>> entities = new HashMap<String, ArrayList<String>>();

    public Entities(String fileName) {
        this.setFile(fileName);
    }

    /**
     * Update the entities list with all available bukkit entities.
     */
    public void update() {
        for (EntityType entity : EntityType.values()) {
            String entityName = entity.toString();
            if (!entities.containsKey(entityName)) {
                ArrayList<String> aliases = new ArrayList<String>();

                String name = "";
                String[] split = entityName.split("_");
                for (String str : split) {
                    name += CWUtil.capitalize(str);
                }
                aliases.add(name);

                entities.put(entityName, aliases);
            }

        }
        save();
    }

    /**
     * Get a entity by alias.
     * @param alias
     * @return EntityType
     */
    public EntityType getEntity(String alias) {
        for (String entityName : entities.keySet()) {
            if (alias.equalsIgnoreCase(entityName) || alias.equalsIgnoreCase(entityName.replace("_", ""))) {
                return EntityType.valueOf(entityName);
            }

            for (String s : entities.get(entityName)) {
                if (s.equalsIgnoreCase(alias)) {
                    return EntityType.valueOf(entityName);
                }
            }
        }
        return null;
    }

    /**
     * Get a display name for the specified entity.
     * It will use the first value in the aliases config.
     * @param entity
     * @return Display name for entity
     */
    public String getDisplayName(EntityType entity) {
        String entityName = entity.toString();
        if (!entities.containsKey(entityName) || entities.get(entityName).size() < 1) {
            String name = "";
            String[] split = entity.toString().split("_");
            for (String str : split) {
                name += CWUtil.capitalize(str);
            }
            return name;
        }
        return entities.get(entityName).get(0);
    }
}
