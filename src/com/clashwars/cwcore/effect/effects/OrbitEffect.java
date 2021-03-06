package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.*;
import org.bukkit.Location;

public class OrbitEffect extends BaseEffect {

    /**
     * Radials to spawn next note.
     */
    public double radialsPerStep = Math.PI / 8;

    /**
     * Radius of circle above head
     */
    public float radius = .4f;

    public float heightPerStep = 1.9f;

    /**
     * Current step. Works as a counter
     */
    protected float step = 0;

    public OrbitEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        iterations = 400;
        period = 1;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        location.add(0, heightPerStep, 0);
        location.add(Math.cos(radialsPerStep * step) * radius, 0, Math.sin(radialsPerStep * step) * radius);
        for (Particle particle : particleList) {
            particle.display(location, visibleRange);
        }
        step++;
    }

}
