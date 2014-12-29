package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.*;
import com.clashwars.cwcore.utils.RandomUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Creates a cloud with rain/snow<br>
 * <b>DEFAULTS:</b><br>
 * cloudSize = .7f
 * particleRadius = cloudSize-.1f
 * yOffset = .8
 * <br>
 * type = EffectType.REPEATING<br>
 * period = 5<br>
 * iterations = 50<br>
 */
public class CloudEffect extends BaseEffect {
	
	/*
	 * Size of the cloud
	 */
	public float cloudSize = .7f;
	
	/*
	 * Radius of the rain/snow
	 */
	public float particleRadius = cloudSize-.1f;
	
	/*
	 * Y-Offset from location
	 */
	public double yOffset = .8;
	
	public CloudEffect(EffectManager manager){
		super(manager);
		type = EffectType.REPEATING;
		period = 5;
		iterations = 50;
	}
	
	@Override
	public void onRun(){
		Location location = getLocation();
		location.add(0, yOffset, 0);
		for(int i = 0; i < 50; i++){
			Vector v = RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextDouble() * cloudSize);
            location.add(v);
            for (Particle particle : secondaryParticleList) {
                particle.display(location, visibleRange);
            }
			location.subtract(v);
		}
		Location l = location.add(0, .2, 0);
		for(int i = 0; i < 15; i++){
			int r = RandomUtils.random.nextInt(2);
			double x = RandomUtils.random.nextDouble() * particleRadius;
			double z = RandomUtils.random.nextDouble() * particleRadius;
			l.add(x, 0, z);
			if (r!=1) {
                for (Particle particle : particleList) {
                    particle.display(l, visibleRange);
                }
            }
			l.subtract(x, 0, z);
			l.subtract(x, 0, z);
			if(r!=1) {
                for (Particle particle : particleList) {
                    particle.display(l, visibleRange);
                }
            }
			l.add(x, 0, z);
		}
	}

}
