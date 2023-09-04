package me.serbob.clickableheads.Managers.Inventory;

import me.serbob.clickableheads.Classes.ClickableHead;
import me.serbob.clickableheads.Managers.VersionManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class InventoryManager {
    public static void openGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(new MainHolder(),9,"Test");

        List<String> test = new ArrayList<>();
        test.add("Stats: "+player.getStatistic(Statistic.valueOf("PLAY_ONE_MINUTE")));

        ClickableHead clickableHead = new ClickableHead(player,player.getName(),test);

        inventory.setItem(4, clickableHead.getHead());

        player.openInventory(inventory);
    }
}
