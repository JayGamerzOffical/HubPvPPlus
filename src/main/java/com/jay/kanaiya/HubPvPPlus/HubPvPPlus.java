package com.jay.kanaiya.HubPvPPlus;

import com.jay.kanaiya.HubPvPPlus.commands.HubPvPCommand;
import com.jay.kanaiya.HubPvPPlus.listeners.*;
import com.jay.kanaiya.HubPvPPlus.util.StringUtil;
import com.jay.kanaiya.HubPvPPlus.core.PvPManager;
import com.jay.kanaiya.HubPvPPlus.itemguilib.ItemGuiLib;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public final class HubPvPPlus extends JavaPlugin {

	private static HubPvPPlus instance;

	private PvPManager pvpManager;

	private File messagesFile;
	private FileConfiguration messagesConfig;

	private File itemsFile;
	private FileConfiguration itemsConfig;

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
		pm.registerEvents(new DamageListener(), this);
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
		itemsFile = new File(getDataFolder(), "items.yml");
		if (!itemsFile.exists()) {
			saveResource("items.yml", false);
		}
		itemsConfig = YamlConfiguration.loadConfiguration(itemsFile);
	}

	public FileConfiguration getItemsConfig() {
		if (itemsConfig == null) {
			loadItemsFile();
		}
		return itemsConfig;
	}

	public void saveItemsFile() {
		if (itemsConfig == null || itemsFile == null) return;
		try {
			itemsConfig.save(itemsFile);
		} catch (IOException e) {
			getLogger().severe("Could not save items.yml file!");
			e.printStackTrace();
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
