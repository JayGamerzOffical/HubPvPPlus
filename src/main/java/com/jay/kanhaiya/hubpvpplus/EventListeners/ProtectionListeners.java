package com.jay.kanhaiya.hubpvpplus.EventListeners;

import com.jay.kanhaiya.hubpvpplus.HubPvPPlus;
import com.jay.kanhaiya.hubpvpplus.PvPHandler.PvPManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ProtectionListeners implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		PvPManager pvPManager = HubPvPPlus.getInstance().getPvpManager();
		if (item == null) return;

		if (pvPManager.isInPvP(p)) {
			if (item.isSimilar(pvPManager.getWeapon())) {
				e.setCancelled(true);
			} else if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItemDrop().getItemStack();
		PvPManager pvPManager = HubPvPPlus.getInstance().getPvpManager();

		if (pvPManager.isInPvP(p)) {
			if (item.isSimilar(pvPManager.getWeapon())) {
				e.setCancelled(true);
			} else if (item.getType().toString().toLowerCase().contains("armor")) {
				e.setCancelled(true);
			}
		}
	}

}
