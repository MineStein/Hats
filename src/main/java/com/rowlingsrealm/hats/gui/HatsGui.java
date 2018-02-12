package com.rowlingsrealm.hats.gui;

import com.rowlingsrealm.hats.HatsPlugin;
import com.rowlingsrealm.hats.hat.Hat;
import com.rowlingsrealm.hats.sound.QuickSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Collection;
import java.util.List;

/**
 * Copyright Tyler Grissom 2018
 */
public class HatsGui {

    private HatsPlugin plugin;

    public HatsPlugin getPlugin() {
        return plugin;
    }

    public HatsGui(HatsPlugin plugin) {
        this.plugin = plugin;
    }

    public int resize(Collection<?> collection) {
        int resize = 9;
        int size = collection.size();
        
        if (size > 9) resize = 18;
        if (size > 18) resize = 27;
        if (size > 27) resize = 36;
        if (size > 36) resize = 45;
        if (size > 45) resize = 54;
        
        return resize;
    }
    
    private Inventory getInventory(Player player) {
        List<Hat> hats = plugin.getHatManager().getLoadedHats();
        Inventory inventory = Bukkit.createInventory(null, resize(hats), "Hats");

        for (Hat hat :
                hats) {
            inventory.addItem(hat.getSelectorItem(player));
        }
        
        return inventory;
    }

    public void open(Player... players) {
        for (Player player :
                players) {
            QuickSound.click(player);
            
            player.openInventory(getInventory(player));
        }
    }
}
