 package com.jay.kanaiya.HubPvPPlus.itemguilib.gui;
 
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.bukkit.Bukkit;
 import org.bukkit.inventory.Inventory;
 
 public class Gui
 {
   private final Inventory inventory;
   private final Map<Integer, GuiObject> objects;
   private final int size;
   
   public Gui(Map<Integer, GuiObject> objects, int size, String name) {
    this.inventory = Bukkit.createInventory(null, size, name);
     this.objects = objects;
     this.size = size;
     
    for (Iterator<Integer> iterator = objects.keySet().iterator(); iterator.hasNext(); ) { int key = ((Integer)iterator.next()).intValue();
      setItem(key, objects.get(Integer.valueOf(key))); }
   
   }
   
   public void setItem(int loc, GuiObject item) {
     this.objects.put(Integer.valueOf(loc), item);
    item.setOwningGui(this);
     this.inventory.setItem(loc, item.getItem());
   }
   
   public Inventory getInventory() {
     return this.inventory;
   }
   
   public boolean slotTaken(int slot) {
     return (this.inventory.getItem(slot) != null);
   }
   
   public List<Integer> getUntakenSlots() {
    List<Integer> ints = new ArrayList<>();
     this.objects.keySet().forEach(i -> {
           if (!slotTaken(i.intValue()))
             ints.add(i); 
        }); return ints;
   }
   
   public int getSize() {
   return this.size;
   }
   
   public Map<Integer, GuiObject> getObjects() {
   return this.objects;
   }
   
   public void fillEmpty(GuiObject fill) {
     for (Iterator<Integer> iterator = getUntakenSlots().iterator(); iterator.hasNext(); ) { int i = ((Integer)iterator.next()).intValue();
     setItem(i, fill); }
   
   }
 }