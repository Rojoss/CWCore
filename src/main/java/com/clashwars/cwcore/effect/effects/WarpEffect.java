package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.Effect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import org.bukkit.Location;

public class WarpEffect extends Effect {

    /**
     * Radius of the spawned circles
     */
    public float radius = 1;

    /**
     * Particles per circle
     */
    public int particles = 20;

    /**
     * Particle to display
     */
    public ParticleEffect particle = ParticleEffect.FIREWORKS_SPARK;

    /**
     * Interval of the circles
     */
    public float grow = .2f;

    /**
     * Circles to display
     */
    public int rings = 12;

    /**
     * Internal counter
     */
    protected int step = 0;

    public WarpEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 2;
        iterations = rings;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        if (step > rings)
            step = 0;
        double x, y, z;
        y = step * grow;
        location.add(0, y, 0);
        for (int i = 0; i < particles; i++) {
            double angle = (double) 2 * Math.PI * i / particles;
            x = Math.cos(angle) * radius;
            z = Math.sin(angle) * radius;
            location.add(x, 0, z);
            particle.display(location, visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
            location.subtract(x, 0, z);
        }
        location.subtract(0, y, 0);
        step++;
    }

}
