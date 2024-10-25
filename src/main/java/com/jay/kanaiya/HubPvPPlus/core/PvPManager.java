package com.jay.kanaiya.HubPvPPlus.core;

import com.jay.kanaiya.HubPvPPlus.HubPvPPlus;
import com.jay.kanaiya.HubPvPPlus.ColorFixedUtil.ConfigColorUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class PvPManager {

	private final Map<Player, PvPState> playerPvpStates;
	private final Map<Player, BukkitRunnable> currentTimers;
	private final List<OldPlayerData> oldPlayerDataList;

	private ItemStack weapon, helmet, chestplate, leggings, boots;
	private FileConfiguration itemsConfig;
	private Map<String, ItemStack> customItems;

	public PvPManager() {
		playerPvpStates = new HashMap<>();
		currentTimers = new HashMap<>();
		oldPlayerDataList = new ArrayList<>();
		customItems = new HashMap<>();
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

	public ItemStack createHubItems(ItemStack itemStack) {
		ItemStack customItem = new ItemStack(itemStack);
		customItems.put(itemStack.getType().toString(), customItem);
		return customItem;
	}
	public boolean isInPvP(Player player) {
		return getPlayerState(player) == PvPState.ON || getPlayerState(player) == PvPState.DISABLING;
	}

	public PvPState getPlayerState(Player p) {
		return playerPvpStates.get(p);
	}

	public ItemStack getItemFromConfig(String name) {
		String material = itemsConfig.getString("items." + name + ".material");
		if (material == null) {
			HubPvPPlus.getInstance().getLogger().warning("Material for item " + name + " is null!");
			return null;
		}

		ItemStack item = createHubItems(new ItemStack(Material.valueOf(material)));
		ItemMeta meta = item.getItemMeta();

		String itemName = itemsConfig.getString("items." + name + ".name");
		if (itemName != null && !itemName.isEmpty() && meta != null) {
			meta.setDisplayName(ConfigColorUtil.colorize(itemName));
		}

		List<String> lore = itemsConfig.getStringList("items." + name + ".lore");
		if (lore != null && !lore.isEmpty() && meta != null) {
			meta.setLore(ConfigColorUtil.colorize(lore));
		}

		List<String> enchants = itemsConfig.getStringList("items." + name + ".enchantments");
		if (enchants != null && !enchants.isEmpty()) {
			for (String enchant : enchants) {
				String[] split = enchant.split(":");
				Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(split[0].toLowerCase()));
				int level = split.length > 1 ? Integer.parseInt(split[1]) : 1;

				if (enchantment != null) {
					item.addUnsafeEnchantment(enchantment, level);
				} else {
					HubPvPPlus.getInstance().getLogger().warning("Could not find enchantment " + split[0]);
				}
			}
		}

		if (meta != null) {
			meta.setUnbreakable(true);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			item.setItemMeta(meta);
		}

		return item;
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

		player.sendMessage(ConfigColorUtil.colorize(HubPvPPlus.getInstance().getConfig().getString("lang.pvp-enabled")));
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

		player.sendMessage(ConfigColorUtil.colorize(HubPvPPlus.getInstance().getConfig().getString("lang.pvp-disabled")));
	}

	public void giveWeapon(Player p) {
		ItemStack newWeapon = weapon;
		int weaponSlot = itemsConfig.getInt("items.weapon.slot") - 1;
		ItemStack currentItem = p.getInventory().getItem(weaponSlot);

		if (currentItem == null || currentItem.getType() == Material.AIR) {
			p.getInventory().setItem(weaponSlot, newWeapon);
		} else if (!currentItem.isSimilar(newWeapon)) {
			p.sendMessage("Please change the item in your weapon slot from plugins/HubPvPPlus/items.yml.");
		}
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
