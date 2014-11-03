package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.Effect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Location;
import org.bukkit.Sound;

public class BoilEffect extends Effect {

    public ParticleEffect particle = ParticleEffect.BUBBLE;
    public Sound sound = Sound.LAVA;
    public Sound popSound = Sound.LAVA_POP;
    public float soundPitch, soundVolume, popPitch, popVolume = 1f;
    public int soundDelay = 5;
    public int popDelayMin = 10;
    public int popDelayMax = 50;
    protected float step = 0;
    protected int popDelay;

    public BoilEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 4;
        iterations = -1;
    }

    @Override
    public void onRun() {
        step++;
        Location location = getLocation();

        if (popDelay < 1) {
            popDelay = CWUtil.random(popDelayMin, popDelayMax);
        }

        if (step % soundDelay == 0) {
            location.getWorld().playSound(location, sound, soundVolume, soundPitch);
        }
        if (step % period == 0) {
            location.getWorld().playSound(location, popSound, popVolume, popPitch);
            popDelay = CWUtil.random(popDelayMin, popDelayMax);
        }

        particle.display(location, visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
    }
}
