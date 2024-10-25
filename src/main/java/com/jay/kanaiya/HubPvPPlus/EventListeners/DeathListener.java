package com.jay.kanaiya.HubPvPPlus.EventListeners;

import com.jay.kanaiya.HubPvPPlus.HubPvPPlus;
import com.jay.kanaiya.HubPvPPlus.PvPHandler.PvPManager;
import com.jay.kanaiya.HubPvPPlus.ColorFixedUtil.ConfigColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        HubPvPPlus instance = HubPvPPlus.getInstance();
        PvPManager pvpManager = instance.getPvpManager();

        Player victim = e.getEntity();
        Player killer = victim.getKiller();

        // Check if the killer is null or if either player is not in PvP mode
        if (killer == null || !pvpManager.isInPvP(victim) || !pvpManager.isInPvP(killer)) return;

        // Prevent dropping items on death
        e.getDrops().clear(); // Clear the drops

        // Keep inventory and experience levels
        e.setKeepInventory(true);
        e.setKeepLevel(true);

        // Disable PvP for the victim
        pvpManager.disablePvP(victim);

        // Set the death message
        e.setDeathMessage(ConfigColorUtil.colorize(instance.getConfig().getString("lang.broadcast_msg")).replace("%victim%", victim.getDisplayName()));
    }
}
