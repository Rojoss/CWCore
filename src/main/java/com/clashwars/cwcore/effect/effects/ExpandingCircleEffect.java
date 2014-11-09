package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ExpandingCircleEffect extends CircleEffect {

    /*
     * Amount of rings
     */
    public int rings = 4;

    /*
     * Distance between rings
     */
    public float distanceBetweenRings = 0.4F;

    public ExpandingCircleEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.INSTANT;
    }

    @Override
    public void onRun() {
        for(int x = 1; x <= rings; x++) {
            for (int step = 0; step <= 360; step += stepSize) {
                Vector v = new Vector();
                v.setX(distanceBetweenRings * x * Math.cos(step));
                v.setZ(distanceBetweenRings * x * Math.sin(step));
                Location location = getLocation().clone();
                particle.display(location.add(v), visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
            }
        }
    }


}
