package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.BaseEffect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Sound;

public class BoilEffect extends BaseEffect {

    public ParticleEffect particle = ParticleEffect.BUBBLE;
    public Sound sound = Sound.LAVA;
    public float soundPitch, soundVolume, popPitch, popVolume = 1f;
    public int soundDelay = 15;
    protected float step = 0;

    public BoilEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 8;
        iterations = -1;
    }

    @Override
    public void onRun() {
        step++;
        Location location = getLocation();

        if (step % soundDelay == 0) {
            location.getWorld().playSound(location, sound, soundVolume, soundPitch);
            ParticleEffect.SMOKE.display(location.add(0,1,0), visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
        }

        particle.display(location, visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
    }
}
