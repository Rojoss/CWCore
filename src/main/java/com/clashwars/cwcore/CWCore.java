package com.clashwars.cwcore;

import com.clashwars.cwcore.config.aliases.Materials;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class CWCore extends JavaPlugin {

    private static CWCore instance;
    private final Logger log = Logger.getLogger("Minecraft");

    private Materials materialsCfg;


    public void onDisable() {
        log("Disabled.");
    }

    public void onEnable() {
        instance = this;

        materialsCfg = new Materials("plugins/CWCore/Aliases/Materials.yml");
        materialsCfg.load();
        materialsCfg.update();

        log("Enabled.");
    }


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

}
