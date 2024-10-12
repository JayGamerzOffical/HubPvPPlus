package com.jay.kanaiya.HubPvPPlus.listeners;

import com.jay.kanaiya.HubPvPPlus.core.OldPlayerData;
import com.jay.kanaiya.HubPvPPlus.core.PvPState;
import com.jay.kanaiya.HubPvPPlus.HubPvPPlus;
import com.jay.kanaiya.HubPvPPlus.core.PvPManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

}
