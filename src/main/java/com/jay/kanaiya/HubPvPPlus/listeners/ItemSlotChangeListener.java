package com.jay.kanaiya.HubPvPPlus.listeners;

import com.jay.kanaiya.HubPvPPlus.HubPvPPlus;
import com.jay.kanaiya.HubPvPPlus.core.PvPManager;
import com.jay.kanaiya.HubPvPPlus.core.PvPState;
import com.jay.kanaiya.HubPvPPlus.util.StringUtil;
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
		Player p = e.getPlayer();
		ItemStack held = p.getInventory().getItem(e.getNewSlot());
		HubPvPPlus instance = HubPvPPlus.instance();
		PvPManager pvpManager = instance.pvpManager();

		if (!p.hasPermission("hpp.use")) return;

		if (Objects.equals(held, pvpManager.getWeapon().getItemStack())) {
			// PvP enabling logic
			if (pvpManager.getPlayerState(p) == PvPState.DISABLING) pvpManager.setPlayerState(p, PvPState.ON);
			if (pvpManager.getPlayerState(p) == PvPState.ENABLING) return;

			if (instance.getConfig().getStringList("disabled-worlds").contains(p.getWorld().getName())) {
				p.sendMessage(StringUtil.colorize(instance.getConfig().getString("lang.disabled-in-world")));
				return;
			}

			if (!pvpManager.isInPvP(p)) {
				pvpManager.setPlayerState(p, PvPState.ENABLING);

				BukkitRunnable enableTask = new BukkitRunnable() {
					int time = instance.getConfig().getInt("enable-cooldown") + 1;

					public void run() {
						time--;
						if (pvpManager.getPlayerState(p) != PvPState.ENABLING || !held.isSimilar(pvpManager.getWeapon().getItemStack())) {
							pvpManager.removeTimer(p);
							cancel();
						} else if (time == 0) {
							pvpManager.enablePvP(p);
							p.playSound(p.getLocation(), "minecraft:entity.enderman.teleport", 1.0f, 1.0f);

							pvpManager.removeTimer(p);
							cancel();
						} else {
							p.sendMessage(StringUtil.colorize(instance.getConfig().getString("lang.pvp-enabling").replace("%time%", Integer.toString(time))));
							p.playSound(p.getLocation(), "minecraft:block.note_block.bass", 2.0f, 1.0f);
						}
					}
				};
				pvpManager.putTimer(p, enableTask);
				enableTask.runTaskTimer(instance, 0L, 20L);
			}

		} else if (pvpManager.isInPvP(p)) {
			// PvP disabling logic
			if (pvpManager.getPlayerState(p) == PvPState.ENABLING) pvpManager.setPlayerState(p, PvPState.OFF);
			if (pvpManager.getPlayerState(p) == PvPState.DISABLING) return;

			pvpManager.setPlayerState(p, PvPState.DISABLING);
			BukkitRunnable disableTask = new BukkitRunnable() {

				int time = instance.getConfig().getInt("disable-cooldown") + 1;

				public void run() {
					time--;
					if (pvpManager.getPlayerState(p) != PvPState.DISABLING || held != null && held.isSimilar(pvpManager.getWeapon().getItemStack())) {
						p.playSound(p.getLocation(), "minecraft:block.note_block.bass", 1.0f, 1.0f);
						pvpManager.removeTimer(p);
						cancel();
					} else if (time == 0) {
						pvpManager.disablePvP(p);
						p.playSound(p.getLocation(), "minecraft:entity.enderman.teleport", 1.0f, 1.0f);
						pvpManager.removeTimer(p);
						cancel();
					} else {
						p.sendMessage(StringUtil.colorize(instance.getConfig().getString("lang.pvp-disabling").replace("%time%", Integer.toString(time))));
						p.playSound(p.getLocation(), "minecraft:block.note_block.bass", 2.0f, 1.0f);
					}
				}
			};
			pvpManager.putTimer(p, disableTask);
			disableTask.runTaskTimer(instance, 0L, 20L);
		} else {
			// Default state
			pvpManager.setPlayerState(p, PvPState.OFF);
			pvpManager.removeTimer(p);
		}
	}
}
