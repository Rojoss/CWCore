package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.*;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Creates a basic circle.<br>
 * <b>DEFAULTS:</b><br>
 * diameter = .4f<br>
 * stepSize = 5<br>
 */
public class ExpandingCircleEffect extends BaseEffect {

    /**
     * Distance between rings
     */
    public float distanceBetweenRings = 0.4F;

    /**
     * Stage count
     */
    public int step = 1;

    /**
     * Degrees to increase per iteration
     */
    public int degreesPerIteration = 18;

    public ExpandingCircleEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 10;
        iterations = 5;
    }

    @Override
    public void onRun() {
        for (int degrees = 0; degrees <= 360; degrees += degreesPerIteration) {
            Vector v = new Vector();
            v.setX(distanceBetweenRings * step * Math.cos(degrees));
            v.setZ(distanceBetweenRings * step * Math.sin(degrees));
            Location location = getLocation().clone();
            location.add(v);
            for (Particle particle : particleList) {
                particle.display(location, visibleRange);
            }
        }
        step++;
    }


}
