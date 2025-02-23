package me.serbob.clickableheads.example.gui;

import me.serbob.clickableheads.objectholders.ClickableHead;
import me.serbob.clickableheads.Managers.gui.TemplateGUI;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class ExampleGUI {
    public static void openGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(new MainHolder(), 9, "Test");

        new ClickableHead(player, "&e" + player.getName(),
                Arrays.asList("Stats: " + player.getStatistic(Statistic.valueOf("PLAY_ONE_MINUTE"))))
                .onClick(event -> {
                    new TemplateGUI("example.yml", "              Statistics", 36, player)
                            .open(player);
                })
                .addToInventory(inventory, 4);

        new ClickableHead(Bukkit.getOfflinePlayer("Xicz_"), "&eXicz_",
                Arrays.asList("Dummy test player"))
                .onClick(event -> {
                    new TemplateGUI("example.yml", "              Statistics", 36,
                            Bukkit.getOfflinePlayer("Xicz_"))
                            .open(player);
                })
                .addToInventory(inventory, 5);

        player.openInventory(inventory);
    }
}
