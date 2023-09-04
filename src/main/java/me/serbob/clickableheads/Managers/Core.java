package me.serbob.clickableheads.Managers;

import me.serbob.clickableheads.Utils.GlobalUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

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
    public static Player checkSkull(ItemStack skull) {
        Player target = null;
        try {
            target = ((SkullMeta) skull.getItemMeta()).getOwningPlayer().getPlayer();
        }catch (Exception ignored){}
        return target;
    }
}
