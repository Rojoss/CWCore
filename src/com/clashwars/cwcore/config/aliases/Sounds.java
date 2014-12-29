package com.clashwars.cwcore.config.aliases;

import com.clashwars.cwcore.config.internal.EasyConfig;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sounds extends EasyConfig {

    public Map<String, ArrayList<String>> sounds = new HashMap<String, ArrayList<String>>();

    public Sounds(String fileName) {
        this.setFile(fileName);
    }

    /**
     * Update the sounds list with all available bukkit sounds.
     */
    public void update() {
        for (Sound sound : Sound.values()) {
            String soundName = sound.toString();
            if (!sounds.containsKey(soundName)) {
                ArrayList<String> aliases = new ArrayList<String>();

                String name = "";
                String[] split = soundName.split("_");
                for (String str : split) {
                    name += CWUtil.capitalize(str);
                }
                aliases.add(name);

                sounds.put(soundName, aliases);
            }

        }
        save();
    }

    /**
     * Get a sound by alias.
     * @param alias
     * @return Sound
     */
    public Sound getSound(String alias) {
        for (String soundName : sounds.keySet()) {
            if (alias.equalsIgnoreCase(soundName) || alias.equalsIgnoreCase(soundName.replace("_", ""))) {
                return Sound.valueOf(soundName);
            }
            for (String s : sounds.get(soundName)) {
                if (s.equalsIgnoreCase(alias)) {
                    return Sound.valueOf(soundName);
                }
            }
        }
        return null;
    }

    /**
     * Get a display name for the specified sound.
     * It will use the first value in the aliases config.
     * @param sound
     * @return Display name for sound
     */
    public String getDisplayName(Sound sound) {
        String soundName = sound.toString();
        if (!sounds.containsKey(soundName) || sounds.get(soundName).size() < 1) {
            String name = "";
            String[] split = sound.toString().split("_");
            for (String str : split) {
                name += CWUtil.capitalize(str);
            }
            return name;
        }
        return sounds.get(soundName).get(0);
    }
}
