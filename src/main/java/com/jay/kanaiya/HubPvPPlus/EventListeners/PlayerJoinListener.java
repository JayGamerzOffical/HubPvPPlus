package com.jay.kanaiya.HubPvPPlus.EventListeners;

import com.jay.kanaiya.HubPvPPlus.PvPHandler.OldPlayerData;
import com.jay.kanaiya.HubPvPPlus.PvPHandler.PvPState;
import com.jay.kanaiya.HubPvPPlus.HubPvPPlus;
import com.jay.kanaiya.HubPvPPlus.PvPHandler.PvPManager;
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
        PvPManager pvPManager = HubPvPPlus.getInstance().getPvpManager();

        if (p.hasPermission("hpp.use") &&
                !HubPvPPlus.getInstance().getConfig().getStringList("disabled-worlds").contains(p.getWorld().getName())) {
            pvPManager.giveWeapon(p);
        }

        pvPManager.getOldPlayerDataList().add(new OldPlayerData(p, p.getInventory().getArmorContents(), p.getAllowFlight()));
        pvPManager.setPlayerState(p, PvPState.OFF);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        PvPManager pvPManager = HubPvPPlus.getInstance().getPvpManager();

        pvPManager.disablePvP(p);
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack droppedItem = event.getItemDrop().getItemStack();

        // Check if the item matches one of your custom items (weapon, armor)
        if (isCustomPvPItem(droppedItem)) {
            // Cancel the drop event
            event.setCancelled(true);
            player.sendMessage("You cannot drop this special PvP item!");
        }
    }

    private boolean isCustomPvPItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }
        PvPManager pvPManager = HubPvPPlus.getInstance().getPvpManager();
        // Check if the item matches your custom weapon or armor
        return item.isSimilar(pvPManager.getWeapon()) ||
                item.isSimilar(pvPManager.getHelmet()) ||
                item.isSimilar(pvPManager.getChestplate()) ||
                item.isSimilar(pvPManager.getLeggings()) ||
                item.isSimilar(pvPManager.getBoots());
    }
    // Prevent the specific item from being moved in the inventory
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        PvPManager pvPManager = HubPvPPlus.getInstance().getPvpManager();
        // Check if the clicked item is the specific item
        if (event.getCurrentItem() != null && event.getCurrentItem().isSimilar(pvPManager.getWeapon())) {
            event.setCancelled(true); // Cancel the movement
            player.sendMessage("You cannot move this item.");
        }
    }

    // Prevent the specific item from being dropped
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        PvPManager pvPManager = HubPvPPlus.getInstance().getPvpManager();
        // Check if the dropped item is the specific item
        if (event.getItemDrop().getItemStack().isSimilar(pvPManager.getWeapon())) {
            event.setCancelled(true); // Cancel the drop
            player.sendMessage("You cannot drop this item.");
        }
    }
}
