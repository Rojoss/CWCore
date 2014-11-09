package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.Effect;
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
public class CircleEffect extends Effect {

    /*
     * ParticleType of spawned particle
     */
    public ParticleEffect particle = ParticleEffect.HAPPY_VILLAGER;

    /*
     * Diameter of circle
     */
    public float diameter = .4f;

    /*
     * Degrees to increase per iteration.
     */
    public int stepSize = 5;


    public CircleEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.INSTANT;
    }

    @Override
    public void onRun() {
        for (int step = 0; step <= 360; step += stepSize) {
            Vector v = new Vector();
            v.setX(diameter * Math.cos(step));
            v.setZ(diameter * Math.sin(step));
            Location location = getLocation().clone();
            particle.display(location.add(v).add(((double)diameter/2)-1, 0, ((double)diameter/2)-1), visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
        }
    }
}
