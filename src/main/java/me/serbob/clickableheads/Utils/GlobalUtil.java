package me.serbob.clickableheads.Utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobalUtil {
    public static String c(String preColor) {
        return format(ChatColor.translateAlternateColorCodes('&',preColor));
    }
    private static final Pattern HEX_PATTERN = Pattern.compile("&(#[A-Fa-f0-9]{6})");
    private static String format(String string) {
        Matcher matcher = HEX_PATTERN.matcher(string);
        while (matcher.find())
            string = string.replace(matcher.group(), "" + net.md_5.bungee.api.ChatColor.of(matcher.group().replace("&","")));
        return string;
    }
    public static List<String> colorizeList(List<String> list) {
        List<String> tempList = new ArrayList();
        for(String key:list) {
            tempList.add(c(key));
        }
        return tempList;
    }
}