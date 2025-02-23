package me.serbob.clickableheads.Managers;

import me.serbob.clickableheads.Managers.Utils.TemplateManager;
import me.serbob.clickableheads.Utils.GlobalUtil;
import me.serbob.clickableheads.objectholders.ClickableHead;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Core {
    public static ItemStack createItem(Material material, String title, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(GlobalUtil.c(title));
        itemMeta.setLore(GlobalUtil.colorizeList(lore));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static OfflinePlayer checkSkull(ItemStack skull) {
        OfflinePlayer target = null;
        try {
            target = ((SkullMeta) skull.getItemMeta()).getOwningPlayer();
        }catch (Exception ignored){}
        return target;
    }
    public static void returnTemplateGUI(ClickableHead clickableHead, String templateName) {
        OfflinePlayer target = clickableHead.getPlayer();
        if(TemplateManager.doesTemplateExist(templateName)) {
            YamlConfiguration templateConfig = TemplateManager.getTemplate(templateName);
            for (String key : templateConfig.getConfigurationSection("gui").getKeys(false)) {
                int position = templateConfig.getInt("gui."+key+".position");
                Material material = Material.valueOf(templateConfig.getString("gui."+key+".material"));
                String name = GlobalUtil.c(TemplateManager.replacePlayerStatisticPlaceholder(target,templateConfig.getString("gui."+key+".name")));
                List<String> lore = new ArrayList<>();
                for(String loreKey:templateConfig.getStringList("gui."+key+".lore")) {
                    lore.add(GlobalUtil.c(TemplateManager.replacePlayerStatisticPlaceholder(target,loreKey)));
                }
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
    }
}
