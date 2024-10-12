/*     */ package com.jay.kanaiya.HubPvPPlus.itemguilib.gui;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import com.jay.kanaiya.HubPvPPlus.itemguilib.ItemGuiLib;
import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.metadata.Metadatable;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class GuiObject implements Metadatable, Listener {
/*  23 */   private final Map<String, List<MetadataValue>> values = new HashMap<>(); private final ItemStack stack;
/*     */   private Gui owningGui;
/*     */   private final Consumer<InventoryClickEvent> consumer;
/*     */   
/*     */   public GuiObject(Material material, int amount) {
/*  28 */     this(new ItemStack(material, amount));
/*     */   }
/*     */   
/*     */   public GuiObject(Material material) {
/*  32 */     this(new ItemStack(material));
/*     */   }
/*     */   
/*     */   public GuiObject(Material material, int amount, short durability) {
/*  36 */     this(new ItemStack(material, amount, durability));
/*     */   }
/*     */   
/*     */   public GuiObject(Material material, short durability) {
/*  40 */     this(new ItemStack(material, 1, durability));
/*     */   }
/*     */   
/*     */   public GuiObject(Material material, int amount, Consumer<InventoryClickEvent> consumer) {
/*  44 */     this(material, amount, (short)0, consumer);
/*     */   }
/*     */   
/*     */   public GuiObject(Material material, int amount, short durability, Consumer<InventoryClickEvent> consumer) {
/*  48 */     this(new ItemStack(material, amount, durability), consumer);
/*     */   }
/*     */   
/*     */   public GuiObject(Material material, Consumer<InventoryClickEvent> consumer) {
/*  52 */     this(new ItemStack(material), consumer);
/*     */   }
/*     */   
/*     */   public GuiObject(Material material, short durability, Consumer<InventoryClickEvent> consumer) {
/*  56 */     this(material, 1, durability, consumer);
/*     */   }
/*     */   
/*     */   public GuiObject(ItemStack stack) {
/*  60 */     this(stack, e -> {
/*     */         
/*     */         });
/*     */   } public GuiObject(ItemStack stack, Consumer<InventoryClickEvent> consumer) {
/*  64 */     this.stack = stack;
/*  65 */     Bukkit.getPluginManager().registerEvents(this, (Plugin) ItemGuiLib.getPluginInstance());
/*  66 */     this.consumer = consumer;
/*     */   }
/*     */   
/*     */   public void setOwningGui(Gui gui) {
/*  70 */     this.owningGui = gui;
/*     */   }
/*     */   
/*     */   public ItemStack getItem() {
/*  74 */     return this.stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMetadata(String s, MetadataValue metadataValue) {
/*  79 */     List<MetadataValue> values = new ArrayList<>();
/*  80 */     values.add(metadataValue);
/*  81 */     this.values.put(s, values);
/*     */   }
/*     */   
/*     */   public GuiObject addLore(String... s) {
/*  85 */     addLore(Arrays.asList(s));
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public GuiObject addLore(List<String> strings) {
/*  90 */     ItemMeta m = getItem().getItemMeta();
/*  91 */     List<String> lore = (m.getLore() != null) ? m.getLore() : new ArrayList<>();
/*     */     
/*  93 */     lore.addAll(strings);
/*  94 */     m.setLore(lore);
/*  95 */     getItem().setItemMeta(m);
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiObject setName(String name) {
/* 101 */     ItemMeta meta = getItem().getItemMeta();
/* 102 */     meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
/* 103 */     getItem().setItemMeta(meta);
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   public GuiObject addFlags(ItemFlag... flags) {
/* 108 */     ItemMeta meta = getItem().getItemMeta();
/* 109 */     meta.addItemFlags(flags);
/* 110 */     getItem().setItemMeta(meta);
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<MetadataValue> getMetadata(String s) {
/* 116 */     return this.values.get(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMetadata(String s) {
/* 121 */     return this.values.containsKey(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMetadata(String s, Plugin plugin) {
/* 126 */     this.values.remove(s);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onClick(InventoryClickEvent e) {
/* 131 */     if (e.getClickedInventory() != null && this.owningGui != null && e.getClickedInventory().equals(this.owningGui.getInventory())) {
/* 132 */       e.setCancelled(true);
/* 133 */       if (e.getCurrentItem() == null) {
/*     */         return;
/*     */       }
/*     */       
/* 137 */       if (e.getCurrentItem().isSimilar(getItem()))
/* 138 */         this.consumer.accept(e); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\JayRaj\BitDownloads\HubPvP-1.4.2.jar!\me\quared\itemguilib\gui\GuiObject.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */