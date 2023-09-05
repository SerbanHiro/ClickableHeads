package me.serbob.clickableheads.Managers.Utils;

import me.serbob.clickableheads.ClickableHeads;
import me.serbob.clickableheads.Utils.Logger;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class TemplateManager {
    public static File templateFolder;
    public static void createTemplateFolder() {
        templateFolder = new File(ClickableHeads.instance.getDataFolder(), "templates");
        if(!templateFolder.exists()) {
            templateFolder.mkdirs();
        }
    }
    public static boolean doesTemplateExist(String templateName) {
        return new File(templateFolder,templateName).exists();
    }
    public static YamlConfiguration getTemplate(String templateName) {
        File file = new File(templateFolder,templateName);
        return YamlConfiguration.loadConfiguration(file);
    }
    public static void saveExampleTemplateConfig() {
        InputStream inputStream = ClickableHeads.instance.getResource("templates/example.yml");
        if (inputStream == null) {
            Logger.log(Logger.LogLevel.WARNING, "Default templates/example.yml not found in JAR.");
            return;
        }
        File exampleFile = new File(templateFolder, "example.yml");
        if (!exampleFile.exists()) {
            try {
                exampleFile.createNewFile();
                Files.copy(inputStream, exampleFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Logger.log(Logger.LogLevel.INFO, "Default templates/example.yml saved to plugin data folder.");
            } catch (IOException e) {
                Logger.log(Logger.LogLevel.WARNING, "Error saving default templates/example.yml to plugin data folder: " + e.getMessage());
            }
        }
    }
    public static String replacePlayerStatisticPlaceholder(Player player, String input) {
        input = input.replace("{playerName}",player.getName());
        System.out.println(getTotalBlocksMined(player)+"");
        while (input.contains("{") && input.contains("}")) {
            int startIndex = input.indexOf("{");
            int endIndex = input.indexOf("}");

            if (startIndex < endIndex) {
                String placeholder = input.substring(startIndex, endIndex + 1);
                String statisticName = input.substring(startIndex + 1, endIndex);

                try {
                    Statistic statistic;
                    String formattedValue;

                    switch (statisticName.toUpperCase()) {
                        case "MINE_BLOCK":
                            formattedValue = formatDouble(getTotalBlocksMined(player));
                            break;
                        case "KILL_ENTITY":
                            formattedValue = formatDouble(getTotalMobsKilled(player));
                            break;
                        default:
                            statistic = Statistic.valueOf(statisticName.toUpperCase());
                            double rawValue = player.getStatistic(statistic);
                            formattedValue = formatStatistic(statistic, rawValue);
                            break;
                    }
                    input = input.replace(placeholder, formattedValue);
                } catch (Exception ignored){
                    input = input.replace(placeholder, "");
                }
            } else {
                input = input.replace("}", "");
            }
        }

        return input;
    }
    private static String formatStatistic(Statistic statistic, double rawValue) {
        switch (statistic) {
            case WALK_ONE_CM:
            case CLIMB_ONE_CM:
            case CROUCH_ONE_CM:
            case FALL_ONE_CM:
            case FLY_ONE_CM:
            case SPRINT_ONE_CM:
            case SWIM_ONE_CM:
            case WALK_ON_WATER_ONE_CM:
            case WALK_UNDER_WATER_ONE_CM:
            case BOAT_ONE_CM:
            case AVIATE_ONE_CM:
            case HORSE_ONE_CM:
            case MINECART_ONE_CM:
            case PIG_ONE_CM:
            case STRIDER_ONE_CM:
                double meters = rawValue / 100.0; // Convert to meters
                String formattedDistance;
                if (meters < 1000) {
                    formattedDistance = String.format("%.0f m", meters);
                } else {
                    formattedDistance = String.format("%.2f km", meters / 1000.0);
                }
                return formattedDistance;

            case DAMAGE_DEALT:
            case DAMAGE_DEALT_ABSORBED:
            case DAMAGE_DEALT_RESISTED:
            case DAMAGE_BLOCKED_BY_SHIELD:
            case DAMAGE_ABSORBED:
                double damage = rawValue / 10.0;
                return formatDouble(damage);
            case PLAY_ONE_MINUTE:
            case SNEAK_TIME:
            case TIME_SINCE_DEATH:
            case TIME_SINCE_REST:
            case TOTAL_WORLD_TIME:
                int seconds = (int) (rawValue / 20); // 20 ticks per second
                int days = seconds / 86400; // 86400 seconds in a day
                seconds %= 86400;
                int hours = seconds / 3600; // 3600 seconds in an hour
                seconds %= 3600;
                int minutes = seconds / 60; // 60 seconds in a minute
                seconds %= 60;

                StringBuilder formattedTime = new StringBuilder();
                if (days > 0) {
                    formattedTime.append(days).append("d ");
                }
                if (hours > 0) {
                    formattedTime.append(hours).append("h ");
                }
                if (minutes > 0) {
                    formattedTime.append(minutes).append("m ");
                }
                if (seconds > 0) {
                    formattedTime.append(seconds).append("s");
                }

                return formattedTime.toString().trim();
            case LEAVE_GAME:
                return String.valueOf((int) rawValue); // Assuming this represents number of games quit
            case MOB_KILLS:
            case DROP:
            case JUMP:
            case DEATHS:
            case PLAYER_KILLS:
            case RAID_TRIGGER:
            case RAID_WIN:
            case CLEAN_SHULKER_BOX:
            case TALKED_TO_VILLAGER:
            case TARGET_HIT:
            case SLEEP_IN_BED:
            case TRADED_WITH_VILLAGER:
                return formatDouble(rawValue);
            default:
                return formatDouble(rawValue);
        }
    }
    public static String formatDouble(double number) {
        if (number >= 1_000_000_000_000d) {
            return String.format("%.1fT", number / 1_000_000_000_000d);
        } else if (number >= 1_000_000_000d) {
            return String.format("%.1fB", number / 1_000_000_000d);
        } else if (number >= 1_000_000d) {
            return String.format("%.1fM", number / 1_000_000d);
        } else if (number >= 1_000d) {
            return String.format("%.1fK", number / 1_000d);
        } else {
            return String.format("%.1f", number);
        }
    }
    public static int getTotalBlocksMined(Player player) {
        Statistic statistic = Statistic.MINE_BLOCK;
        int totalMined = 0;

        for (Material material : Material.values()) {
            totalMined += player.getStatistic(statistic, material);
        }

        return totalMined;
    }
    public static int getTotalMobsKilled(Player player) {
        Statistic statistic = Statistic.KILL_ENTITY;
        int totalKills = 0;

        for (EntityType entityType : EntityType.values()) {
            if (entityType.isAlive()) { // Only consider living entities as mobs
                totalKills += player.getStatistic(statistic, entityType);
            }
        }

        return totalKills;
    }
}
