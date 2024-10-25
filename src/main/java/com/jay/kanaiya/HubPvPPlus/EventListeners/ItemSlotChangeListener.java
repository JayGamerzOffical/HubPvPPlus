package com.jay.kanaiya.HubPvPPlus.EventListeners;

import com.jay.kanaiya.HubPvPPlus.HubPvPPlus;
import com.jay.kanaiya.HubPvPPlus.core.PvPManager;
import com.jay.kanaiya.HubPvPPlus.core.PvPState;
import com.jay.kanaiya.HubPvPPlus.ColorFixedUtil.ConfigColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class ItemSlotChangeListener implements Listener {

	@EventHandler
	public void onSlotChange(PlayerItemHeldEvent e) {
		Player player = e.getPlayer();
		ItemStack newHeldItem = player.getInventory().getItem(e.getNewSlot());
		HubPvPPlus hubPvPPlusInstance = HubPvPPlus.getInstance();
		PvPManager pvpManager = hubPvPPlusInstance.getPvpManager();

		if (!player.hasPermission("hpp.use")) {
			return;
		}

		if (hubPvPPlusInstance.getConfig().getStringList("disabled-worlds").contains(player.getWorld().getName())) {
			if (newHeldItem != null && newHeldItem.isSimilar(pvpManager.getWeapon())) {
				player.getInventory().setItem(e.getNewSlot(), null);
				player.sendMessage(ConfigColorUtil.colorize(hubPvPPlusInstance.getConfig().getString("lang.disabled-in-world")));
				return;
			}
		}

		if (Objects.equals(newHeldItem, pvpManager.getWeapon())) {
			if (pvpManager.getPlayerState(player) == PvPState.DISABLING) {
				pvpManager.setPlayerState(player, PvPState.ON);
			}
			if (pvpManager.getPlayerState(player) == PvPState.ENABLING) {
				return;
			}

			if (!pvpManager.isInPvP(player)) {
				pvpManager.setPlayerState(player, PvPState.ENABLING);

				BukkitRunnable enableTask = new BukkitRunnable() {
					int countdown = hubPvPPlusInstance.getConfig().getInt("enable-cooldown") + 1;

					public void run() {
						countdown--;
						if (pvpManager.getPlayerState(player) != PvPState.ENABLING || !Objects.requireNonNull(newHeldItem).isSimilar(pvpManager.getWeapon())) {
							pvpManager.removeTimer(player);
							cancel();
						} else if (countdown == 0) {
							pvpManager.enablePvP(player);
							playSoundIfEnabled(player, hubPvPPlusInstance, "minecraft:entity.enderman.teleport");
							pvpManager.removeTimer(player);
							cancel();
						} else {
							notifyPlayerWithMessage(player, hubPvPPlusInstance, "lang.pvp-enabling", countdown);
							playSoundIfEnabled(player, hubPvPPlusInstance, "minecraft:block.note_block.bass");
						}
					}
				};
				pvpManager.putTimer(player, enableTask);
				enableTask.runTaskTimer(hubPvPPlusInstance, 0L, 20L);
			}

		} else if (pvpManager.isInPvP(player)) {
			if (pvpManager.getPlayerState(player) == PvPState.ENABLING) {
				pvpManager.setPlayerState(player, PvPState.OFF);
			}
			if (pvpManager.getPlayerState(player) == PvPState.DISABLING) {
				return;
			}

			pvpManager.setPlayerState(player, PvPState.DISABLING);

			BukkitRunnable disableTask = new BukkitRunnable() {
				int countdown = hubPvPPlusInstance.getConfig().getInt("disable-cooldown") + 1;

				public void run() {
					countdown--;
					if (pvpManager.getPlayerState(player) != PvPState.DISABLING || (newHeldItem != null && newHeldItem.isSimilar(pvpManager.getWeapon()))) {
						playSoundIfEnabled(player, hubPvPPlusInstance, "minecraft:block.note_block.bass");
						pvpManager.removeTimer(player);
						cancel();
					} else if (countdown == 0) {
						pvpManager.disablePvP(player);
						playSoundIfEnabled(player, hubPvPPlusInstance, "minecraft:entity.enderman.teleport");
						pvpManager.removeTimer(player);
						cancel();
					} else {
						notifyPlayerWithMessage(player, hubPvPPlusInstance, "lang.pvp-disabling", countdown);
						playSoundIfEnabled(player, hubPvPPlusInstance, "minecraft:block.note_block.bass");
					}
				}
			};
			pvpManager.putTimer(player, disableTask);
			disableTask.runTaskTimer(hubPvPPlusInstance, 0L, 20L);
		} else {
			pvpManager.setPlayerState(player, PvPState.OFF);
			pvpManager.removeTimer(player);
		}
	}

	private void playSoundIfEnabled(Player player, HubPvPPlus instance, String sound) {
		if (instance.getConfig().getBoolean("sound-effects")) {
			player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
		}
	}

	private void notifyPlayerWithMessage(Player player, HubPvPPlus instance, String configPath, int time) {
		player.sendMessage(ConfigColorUtil.colorize(Objects.requireNonNull(instance.getConfig().getString(configPath)).replace("%time%", Integer.toString(time))));
	}
}