package com.jay.kanaiya.HubPvPPlus;

import com.jay.kanaiya.HubPvPPlus.commands.HubPvPCommand;
import com.jay.kanaiya.HubPvPPlus.listeners.*;
import com.jay.kanaiya.HubPvPPlus.util.StringUtil;
import com.jay.kanaiya.HubPvPPlus.core.PvPManager;
import com.jay.kanaiya.HubPvPPlus.itemguilib.ItemGuiLib;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class HubPvPPlus extends JavaPlugin {

	private static HubPvPPlus instance;

	private PvPManager pvpManager;


    public static HubPvPPlus instance() {
		return instance;
	}

	@Override
	public void onDisable() {
		pvpManager.disable();
		getServer().getConsoleSender().sendMessage(StringUtil.colorize("&c" + getDescription().getName() + " v" + getDescription().getVersion() + " disabled."));
	}

	@Override
	public void onEnable() {
		instance = this;
		ItemGuiLib.setPluginInstance(this);
		pvpManager = new PvPManager();

		registerListeners();
		registerCommands();

		// Load config files
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();

		loadItemsFile();

		logPluginStartup();
	}

	private void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new DamageListener(), this);  // Register DamageListener
		pm.registerEvents(new DeathListener(), this);
		pm.registerEvents(new ItemSlotChangeListener(), this);
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new ProtectionListeners(), this);
	}

	private void registerCommands() {
		Objects.requireNonNull(getCommand("hpp")).setExecutor(new HubPvPCommand());
		Objects.requireNonNull(getCommand("hpp")).setTabCompleter(new HubPvPCommand());
	}

	// Load and save methods for items.yml
	public void loadItemsFile() {
        File itemsFile = new File(getDataFolder(), "items.yml");
		if (!itemsFile.exists()) {
			saveResource("items.yml", false);
		}
    }

	private void logPluginStartup() {
		Logger logger = getLogger();
		logger.info("========================================");
		logger.info("  SSSSS  W     W   AAAAA   GGGGG  GGGGG  EEEEE  RRRR   SSSSS ");
		logger.info(" S       W     W  A     A G       G      E      R   R S      ");
		logger.info("  SSSS   W  W  W  AAAAAAA G  GGG  G  GGG  EEEEE  RRRR   SSSS  ");
		logger.info("      S  W W W W  A     A G     G G     G E      R R        S ");
		logger.info("  SSSSS   W   W   A     A  GGGGG   GGGGG  EEEEE  R  RR  SSSSS ");
		logger.info("========================================");
		logger.info("Plugin Name: HubPvP Plus");
		logger.info("Version: " + getDescription().getVersion());
		logger.info("Author: " + "Jay Kanaiya");
		logger.info("Plugin is Successfully enabled!");
	}

	public PvPManager pvpManager() {
		return pvpManager;
	}
}
