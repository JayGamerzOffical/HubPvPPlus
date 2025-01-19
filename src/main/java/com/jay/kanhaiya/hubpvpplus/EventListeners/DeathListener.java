package com.jay.kanhaiya.hubpvpplus.EventListeners;

import com.jay.kanhaiya.hubpvpplus.ColorFixedUtil.ConfigColorUtil;
import com.jay.kanhaiya.hubpvpplus.HubPvPPlus;
import com.jay.kanhaiya.hubpvpplus.PvPHandler.OldPlayerData;
import com.jay.kanhaiya.hubpvpplus.PvPHandler.PvPManager;
import com.jay.kanhaiya.hubpvpplus.PvPHandler.PvPState;
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
        if (killer == null || !pvpManager.isInPvP(victim) || !pvpManager.isInPvP(killer)) {

            // Prevent dropping items on death
            e.getDrops().clear(); // Clear the drops

            e.setKeepLevel(true);
            PvPManager pvPManager = HubPvPPlus.getInstance().getPvpManager();
            pvPManager.setPlayerState(victim, PvPState.OFF);
            if (victim.hasPermission("lp.use") &&
                    !HubPvPPlus.getInstance().getConfig().getStringList("restricted-worlds").contains(victim.getWorld().getName())) {
                pvPManager.giveWeapon(victim);
            }
            return;
        }

        // Prevent dropping items on death
        e.getDrops().clear(); // Clear the drops

        e.setKeepLevel(true);
        PvPManager pvPManager = HubPvPPlus.getInstance().getPvpManager();

        if (victim.hasPermission("lp.use") &&
                !HubPvPPlus.getInstance().getConfig().getStringList("restricted-worlds").contains(victim.getWorld().getName())) {
            pvPManager.giveWeapon(victim);
        }

        pvPManager.getOldPlayerDataList().add(new OldPlayerData(victim, victim.getInventory().getArmorContents(), victim.getAllowFlight()));
        pvPManager.setPlayerState(victim, PvPState.OFF);

        // Set the death message
       e.setDeathMessage(ConfigColorUtil.colorize(instance.getConfig().getString("messages.death-broadcast")).replace("%victim%", victim.getDisplayName()));
    }

}
