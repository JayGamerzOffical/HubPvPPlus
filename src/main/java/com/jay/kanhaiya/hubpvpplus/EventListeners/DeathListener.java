package com.jay.kanhaiya.hubpvpplus.EventListeners;

import com.jay.kanhaiya.hubpvpplus.ColorFixedUtil.ConfigColorUtil;
import com.jay.kanhaiya.hubpvpplus.DamageIndi.CreateHologramTask;
import com.jay.kanhaiya.hubpvpplus.DamageIndi.VectorGenerator;
import com.jay.kanhaiya.hubpvpplus.HubPvPPlus;
import com.jay.kanhaiya.hubpvpplus.PvPHandler.PvPManager;
import com.jay.kanhaiya.hubpvpplus.PvPHandler.PvPState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class DeathListener implements Listener {

  @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent e) {
      Player victim = e.getEntity();
      // Get the main instance and PvPManager
      HubPvPPlus instance = HubPvPPlus.getInstance();
      PvPManager pvpManager = instance.getPvpManager();
      Player killer = victim.getKiller();
      // Check if killer is null or if either player is not in PvP mode
      if (killer == null || !pvpManager.isInPvP(victim) || !pvpManager.isInPvP(killer)) {
          return;
      }

      if ( !HubPvPPlus.getInstance().getConfig ().getStringList ("restricted-worlds").contains (victim.getWorld ().getName ()) && HubPvPPlus.getInstance().getPvpManager ().isInPvP (victim) && HubPvPPlus.getInstance().getPvpManager ().isInPvP (killer)  ){
          // Prevent dropping items on death and keep experience
          e.getDrops().clear();
          e.setKeepLevel(true);

          // Set the victim's PvP state to OFF
          pvpManager.setPlayerState(victim, PvPState.OFF);

          // Check permissions and give the victim their weapon back
          if (victim.hasPermission("hubpvpplus.use") &&
                  !instance.getConfig().getStringList("restricted-worlds").contains(victim.getWorld().getName())) {
              pvpManager.giveWeapon(victim);
          }

          // Set the death message
          String deathMessage = instance.getConfig().getString("messages.death-broadcast");
          if (deathMessage == null) {
              deathMessage = "&c%victim% has died!";
          }
          e.setDeathMessage(ConfigColorUtil.colorize(deathMessage.replace("%victim%", victim.getDisplayName())));
      }
    }
}
