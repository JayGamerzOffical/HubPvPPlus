package com.jay.kanhaiya.hubpvpplus;

import com.jay.kanhaiya.hubpvpplus.ColorFixedUtil.ConfigColorUtil;
import com.jay.kanhaiya.hubpvpplus.PvPHandler.PvPManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class LP_1_20 implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack droppedItem = event.getOffHandItem ();
        ItemStack dItem = event.getMainHandItem ();

        // Check if the item matches one of your custom items (weapon, armor)
        if (isCustomPvPItem(droppedItem)|| isCustomPvPItem(dItem)) {
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
}
