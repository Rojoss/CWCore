package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.*;
import com.clashwars.cwcore.utils.VectorUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Creates a circle that can orbit around.<br>
 * <b>DEFAULTS:</b><br>
 * itemID = xRotation, yRotation, zRotation = 0<br>
 * angularVelocityX = Math.PI / 200<br>
 * angularVelocityY = Math.PI / 170<br>
 * angularVelocityZ = Math.PI / 155<br>
 * radius = .4f<br>
 * enableRotation = true<br>
 * particles = 20<br>
 * <br>
 * type = EffectType.REPEATING<br>
 * period = 4<br>
 * iterations = 25<br>
 */
public class AnimatedCircleEffect extends BaseEffect {

    /*
     * Rotation of the torus.
     */
    public double xRotation, yRotation, zRotation = 0;
    
    /*
     * Turns the cube by this angle each iteration around the x-axis
     */
    public double angularVelocityX = Math.PI / 200;

    /*
     * Turns the cube by this angle each iteration around the y-axis
     */
    public double angularVelocityY = Math.PI / 170;

    /*
     * Turns the cube by this angle each iteration around the z-axis
     */
    public double angularVelocityZ = Math.PI / 155;

    /*
     * Radius of circle above head
     */
    public float radius = .4f;

    /*
     * Current step. Works as a counter
     */
    protected float step = 0;
   
    /*
     * Subtracts from location if needed
     */
    public double xSubtract, ySubtract, zSubtract;
    
    /*
     * Should it rotate?
     */
    public boolean enableRotation = true;
    
    /*
     * Amount of particles per circle
     */
    public int particles = 20;

    public AnimatedCircleEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 2;
        iterations = 50;
    }
    
    @Override
    public void onRun(){
    	Location location = getLocation();
    	location.subtract(xSubtract, ySubtract, zSubtract);
    	double inc = (2*Math.PI)/particles;
    	double angle = step * inc;
		Vector v = new Vector();
		v.setX(Math.cos(angle) * radius);
    	v.setZ(Math.sin(angle) * radius);
    	VectorUtils.rotateVector(v, xRotation, yRotation, zRotation);
		if(enableRotation)
			VectorUtils.rotateVector(v, angularVelocityX * step, angularVelocityY * step, angularVelocityZ * step);

        location.add(v);
        for (Particle particle : particleList) {
            particle.display(location, visibleRange);
        }
        location.subtract(v);

		step++;
    }

}
