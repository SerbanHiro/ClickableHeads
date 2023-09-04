package me.serbob.clickableheads.Commands;

import me.serbob.clickableheads.Managers.Inventory.InventoryManager;
import me.serbob.clickableheads.Utils.GlobalUtil;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClickableHeadsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(GlobalUtil.c("You are not a player!"));
            return false;
        }
        if(!sender.hasPermission("clickableheads.test")) {
            sender.sendMessage(GlobalUtil.c("You do not have permission to use this command"));
            return false;
        }
        Player player = (Player) sender;
        InventoryManager.openGUI(player);
        return true;
    }
}
