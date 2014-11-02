package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.Effect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.utils.RandomUtils;
import org.bukkit.*;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

/**
 * Creates a bunch of fireworks and effects.<br>
 * <b>DEFAULTS:</b><br>
 * fireworkType = FireworkEffect.Type.BURST<br>
 * color = Color.RED<br>
 * color2 = Color.ORANGE<br>
 * color3 = Color.BLACK<br>
 * fadeColor = Color.BLACK<br>
 * intensity = 2<br>
 * radius = 2<br>
 * explosions = 10<br>
 * soundInterval = 5<br>
 * sound = Sound.EXPLODE<br>
 * soundVolume = 100<br>
 * soundPitch = 1<br>
 * <br>
 * type = EffectType.REPEATING<br>
 * period = 2<br>
 * iterations = 400<br>
 */
public class BigBangEffect extends Effect {

    public FireworkEffect.Type fireworkType = FireworkEffect.Type.BURST;
    public Color color = Color.RED;
    public Color color2 = Color.ORANGE;
    public Color color3 = Color.BLACK;
    public Color fadeColor = Color.BLACK;
    public int intensity = 2;
    public float radius = 2;
    public int explosions = 10;
    public int soundInterval = 5;
    public Sound sound = Sound.EXPLODE;
    public float soundVolume = 100;
    public float soundPitch = 1;
    protected int step = 0;

    protected FireworkEffect firework;

    public BigBangEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 2;
        iterations = 400;
    }

    @Override
    public void onRun() {
        if (firework == null) {
            Builder b = FireworkEffect.builder().with(fireworkType);
            b.withColor(color).withColor(color2).withColor(color3);
            b.withFade(fadeColor);
            b.trail(true);
            firework = b.build();
        }
        Location location = getLocation();
        for (int i = 0; i < explosions; i++) {
            Vector v = RandomUtils.getRandomVector().multiply(radius);
            detonate(location, v);
            if (soundInterval != 0 && step % soundInterval == 0)
                location.getWorld().playSound(location, sound, soundVolume, soundPitch);
        }
        step++;
    }

    protected void detonate(Location location, Vector v) {
        final Firework fw = (Firework) location.getWorld().spawnEntity(location.add(v), EntityType.FIREWORK);
        location.subtract(v);
        FireworkMeta meta = fw.getFireworkMeta();
        meta.setPower(0);
        for (int i = 0; i < intensity; i++) {
            meta.addEffect(firework);
        }
        fw.setFireworkMeta(meta);
        fw.detonate();
    }
}
