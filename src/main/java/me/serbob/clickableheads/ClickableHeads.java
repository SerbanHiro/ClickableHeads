package me.serbob.clickableheads;

import me.serbob.clickableheads.APIs.Vault.EconomyHook;
import me.serbob.clickableheads.example.commands.ClickableHeadsCommand;
import me.serbob.clickableheads.example.listener.ExampleListener;
import me.serbob.clickableheads.Managers.Utils.TemplateManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClickableHeads extends JavaPlugin {
    public static ClickableHeads instance;
    public static EconomyHook economyHook;
    @Override
    public void onEnable() {
        instance=this; // this is how I get the instances
        economyHook = new EconomyHook();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().severe(
                    String.format("[%s] - PAPI Feature disabled due to no PlaceholderAPI dependency found!",
                            getDescription().getName()));
        }
        TemplateManager.createTemplateFolder();
        TemplateManager.saveExampleTemplateConfig();

        getCommand("clickableheads").setExecutor(new ClickableHeadsCommand());
        getServer().getPluginManager().registerEvents(new ExampleListener(),this);
    }
    @Override
    public void onDisable() {
    }
}
