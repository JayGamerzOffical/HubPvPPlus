package com.jay.kanhaiya.hubpvpplus;

import com.jay.kanhaiya.hubpvpplus.ColorFixedUtil.ConfigColorUtil;
import com.jay.kanhaiya.hubpvpplus.DamageIndi.HologramManager;
import com.jay.kanhaiya.hubpvpplus.DamageIndi.VectorGenerator;
import com.jay.kanhaiya.hubpvpplus.DamageIndi.VectorRingBuffer;
import com.jay.kanhaiya.hubpvpplus.EventListeners.*;
import com.jay.kanhaiya.hubpvpplus.commands.PvPCommandHandler;
import com.jay.kanhaiya.hubpvpplus.PvPHandler.PvPManager;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class HubPvPPlus extends JavaPlugin {

	@Getter
    private static HubPvPPlus instance;
	public HologramManager hologramManager;

    private VectorRingBuffer ringBuffer;
	@Getter
    private PvPManager pvpManager;


    @Override
	public void onDisable() {
		pvpManager.disable();
	}

	@Override
	public void onEnable() {
		instance = this;
		pvpManager = new PvPManager();
		if ( getServer ().getBukkitVersion ().startsWith ("1.20") || getServer ().getBukkitVersion ().startsWith ("1.21") || getServer ().getBukkitVersion ().startsWith ("1.22") ){
			getServer ( ).getPluginManager ().registerEvents (new LP_1_20 ( ),this);
		}
		hologramManager = new HologramManager ( );
		VectorGenerator vectorGenerator = new VectorGenerator ( );
		ringBuffer = new VectorRingBuffer (50, vectorGenerator);
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
		pm.registerEvents(new DamageListener (), this);  // Register DamageListener
		pm.registerEvents(new DeathListener (), this);
		pm.registerEvents(new ItemSlotChangeListener (), this);
		pm.registerEvents(new PvPManager(), this);
		pm.registerEvents(new PlayerJoinListener (this), this);
		pm.registerEvents(new ProtectionListeners (), this);
	}

	private void registerCommands() {
		Objects.requireNonNull(getCommand("hubpvpplus")).setExecutor (new PvPCommandHandler(this));
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
		logger.info("  SSSSS  W     W   AAAAA   GGGGsG  GGGGG  EEEEE  RRRR   SSSSS ");
		logger.info(" S       W     W  A     A G       G      E      R   R S      ");
		logger.info("  SSSS   W  W  W  AAAAAAA G  GGG  G  GGG  EEEEE  RRRR   SSSS  ");
		logger.info("      S  W W W W  A     A G     G G     G E      R R        S ");
		logger.info("  SSSSS   W   W   A     A  GGGGG   GGGGG  EEEEE  R  RR  SSSSS ");
		logger.info("========================================");
		logger.info("Plugin Name: HubPvP Plus");
		logger.info("Version: " + getDescription().getVersion());
		logger.info("Author: " + "Kanhaiya Swagger");
		logger.info ("Sponsored By: " + "https://swagger.cloud/");
		logger.info ("Official DC: " + "https://minecraftinfinity.com/discord");
		logger.info("HubPvP Plus is Successfully enabled!");
	}

    public VectorRingBuffer getRingBuffer() {
        return ringBuffer;
    }

}
