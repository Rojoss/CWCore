package com.clashwars.cwcore.config.aliases;

import com.clashwars.cwcore.config.internal.EasyConfig;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PotionEffects extends EasyConfig {

    public Map<String, ArrayList<String>> effects = new HashMap<String, ArrayList<String>>();

    public PotionEffects(String fileName) {
        this.setFile(fileName);
    }

    /**
     * Update the effects list with all available bukkit effects.
     */
    public void update() {
        for (PotionEffectType effect : PotionEffectType.values()) {
            if (effect == null || effect.getName() == null) {
                continue;
            }
            String effectName = effect.getName();
            if (!effects.containsKey(effectName)) {
                ArrayList<String> aliases = new ArrayList<String>();

                String name = "";
                String[] split = effectName.split("_");
                for (String str : split) {
                    name += CWUtil.capitalize(str);
                }
                aliases.add(name);

                effects.put(effectName, aliases);
            }

        }
        save();
    }

    /**
     * Get a effect by alias.
     * @param alias
     * @return PotionEffectType
     */
    public PotionEffectType getEffect(String alias) {
        for (PotionEffectType effectType : PotionEffectType.values()) {
            if (effectType == null) {
                continue;
            }
            if (effectType.getName().equalsIgnoreCase(alias) || effectType.getName().replace("_", "").equalsIgnoreCase(alias)) {
                return effectType;
            }
        }

        for (String effectName : effects.keySet()) {
            for (String a : effects.get(effectName)) {
                if (a.equalsIgnoreCase(alias)) {
                    return PotionEffectType.getByName(effectName);
                }
            }
        }
        return null;
    }

    /**
     * Get a display name for the specified effect.
     * It will use the first value in the aliases config.
     * @param effect
     * @return Display name for effect
     */
    public String getDisplayName(PotionEffectType effect) {
        String effectName = effect.getName();
        if (!effects.containsKey(effectName) || effects.get(effectName).size() < 1) {
            String name = "";
            String[] split = effect.getName().split("_");
            for (String str : split) {
                name += CWUtil.capitalize(str);
            }
            return name;
        }
        return effects.get(effectName).get(0);
    }
}
