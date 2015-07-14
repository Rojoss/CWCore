package com.clashwars.cwcore;

import com.clashwars.cwcore.commands.Commands;
import com.clashwars.cwcore.config.PlayerConfig;
import com.clashwars.cwcore.config.aliases.*;
import com.clashwars.cwcore.cuboid.Selection;
import com.clashwars.cwcore.cuboid.SelectionListener;
import com.clashwars.cwcore.dependencies.internal.DependencyManager;
import com.clashwars.cwcore.effect.EffectManager;
import com.clashwars.cwcore.effect.EntityManager;
import com.clashwars.cwcore.effect.event.ItemListener;
import com.clashwars.cwcore.events.CustomEventHandler;
import com.clashwars.cwcore.hat.Hat;
import com.clashwars.cwcore.hat.HatManager;
import com.clashwars.cwcore.helpers.EntityHider;
import com.clashwars.cwcore.player.Vanish;
import com.clashwars.cwcore.scoreboard.ScoreboardListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class CWCore extends JavaPlugin {

    private static CWCore instance;
    private final Logger log = Logger.getLogger("Minecraft");

    private Commands cmds;
    private DependencyManager dm;
    private CooldownManager cdm;
    private EntityManager entityManager;
    private Selection sel;
    private HatManager hm;
    private EntityHider entityHider;

    private PlayerConfig playerCfg;
    private Materials materialsCfg;
    private Sounds soundsCfg;
    private PotionEffects effectsCfg;
    private Entities entityCfg;
    private Enchantments enchantsCfg;
    private Biomes biomesCfg;
    private Particles particlesCfg;


    public void onDisable() {
        Collection<Hat> hats = new HashMap<UUID, Hat>(HatManager.getHats()).values();
        for (Hat hat : hats) {
            hat.remove();
        }
        log("Disabled.");
    }

    public void onEnable() {
        instance = this;

        //Load dependencies
        dm = new DependencyManager(this);
        dm.loadDependencies();

        //Load command system.
        cmds = new Commands(this);

        //Other
        cdm = new CooldownManager();
        sel = new Selection();
        hm = new HatManager();
        entityHider = new EntityHider(this, EntityHider.Policy.BLACKLIST);

        //Load effects
        entityManager = new EntityManager(this);
        EffectManager.initialize();

        //Config
        playerCfg = new PlayerConfig("plugins/CWCore/PlayerData.yml");
        playerCfg.load();

        //Listeners
        getServer().getPluginManager().registerEvents(new CustomEventHandler(this), this);
        getServer().getPluginManager().registerEvents(new ItemMenu.Events(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new SelectionListener(this), this);
        getServer().getPluginManager().registerEvents(new ScoreboardListener(), this);
        getServer().getPluginManager().registerEvents(hm, this);
        getServer().getPluginManager().registerEvents(new Vanish(), this);

        loadAliases();

        log("Enabled.");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return cmds.onCommand(sender, cmd, label, args);
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

        particlesCfg = new Particles("plugins/CWCore/Aliases/Particles.yml");
        particlesCfg.load();
        particlesCfg.update();
    }


    /**
     * Log a [INFO] message to the console.
     * @param msg
     */
    public void log(Object msg) {
        log.info("[CWCore " + getDescription().getVersion() + "]: " + msg.toString());
    }

    /**
     * Log a [SEVERE] message to the console.
     * @param error
     */
    public void error(Object error) {
        log.severe("[CWCore " + getDescription().getVersion() + "]: " + error.toString());
    }



    /**
     * Get the static plugin instance.
     * @return {@link com.clashwars.cwcore.CWCore}
     */
    public static CWCore inst() {
        return instance;
    }


    /**
     * Get the dependency manager.
     * All dependencies can be accessed from here.
     * Some dependencies like WorldGuard have a Util class like CWWorldGuard for easier access.
     * But with this you can access the main classes and like economy manager and protocol manager.
     * @return DependencyManager
     */
    public DependencyManager getDM() {
        return dm;
    }

    /**
     * Get the dependency manager.
     * All dependencies can be accessed from here.
     * Some dependencies like WorldGuard have a Util class like CWWorldGuard for easier access.
     * But with this you can access the main classes and like economy manager and protocol manager.
     * @return DependencyManager
     * @deprecated Use getDM() instead.
     */
    public DependencyManager GetDM() {
        return dm;
    }

    /**
     * Get the cooldown manager.
     * This can handle all cooldowns as each cooldown has a unique name you specify.
     * @return CooldownManager
     */
    public CooldownManager getCDM() {
        return cdm;
    }

    /**
     * Get configuration with player data like vanished and frozen players.
     * @return PlayerConfig
     */
    public PlayerConfig getPlayerCfg() {
        return playerCfg;
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
     * @return {@link com.clashwars.cwcore.config.aliases.Biomes}
     */
    public Biomes getBiomes() {
        return biomesCfg;
    }

    /**
     * Get the particle aliases config/manager.
     * Can be used to get a particle by name or get a better displayname for a particle.
     * @return {@link com.clashwars.cwcore.config.aliases.Particles}
     */
    public Particles getParticles() {
        return particlesCfg;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public List<EffectManager> getEffectManagers() {
        return EffectManager.getManagers();
    }

    public Selection getSel() {
        return sel;
    }

    public HatManager getHatMan() {
        return hm;
    }

    public EntityHider getEntityHider() {
        return entityHider;
    }
}
