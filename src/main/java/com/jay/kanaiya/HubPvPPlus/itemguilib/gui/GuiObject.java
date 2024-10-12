 package com.jay.kanaiya.HubPvPPlus.itemguilib.gui;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.function.Consumer;
 import com.jay.kanaiya.HubPvPPlus.itemguilib.ItemGuiLib;
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
   private Gui owningGui;
   private final Consumer<InventoryClickEvent> consumer;
   
   public GuiObject(Material material, int amount) {
     this(new ItemStack(material, amount));
   }
   
   public GuiObject(Material material) {
    this(new ItemStack(material));
   }
   
   public GuiObject(Material material, int amount, short durability) {
     this(new ItemStack(material, amount, durability));
   }
   
   public GuiObject(Material material, short durability) {
     this(new ItemStack(material, 1, durability));
   }
   
   public GuiObject(Material material, int amount, Consumer<InventoryClickEvent> consumer) {
     this(material, amount, (short)0, consumer);
   }
   
   public GuiObject(Material material, int amount, short durability, Consumer<InventoryClickEvent> consumer) {
     this(new ItemStack(material, amount, durability), consumer);
   }
   
   public GuiObject(Material material, Consumer<InventoryClickEvent> consumer) {
     this(new ItemStack(material), consumer);
   }
   
   public GuiObject(Material material, short durability, Consumer<InventoryClickEvent> consumer) {
     this(material, 1, durability, consumer);
   }
   
   public GuiObject(ItemStack stack) {
     this(stack, e -> {
         
         });
   } public GuiObject(ItemStack stack, Consumer<InventoryClickEvent> consumer) {
     this.stack = stack;
     Bukkit.getPluginManager().registerEvents(this, (Plugin)ItemGuiLib.getPluginInstance());
     this.consumer = consumer;
   }
   
   public void setOwningGui(Gui gui) {
     this.owningGui = gui;
   }
   
   public ItemStack getItem() {return this.stack;
   }
 
   
   public void setMetadata(String s, MetadataValue metadataValue) {
   List<MetadataValue> values = new ArrayList<>();
     values.add(metadataValue);
     this.values.put(s, values);
   }
   
   public GuiObject addLore(String... s) {
    addLore(Arrays.asList(s));
    return this;
   }
   
   public GuiObject addLore(List<String> strings) {
     ItemMeta m = getItem().getItemMeta();
    List<String> lore = (m.getLore() != null) ? m.getLore() : new ArrayList<>();
     
     lore.addAll(strings);
     m.setLore(lore);
     getItem().setItemMeta(m);
     return this;
   }
 
   
   public GuiObject setName(String name) {
     ItemMeta meta = getItem().getItemMeta();
     meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
     getItem().setItemMeta(meta);
     return this;
   }
   
   public GuiObject addFlags(ItemFlag... flags) {
    ItemMeta meta = getItem().getItemMeta();
    meta.addItemFlags(flags);
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
