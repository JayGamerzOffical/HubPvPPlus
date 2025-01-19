package com.jay.kanhaiya.hubpvpplus.PvPHandler;

import com.jay.kanhaiya.hubpvpplus.HubPvPPlus;
import com.jay.kanhaiya.hubpvpplus.ColorFixedUtil.ConfigColorUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

@Getter
public class PvPManager implements Listener {

	private final Map<Player, PvPState> playerPvpStates;
	private final Map<Player, BukkitRunnable> currentTimers;
	private final List<OldPlayerData> oldPlayerDataList;

	private ItemStack weapon, helmet, chestplate, leggings, boots;
	private FileConfiguration itemsConfig;

	public PvPManager() {
		playerPvpStates = new HashMap<>();
		currentTimers = new HashMap<>();
		oldPlayerDataList = new ArrayList<>();
		loadItemsConfig();
		loadItems();
	}

	public void loadItemsConfig() {
		File itemsFile = new File(HubPvPPlus.getInstance().getDataFolder(), "items.yml");
		if (!itemsFile.exists()) {
			HubPvPPlus.getInstance().saveResource("items.yml", false);
		}
		itemsConfig = YamlConfiguration.loadConfiguration(itemsFile);
	}

	public void loadItems() {
		weapon = getItemFromConfig("weapon");
		helmet = getItemFromConfig("helmet");
		chestplate = getItemFromConfig("chestplate");
		leggings = getItemFromConfig("leggings");
		boots = getItemFromConfig("boots");
	}

	public boolean isInPvP(Player player) {
		return getPlayerState(player) == PvPState.ON || getPlayerState(player) == PvPState.DISABLING;
	}

	public PvPState getPlayerState(Player p) {
		return playerPvpStates.get(p);
	}

	public ItemStack getItemFromConfig(String name) {
		String material = itemsConfig.getString("gear." + name + ".type");
		if (material == null) {
			HubPvPPlus.getInstance().getLogger().warning("Material for item " + name + " is null!");
			return null;
		}

		ItemStack item = new ItemStack(Material.valueOf(material.toUpperCase()));
		ItemMeta meta = item.getItemMeta();

		if (meta == null) {
			HubPvPPlus.getInstance().getLogger().warning("Could not fetch ItemMeta for " + material);
			return item;
		}

// Set item name with hex color support
		String itemName = itemsConfig.getString("gear." + name + ".display-name");
		if (itemName != null && !itemName.isEmpty()) {
		String parsedComponent = ConfigColorUtil.colorize(itemName);
			meta.setDisplayName(parsedComponent);
		}

		// Set lore with hex color support
		List<String> lore = itemsConfig.getStringList("gear." + name + ".description");
		if (lore != null && !lore.isEmpty()) {
			String to =ConfigColorUtil.colorize(lore.toString());
			meta.setLore(Collections.singletonList(to));
		}

		// Apply enchantments
		List<String> enchants = itemsConfig.getStringList("gear." + name + ".enchantments");
		if (enchants != null && !enchants.isEmpty()) {
			for (String enchant : enchants) {
				try {
					String[] split = enchant.split(":");
					String enchantmentName = split[0].toLowerCase();
					int level = split.length > 1 ? Integer.parseInt(split[1]) : 1;

					Enchantment enchantment;

					// Handle enchantments for different versions
					if ( HubPvPPlus.getInstance().getServer ().getVersion().startsWith("1.8") || HubPvPPlus.getInstance().getServer ().getVersion().startsWith("1.9") || HubPvPPlus.getInstance().getServer ().getVersion().startsWith("1.10") || HubPvPPlus.getInstance().getServer ().getVersion().startsWith("1.11")) {
						enchantment = Enchantment.getByName(enchantmentName.toUpperCase());
					} else {
						// Use NamespacedKey for 1.13+
						enchantment = Enchantment.getByName (enchantmentName);
					}

					if (enchantment != null) {
						meta.addEnchant(enchantment, level, true);
					} else {
						HubPvPPlus.getInstance().getLogger().warning("Could not find enchantment: " + enchantmentName);
					}
				} catch (Exception e) {
					HubPvPPlus.getInstance().getLogger().info("Error applying enchantment: " + enchant);
					e.printStackTrace();
				}
			}
		} else {
			HubPvPPlus.getInstance().getLogger().info("Error applying enchantment: null");
		}

		// Apply item flags and unbreakable status

		/*List<String> itemFlags = null;
		 itemFlags = itemsConfig.getStringList("gear." + name + ".flags");
		if (itemFlags != null) {
			for (String flag : itemFlags) {
				try {
					meta.addItemFlags(ItemFlag.valueOf(flag));
				} catch (IllegalArgumentException e) {
					LobbyPvP.getInstance().getLogger().warning("Invalid ItemFlag: " + flag);
				}
			}
		}*/
		item.setItemMeta(meta);
		return item;
	}
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player killed = (Player) event.getEntity();
			Player killer = killed.getKiller();

