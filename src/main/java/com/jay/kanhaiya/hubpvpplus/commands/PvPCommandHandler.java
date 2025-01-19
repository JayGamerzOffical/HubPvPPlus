package com.jay.kanhaiya.hubpvpplus.commands;

import com.jay.kanhaiya.hubpvpplus.ColorFixedUtil.ConfigColorUtil;
import com.jay.kanhaiya.hubpvpplus.HubPvPPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
public class PvPCommandHandler implements CommandExecutor, TabCompleter {

    private final HubPvPPlus plugin;

    public PvPCommandHandler(HubPvPPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] arguments) {
        if (arguments.length == 0) {
            sender.sendMessage(ChatColor.RED + "Incorrect usage. Try: /hpp <sub-command>");
            return true;
        }

        switch (arguments[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                plugin.getPvpManager().loadItemsConfig();
                plugin.getPvpManager().loadItems();
                sender.sendMessage(ConfigColorUtil.colorize(plugin.getConfig().getString("messages.config-reloaded")));
                break;

            case "addblackworld":
                if (arguments.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /" + "hpp" + " addblackworld <worldName>");
                    break;
                }
                String worldToAdd = arguments[1];
                List<String> blacklistedWorlds = plugin.getConfig().getStringList("restricted-worlds");
                if (blacklistedWorlds.contains(worldToAdd)) {
                    sender.sendMessage(ChatColor.YELLOW + "World '" + worldToAdd + "' is already blacklisted.");
                } else {
                    blacklistedWorlds.add(worldToAdd);
                    plugin.getConfig().set("restricted-worlds", blacklistedWorlds);
                    plugin.saveConfig();
                    sender.sendMessage(ChatColor.GREEN + "World '" + worldToAdd + "' has been added to the blacklist.");
                }
                break;

            case "removeblackworld":
                if (arguments.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /" + "hpp" + " removeblackworld <worldName>");
                    break;
                }
                String worldToRemove = arguments[1];
                blacklistedWorlds = plugin.getConfig().getStringList("restricted-worlds");
                if (blacklistedWorlds.contains(worldToRemove)) {
                    blacklistedWorlds.remove(worldToRemove);
                    plugin.getConfig().set("restricted-worlds", blacklistedWorlds);
                    plugin.saveConfig();
                    sender.sendMessage(ChatColor.GREEN + "World '" + worldToRemove + "' has been removed from the blacklist.");
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "World '" + worldToRemove + "' is not blacklisted.");
                }
                break;

            case "listblackworlds":
                blacklistedWorlds = plugin.getConfig().getStringList("restricted-worlds");
                if (blacklistedWorlds.isEmpty()) {
                    sender.sendMessage(ChatColor.YELLOW + "No worlds are currently blacklisted.");
                } else {
                    sender.sendMessage(ChatColor.GREEN + "Blacklisted worlds: " + String.join(", ", blacklistedWorlds));
                }
                break;
            case "help":
                sender.sendMessage(ChatColor.GREEN + "Available Commands:");
                sender.sendMessage(ChatColor.AQUA + "/" + "hpp" + " reload" + ChatColor.WHITE + " - Reload the plugin configuration.");
                sender.sendMessage(ChatColor.AQUA + "/" + "hpp" + " addblackworld <worldName>" + ChatColor.WHITE + " - Add a world to the blacklist.");
                sender.sendMessage(ChatColor.AQUA + "/" + "hpp" + " removeblackworld <worldName>" + ChatColor.WHITE + " - Remove a world from the blacklist.");
                sender.sendMessage(ChatColor.AQUA + "/" + "hpp" + " listblackworlds" + ChatColor.WHITE + " - List all blacklisted worlds.");
                sender.sendMessage(ChatColor.AQUA + "/" + "hpp" + " help" + ChatColor.WHITE + " - Show this help message.");
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown argument. Try: /" + "hpp" + " <reload|addblackworld|removeblackworld|listblackworlds>");
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            if ("reload".startsWith(args[0].toLowerCase())) suggestions.add("reload");
            if ("addblackworld".startsWith(args[0].toLowerCase())) suggestions.add("addblackworld");
            if ("removeblackworld".startsWith(args[0].toLowerCase())) suggestions.add("removeblackworld");
            if ("listblackworlds".startsWith(args[0].toLowerCase())) suggestions.add("listblackworlds");
            if ("help".startsWith(args[0].toLowerCase())) suggestions.add("help");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("addblackworld") || args[0].equalsIgnoreCase("removeblackworld")) {
                plugin.getServer().getWorlds().forEach(world -> {
                    if (world.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                        suggestions.add(world.getName());
                    }
                });
            }
        }

        return suggestions;
    }
}