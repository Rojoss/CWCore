package com.clashwars.cwcore.effect.effects;

import com.clashwars.cwcore.effect.Effect;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EffectType;
import com.clashwars.cwcore.packet.ParticleEffect;
import com.clashwars.cwcore.utils.MathUtils;
import com.clashwars.cwcore.utils.StringParser;
import com.clashwars.cwcore.utils.VectorUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextEffect extends Effect {

    /**
     * Particle to draw the text
     */
    public ParticleEffect particle = ParticleEffect.FIREWORKS_SPARK;

    /**
     * Text to display
     */
    public String text = "Text";

    /**
     * Invert the text
     */
    public boolean invert = false;

    /**
     * Each stepX pixel will be shown. Saves packets for lower fontsizes.
     */
    public int stepX = 1;

    /**
     * Each stepY pixel will be shown. Saves packets for lower fontsizes.
     */
    public int stepY = 1;

    /**
     * Scale the font down
     */
    public float size = (float) 1 / 5;

    /**
     * Set this only to true if you are working with changing text. I'll advice
     * the parser to recalculate the BufferedImage every iteration.
     * Recommended FALSE
     */
    public boolean realtime = false;

    /**
     * Font to create the Text
     */
    public Font font;

    /**
     * Contains an image version of the String
     */
    protected BufferedImage image = null;

    public TextEffect(EffectManager effectManager) {
        super(effectManager);
        this.font = new Font("Tahoma", Font.PLAIN, 16);
        type = EffectType.REPEATING;
        period = 40;
        iterations = 20;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public void onRun() {
        if (font == null) {
            cancel();
            return;
        }
        Location location = getLocation();
        int clr = 0;
        try {
            if (image == null || realtime)
                image = StringParser.stringToBufferedImage(font, text);
            for (int y = 0; y < image.getHeight(); y += stepY) {
                for (int x = 0; x < image.getWidth(); x += stepX) {
                    clr = image.getRGB(x, y);
                    if (!invert && Color.black.getRGB() != clr)
                        continue;
                    else if (invert && Color.black.getRGB() == clr)
                        continue;
                    Vector v = new Vector((float) image.getWidth() / 2 - x, (float) image.getHeight() / 2 - y, 0).multiply(size);
                    VectorUtils.rotateAroundAxisY(v, -location.getYaw() * MathUtils.degreesToRadians);
                    particle.display(location.add(v), visibleRange, (float)particleOffset.getX(), (float)particleOffset.getY(), (float)particleOffset.getZ(), speed, amt);
                    location.subtract(v);
                }
            }
        } catch (Exception ex) {
            // This seems to happen on bad characters in strings,
            // I'm choosing to ignore the exception and cancel the effect for now.
            cancel(true);
        }
    }
}