			if (killer != null && killer.isOnline()) {
				// Store additional attributes (for example: health-on-kill, respawn-at-spawn)
				boolean respawnAtSpawn = HubPvPPlus.getInstance().getConfig().getBoolean("respawn-at-spawn", false);
				if(respawnAtSpawn){
					killed.teleport(killed.getLocation().getWorld().getSpawnLocation());
				}
				// Use these values in gameplay logic later as needed
				ItemStack helmet = killer.getInventory().getHelmet();
				if (helmet != null && helmet.isSimilar(this.helmet)) {
					int healthOnKill = HubPvPPlus.getInstance().getConfig().getInt("health-reward-on-kill", 0);
					String healthMessage = itemsConfig.getString("messages.health-reward-message", "").replace("%killed%", killed.getName())
							.replace("%extra%", String.valueOf(healthOnKill));

					double newHealth = Math.min(killer.getHealth() + healthOnKill, killer.getHealth ());
					killer.setHealth(newHealth);

					if (!healthMessage.isEmpty()) {
						killer.sendMessage(ConfigColorUtil.colorize(healthMessage));
					}
				}
			}
		}
	}



	public void enablePvP(Player player) {
		setPlayerState(player, PvPState.ON);
		if (getOldData(player) != null) getOldPlayerDataList().remove(getOldData(player));
		getOldPlayerDataList().add(new OldPlayerData(player, player.getInventory().getArmorContents(), player.getAllowFlight()));

		player.setAllowFlight(false);
		player.getInventory().setHelmet(helmet);
		player.getInventory().setChestplate(chestplate);
		player.getInventory().setLeggings(leggings);
		player.getInventory().setBoots(boots);

		player.sendMessage(ConfigColorUtil.colorize(HubPvPPlus.getInstance().getConfig().getString("messages.pvp-activated")));
	}

	public void setPlayerState(Player p, PvPState state) {
		playerPvpStates.put(p, state);
	}

	public @Nullable OldPlayerData getOldData(Player p) {
		return oldPlayerDataList.stream().filter(data -> data.player().equals(p)).findFirst().orElse(null);
	}

	public void disablePvP(Player player) {
		setPlayerState(player, PvPState.OFF);
		OldPlayerData oldPlayerData = getOldData(player);
		if (oldPlayerData != null) {
			player.getInventory().setHelmet(oldPlayerData.armor()[3] == null ? new ItemStack(Material.AIR) : oldPlayerData.armor()[3]);
			player.getInventory().setChestplate(oldPlayerData.armor()[2] == null ? new ItemStack(Material.AIR) : oldPlayerData.armor()[2]);
			player.getInventory().setLeggings(oldPlayerData.armor()[1] == null ? new ItemStack(Material.AIR) : oldPlayerData.armor()[1]);
			player.getInventory().setBoots(oldPlayerData.armor()[0] == null ? new ItemStack(Material.AIR) : oldPlayerData.armor()[0]);
			player.setAllowFlight(oldPlayerData.canFly());
		}

		player.sendMessage(ConfigColorUtil.colorize(HubPvPPlus.getInstance().getConfig().getString("messages.pvp-deactivated")));
	}


	public void giveWeapon(Player p) {
		ItemStack newWeapon = weapon.clone(); // Clone to prevent modifying the original
		int weaponSlot = itemsConfig.getInt("gear.weapon.inventory-slot");
		ItemStack currentItem = p.getInventory().getItem(5);
			p.getInventory().setItem(weaponSlot, newWeapon);
	}

	public void putTimer(Player p, BukkitRunnable timerTask) {
		if (getCurrentTimers().containsKey(p)) {
			getCurrentTimers().get(p).cancel();
		}
		getCurrentTimers().put(p, timerTask);
	}

	public void removeTimer(Player p) {
		if (getCurrentTimers().containsKey(p)) {
			getCurrentTimers().get(p).cancel();
		}
		getCurrentTimers().remove(p);
	}
	public void disable() {
		for (Player p : playerPvpStates.keySet()) {
			if (isInPvP(p)) disablePvP(p);
		}
		playerPvpStates.clear();
	}
}
