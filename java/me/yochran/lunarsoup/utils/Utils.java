package me.yochran.lunarsoup.utils;

import me.yochran.lunarsoup.LunarSoup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Utils {

    private static final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Function to translate a message's color codes.
     * @param message: The message to translate.
     * @return String
     */
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Broadcast a message to all the players on the server.
     * @param message: The specified message to broadcast.
     */
    public static void broadcastMessage(String message) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> player.sendMessage(Utils.translate(message)));
    }
}
