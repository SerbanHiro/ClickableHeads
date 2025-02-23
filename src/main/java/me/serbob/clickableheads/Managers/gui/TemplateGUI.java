package me.serbob.clickableheads.Managers.gui;

import me.serbob.clickableheads.ClickableHeads;
import me.serbob.clickableheads.Managers.Utils.TemplateManager;
import me.serbob.clickableheads.Utils.GlobalUtil;
import me.serbob.clickableheads.example.gui.MainHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TemplateGUI {
    private final Inventory inventory;
    private final YamlConfiguration config;
    private final OfflinePlayer target;

    public TemplateGUI(
            String templateName,
            String title,
            int size,
            OfflinePlayer target,
            InventoryHolder holder
    ) {
        this.inventory = Bukkit.createInventory(holder, size, title);
        this.config = TemplateManager.getTemplate(templateName);
        this.target = target;
        setupItems();
    }

    private void setupItems() {
        if (config == null) return;

        for (String key : config.getConfigurationSection("gui").getKeys(false)) {
            int position = config.getInt("gui." + key + ".position");
            Material material = Material.valueOf(config.getString("gui." + key + ".material"));
            String name = config.getString("gui." + key + ".name");
            List<String> lore = config.getStringList("gui." + key + ".lore");

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(GlobalUtil.c(TemplateManager.replacePlayerStatisticPlaceholder(target, name)));

            List<String> formattedLore = new ArrayList<>();
            for (String line : lore) {
                formattedLore.add(GlobalUtil.c(TemplateManager.replacePlayerStatisticPlaceholder(target, line)));
            }
            meta.setLore(formattedLore);
            item.setItemMeta(meta);


            inventory.setItem(position, item);
        }
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }
}
