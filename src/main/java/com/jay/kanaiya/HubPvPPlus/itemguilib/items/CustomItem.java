/*     */ package com.jay.kanaiya.HubPvPPlus.itemguilib.items;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import com.jay.kanaiya.HubPvPPlus.itemguilib.ItemGuiLib;
import org.bukkit.Bukkit;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class CustomItem
/*     */   implements Listener {
/*     */   private final ItemStack itemStack;
/*     */   private Consumer<PlayerInteractEvent> consumer;
/*     */   
/*     */   public CustomItem(ItemStack itemStack, Consumer<PlayerInteractEvent> consumer) {
/*  25 */     this.itemStack = itemStack;
/*  26 */     this.consumer = consumer;
/*     */     
/*  28 */     Bukkit.getPluginManager().registerEvents(this, (Plugin) ItemGuiLib.getPluginInstance());
/*     */   }
/*     */   
/*     */   public CustomItem(ItemStack itemStack) {
/*  32 */     this.itemStack = itemStack;
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack() {
/*  36 */     return this.itemStack;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteract(PlayerInteractEvent e) {
/*  41 */     if (e.getPlayer().getInventory().getItemInHand().isSimilar(this.itemStack) && (
/*  42 */       e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
/*  43 */       e.setCancelled(true);
/*     */       
/*  45 */       this.consumer.accept(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomItem addLore(String... strings) {
/*  56 */     addLore(Arrays.asList(strings));
/*  57 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomItem addLore(List<String> strings) {
/*  66 */     ItemMeta m = getItemStack().getItemMeta();
/*  67 */     List<String> lore = (m.getLore() != null) ? m.getLore() : new ArrayList<>();
/*     */     
/*  69 */     lore.addAll(strings);
/*  70 */     m.setLore(lore);
/*  71 */     getItemStack().setItemMeta(m);
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomItem setName(String name) {
/*  81 */     ItemMeta meta = getItemStack().getItemMeta();
/*  82 */     meta.setDisplayName(name);
/*  83 */     getItemStack().setItemMeta(meta);
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomItem addFlags(ItemFlag... flags) {
/*  93 */     ItemMeta meta = getItemStack().getItemMeta();
/*  94 */     meta.addItemFlags(flags);
/*  95 */     getItemStack().setItemMeta(meta);
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomItem addEnchant(Enchantment enchant, int level) {
/* 106 */     ItemMeta meta = getItemStack().getItemMeta();
/* 107 */     meta.addEnchant(enchant, level, false);
/* 108 */     getItemStack().setItemMeta(meta);
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   public CustomItem setUnbreakable(boolean unbreakable) {
/* 113 */     ItemMeta meta = getItemStack().getItemMeta();
/* 114 */     meta.setUnbreakable(unbreakable);
/* 115 */     getItemStack().setItemMeta(meta);
/* 116 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\JayRaj\BitDownloads\HubPvP-1.4.2.jar!\me\quared\itemguilib\items\CustomItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */