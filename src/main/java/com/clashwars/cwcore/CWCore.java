package com.clashwars.cwcore;

import com.clashwars.cwcore.config.aliases.*;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class CWCore extends JavaPlugin {

    private static CWCore instance;
    private final Logger log = Logger.getLogger("Minecraft");

    private Materials materialsCfg;
    private Sounds soundsCfg;
    private PotionEffects effectsCfg;
    private Entities entityCfg;
    private Enchantments enchantsCfg;
    private Biomes biomesCfg;


    public void onDisable() {
        log("Disabled.");
    }

    public void onEnable() {
        instance = this;

        loadAliases();

        log("Enabled.");
    }


    //Load all configs for aliases and update them to match the bukkit enums.
    private void loadAliases() {
        materialsCfg = new Materials("plugins/CWCore/Aliases/Materials.yml");
        materialsCfg.load();
        materialsCfg.update();

        soundsCfg = new Sounds("plugins/CWCore/Aliases/Sounds.yml");
        soundsCfg.load();
        soundsCfg.update();

        effectsCfg = new PotionEffects("plugins/CWCore/Aliases/PotionEffects.yml");
        effectsCfg.load();
        effectsCfg.update();

        entityCfg = new Entities("plugins/CWCore/Aliases/Entities.yml");
        entityCfg.load();
        entityCfg.update();

        enchantsCfg = new Enchantments("plugins/CWCore/Aliases/Enchantments.yml");
        enchantsCfg.load();
        enchantsCfg.update();

        biomesCfg = new Biomes("plugins/CWCore/Aliases/Biomes.yml");
        biomesCfg.load();
        biomesCfg.update();
    }


    /**
     * Log a [INFO] message to the console.
     * @param msg
     */
    public void log(Object msg) {
        log.info("[CWCore " + getDescription().getVersion() + "]: " + msg.toString());
    }



    /**
     * Get the static plugin instance.
     * @return {@link com.clashwars.cwcore.CWCore}
     */
    public static CWCore inst() {
        return instance;
    }

    /**
     * Get the materials aliases config/manager.
     * Can be used to get a material by name or get a better displayname for a material.
     * @return {@link com.clashwars.cwcore.config.aliases.Materials}
     */
    public Materials getMaterials() {
        return materialsCfg;
    }

    /**
     * Get the sounds aliases config/manager.
     * Can be used to get a sound by name or get a better displayname for a sound.
     * @return {@link com.clashwars.cwcore.config.aliases.Sounds}
     */
    public Sounds getSounds() {
        return soundsCfg;
    }

    /**
     * Get the potion effects aliases config/manager.
     * Can be used to get a effect by name or get a better displayname for a effect.
     * @return {@link com.clashwars.cwcore.config.aliases.PotionEffects}
     */
    public PotionEffects getPotionEffects() {
        return effectsCfg;
    }

    /**
     * Get the entity aliases config/manager.
     * Can be used to get a entity by name or get a better displayname for a entity.
     * @return {@link com.clashwars.cwcore.config.aliases.Entities}
     */
    public Entities getEntities() {
        return entityCfg;
    }

    /**
     * Get the enchants aliases config/manager.
     * Can be used to get a enchantment by name or get a better displayname for a enchantment.
     * @return {@link com.clashwars.cwcore.config.aliases.Enchantments}
     */
    public Enchantments getEnchants() {
        return enchantsCfg;
    }

    /**
     * Get the biomes aliases config/manager.
     * Can be used to get a biome by name or get a better displayname for a biome.
     * @return {@link com.clashwars.cwcore.config.aliases.Enchantments}
     */
    public Biomes getBiomes() {
        return biomesCfg;
    }
}
