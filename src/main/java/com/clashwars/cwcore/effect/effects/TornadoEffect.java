package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.Effect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.RandomUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class TornadoEffect extends Effect{
	
	/*
	 * Tornado particle
	 */
	public ParticleEffect tornadoParticle = ParticleEffect.FLAME;
	
	/*
	 * Particle of the cloud
	 */
	public ParticleEffect cloudParticle = ParticleEffect.CLOUD;
	
	/*
	 * Size of the cloud
	 */
	public float cloudSize = 2.5f;
	
	/*
	 * Y-Offset from location
	 */
	public double yOffset = .8;
	
	/*
	 * Height of the Tornado
	 */
	public float tornadoHeight = 5f;
	
	/*
	 * Max radius of the Tornado
	 */
	public float maxTornadoRadius = 5f;
	
	/*
	 * Should the cloud appear?
	 */
	public boolean showCloud = true;
	
	/*
	 * Should the tornado appear?
	 */
	public boolean showTornado = true;
    
    /*
     * Distance between each row
     */
    public double distance = .375d;
    
    /*
     * Internal counter
     */
    protected int step = 0;
	
	public TornadoEffect(EffectManager manager){
		super(manager);
		type = EffectType.REPEATING;
		period = 5;
		iterations = 20;
	}
	
	@Override
	public void onRun(){
		Location l = getLocation().add(0, yOffset, 0);
		for(int i = 0; i < (100 * cloudSize); i++){
			Vector v = RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextDouble() * cloudSize);
			if(showCloud){
                cloudParticle.display(l.add(v), visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
				l.subtract(v);
			}
		}
		Location t = l.clone().add(0, .2, 0);
		double r = .45 * (maxTornadoRadius*(2.35/tornadoHeight));
		for(double y = 0; y < tornadoHeight; y+=distance){
			double fr = r * y;
			if(fr > maxTornadoRadius)
				fr = maxTornadoRadius;
			for(Vector v : createCircle(y, fr)){
				if(showTornado){
                    tornadoParticle.display(t.add(v), visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
					t.subtract(v);
					step++;
				}
			}
		}
		l.subtract(0, yOffset, 0);
	}
	
	public ArrayList<Vector> createCircle(double y, double radius){
		double amount = radius * 64;
		double inc = (2*Math.PI)/amount;
		ArrayList<Vector> vecs = new ArrayList<Vector>();
		for(int i = 0; i < amount; i++){
			double angle = i * inc;
			double x = radius * Math.cos(angle);
			double z = radius * Math.sin(angle);
			Vector v = new Vector(x, y, z);
			vecs.add(v);
		}
		return vecs;
	}

}
