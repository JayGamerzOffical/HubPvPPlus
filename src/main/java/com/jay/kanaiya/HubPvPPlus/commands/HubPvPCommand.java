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
				plugin.reloadConfig();
				File itemsFile = new File(plugin.getDataFolder(), "items.yml");
				if (itemsFile.exists()) {
					plugin.getConfig().set("items", null);
					plugin.saveConfig();
					plugin.getConfig().options().copyDefaults(true);
					plugin.saveDefaultConfig();
					plugin.pvpManager().loadItems();
					sender.sendMessage(StringUtil.colorize(plugin.getConfig().getString("lang.reloaded")));
				} else {
					sender.sendMessage(ChatColor.RED + "items.yml file not found!");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Invalid arguments. Use: /" + label + " <reload>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Invalid usage. Use: /" + label + " <args>");
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (args.length <= 1) {
			return Collections.singletonList("reload");
		}
		return null;
	}
}
