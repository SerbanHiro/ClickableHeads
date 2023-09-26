package me.serbob.clickableheads.Managers.Inventory;

import me.serbob.clickableheads.Classes.ClickableHead;
import me.serbob.clickableheads.Managers.VersionManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class InventoryManager {
    public static void openGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(new MainHolder(),9,"Test");

        List<String> test = new ArrayList<>();
        test.add("Stats: "+player.getStatistic(Statistic.valueOf("PLAY_ONE_MINUTE")));

        ClickableHead clickableHead = new ClickableHead(player,player.getName(),test);
        OfflinePlayer dummyPlayer = Bukkit.getOfflinePlayer("Spectetor");
        ClickableHead dummyClickableHead = new ClickableHead(dummyPlayer,dummyPlayer.getName(), Arrays.asList("Dummy test player"));

        inventory.setItem(4, clickableHead.getHead());
        inventory.setItem(5,dummyClickableHead.getHead());

        player.openInventory(inventory);
    }
}
