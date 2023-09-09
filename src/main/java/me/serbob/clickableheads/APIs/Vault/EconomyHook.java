package me.serbob.clickableheads.APIs.Vault;

import me.serbob.clickableheads.ClickableHeads;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyHook {
    private static Economy econ = null;
    public EconomyHook() {
        setupEconomy();
    }
    public static boolean isVaultEnabled() {
        return ClickableHeads.instance.getServer().getPluginManager().getPlugin("Vault") != null;
    }
    public static Economy getEconomy() {
        return econ;
    }
    private boolean setupEconomy() {
        if (ClickableHeads.instance.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = ClickableHeads.instance.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static String getFormattedMoney(Player player) {
        double money = getEconomy().getBalance(Bukkit.getOfflinePlayer(player.getUniqueId()));
        String formattedMoney = getEconomy().currencyNamePlural()+formatMoney(money);

        return formattedMoney;
    }
    public static String formatCustomMoney(double amount) {
        return getEconomy().currencyNamePlural()+formatMoney(amount);
    }
    public static String formatMoney(double money) {
        if (money >= 1_000_000_000_000d) {
            return String.format("%.1fT", money / 1_000_000_000_000d);
        } else if (money >= 1_000_000_000d) {
            return String.format("%.1fB", money / 1_000_000_000d);
        } else if (money >= 1_000_000d) {
            return String.format("%.1fM", money / 1_000_000d);
        } else if (money >= 1_000d) {
            return String.format("%.1fK", money / 1_000d);
        } else {
            return String.format("%.1f", money);
        }
    }
}
