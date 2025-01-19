package com.jay.kanhaiya.hubpvpplus.EventListeners;

import com.jay.kanhaiya.hubpvpplus.DamageIndi.CreateHologramTask;
import com.jay.kanhaiya.hubpvpplus.DamageIndi.VectorGenerator;
import com.jay.kanhaiya.hubpvpplus.HubPvPPlus;
import com.jay.kanhaiya.hubpvpplus.PvPHandler.PvPManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class DamageListener implements Listener {



	@EventHandler(priority = EventPriority.HIGHEST)
	public void dfgsdfghj(PlayerItemDamageEvent event) {
		Player player = event.getPlayer ( );
		if (  player != null){
			if ( !HubPvPPlus.getInstance().getConfig ().getList ("restricted-worlds").contains (event.getPlayer ().getWorld ().getName ()) && HubPvPPlus.getInstance().getPvpManager ().isInPvP ( event.getPlayer ()) && HubPvPPlus.getInstance ().getPvpManager ().getWeapon ().isSimilar (event.getItem ()))
			{
				event.setCancelled (true);
			}
		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void dfgsdfghj(EntityDamageEvent event) {
		if ( event.getEntity () instanceof Player ){
			if ( event.isCancelled () && !HubPvPPlus.getInstance().getConfig ().getList ("restricted-worlds").contains (event.getEntity ().getWorld ().getName ()) && HubPvPPlus.getInstance().getPvpManager ().isInPvP (((Player) event.getEntity ()).getPlayer ()) &&  ((Player) event.getEntity()).getPlayer().getInventory ().contains (HubPvPPlus.getInstance().getPvpManager ().getWeapon ()) ){
				event.setCancelled (false);
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void enityDAMGEEvent(EntityDamageByEntityEvent event) {
		if ( event.getDamager () == null || event.getEntity ()== null )return;
		if ( !(event.getDamager () instanceof Player) || !(event.getEntity () instanceof Player)  )return;

		Player attacker = (Player) event.getDamager ();
		Player victim = (Player) event.getEntity ();

		if ( event.isCancelled () && !HubPvPPlus.getInstance().getConfig ().getList ("restricted-worlds").contains (attacker.getWorld ().getName ()) && HubPvPPlus.getInstance().getPvpManager ().isInPvP (attacker) && HubPvPPlus.getInstance().getPvpManager ().isInPvP (victim) &&((Player) event.getEntity()).getPlayer().getInventory ().contains (HubPvPPlus.getInstance().getPvpManager ().getWeapon ())){
		event.setCancelled (false);
			VectorGenerator vectorGenerator = new VectorGenerator ();
			CreateHologramTask createHologramTask = new CreateHologramTask (HubPvPPlus.getInstance(), vectorGenerator, event, (Player) event.getDamager(), HubPvPPlus.getInstance().hologramManager);
			createHologramTask.run();
		}else if ( !HubPvPPlus.getInstance().getConfig ().getList ("restricted-worlds").contains (attacker.getWorld ().getName ()) && HubPvPPlus.getInstance().getPvpManager ().isInPvP (attacker) && HubPvPPlus.getInstance().getPvpManager ().isInPvP (victim) &&((Player) event.getEntity()).getPlayer().getInventory ().contains (HubPvPPlus.getInstance().getPvpManager ().getWeapon ()) ){
			VectorGenerator vectorGenerator = new VectorGenerator ();
			CreateHologramTask createHologramTask = new CreateHologramTask (HubPvPPlus.getInstance(), vectorGenerator, event, (Player) event.getDamager(), HubPvPPlus.getInstance().hologramManager);
			createHologramTask.run();
		}
		if ( !HubPvPPlus.getInstance().getPvpManager ().isInPvP (attacker) &&  !HubPvPPlus.getInstance().getConfig ().getList ("restricted-worlds").contains (attacker.getWorld ().getName ()) ){
			event.setCancelled (true);
		}
	}
/*	@EventHandler
	public void onMoveWithFKey(InventoryClickEvent event) {
		if ( event.getClick ( ) == ClickType. &&) {
			event.setCancelled (true);
		}
	}*/
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		HubPvPPlus instance = HubPvPPlus.getInstance();
		World newWorld = player.getLocation().getWorld();
		PvPManager pvpManager = instance.getPvpManager();
		if (instance.getConfig().getStringList("restricted-worlds").contains(Objects.requireNonNull(newWorld).getName()) || newWorld.equals ("world")) {
			removeWeaponsFromInventory(player,pvpManager.getWeapon());
		}
	}

	private void removeWeaponsFromInventory(Player player, ItemStack is) {
				player.getInventory().remove(is);}

}
