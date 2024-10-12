package com.jay.kanaiya.HubPvPPlus.itemguilib.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItem {
    private final ItemStack itemStack;

    public CustomItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Set the display name of the item.
     *
     * @param name The new display name.
     */
    public void setName(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            itemStack.setItemMeta(meta);
        }
    }

    /**
     * Add lore to the item.
     *
     * @param lore The list of lore strings to add.
     */
    public void addLore(List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            List<String> existingLore = meta.getLore();
            if (existingLore == null) {
                existingLore = new ArrayList<>();
            }
            existingLore.addAll(lore);
            meta.setLore(existingLore);
            itemStack.setItemMeta(meta);
        }
    }

    /**
     * Add enchantment to the item.
     *
     * @param enchantment The enchantment to add.
     * @param level       The level of the enchantment.
     */
    public void addEnchantment(org.bukkit.enchantments.Enchantment enchantment, int level) {
        itemStack.addEnchantment(enchantment, level);
    }

    /**
     * Adds ItemFlags to the item to hide certain attributes (e.g., enchantments).
     *
     * @param flags The flags to add to the item.
     */
    public void addFlags(org.bukkit.inventory.ItemFlag... flags) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(flags);
            itemStack.setItemMeta(meta);
        }
    }

    /**
     * Make the item unbreakable.
     *
     * @param unbreakable Whether the item is unbreakable.
     */
    public void setUnbreakable(boolean unbreakable) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(unbreakable);
            itemStack.setItemMeta(meta);
        }
    }
}
