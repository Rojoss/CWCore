package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.*;
import com.clashwars.cwcore.utils.RandomUtils;
import org.bukkit.Location;

public class SmokeEffect extends BaseEffect {

    public int particleCount = 20;

    public double maxDistance = 0.6d;
    public double maxYDistance = 2;

    public SmokeEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 1;
        iterations = 300;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        for (int i = 0; i < particleCount; i++) {
            location.add(RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextDouble() * maxDistance));
            location.add(0, RandomUtils.random.nextFloat() * maxYDistance, 0);
            for (Particle particle : particleList) {
                particle.display(location, visibleRange);
            }
        }
    }

}
