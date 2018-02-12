package com.rowlingsrealm.hats.listener;

import com.rowlingsrealm.hats.HatsPlugin;
import com.rowlingsrealm.hats.hat.Hat;
import com.rowlingsrealm.hats.message.Message;
import com.rowlingsrealm.hats.sound.QuickSound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Copyright Tyler Grissom 2018
 */
public class InventoryListener implements Listener {

    private HatsPlugin plugin;

    public HatsPlugin getPlugin() {
        return plugin;
    }

    public InventoryListener(HatsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryCreativeClick(final InventoryCreativeEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                for (ItemStack item :
                        event.getWhoClicked().getInventory()) {
                    if (item == null || item.getItemMeta().getDisplayName() == null) continue;

                    Hat hat = plugin.getHatManager().getHat(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

                    if (hat != null) event.getWhoClicked().getInventory().remove(item);
                }
            }
        }, 1);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (event.getClickedInventory() instanceof PlayerInventory && player.getInventory().equals(event.getClickedInventory())) {
            if (event.getSlot() == 39) {
                Hat hat = plugin.getHatManager().getHat(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

                player.sendMessage(Message.HAT_DEQUIPPED.get("hat", ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(hat.getName()))));

                QuickSound.click(player);

                if (hat != null) {
                    event.setCancelled(true);

                    player.getInventory().setHelmet(null);
                }
            }
        }

        if (event.getInventory().getName().equals("Hats")) {
            event.setCancelled(true);

            if (item == null ||
                    item.getItemMeta() == null ||
                    item.getItemMeta().getDisplayName() == null) return;

            Hat hat = plugin.getHatManager().getHat(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

            if (!player.hasPermission(hat.getPermission())) {
                player.sendMessage(Message.DONT_OWN.get());

                return;
            }

            player.getInventory().setHelmet(hat.getEquippableItem());
            player.sendMessage(Message.HAT_EQUIPPED.get("hat", ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(hat.getName()))));
            player.closeInventory();

            QuickSound.click(player);
        }
    }
}
