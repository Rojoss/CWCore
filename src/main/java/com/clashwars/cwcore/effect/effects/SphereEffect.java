package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.Effect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.RandomUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class SphereEffect extends Effect {
    /**
     * ParticleType of spawned particle
     */
    public ParticleEffect particle = ParticleEffect.MOB_SPELL;

    /**
     * Radius of the sphere
     */
    public double radius = 0.6;

    /**
     * Y-Offset of the sphere
     */
    public double yOffset = 0;

    /**
     * Particles to display
     */
    public int particles = 50;

    public SphereEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        iterations = 500;
        period = 1;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        location.add(0, yOffset, 0);
        for (int i = 0; i < particles; i++) {
            Vector vector = RandomUtils.getRandomVector().multiply(radius);
            location.add(vector);
            particle.display(location, visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);;
            location.subtract(vector);
        }
    }

}