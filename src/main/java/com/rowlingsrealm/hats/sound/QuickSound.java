package com.rowlingsrealm.hats.sound;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Copyright Tyler Grissom 2018
 */
public class QuickSound {

    public static void play(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1F, 1F);
    }

    public static void click(Player player) {
        play(player, Sound.UI_BUTTON_CLICK);
    }

    public static void levelUp(Player player) {
        play(player, Sound.ENTITY_PLAYER_LEVELUP);
    }
}
