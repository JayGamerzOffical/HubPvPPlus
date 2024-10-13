package com.jay.kanaiya.HubPvPPlus.itemguilib.items;

import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class CustomItemManager {

    private static CustomItemManager instance;
    private final Map<String, CustomItem> customItems;

    private CustomItemManager() {
        customItems = new HashMap<>();
    }

    public static CustomItemManager get() {
        if (instance == null) {
            instance = new CustomItemManager();
        }
        return instance;
    } public CustomItem createCustomItem(ItemStack itemStack) {
        CustomItem customItem = new CustomItem(itemStack);
        // Register the custom item using the item type or a custom name (if applicable)
        customItems.put(itemStack.getType().toString(), customItem);
        return customItem;
    }

}