package com.jay.kanhaiya.hubpvpplus.DamageIndi;

import com.jay.kanhaiya.hubpvpplus.HubPvPPlus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class CreateHologramTask extends BukkitRunnable {

    private final HubPvPPlus plugin;
    private final EntityDamageEvent e;
    private final VectorGenerator vectorGenerator;
    private final HologramManager hologramManager;

    private Player playerDamager = null;

    private final ChatColor RED = ChatColor.RED;

    public CreateHologramTask(HubPvPPlus plugin, VectorGenerator vectorGenerator, EntityDamageEvent e, HologramManager hologramManager) {
        this.plugin = plugin;
        this.e = e;
        this.vectorGenerator = vectorGenerator;
        this.hologramManager = hologramManager;
    }

    public CreateHologramTask(HubPvPPlus plugin, VectorGenerator vectorGenerator, EntityDamageEvent e, Player playerDamager, HologramManager hologramManager) {
        this.plugin = plugin;
        this.e = e;
        this.playerDamager = playerDamager;
        this.vectorGenerator = vectorGenerator;
        this.hologramManager = hologramManager;
    }

    @Override
    public void run() {
        double dmgFinal = e.getFinalDamage();
        Entity victim = e.getEntity();

        ArmorStand hologram = (ArmorStand) victim.getWorld().spawnEntity(victim.getLocation(), EntityType.ARMOR_STAND);
        hologram.setVisible(false);
        hologram.setMetadata ("IsDamage",new FixedMetadataValue (plugin,true));
        hologram.setBasePlate(false);
        hologram.setArms(false);
        hologram.setSmall(true);
        //hologram.(true);
        hologram.setCanPickupItems(false);
       // hologram.gli(true);
        hologram.setLeftLegPose(EulerAngle.ZERO.add(180, 0, 0));
        hologram.setRightLegPose(EulerAngle.ZERO.add(180, 0, 0));
       /// hologram.inv(true);
        hologram.setVelocity(plugin.getRingBuffer().getNext());
        hologramManager.addHologram(hologram);

        String customName = (ChatColor.RED+ "☣ "+ ((int) dmgFinal)+" ☣");

        //checking if a critical hit
        if (playerDamager != null) {
            if (playerDamager.getFallDistance() > 0
                    && !(((Entity) playerDamager).isOnGround())
                    && !(playerDamager.getLocation().getBlock().getType().equals(Material.VINE))
                    && !(playerDamager.getLocation().getBlock().getType().equals(Material.LADDER))
                    && !(playerDamager.getLocation().getBlock().getType().equals(Material.WATER))
                    && !(playerDamager.getLocation().getBlock().getType().equals(Material.LAVA))
                    && !(playerDamager.hasPotionEffect(PotionEffectType.BLINDNESS))
                    && playerDamager.getVehicle() == null) {
                customName = customName + ChatColor.DARK_RED + "" + ChatColor.ITALIC + ChatColor.BOLD + " Crit!";
            }
        }

        hologram.setCustomNameVisible(true);
        hologram.setCustomName(customName);

       // victim.getWorld().spawnParticle(Particle.HEART, victim.getLocation().add(0, 2.5, 0), 1);

        CleanupHologramTask cleanupTask = new CleanupHologramTask(hologram, hologramManager);
        cleanupTask.runTaskLater(plugin, 20);
    }
}

