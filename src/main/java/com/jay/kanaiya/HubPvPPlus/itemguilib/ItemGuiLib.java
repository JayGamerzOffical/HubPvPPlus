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

