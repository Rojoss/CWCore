package com.clashwars.cwcore.effect.effects;

/* Idea by coco5843 */

import com.clashwars.cwcore.effect.*;
import com.clashwars.cwcore.utils.RandomUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class DiscoBallEffect extends BaseEffect {

    /**
     * Radius of the sphere
     */
    public float sphereRadius = .6f;

    /**
     * Min and max sizes of the lines
     */
    public int max = 15;

    /**
     * Max number of lines
     */
    public int maxLines = 7;

    /**
     * Max number of particles per line
     */
    public int lineParticles = 100, sphereParticles = 50;

    /**
     * Direction of the lines
     */
    public Direction direction = Direction.DOWN;

    public DiscoBallEffect(EffectManager manager){
        super(manager);
        type = EffectType.REPEATING;
        period = 7;
        iterations = 500;
    }

    public void onRun(){
        Location location = getLocation();
        //Lines
        int mL = RandomUtils.random.nextInt(maxLines-2)+2;
        for(int m = 0; m < mL*2; m++) {
            double x = RandomUtils.random.nextInt(max - max*(-1)) + max*(-1);
            double y = RandomUtils.random.nextInt(max - max*(-1)) + max*(-1);
            double z = RandomUtils.random.nextInt(max - max*(-1)) + max*(-1);
            if(direction == Direction.DOWN)
                y = RandomUtils.random.nextInt(max*2 - max) + max;
            else if(direction == Direction.UP)
                y = RandomUtils.random.nextInt(max*(-1) - max*(-2)) + max*(-2);
            Location target = location.clone().subtract(x, y, z);
            if (target == null) {
                cancel();
                return;
            }
            Vector link = target.toVector().subtract(location.toVector());
            float length = (float) link.length();
            link.normalize();

            float ratio = length / lineParticles;
            Vector v = link.multiply(ratio);
            Location loc = location.clone().subtract(v);
            for (int i = 0; i < lineParticles; i++) {
                loc.add(v);
                for (Particle particle : secondaryParticleList) {
                    particle.display(location, visibleRange);
                }
            }
        }

        //Sphere
        for (int i = 0; i < sphereParticles; i++) {
            Vector vector = RandomUtils.getRandomVector().multiply(sphereRadius);
            location.add(vector);
            for (Particle particle : particleList) {
                particle.display(location, visibleRange);
            }
            location.subtract(vector);
        }
    }

    public enum Direction{
        UP, DOWN, BOTH;
    }

}