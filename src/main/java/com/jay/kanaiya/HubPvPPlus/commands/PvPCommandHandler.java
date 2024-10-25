package com.jay.kanaiya.HubPvPPlus.commands;

import com.jay.kanaiya.HubPvPPlus.ColorFixedUtil.ConfigColorUtil;
import com.jay.kanaiya.HubPvPPlus.HubPvPPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class PvPCommandHandler implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] arguments) {
        if (arguments.length == 1) {
            if ("reload".equalsIgnoreCase(arguments[0])) {
                HubPvPPlus pluginInstance = HubPvPPlus.getInstance();
                pluginInstance.reloadConfig(); // Refresh the config file

                // Reload PvP items from items.yml
                pluginInstance.getPvpManager().loadItemsConfig(); // Load items config file
                pluginInstance.getPvpManager().loadItems(); // Reload actual items

                sender.sendMessage(ConfigColorUtil.colorize(pluginInstance.getConfig().getString("language.reload_message")));
            } else {
                sender.sendMessage(ChatColor.RED + "Unknown argument. Try: /" + alias + " <reload>");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Incorrect usage. Try: /" + alias + " <command>");
        }
        return true; // Return true if command was handled
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] arguments) {
        if (arguments.length == 1) {
            return Collections.singletonList("reload");
        }
        return Collections.emptyList();
    }
}
