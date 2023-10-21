package me.serbob.clickableheads.APIs.PlaceholderAPI;

import org.bukkit.Bukkit;

public class PlaceholderAPI {
    public static boolean isPAPIenabled() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
}
