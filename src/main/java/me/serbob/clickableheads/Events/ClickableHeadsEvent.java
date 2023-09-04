package me.serbob.clickableheads.Events;

import me.serbob.clickableheads.Classes.ClickableHead;
import me.serbob.clickableheads.Managers.Core;
import me.serbob.clickableheads.Managers.Inventory.InventoryManager;
import me.serbob.clickableheads.Managers.Inventory.MainHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClickableHeadsEvent implements Listener {
    @EventHandler
    public void checkHead(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item= event.getCurrentItem();
        InventoryHolder holder = event.getWhoClicked().getOpenInventory().getTopInventory().getHolder();
        if(holder instanceof MainHolder) {
            if (item == null) {
                return;
            }
            if (item.getType() == null) {
                return;
            }
            if (item.getType() == Material.AIR) {
                return;
            }
            Player target=Core.checkSkull(item);

            List<String> test = new ArrayList<>();
            if(target!=null) {
                ClickableHead clickableHead = new ClickableHead(
                        target,
                        target.getName(),
                        Collections.emptyList()
                );
                if (clickableHead.isClickableHead()) {
                    clickableHead.initializeGUI(new MainHolder(), 36, "              Statistics");

                    generateClickableHeadGUI(clickableHead);

                    player.sendMessage(clickableHead.getName());

                    clickableHead.openGUI(player);
                }
            }
            if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("back")) {
                InventoryManager.openGUI(player);
            }
            event.setCancelled(true);
        }
    }

    /**
     * This is just an example, you can add whatever parameters you want
     * @param clickableHead
     */
    public void generateClickableHeadGUI(ClickableHead clickableHead) {
        Player target = clickableHead.getPlayer();
        List<String> test = new ArrayList<>();
        test.add("&aCurrent kills: &f" + target.getStatistic(Statistic.PLAYER_KILLS));
        clickableHead.addItem(10, Core.createItem(
                Material.valueOf("DIAMOND_SWORD"),
                "&e&lPLAYER KILLS",
                test
        ));
        test = new ArrayList<>();
        test.add("&aHere you can see player's statistics");
        clickableHead.addItem(31, Core.createItem(
                Material.valueOf("ARROW"),
                "&e&lINFO",
                test
        ));
        test = new ArrayList<>();
        test.add("Go back");
        clickableHead.addItem(32, Core.createItem(
                Material.valueOf("BARRIER"),
                "&cBACK",
                test
        ));
    }
}
