package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.*;
import com.clashwars.cwcore.utils.MathUtils;
import com.clashwars.cwcore.utils.RandomUtils;
import com.clashwars.cwcore.utils.VectorUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Creates a cone.<br>
 * <b>DEFAULTS:</b><br>
 * lengthGrow = .05f
 * angularVelocity = Math.PI / 16
 * particles = 10
 * radiusGrow = 0.006f
 * particlesCone = 180
 * rotation = 0
 * randomize = false
 * <br>
 * type = EffectType.REPEATING<br>
 * period = 1<br>
 * iterations = 200<br>
 */
public class ConeEffect extends BaseEffect {

    /**
     * Growing per iteration in the length (0.05)
     */
    public float lengthGrow = .05f;

    /**
     * Radials per iteration to spawn the next particle (PI / 16)
     */
    public double angularVelocity = Math.PI / 16;

    /**
     * Cone-particles per interation (10)
     */
    public int particles = 10;

    /**
     * Growth in blocks per iteration on the radius (0.006)
     */
    public float radiusGrow = 0.006f;

    /**
     * Conesize in particles per cone
     */
    public int particlesCone = 180;

    /**
     * Start-angle or rotation of the cone
     */
    public double rotation = 0;

    /**
     * Randomize every cone on creation (false)
     */
    public boolean randomize = false;

    /**
     * Current step. Works as counter
     */
    protected int step = 0;

    public ConeEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 1;
        iterations = 200;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        for (int x = 0; x < particles; x++) {
            if (step > particlesCone)
                step = 0;
            if (randomize && step == 0)
                rotation = RandomUtils.getRandomAngle();
            double angle = step * angularVelocity + rotation;
            float radius = step * radiusGrow;
            float length = step * lengthGrow;
            Vector v = new Vector(Math.cos(angle) * radius, length, Math.sin(angle) * radius);
            VectorUtils.rotateAroundAxisX(v, (location.getPitch() + 90) * MathUtils.degreesToRadians);
            VectorUtils.rotateAroundAxisY(v, -location.getYaw() * MathUtils.degreesToRadians);

            location.add(v);
            for (Particle particle : particleList) {
                particle.display(location, visibleRange);
            }
            location.subtract(v);
            step++;
        }
    }
}
