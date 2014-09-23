package com.clashwars.cwcore.config.aliases;

import com.clashwars.cwcore.config.internal.EasyConfig;
import com.clashwars.cwcore.reflection.ParticleEffect;
import com.clashwars.cwcore.utils.CWUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Particles extends EasyConfig {

    public Map<String, ArrayList<String>> particles = new HashMap<String, ArrayList<String>>();

    public Particles(String fileName) {
        this.setFile(fileName);
    }

    /**
     * Update the particles list with all available bukkit particles.
     */
    public void update() {
        for (ParticleEffect particle : ParticleEffect.values()) {
            String particleName = particle.toString();
            if (!particles.containsKey(particleName)) {
                ArrayList<String> aliases = new ArrayList<String>();

                String name = "";
                String[] split = particleName.split("_");
                for (String str : split) {
                    name += CWUtil.capitalize(str);
                }
                aliases.add(name);

                particles.put(particleName, aliases);
            }

        }
        save();
    }

    /**
     * Get a particle by alias.
     * @param alias
     * @return ParticleEffect
     */
    public ParticleEffect getParticle(String alias) {
        for (String particleName : particles.keySet()) {
            if (alias.equalsIgnoreCase(particleName) || alias.equalsIgnoreCase(particleName.replace("_", ""))) {
                return ParticleEffect.valueOf(particleName);
            }

            for (String s : particles.get(particleName)) {
                if (s.equalsIgnoreCase(alias)) {
                    return ParticleEffect.valueOf(particleName);
                }
            }
        }
        return null;
    }

    /**
     * Get a display name for the specified particle.
     * It will use the first value in the aliases config.
     * @param particle
     * @return Display name for particle
     */
    public String getDisplayName(ParticleEffect particle) {
        String particleName = particle.toString();
        if (!particles.containsKey(particleName) || particles.get(particleName).size() < 1) {
            String name = "";
            String[] split = particle.toString().split("_");
            for (String str : split) {
                name += CWUtil.capitalize(str);
            }
            return name;
        }
        return particles.get(particleName).get(0);
    }
}
