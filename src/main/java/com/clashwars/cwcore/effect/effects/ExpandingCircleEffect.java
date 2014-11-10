package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.BaseEffect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Creates a basic circle.<br>
 * <b>DEFAULTS:</b><br>
 * particle = ParticleEffect.HAPPY_VILLAGER<br>
 * diameter = .4f<br>
 * stepSize = 5<br>
 */
public class ExpandingCircleEffect extends BaseEffect {

    /*
     * ParticleType of spawned particle
     */
    public ParticleEffect particle = ParticleEffect.HAPPY_VILLAGER;

    /*
     * Distance between rings
     */
    public float distanceBetweenRings = 0.4F;

    /*
     * Stage count
     */
    public int step = 1;

    /*
     * Degrees to increase per iteration
     */
    public int degreesPerIteration = 18;

    public ExpandingCircleEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 10;
        iterations = 20;
    }

    @Override
    public void onRun() {
        if(step > iterations) {
            return;
        }
        for (int degrees = 0; degrees <= 360; degrees += degreesPerIteration) {
            Vector v = new Vector();
            v.setX(distanceBetweenRings * step * Math.cos(degrees));
            v.setZ(distanceBetweenRings * step * Math.sin(degrees));
            Location location = getLocation().clone();
            particle.display(location.add(v), visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
        }
        step++;
    }


}
