package me.yochran.lunarsoup.utils;

import org.bukkit.ChatColor;

public class Utils {

    /**
     * Function to translate a message's color codes.
     * @param message: The message to translate.
     * @return String
     */
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
