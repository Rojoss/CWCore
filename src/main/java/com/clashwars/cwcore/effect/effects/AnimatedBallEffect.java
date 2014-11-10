package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.BaseEffect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.MathUtils;
import com.clashwars.cwcore.utils.VectorUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Creates an animated Sphere.. Thanks to the author (Qukie) for sharing it!
 * <br>
 * <b>DEFAULTS:</b><br>
 * particle = ParticleEffect.WITCH_MAGIC<br>
 * particles = 150<br>
 * particlesPerIteration = 10<br>
 * size = 1F<br>
 * xFactor = 1F, yFactor = 2F, zFactor = 1F<br>
 * xOffset, yOffset = 0.8F, zOffset<br>
 * xRotation, yRotation, zRotation = 0<br>
 * <br>
 * type = EffectType.REPEATING<br>
 * iterations = 500<br>
 * period = 1<br>
 */
public class AnimatedBallEffect extends BaseEffect {

    /**
     * ParticleType of spawned particle
     */
    public ParticleEffect particle = ParticleEffect.WITCH_MAGIC;

    /**
     * Ball particles total (150)
     */
    public int particles = 150;

    /**
     * The amount of particles, displayed in one iteration (10)
     */
    public int particlesPerIteration = 10;

    /**
     * Size of this ball (1)
     */
    public float size = 1F;

    /**
     * Factors (1, 2, 1)
     */
    public float xFactor = 1F, yFactor = 2F, zFactor = 1F;

    /**
     * Offsets (0, 0.8, 0)
     */
    public float xOffset, yOffset = 0.8F, zOffset;

    /**
     * Rotation of the ball.
     */
    public double xRotation, yRotation, zRotation = 0;

    /**
     * Internal Counter
     */
    protected int step;

    public AnimatedBallEffect(EffectManager effectManager) {
        super(effectManager);
        this.type = EffectType.REPEATING;
        this.iterations = 500;
        this.period = 1;
    }

    @Override
    public void onRun() {
        Vector vector = new Vector();
        Location location = getLocation();
        for (int i = 0; i < particlesPerIteration; i++) {
            step++;

            float t = (MathUtils.PI / particles) * step;
            float r = MathUtils.sin(t) * size;
            float s = 2 * MathUtils.PI * t;

            vector.setX(xFactor * r * MathUtils.cos(s) + xOffset);
            vector.setZ(zFactor * r * MathUtils.sin(s) + zOffset);
            vector.setY(yFactor * size * MathUtils.cos(t) + yOffset);

            VectorUtils.rotateVector(vector, xRotation, yRotation, zRotation);

            particle.display(location.add(vector), visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
            location.subtract(vector);
        }
    }

}
