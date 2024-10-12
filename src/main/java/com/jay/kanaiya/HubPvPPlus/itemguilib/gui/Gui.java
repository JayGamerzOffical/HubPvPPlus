/*    */ package com.jay.kanaiya.HubPvPPlus.itemguilib.gui;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class Gui
/*    */ {
/*    */   private final Inventory inventory;
/*    */   private final Map<Integer, GuiObject> objects;
/*    */   private final int size;
/*    */   
/*    */   public Gui(Map<Integer, GuiObject> objects, int size, String name) {
/* 17 */     this.inventory = Bukkit.createInventory(null, size, name);
/* 18 */     this.objects = objects;
/* 19 */     this.size = size;
/*    */     
/* 21 */     for (Iterator<Integer> iterator = objects.keySet().iterator(); iterator.hasNext(); ) { int key = ((Integer)iterator.next()).intValue();
/* 22 */       setItem(key, objects.get(Integer.valueOf(key))); }
/*    */   
/*    */   }
/*    */   
/*    */   public void setItem(int loc, GuiObject item) {
/* 27 */     this.objects.put(Integer.valueOf(loc), item);
/* 28 */     item.setOwningGui(this);
/* 29 */     this.inventory.setItem(loc, item.getItem());
/*    */   }
/*    */   
/*    */   public Inventory getInventory() {
/* 33 */     return this.inventory;
/*    */   }
/*    */   
/*    */   public boolean slotTaken(int slot) {
/* 37 */     return (this.inventory.getItem(slot) != null);
/*    */   }
/*    */   
/*    */   public List<Integer> getUntakenSlots() {
/* 41 */     List<Integer> ints = new ArrayList<>();
/* 42 */     this.objects.keySet().forEach(i -> {
/*    */           if (!slotTaken(i.intValue()))
/*    */             ints.add(i); 
/* 45 */         }); return ints;
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 49 */     return this.size;
/*    */   }
/*    */   
/*    */   public Map<Integer, GuiObject> getObjects() {
/* 53 */     return this.objects;
/*    */   }
/*    */   
/*    */   public void fillEmpty(GuiObject fill) {
/* 57 */     for (Iterator<Integer> iterator = getUntakenSlots().iterator(); iterator.hasNext(); ) { int i = ((Integer)iterator.next()).intValue();
/* 58 */       setItem(i, fill); }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\JayRaj\BitDownloads\HubPvP-1.4.2.jar!\me\quared\itemguilib\gui\Gui.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */