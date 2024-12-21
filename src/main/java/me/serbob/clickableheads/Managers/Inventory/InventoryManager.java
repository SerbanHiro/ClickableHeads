package me.serbob.clickableheads.Managers.Inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryManager {
    private static final Map<UUID, Inventory> activeInventories = new HashMap<>();

    public static void registerInventory(Player player, Inventory inventory) {
        activeInventories.put(player.getUniqueId(), inventory);
    }

    public static void unregisterInventory(Player player) {
        activeInventories.remove(player.getUniqueId());
    }

    public static Inventory getPlayerInventory(Player player) {
        return activeInventories.get(player.getUniqueId());
    }

    public static boolean hasActiveInventory(Player player) {
        return activeInventories.containsKey(player.getUniqueId());
    }
}
