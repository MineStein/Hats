package com.rowlingsrealm.hats;

import com.rowlingsrealm.hats.command.HatsCommand;
import com.rowlingsrealm.hats.hat.Hat;
import com.rowlingsrealm.hats.hat.HatManager;
import com.rowlingsrealm.hats.listener.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright Tyler Grissom 2018
 */
public class HatsPlugin extends JavaPlugin {

    private HatsPlugin plugin;
    public static HatsPlugin instance;
    private HatManager hatManager;

    public HatsPlugin getPlugin() {
        return plugin;
    }

    public HatManager getHatManager() {
        return hatManager;
    }

    @Override
    public void onEnable() {
        plugin = this;
        instance = this;
        hatManager = new HatManager(this);

        ConfigurationSerialization.registerClass(Hat.class, "Hat");

        getConfig().options().copyDefaults(true);
        saveConfig();

        hatManager.loadHats();

        // TODO implement pretty command registration and listeners

        getCommand("hats").setExecutor(new HatsCommand(this));

        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
