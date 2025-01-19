package com.jay.kanhaiya.hubpvpplus.DamageIndi;

import org.bukkit.entity.ArmorStand;

import java.util.HashMap;
import java.util.UUID;

public class HologramManager {

    private HashMap<UUID, ArmorStand> hologramList;

    public HologramManager()
    {
        this.hologramList = new HashMap<>();
    }

    public HashMap<UUID, ArmorStand> getHologramList()
    {
        return hologramList;
    }

    public void addHologram(ArmorStand hologram)
    {
        hologramList.put(hologram.getUniqueId(), hologram);
    }

    public void removeHologram(ArmorStand hologram)
    {
        if(!hologramList.containsKey(hologram.getUniqueId())) return;

        hologramList.remove(hologram.getUniqueId());
    }
}