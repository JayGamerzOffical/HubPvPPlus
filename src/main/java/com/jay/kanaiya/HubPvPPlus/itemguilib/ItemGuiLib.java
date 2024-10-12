/*    */ package com.jay.kanaiya.HubPvPPlus.itemguilib;
/*    */ 
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public final class ItemGuiLib
/*    */ {
/*    */   private static JavaPlugin instance;
/*    */   
/*    */   public static void setPluginInstance(JavaPlugin instance) {
/* 10 */     ItemGuiLib.instance = instance;
/*    */   }
/*    */   
/*    */   public static JavaPlugin getPluginInstance() {
/* 14 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\JayRaj\BitDownloads\HubPvP-1.4.2.jar!\me\quared\itemguilib\ItemGuiLib.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */