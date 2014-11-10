package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.BaseEffect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import org.bukkit.Location;

public class MusicEffect extends BaseEffect {

    /**
     * Radials to spawn next note.
     */
    public double radialsPerStep = Math.PI / 8;

    /**
     * Radius of circle above head
     */
    public float radius = .4f;

    /**
     * Current step. Works as a counter
     */
    protected float step = 0;

    public MusicEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        iterations = 400;
        period = 1;
        speed = .5f;
        amt = 1;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        location.add(0, 1.9f, 0);
        location.add(Math.cos(radialsPerStep * step) * radius, 0, Math.sin(radialsPerStep * step) * radius);
        ParticleEffect.NOTE.display(location, visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
        step++;
    }

}
