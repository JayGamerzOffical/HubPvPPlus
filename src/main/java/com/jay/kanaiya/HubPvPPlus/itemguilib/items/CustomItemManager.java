package com.jay.kanaiya.HubPvPPlus.itemguilib.items;

import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class CustomItemManager {

    private static CustomItemManager instance;
    private final Map<String, CustomItem> customItems;

    private CustomItemManager() {
        this.customItems = new HashMap<>();
    }

    public static CustomItemManager get() {
        if (instance == null) {
            instance = new CustomItemManager();
        }
        return instance;
    }

    /**
     * Create and register a custom item with a unique identifier.
     *
     * @param itemStack ItemStack to be used for creating the custom item.
     * @return CustomItem instance created.
     */
    public CustomItem createCustomItem(ItemStack itemStack) {
        CustomItem customItem = new CustomItem(itemStack);
        String key = generateUniqueKey(customItem);
        customItems.put(key, customItem);
        return customItem;
    }

    /**
     * Get a custom item by its identifier.
     *
     * @param key The identifier of the custom item.
     * @return The CustomItem if found, null otherwise.
     */
    public CustomItem getCustomItem(String key) {
        return customItems.get(key);
    }

    /**
     * Remove a custom item by its identifier.
     *
     * @param key The identifier of the custom item.
     */
    public void removeCustomItem(String key) {
        customItems.remove(key);
    }

    /**
     * Generate a unique key for each custom item based on the item properties.
     *
     * @param customItem Custom item to generate the key for.
     * @return Generated unique key.
     */
    private String generateUniqueKey(CustomItem customItem) {
        // You can customize this to generate unique keys based on the item meta or stack information
        return customItem.getItemStack().getType().toString() + "_" + customItems.size();
    }
}
