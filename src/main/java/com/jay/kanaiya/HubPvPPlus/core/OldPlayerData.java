package com.jay.kanaiya.HubPvPPlus.core;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public record OldPlayerData(Player player, ItemStack[] armor, boolean canFly) {

}
