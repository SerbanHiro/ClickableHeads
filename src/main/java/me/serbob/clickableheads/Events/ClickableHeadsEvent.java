package me.serbob.clickableheads.Events;

import me.serbob.clickableheads.Classes.ClickableHead;
import me.serbob.clickableheads.Managers.Core;
import me.serbob.clickableheads.Managers.Inventory.InventoryManager;
import me.serbob.clickableheads.Managers.Inventory.MainHolder;
import me.serbob.clickableheads.Managers.Utils.TemplateManager;
import me.serbob.clickableheads.Utils.GlobalUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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

            if(target!=null) {
                ClickableHead clickableHead = new ClickableHead(
                        target,
                        target.getName(),
                        Collections.emptyList()
                );
                if (clickableHead.isClickableHead()) {
                    clickableHead.initializeGUI(new MainHolder(), 36, "              Statistics");

                    //generateClickableHeadGUI(clickableHead);

                    Core.returnTemplateGUI(clickableHead,"example.yml");

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
        Player target = clickableHead.getPlayer().getPlayer();
        List<String> test = new ArrayList<>();
        if(TemplateManager.doesTemplateExist("example.yml")) {
            System.out.println("1");
            YamlConfiguration templateConfig = TemplateManager.getTemplate("example.yml");
            System.out.println("2");
            for (String key : templateConfig.getConfigurationSection("gui").getKeys(false)) {
                int position = templateConfig.getInt("gui."+key+".position");
                System.out.println(position+"");
                Material material = Material.valueOf(templateConfig.getString("gui."+key+".material"));
                System.out.println(material+"");
                String name = GlobalUtil.c(TemplateManager.replacePlayerStatisticPlaceholder(target,templateConfig.getString("gui."+key+".name")));
                System.out.println(name);
                List<String> lore = new ArrayList<>();
                for(String loreKey:templateConfig.getStringList("gui."+key+".lore")) {
                    lore.add(GlobalUtil.c(TemplateManager.replacePlayerStatisticPlaceholder(target,loreKey)));
                }
                System.out.println(lore);
                ItemStack itemStack = new ItemStack(material);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(name);
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                clickableHead.addItem(
                        position,
                        itemStack
                );
            }

        }
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
