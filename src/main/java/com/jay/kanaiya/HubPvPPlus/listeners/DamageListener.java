package com.jay.kanaiya.HubPvPPlus.listeners;

import com.jay.kanaiya.HubPvPPlus.HubPvPPlus;
import com.jay.kanaiya.HubPvPPlus.core.PvPManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class DamageListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH) // Work on whitelisted/blacklisted worlds
	public void onDamage(EntityDamageByEntityEvent e) {
		HubPvPPlus instance = HubPvPPlus.instance();
		PvPManager pvpManager = instance.pvpManager();
		if (e.getEntity() instanceof Player damager && e.getDamager() instanceof Player damaged) {
			World world = damager.getLocation().getWorld();

			if (instance.getConfig().getStringList("disabled-worlds").contains(Objects.requireNonNull(world).getName())) {
				e.setCancelled(true);
			}

			e.setCancelled(!pvpManager.isInPvP(damager) || !pvpManager.isInPvP(damaged));
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		HubPvPPlus instance = HubPvPPlus.instance();
		World newWorld = player.getLocation().getWorld();
		PvPManager pvpManager = instance.pvpManager();
		if (instance.getConfig().getStringList("disabled-worlds").contains(Objects.requireNonNull(newWorld).getName())) {
			removeWeaponsFromInventory(player,pvpManager.getWeapon().getItemStack());
		}
	}

	private void removeWeaponsFromInventory(Player player, ItemStack is) {
				player.getInventory().remove(is);}

}
