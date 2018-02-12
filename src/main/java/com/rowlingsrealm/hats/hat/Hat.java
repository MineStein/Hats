package com.rowlingsrealm.hats.hat;

import com.rowlingsrealm.hats.message.Message;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright Tyler Grissom 2018
 */
@SerializableAs("Hat")
public class Hat implements ConfigurationSerializable {

    // TODO Convert to Permission object

    @Getter private String name, description, permission;
    @Getter private Material type;
    @Getter private int data, damage;

    public Hat(Map<String, Object> map) {
        this.name = (String) map.get("name");
        this.description = (String) map.get("description");
        this.permission = (String) map.get("permission");
        this.type = Material.getMaterial((String) map.get("type"));
        this.data = (Integer) map.get("data");
        this.damage = (Integer) map.get("damage");
    }

    public static Hat deserialize(Map<String, Object> map) {
        return new Hat(map);
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>(); {
            map.put("name", name);
            map.put("description", description);
            map.put("permission", permission);
            map.put("type", type);
            map.put("data", data);
            map.put("damage", damage);
        }

        return map;
    }
    public ItemStack getSelectorItem(Player player) {
        ItemStack itemStack = new ItemStack(type, 1, (short) damage, (byte) data); {
            ItemMeta meta = itemStack.getItemMeta();

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

            // TODO Do this more elegantly cause this is terrible
            if (player != null) {
                List<String> lore = Arrays.asList(
                        ChatColor.translateAlternateColorCodes('&', description),
                        "",
                        player.hasPermission(getPermission()) ? "§aOwned" : "§cUnowned"
                );

                meta.setLore(lore);
            }

            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }

    public ItemStack getEquippableItem() {
        ItemStack itemStack = getSelectorItem(null); {
            ItemMeta meta = itemStack.getItemMeta();

            List<String> lore = Arrays.asList(
                    ChatColor.translateAlternateColorCodes('&', description),
                    "",
                    Message.CLICK_REMOVE.get()
            );

            meta.setLore(lore);

            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }
}
