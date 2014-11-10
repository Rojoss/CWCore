package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.BaseEffect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.RandomUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Creates a cloud with rain/snow<br>
 * <b>DEFAULTS:</b><br>
 * cloudParticle = ParticleEffect.CLOUD<br>
 * mainParticle = ParticleEffect.DRIP_WATER<br>
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
	 * Particle of the cloud
	 */
	public ParticleEffect cloudParticle = ParticleEffect.CLOUD;
	
	/*
	 * Particle of the rain/snow
	 */
	public ParticleEffect mainParticle = ParticleEffect.DRIP_WATER;
	
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
			cloudParticle.display(location.add(v), visibleRange, 0, 0, 0, 0, 7);
			location.subtract(v);
		}
		Location l = location.add(0, .2, 0);
		for(int i = 0; i < 15; i++){
			int r = RandomUtils.random.nextInt(2);
			double x = RandomUtils.random.nextDouble() * particleRadius;
			double z = RandomUtils.random.nextDouble() * particleRadius;
			l.add(x, 0, z);
			if(r!=1)
				mainParticle.display(l, visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
			l.subtract(x, 0, z);
			l.subtract(x, 0, z);
			if(r!=1)
				mainParticle.display(l, visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
			l.add(x, 0, z);
		}
	}

}
