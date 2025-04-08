package com.jay.kanhaiya.hubpvpplus.EventListeners;

import com.jay.kanhaiya.hubpvpplus.ColorFixedUtil.ConfigColorUtil;
import com.jay.kanhaiya.hubpvpplus.PvPHandler.PvPState;
import com.jay.kanhaiya.hubpvpplus.HubPvPPlus;
import com.jay.kanhaiya.hubpvpplus.PvPHandler.PvPManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class PlayerJoinListener implements Listener {
HubPvPPlus lobbyPvP;

    public PlayerJoinListener(HubPvPPlus hubPvPPlus) {
        this.lobbyPvP = hubPvPPlus;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PvPManager pvPManager = HubPvPPlus.getInstance().getPvpManager();
        if ( !HubPvPPlus.getInstance().getConfig ().getStringList ("restricted-worlds").contains (p.getWorld ().getName ())&& !p.getWorld ( ).getPVP ( ) ) {
            p.getWorld ( ).setPVP (true);
            pvPManager.giveWeapon (p);
            new BukkitRunnable (){
                @Override
                public void run() {
                    pvPManager.giveWeapon (p);
                }
            }.runTaskLater (lobbyPvP,100);
        }else if (  !HubPvPPlus.getInstance().getConfig ().getStringList ("restricted-worlds").contains (p.getWorld ().getName ())){
            pvPManager.giveWeapon (p);
            new BukkitRunnable (){
                @Override
                public void run() {
                    pvPManager.giveWeapon (p);
                }
            }.runTaskLater (lobbyPvP,100);
        }
        pvPManager.setPlayerState(p, PvPState.OFF);

        //pvPManager.getOldPlayerDataList().add(new OldPlayerData(p, p.getInventory().getArmorContents(), p.getAllowFlight()));
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        PvPManager pvPManager = HubPvPPlus.getInstance().getPvpManager();
        if ( !HubPvPPlus.getInstance().getConfig ().getStringList ("restricted-worlds").contains (p.getWorld ().getName ())&& !p.getWorld ( ).getPVP ( ) ) {
            p.getWorld ( ).setPVP (true);
            pvPManager.giveWeapon (p);
            new BukkitRunnable (){
                @Override
                public void run() {
                    pvPManager.giveWeapon (p);
                }
            }.runTaskLater (lobbyPvP,100);
        }else if (  !HubPvPPlus.getInstance().getConfig ().getStringList ("restricted-worlds").contains (p.getWorld ().getName ())){
            pvPManager.giveWeapon (p);
            new BukkitRunnable (){
                @Override
                public void run() {
                    pvPManager.giveWeapon (p);
                }
            }.runTaskLater (lobbyPvP,100);
        }

       // pvPManager.getOldPlayerDataList().add(new OldPlayerData(p, p.getInventory().getArmorContents(), p.getAllowFlight()));
        pvPManager.setPlayerState(p, PvPState.OFF);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PvPManager pvpManager = HubPvPPlus.getInstance().getPvpManager();
        Player p = e.getPlayer();
        PvPManager pvPManager = HubPvPPlus.getInstance().getPvpManager();
        p.getInventory().remove(pvpManager.getWeapon());
        pvPManager.disablePvP(p);
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack droppedItem = event.getItemDrop().getItemStack();

        // Check if the item matches one of your custom items (weapon, armor)
        if (isCustomPvPItem(droppedItem)) {
            // Cancel the drop event
            player.sendMessage(ConfigColorUtil.colorize(Objects.requireNonNull(HubPvPPlus.getInstance().getConfig().getString("messages.cant-drop-item"))));
            event.setCancelled(true);
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
            player.sendMessage(ConfigColorUtil.colorize(Objects.requireNonNull(HubPvPPlus.getInstance().getConfig().getString("messages.cant-move-item"))));
        }
    }
 /*   private void showParticles(Player player) {
        player.getWorld().spawnParticle(Particle.DRIP_LAVA, player.getLocation(), 10);
    }*/
}
