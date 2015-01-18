package com.clashwars.cwcore.effect;

import com.clashwars.cwcore.packet.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Particle {

    ParticleEffect particleEffect = ParticleEffect.FLAME;
    float xOffset,yOffset,zOffset = 0;
    float speed = 0;
    int amount = 0;
    ParticleEffect.ParticleColor color = null;

    public Particle(ParticleEffect effect, float xOffset, float yOffset, float zOffset, float speed, int amount, ParticleEffect.ParticleColor color) {
        this.particleEffect = effect;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.speed = speed;
        this.amount = amount;
        this.color = color;
    }

    public Particle(ParticleEffect effect) {
        this.particleEffect = effect;
    }

    public Particle(ParticleEffect effect, float offset, float speed, int amount) {
        this(effect, offset, offset, offset, speed, amount);
    }

    public Particle(ParticleEffect effect, Vector offset, float speed, int amount) {
        this(effect, (float)offset.getX(), (float)offset.getY(), (float)offset.getZ(), speed, amount);
    }

    public Particle(ParticleEffect effect, float xOffset, float yOffset, float zOffset, float speed, int amount) {
        this(effect, xOffset, yOffset, zOffset, speed, amount, null);
    }

    public Particle(ParticleEffect effect, float speed, int amount) {
        this.particleEffect = effect;
        this.speed = speed;
        this.amount = amount;
    }

    public Particle(ParticleEffect effect, int amount) {
        this.particleEffect = effect;
        this.amount = amount;
    }


    public void display(Location location, double range) {
        if (color != null) {
            particleEffect.display(color, location, range);
        } else {
            particleEffect.display(xOffset, yOffset, zOffset, speed, amount, location, range);
        }
    }

    public void display(Location location) {
        if (color != null) {
            particleEffect.display(color, location, 16);
        } else {
            particleEffect.display(xOffset, yOffset, zOffset, speed, amount, location);
        }
    }

    public void display(Location location, String subtype, double range) {
        //TODO: Fix this up.
        particleEffect.display(xOffset, yOffset, zOffset, speed, amount, location, range);
    }
}
