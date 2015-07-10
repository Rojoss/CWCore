package com.clashwars.cwcore.dependencies.internal;

import com.clashwars.cwcore.CWCore;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.earth2me.essentials.Essentials;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class DependencyManager {

    private CWCore cwc;

    private Economy economy;
    private Permission permissions;
    private WorldEditPlugin worldedit;
    private WorldGuardPlugin worldguard;
    private Essentials essentials;
    private ProtocolLibrary protocolLib;
    private ProtocolManager protocolManager;

    public DependencyManager(CWCore cwc) {
        this.cwc = cwc;
    }

    public void loadDependencies() {
        cwc.log("Loading dependencies...");
        loadEconomy();
        loadPermissions();
        loadWorldedit();
        loadWorldguard();
        loadEssentials();
        loadProtocolLib();
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


    // PERMISSIONS
    private void loadPermissions() {
        if (cwc.getServer().getPluginManager().getPlugin("Vault") == null) {
            cwc.error("Vault dependency couldn't be loaded!");
            return;
        }
        RegisteredServiceProvider<Permission> rsp = cwc.getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            cwc.error("Permissions from vault couldn't be loaded!");
            return;
        }
        permissions = rsp.getProvider();
        if (permissions == null) {
            cwc.error("Permissions from vault couldn't be loaded!");
            return;
        }
        cwc.log("Permissions (Vault) dependency loaded!");
    }

    /**
     * Get Permission class from Vault to manage permissions.
     * @return Permission
     */
    public Permission getPermissions() {
        return permissions;
    }


    // WORLDEDIT
    private void loadWorldedit() {
        Plugin plugin = cwc.getServer().getPluginManager().getPlugin("WorldEdit");
        if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
            cwc.error("WorldEdit dependency couldn't be loaded!");
            return;
        }
        worldedit = (WorldEditPlugin) plugin;
        cwc.log("WorldEdit dependency loaded!");
    }

    /**
     * Get WorldEditPlugin plugin class.
     * @return WorldEditPlugin
     */
    public WorldEditPlugin getWorldedit() {
        return worldedit;
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
