package com.jay.kanaiya.HubPvPPlus.commands;

import com.jay.kanaiya.HubPvPPlus.util.StringUtil;
import com.jay.kanaiya.HubPvPPlus.HubPvPPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class HubPvPCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				HubPvPPlus plugin = HubPvPPlus.instance();
				plugin.reloadConfig(); // Reload the main config

				// Load items from items.yml
				plugin.pvpManager().loadItemsConfig(); // Ensure items are loaded properly
				plugin.pvpManager().loadItems(); // Reload the items from items.yml

				sender.sendMessage(StringUtil.colorize(plugin.getConfig().getString("lang.reloaded")));
			} else {
				sender.sendMessage(ChatColor.RED + "Invalid arguments. Use: /" + label + " <reload>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Invalid usage. Use: /" + label + " <args>");
		}
		return true; // Return true if the command was successful
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (args.length <= 1) {
			return Collections.singletonList("reload");
		}
		return null;
	}
}
