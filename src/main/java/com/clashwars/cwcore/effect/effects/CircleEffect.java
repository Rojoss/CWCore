package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.Effect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.VectorUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Creates a circle that can orbit around.<br>
 * <b>DEFAULTS:</b><br>
 * particle = ParticleEffect.HAPPY_VILLAGER<br>
 * itemID = xRotation, yRotation, zRotation = 0<br>
 * radius = .4f<br>
 * particles = 20<br>
 * <br>
 * type = EffectType.REPEATING<br>
 * period = 4<br>
 * iterations = 25<br>
 */
public class CircleEffect extends Effect {

    /*
     * ParticleType of spawned particle
     */
    public ParticleEffect particle = ParticleEffect.HAPPY_VILLAGER;

    /*
     * Rotation of the torus.
     */
    public double xRotation, yRotation, zRotation = 0;

    /*
     * Radius of circle above head
     */
    public float radius = .4f;

    /*
     * Current step. Works as a counter
     */
    protected float step = 0;

    /*
     * Subtracts from location if needed
     */
    public double xSubtract, ySubtract, zSubtract;

    /*
     * Amount of particles per circle
     */
    public int particles = 20;

    public CircleEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.INSTANT;
        period = 1;
        iterations = 200;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        location.subtract(xSubtract, ySubtract, zSubtract);
        double inc = (2*Math.PI)/particles;
        double angle = step * inc;
        Vector v = new Vector();
        v.setX(radius * Math.cos(angle));
        v.setZ(radius * Math.sin(angle));
        VectorUtils.rotateVector(v, xRotation, yRotation, zRotation);
        particle.display(location.add(v), visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
        step++;
    }

}
