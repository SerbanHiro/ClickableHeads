package me.serbob.clickableheads.example.listener;

import me.serbob.clickableheads.Classes.ClickableHead;
import me.serbob.clickableheads.example.gui.MainHolder;
import me.serbob.clickableheads.example.gui.ExampleGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class ExampleListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof MainHolder)) return;
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;

        ClickableHead.handleClick(event);

        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() &&
                ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("back")) {
            ExampleGUI.openGUI((Player) event.getWhoClicked());
        }
    }

    @EventHandler // MAKE SURE YOU CALL THIS TOO!!!!!
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof MainHolder) {
            ClickableHead.cleanup(event.getInventory());
        }
    }
}
