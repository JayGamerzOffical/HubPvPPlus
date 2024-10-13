/**
 * This is for Future Work and other testing.
 *
 * @param ForFuture
 */
package com.jay.kanaiya.HubPvPPlus.itemguilib;
 
 import org.bukkit.plugin.java.JavaPlugin;
 
 public final class ItemGuiLib
 {
   private static JavaPlugin instance;
   
   public static void setPluginInstance(JavaPlugin instance) {
    ItemGuiLib.instance = instance;
   }
   
   public static JavaPlugin getPluginInstance() {
     return instance;
   }
 }

