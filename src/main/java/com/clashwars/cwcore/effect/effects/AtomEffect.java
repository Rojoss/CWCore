package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.Effect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.RandomUtils;
import com.clashwars.cwcore.utils.VectorUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Creates an atom.<br>
 * <b>DEFAULTS:</b><br>
 * particleNucleus = ParticleEffect.DRIP_WATER<br>
 * particleOrbital = ParticleEffect.DRIP_LAVA<br>
 * radius = 3<br>
 * radiusNucleus = .2f<br>
 * particlesNucleus = 10<br>
 * particlesOrbital = 10<br>
 * orbitals = 3<br>
 * rotation = 0<br>
 * angularVelocity = Math.PI / 80d<br>
 * <br>
 * type = EffectType.REPEATING<br>
 * period = 2<br>
 * iterations = 200<br>
 */
public class AtomEffect extends Effect {

    /**
     * ParticleType of the nucleus
     */
    public ParticleEffect particleNucleus = ParticleEffect.DRIP_WATER;

    /**
     * ParticleType of orbitals
     */
    public ParticleEffect particleOrbital = ParticleEffect.DRIP_LAVA;

    /**
     * Radius of the atom
     */
    public int radius = 3;

    /**
     * Radius of the nucleus as a fraction of the atom-radius
     */
    public float radiusNucleus = .2f;

    /**
     * Particles to be spawned in the nucleus per iteration
     */
    public int particlesNucleus = 10;

    /**
     * Particles to be spawned per orbital per iteration
     */
    public int particlesOrbital = 10;

    /**
     * Orbitals around the nucleus
     */
    public int orbitals = 3;

    /**
     * Rotation around the Y-axis
     */
    public double rotation = 0;

    /**
     * Velocity of the orbitals
     */
    public double angularVelocity = Math.PI / 80d;

    /**
     * Internal counter
     */
    protected int step = 0;

    public AtomEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 2;
        iterations = 200;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        for (int i = 0; i < particlesNucleus; i++) {
            Vector v = RandomUtils.getRandomVector().multiply(radius * radiusNucleus);
            location.add(v);
            particleNucleus.display(location, visibleRange, 0, 0, 0, 0, 0);
            location.subtract(v);
        }
        for (int i = 0; i < particlesOrbital; i++) {
            double angle = step * angularVelocity;
            for (int j = 0; j < orbitals; j++) {
                double xRotation = (Math.PI / orbitals) * j;
                Vector v = new Vector(Math.cos(angle), Math.sin(angle), 0).multiply(radius);
                VectorUtils.rotateAroundAxisX(v, xRotation);
                VectorUtils.rotateAroundAxisY(v, rotation);
                location.add(v);
                particleOrbital.display(location, visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
                location.subtract(v);
            }
            step++;
        }
    }

}
