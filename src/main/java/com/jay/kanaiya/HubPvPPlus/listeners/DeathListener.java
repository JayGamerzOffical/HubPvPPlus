package com.jay.kanaiya.HubPvPPlus.listeners;

import com.jay.kanaiya.HubPvPPlus.HubPvPPlus;
import com.jay.kanaiya.HubPvPPlus.core.PvPManager;
import com.jay.kanaiya.HubPvPPlus.util.StringUtil;
import org.bukkit.Location;
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

        int healthOnKill = instance.getConfig().getInt("health-on-kill");

        e.setKeepInventory(true);
        e.setKeepLevel(true);

        victim.getInventory().setHeldItemSlot(0);

        if (healthOnKill != -1) {
            double newHealth = killer.getHealth() + healthOnKill;
            // Cap the health at 20 (default max health in 1.8.8)
            killer.setHealth(Math.min(newHealth, killer.getMaxHealth()));

            killer.sendMessage(StringUtil.colorize(instance.getConfig().getString("health-gained-message")
                    .replace("%extra%", String.valueOf(healthOnKill)).replace("%killed%", victim.getDisplayName())));
        }

        pvpManager.disablePvP(victim);

        victim.sendMessage(StringUtil.colorize(instance.getConfig().getString("lang.killed")).replace("%killer%", killer.getDisplayName()));

        killer.sendMessage(
                StringUtil.colorize(instance.getConfig().getString("lang.killed-other")).replace("%killed%", victim.getDisplayName()));

        e.setDeathMessage("");
    }
}
