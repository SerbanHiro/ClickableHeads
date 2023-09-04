package me.serbob.clickableheads.Classes;

import me.serbob.clickableheads.ClickableHeads;
import me.serbob.clickableheads.Managers.VersionManager;
import me.serbob.clickableheads.Utils.GlobalUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;
import java.util.List;

public class ClickableHead {
    private Inventory GUI;
    private final OfflinePlayer player;
    private final ItemStack head;
    private final String name;
    private final List<String> lore;
    public ClickableHead(OfflinePlayer player,
                         String name,
                         List<String> lore
                          ) {
        GUI=null;
        this.player=player;
        this.name=player==null?"Name is null":name;
        this.lore=player==null?Collections.emptyList():lore;
        head=player==null?invalidHead():getAllVersionsSkull(player,name,lore);
    }
    public boolean isClickableHead() {
        return head!=null;
    }
    public OfflinePlayer getPlayer() {
        return player;
    }
    public ItemStack getHead() {
        return head;
    }
    public String getName() {
        return name;
    }
    public List<String> getLore() {
        return lore;
    }
    /**
     * Initialize GUI, use this for showing player stats.
     * @param inventoryHolder
     * @param size
     * @param title
     */
    public void initializeGUI(InventoryHolder inventoryHolder, int size, String title) {
        GUI= Bukkit.createInventory(inventoryHolder,size,title);
    }
    /**
     * Add item inside the GUI after it has been initialized
     * @param position
     * @param itemToBeAdded
     */
    public void addItem(int position, ItemStack itemToBeAdded) {
        GUI.setItem(position,itemToBeAdded);
    }
    /**
     * Just open the GUI (
     * @param specifiedPlayer
     */
    public void openGUI(Player specifiedPlayer) {
        specifiedPlayer.openInventory(GUI);
    }
    /**
     * Generate an invalid itemstack in case the player == null
     * @return invalid
     */
    private ItemStack invalidHead() {
        ItemStack invalid = new ItemStack(Material.REDSTONE);
        ItemMeta invalidMeta = invalid.getItemMeta();
        invalidMeta.setDisplayName(GlobalUtil.c("&cINVALID PLAYER!"));
        invalid.setItemMeta(invalidMeta);
        return invalid;
    }
    /**
     * This function automatically checks for the version, therefore it gives the suitable skull
     * @param player
     * @param name
     * @param lore
     * @return headItem
     */
    private ItemStack getAllVersionsSkull(OfflinePlayer player, String name, List<String> lore) {
        ItemStack headItem;
        if (!VersionManager.isVersion1_12OrBelow()) {
            // For Minecraft versions 1.13 and onwards
            headItem = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) headItem.getItemMeta();
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
            skullMeta.setDisplayName(name);
            skullMeta.setLore(lore);
            headItem.setItemMeta(skullMeta);
        } else {
            // For Minecraft versions prior to 1.13
            headItem = getSkullItem(player.getName(),
                    name,
                    lore);
        }

        return headItem;
    }
    /**
     * This function is used for returning skull's for versions prior to 1.13
     * @param owner
     * @param displayName
     * @param lore
     * @return skullItem
     */
    private ItemStack getSkullItem(String owner, String displayName, List<String> lore) {
        ItemStack skullItem = new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
        skullMeta.setOwner(owner);
        skullMeta.setDisplayName(displayName);
        skullMeta.setLore(lore);
        skullItem.setItemMeta(skullMeta);
        return skullItem;
    }
}
