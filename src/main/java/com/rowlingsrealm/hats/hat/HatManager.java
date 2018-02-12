package com.rowlingsrealm.hats.hat;

import com.rowlingsrealm.hats.HatsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright Tyler Grissom 2018
 */
public class HatManager {

    private HatsPlugin plugin;
    private List<Hat> loadedHats;

    public HatsPlugin getPlugin() {
        return plugin;
    }

    public List<Hat> getLoadedHats() {
        return loadedHats;
    }

    public HatManager(HatsPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadHats() {
        if (loadedHats == null) loadedHats = new ArrayList<>();
        if (loadedHats.size() > 0) loadedHats.clear();

        FileConfiguration config = plugin.getConfig();

        List<Hat> hats = new ArrayList<>();
        List<Map<?, ?>> maps = config.getMapList("hats");

        for (Map<?, ?> listItem :
                maps) {
            hats.add(new Hat((Map<String, Object>) listItem));
        }

        if (hats.size() == 0) {
            plugin.getLogger().warning("No hats configured!");

            return;
        }

        List<String> names = new ArrayList<>();
        
        for (Hat hat : hats) {
            if (names.contains(hat.getName())) {
                plugin.getLogger().warning(String.format("Multiple-registration for hat name '%s', disabling all...", hat.getName()));

                for (Hat innerHat :
                        hats) {
                    if (innerHat.getName().equals(hat.getName())) hats.remove(innerHat);
                }

                return;
            }

            names.add(hat.getName());
        }

        loadedHats = hats;

        plugin.getLogger().info(String.format("Loaded %s hats.", String.valueOf(loadedHats.size())));
    }

    public Hat getHat(String name) {
        for (Hat hat : loadedHats) {
            if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name)).equals(name)) return hat;
        }

        return null;
    }
}
