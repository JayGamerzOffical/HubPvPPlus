package com.jay.kanaiya.HubPvPPlus.listeners;

import com.jay.kanaiya.HubPvPPlus.HubPvPPlus;
import com.jay.kanaiya.HubPvPPlus.core.PvPManager;
import com.jay.kanaiya.HubPvPPlus.util.StringUtil;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        HubPvPPlus instance = HubPvPPlus.instance();
        PvPManager pvpManager = instance.pvpManager();

        if (e.getEntity().getKiller() == null) return;

        Player victim = e.getEntity();
        Player killer = victim.getKiller();

        if (!pvpManager.isInPvP(victim) || !pvpManager.isInPvP(killer)) return;


        e.setKeepInventory(true);
        e.setKeepLevel(true);

        pvpManager.disablePvP(victim);
        e.setDeathMessage(StringUtil.colorize(instance.getConfig().getString("lang.broadcast_msg")).replace("%victim%", victim.getDisplayName()));
    }

}
