package com.jay.kanaiya.HubPvPPlus.PvPHandler;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public record OldPlayerData(Player player, ItemStack[] armor, boolean canFly) {

}
