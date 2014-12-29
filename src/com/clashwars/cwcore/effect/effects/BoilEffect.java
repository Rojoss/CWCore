package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.*;
import org.bukkit.Location;
import org.bukkit.Sound;

public class BoilEffect extends BaseEffect {

    public Sound sound = Sound.LAVA;
    public float soundPitch, soundVolume = 1f;
    public int soundDelay = 15;
    protected float step = 0;

    public BoilEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 8;
        iterations = -1;
    }

    @Override
    public void onRun() {
        step++;
        Location location = getLocation();

        for (Particle particle : particleList) {
            particle.display(location, visibleRange);
        }

        if (step % soundDelay == 0) {
            for (Particle particle : secondaryParticleList) {
                particle.display(location, visibleRange);
            }
            location.getWorld().playSound(location, sound, soundVolume, soundPitch);
        }
    }
}
