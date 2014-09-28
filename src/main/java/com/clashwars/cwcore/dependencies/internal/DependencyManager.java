package com.clashwars.cwcore.dependencies.internal;

import com.clashwars.cwcore.CWCore;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.earth2me.essentials.Essentials;
import com.massivecraft.factions.Factions;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class DependencyManager {

    private CWCore cwc;

    private Economy economy;
    private WorldGuardPlugin worldguard;
    private Essentials essentials;
    private Factions factions;
    private ProtocolLibrary protocolLib;
    private ProtocolManager protocolManager;

    public DependencyManager(CWCore cwc) {
        this.cwc = cwc;
    }

    public void loadDependencies() {
        cwc.log("Loading dependencies...");
        loadEconomy();
        loadWorldguard();
        loadEssentials();
        loadProtocolLib();
        loadFactions();
    }

    // ECONOMY
    private void loadEconomy() {
        if (cwc.getServer().getPluginManager().getPlugin("Vault") == null) {
            cwc.error("Vault dependency couldn't be loaded!");
            return;
        }
        RegisteredServiceProvider<Economy> rsp = cwc.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            cwc.error("Economy from vault couldn't be loaded!");
            return;
        }
        economy = rsp.getProvider();
        if (economy == null) {
            cwc.error("Economy from vault couldn't be loaded!");
            return;
        }
        cwc.log("Economy (Vault) dependency loaded!");
    }

    /**
     * Get Economy class from Vault to manage economy.
     * @return Economy
     */
    public Economy getEconomy() {
        return economy;
    }


    // WORLDGUARD
    private void loadWorldguard() {
        Plugin plugin = cwc.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            cwc.error("WorldGuard dependency couldn't be loaded!");
            return;
        }
        worldguard = (WorldGuardPlugin) plugin;
        cwc.log("WorldGuard dependency loaded!");
    }

    /**
     * Get WorldGuardPlugin plugin class.
     * @return WorldGuardPlugin
     */
    public WorldGuardPlugin getWorldguard() {
        return worldguard;
    }


    //ESSENTIALS
    private void loadEssentials() {
        Plugin plugin = cwc.getServer().getPluginManager().getPlugin("Essentials");
        if (plugin == null || !(plugin instanceof Essentials)) {
            cwc.error("Essentials dependency couldn't be loaded!");
            return;
        }
        essentials = (Essentials) plugin;
        cwc.log("Essentials dependency loaded!");
    }

    /**
     * Get Essentials plugin class.
     * @return Essentials
     */
    public Essentials getEssentials() {
        return essentials;
    }


    //FACTIONS
    private void loadFactions() {
        Plugin plugin = cwc.getServer().getPluginManager().getPlugin("Factions");
        if (plugin == null || !(plugin instanceof Factions)) {
            cwc.error("Factions dependency couldn't be loaded!");
            return;
        }
        factions = (Factions) plugin;
        cwc.log("Factions dependency loaded!");
    }

    /**
     * Get Factions plugin class.
     * @return Factions
     */
    public Factions getFactions() {
        return factions;
    }



    //PROTOCOLLIB
    private void loadProtocolLib() {
        Plugin plugin = cwc.getServer().getPluginManager().getPlugin("ProtocolLib");
        if (plugin == null || !(plugin instanceof ProtocolLibrary)) {
            cwc.error("ProtocolLib dependency couldn't be loaded!");
            return;
        }
        cwc.log("ProtocolLib dependency loaded!");
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    /**
     * Get Protocol  Manager class.
     * @return ProtocolManager
     */
    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
