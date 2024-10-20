package com.jay.kanaiya.HubPvPPlus.listeners;

import com.jay.kanaiya.HubPvPPlus.core.OldPlayerData;
import com.jay.kanaiya.HubPvPPlus.core.PvPState;
import com.jay.kanaiya.HubPvPPlus.HubPvPPlus;
import com.jay.kanaiya.HubPvPPlus.core.PvPManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PvPManager pvPManager = HubPvPPlus.instance().pvpManager();

        if (p.hasPermission("hpp.use") &&
                !HubPvPPlus.instance().getConfig().getStringList("disabled-worlds").contains(p.getWorld().getName())) {
            pvPManager.giveWeapon(p);
        }

        pvPManager.getOldPlayerDataList().add(new OldPlayerData(p, p.getInventory().getArmorContents(), p.getAllowFlight()));
        pvPManager.setPlayerState(p, PvPState.OFF);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        PvPManager pvPManager = HubPvPPlus.instance().pvpManager();

        pvPManager.removePlayer(p);
    }

    // Prevent the specific item from being moved in the inventory
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        PvPManager pvPManager = HubPvPPlus.instance().pvpManager();
        // Check if the clicked item is the specific item
        if (event.getCurrentItem() != null && event.getCurrentItem().isSimilar(pvPManager.getWeapon().getItemStack())) {
            event.setCancelled(true); // Cancel the movement
            player.sendMessage("You cannot move this item.");
        }
    }

    // Prevent the specific item from being dropped
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        PvPManager pvPManager = HubPvPPlus.instance().pvpManager();
        // Check if the dropped item is the specific item
        if (event.getItemDrop().getItemStack().isSimilar(pvPManager.getWeapon().getItemStack())) {
            event.setCancelled(true); // Cancel the drop
            player.sendMessage("You cannot drop this item.");
        }
    }
}
