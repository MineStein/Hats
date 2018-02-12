package com.rowlingsrealm.hats.command;

import com.rowlingsrealm.hats.HatsPlugin;
import com.rowlingsrealm.hats.gui.HatsGui;
import com.rowlingsrealm.hats.message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Copyright Tyler Grissom 2018
 */
public class HatsCommand extends CommandBase {

    private HatsPlugin plugin;

    public HatsPlugin getPlugin() {
        return plugin;
    }

    public HatsCommand(HatsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    void execute(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            HatsGui gui = new HatsGui(plugin);

            gui.open(player);
        } else {
            sender.sendMessage(Message.ONLY_PLAYERS.get());
        }
    }
}
