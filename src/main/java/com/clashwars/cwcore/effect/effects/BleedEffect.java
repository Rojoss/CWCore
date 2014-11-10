package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.BaseEffect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.utils.RandomUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Creates a bunch of fireworks and effects.<br>
 * <b>DEFAULTS:</b><br>
 * hurt = true<br>
 * itemID = 152<br>
 * <br>
 * type = EffectType.REPEATING<br>
 * period = 4<br>
 * iterations = 25<br>
 */
public class BleedEffect extends BaseEffect {

    /**
     * Play the Hurt Effect for the Player
     */
    public boolean hurt = true;

    /**
     * Color of blood. Default is red (152)
     */
    public int itemID = 152;

    public BleedEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 4;
        iterations = 25;
    }

    @Override
    public void onRun() {
        // Location to spawn the blood-item.
        Location location = getLocation();
        location.add(0, RandomUtils.random.nextFloat() * 1.75f, 0);
        location.getWorld().playEffect(location, org.bukkit.Effect.STEP_SOUND, itemID);

        Entity entity = getEntity();
        if (hurt && entity != null) {
            entity.playEffect(org.bukkit.EntityEffect.HURT);
        }
    }
}
