package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.Effect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.MathUtils;
import com.clashwars.cwcore.utils.RandomUtils;
import com.clashwars.cwcore.utils.VectorUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class EarthEffect extends Effect {

    /**
     * Precision of generation. Higher numbers have better results, but increase the time of generation. Don't pick Number above 10.000
     */
    public int precision = 100;

    /**
     * Amount of Particles to form the World
     */
    public int particles = 500;

    /**
     * Radius of the World
     */
    public float radius = 1;

    /**
     * Height of the mountains.
     */
    public float mountainHeight = .5f;

    /**
     * Current step. Works as counter
     */
    protected int step = 0;

    /**
     * Triggers invalidation on first run
     */
    protected boolean firstStep = true;

    /**
     * Caches vectors to increase performance
     */
    protected final Set<Vector> cacheGreen, cacheBlue;

    public EarthEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 5;
        iterations = 200;
        cacheGreen = new HashSet<Vector>();
        cacheBlue = new HashSet<Vector>();
    }

    public void invalidate() {
        firstStep = false;
        cacheGreen.clear();
        cacheBlue.clear();

        Set<Vector> cache = new HashSet<Vector>();
        int sqrtParticles = (int) Math.sqrt(particles);
        float theta = 0, phi, thetaStep = MathUtils.PI / sqrtParticles, phiStep = MathUtils.PI2 / sqrtParticles;
        for (int i = 0; i < sqrtParticles; i++) {
            theta += thetaStep;
            phi = 0;
            for (int j = 0; j < sqrtParticles; j++) {
                phi += phiStep;
                float x = radius * MathUtils.sin(theta) * MathUtils.cos(phi);
                float y = radius * MathUtils.sin(theta) * MathUtils.sin(phi);
                float z = radius * MathUtils.cos(theta);
                cache.add(new Vector(x, y, z));
            }
        }

        float increase = mountainHeight / precision;
        for (int i = 0; i < precision; i++) {
            double r1 = RandomUtils.getRandomAngle(), r2 = RandomUtils.getRandomAngle(), r3 = RandomUtils.getRandomAngle();
            for (Vector v : cache) {
                if (v.getY() > 0) {
                    v.setY(v.getY() + increase);
                } else {
                    v.setY(v.getY() - increase);
                }
                if (i != precision - 1)
                    VectorUtils.rotateVector(v, r1, r2, r3);
            }
        }

        float minSquared = Float.POSITIVE_INFINITY, maxSquared = Float.NEGATIVE_INFINITY;
        for (Vector current : cache) {
            float lengthSquared = (float) current.lengthSquared();
            if (minSquared > lengthSquared)
                minSquared = lengthSquared;
            if (maxSquared < lengthSquared)
                maxSquared = lengthSquared;
        }

        // COLOR PARTICLES
        float average = (minSquared + maxSquared) / 2;
        for (Vector v : cache) {
            float lengthSquared = (float) v.lengthSquared();
            if (lengthSquared >= average) cacheGreen.add(v);
            else cacheBlue.add(v);
        }
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        if (firstStep) invalidate();
        for (Vector v : cacheGreen) {
            ParticleEffect.HAPPY_VILLAGER.display(location.add(v), visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt*2);
            location.subtract(v);
        }
        for (Vector v : cacheBlue) {
            ParticleEffect.DRIP_WATER.display(location.add(v), visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
            location.subtract(v);
        }
    }
}