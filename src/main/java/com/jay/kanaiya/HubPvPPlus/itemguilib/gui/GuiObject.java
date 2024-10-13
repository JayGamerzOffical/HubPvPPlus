 package com.jay.kanaiya.HubPvPPlus.itemguilib.gui;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.function.Consumer;
 import com.jay.kanaiya.HubPvPPlus.itemguilib.ItemGuiLib;
 import lombok.Setter;
 import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.Material;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.Listener;
 import org.bukkit.event.inventory.InventoryClickEvent;
 import org.bukkit.inventory.ItemFlag;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.meta.ItemMeta;
 import org.bukkit.metadata.MetadataValue;
 import org.bukkit.metadata.Metadatable;
 import org.bukkit.plugin.Plugin;
 
 public class GuiObject implements Metadatable, Listener {
   private final Map<String, List<MetadataValue>> values = new HashMap<>(); private final ItemStack stack;
   @Setter
   private Gui owningGui;
   private final Consumer<InventoryClickEvent> consumer;

   
   public GuiObject(Material material, int amount, short durability, Consumer<InventoryClickEvent> consumer) {
     this(new ItemStack(material, amount, durability), consumer);
   }
    public GuiObject(ItemStack stack, Consumer<InventoryClickEvent> consumer) {
     this.stack = stack;
     Bukkit.getPluginManager().registerEvents(this, (Plugin)ItemGuiLib.getPluginInstance());
     this.consumer = consumer;
   }

     public ItemStack getItem() {return this.stack;
   }
 
   
   public void setMetadata(String s, MetadataValue metadataValue) {
   List<MetadataValue> values = new ArrayList<>();
     values.add(metadataValue);
     this.values.put(s, values);
   }
   public GuiObject setName(String name) {
     ItemMeta meta = getItem().getItemMeta();
     meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
     getItem().setItemMeta(meta);
     return this;
   }

   public List<MetadataValue> getMetadata(String s) {
    return this.values.get(s);
   }
 
   
   public boolean hasMetadata(String s) {
     return this.values.containsKey(s);
   }
 
   
   public void removeMetadata(String s, Plugin plugin) {
     this.values.remove(s);
   }
   
   @EventHandler
   public void onClick(InventoryClickEvent e) {
     if (e.getClickedInventory() != null && this.owningGui != null && e.getClickedInventory().equals(this.owningGui.getInventory())) {
       e.setCancelled(true);
      if (e.getCurrentItem() == null) {
         return;
       }
       
      if (e.getCurrentItem().isSimilar(getItem()))
        this.consumer.accept(e);
     } 
   }
 }
