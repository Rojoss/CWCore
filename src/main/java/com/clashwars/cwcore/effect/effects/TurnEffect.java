package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.BaseEffect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class TurnEffect extends BaseEffect {

    /**
     * Angular movement per iteration
     */
    public float step = 11.25f;

    public TurnEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 1;
        iterations = (int) (360 * 5 / step);
    }

    @Override
    public void onRun() {
        Entity entity = getEntity();
        if (entity == null) {
            cancel();
            return;
        }
        Location loc = entity.getLocation();
        loc.setYaw(loc.getYaw() + step);
        entity.teleport(loc);
    }

}
