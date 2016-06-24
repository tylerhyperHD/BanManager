/*
 * This class file is (c) 2016 tylerhyperHD
 */
package me.confuser.banmanager;

public class PluginLogger {
    
    private static BanManager plugin;
    
    public PluginLogger(BanManager plugin) {
        PluginLogger.plugin = plugin;
    }
    
    public static void info(String msg) {
        plugin.getLogger().info(msg);
    }
    
    public static void warn(String msg) {
        plugin.getLogger().warning(msg);
    }
    
    public static void warn(Exception ex) {
        plugin.getLogger().warning(ex.getMessage());
    }
    
    public static void severe(String msg) {
        plugin.getLogger().severe(msg);
    }
    
    public static void severe(Exception ex) {
        plugin.getLogger().severe(ex.getMessage());
    }
}
