package me.serbob.clickableheads.objectholders;

import me.serbob.clickableheads.Managers.VersionManager;
import me.serbob.clickableheads.Utils.GlobalUtil;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

public class ClickableHead {
    private static final Map<String, Consumer<InventoryClickEvent>> clickHandlers = new WeakHashMap<>();

    private final ItemStack head;
    private final OfflinePlayer player;
    private String name;
    private List<String> lore;
    private Consumer<InventoryClickEvent> clickHandler;

    public ClickableHead(OfflinePlayer player) {
        this.player = player;
        this.name = player == null ? "Name is null" : player.getName();
        this.lore = Collections.emptyList();
        this.head = player == null ? invalidHead() : createHead();
    }

    public ClickableHead(OfflinePlayer player, String name) {
        this.player = player;
        this.name = name;
        this.lore = Collections.emptyList();
        this.head = player == null ? invalidHead() : createHead();
    }

    public ClickableHead(OfflinePlayer player, String name, List<String> lore) {
        this.player = player;
        this.name = name;
        this.lore = lore;
        this.head = player == null ? invalidHead() : createHead();
    }

    public void setName(String name) {
        this.name = name;
        updateHeadMeta();
    }

    public String getName() {
        return name;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
        updateHeadMeta();
    }

    public List<String> getLore() {
        return lore;
    }

    public ItemStack getHead() {
        return head;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public ClickableHead onClick(Consumer<InventoryClickEvent> handler) {
        this.clickHandler = handler;
        return this;
    }

    public void addToInventory(Inventory inventory, int slot) {
        inventory.setItem(slot, head);
        if (clickHandler != null) {
            clickHandlers.put(getKey(inventory, slot), clickHandler);
        }
    }

    private String getKey(Inventory inventory, int slot) {
        return inventory.hashCode() + ":" + slot;
    }

    public static void handleClick(InventoryClickEvent event) {
        String key = event.getInventory().hashCode() + ":" + event.getSlot();
        Consumer<InventoryClickEvent> handler = clickHandlers.get(key);
        if (handler != null) {
            handler.accept(event);
        }
    }

    private void updateHeadMeta() {
        ItemMeta meta = head.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(GlobalUtil.c(name));
            meta.setLore(GlobalUtil.colorizeList(lore));
            head.setItemMeta(meta);
        }
    }

    private ItemStack invalidHead() {
        ItemStack invalid = new ItemStack(Material.REDSTONE);
        ItemMeta invalidMeta = invalid.getItemMeta();
        invalidMeta.setDisplayName(GlobalUtil.c("&cINVALID PLAYER!"));
        invalid.setItemMeta(invalidMeta);
        return invalid;
    }

    private ItemStack createHead() {
        ItemStack headItem;
        SkullMeta skullMeta;

        if (!VersionManager.isVersion1_12OrBelow()) {
            headItem = new ItemStack(Material.PLAYER_HEAD);
        } else {
            headItem = new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
        }

        skullMeta = (SkullMeta) headItem.getItemMeta();

        try {
            Class<?> gameProfileClass = Class.forName("com.mojang.authlib.GameProfile");
            Constructor<?> profileConstructor = gameProfileClass.getDeclaredConstructor(UUID.class, String.class);
            Object profile = profileConstructor.newInstance(player.getUniqueId(), player.getName());

            Method setProfileMethod = skullMeta.getClass().getDeclaredMethod("setProfile", gameProfileClass);
            setProfileMethod.setAccessible(true);
            setProfileMethod.invoke(skullMeta, profile);
        } catch (Exception ignored) {
            skullMeta.setOwningPlayer(player);
        }

        skullMeta.setDisplayName(GlobalUtil.c(name));
        skullMeta.setLore(GlobalUtil.colorizeList(lore));
        headItem.setItemMeta(skullMeta);
        return headItem;
    }

    public static void cleanup(Inventory inventory) {
        String prefix = inventory.hashCode() + ":";
        clickHandlers.entrySet().removeIf(entry -> entry.getKey().startsWith(prefix));
    }
}
