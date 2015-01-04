package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.*;
import com.clashwars.cwcore.utils.MathUtils;
import com.clashwars.cwcore.utils.VectorUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VortexEffect extends BaseEffect {

    /**
     * Radius of vortex (2)
     */
    public float radius = 2;

    /**
     * Growing per iteration (0.05)
     */
    public float grow = .05f;

    /**
     * Radials per iteration (PI / 16)
     */
    public double radials = Math.PI / 16;

    /**
     * Helix-circles per iteration (3)
     */
    public int circles = 3;

    /**
     * Amount of helices (4)
     * Yay for the typo
     */
    public int helixes = 4;

    /**
     * Current step. Works as counter
     */
    protected int step = 0;

    public VortexEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 1;
        iterations = 200;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        for (int x = 0; x < circles; x++) {
            for (int i = 0; i < helixes; i++) {
                double angle = step * radials + (2 * Math.PI * i / helixes);
                Vector v = new Vector(Math.cos(angle) * radius, step * grow, Math.sin(angle) * radius);
                VectorUtils.rotateAroundAxisX(v, (location.getPitch() + 90) * MathUtils.degreesToRadians);
                VectorUtils.rotateAroundAxisY(v, -location.getYaw() * MathUtils.degreesToRadians);

                location.add(v);
                for (Particle particle : particleList) {
                    particle.display(location, visibleRange);
                }
                location.subtract(v);
            }
            step++;
        }
    }

}