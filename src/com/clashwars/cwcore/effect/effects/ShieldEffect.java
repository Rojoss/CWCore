package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.*;
import com.clashwars.cwcore.utils.RandomUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ShieldEffect extends BaseEffect {

    /**
     * Radius of the shield
     */
    public int radius = 3;

    /**
     * Particles to display
     */
    public int particles = 50;

    /**
     * Set to false for a half-sphere and true for a complete sphere
     */
    public boolean sphere = false;

    public ShieldEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        iterations = 500;
        period = 1;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        for (int i = 0; i < particles; i++) {
            Vector vector = RandomUtils.getRandomVector().multiply(radius);
            if (!sphere)
                vector.setY(Math.abs(vector.getY()));
            location.add(vector);
            for (Particle particle : particleList) {
                particle.display(location, visibleRange);
            }
            location.subtract(vector);
        }
    }

}
