package me.serbob.clickableheads;

import me.serbob.clickableheads.Commands.ClickableHeadsCommand;
import me.serbob.clickableheads.Events.ClickableHeadsEvent;
import me.serbob.clickableheads.Managers.Utils.TemplateManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClickableHeads extends JavaPlugin {
    public static ClickableHeads instance;
    @Override
    public void onEnable() {
        instance=this; // this is how I get the instances
        TemplateManager.createTemplateFolder();
        TemplateManager.saveExampleTemplateConfig();
        getCommand("clickableheads").setExecutor(new ClickableHeadsCommand());
        getServer().getPluginManager().registerEvents(new ClickableHeadsEvent(),this);
    }
    @Override
    public void onDisable() {
    }
}
