package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.Effect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.RandomUtils;
import org.bukkit.Location;

public class SmokeEffect extends Effect {

    /**
     * ParticleType of spawned particle
     */
    public ParticleEffect particle = ParticleEffect.SMOKE;

    public SmokeEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 1;
        iterations = 300;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        for (int i = 0; i < 20; i++) {
            location.add(RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextDouble() * 0.6d));
            location.add(0, RandomUtils.random.nextFloat() * 2, 0);
            particle.display(location, visibleRange);
        }
    }

}
