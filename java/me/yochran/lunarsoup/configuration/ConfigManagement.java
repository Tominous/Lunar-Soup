package me.yochran.lunarsoup.configuration;

import me.yochran.lunarsoup.LunarSoup;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManagement {

    private static final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Get a config by its name.
     * @param name: The name of the config
     * @return Config
     */
    public static Config getConfig(String name) {
        return Config.getConfigs().get(name);
    }

    /**
     * Get the plugins config.
     * @return FileConfiguration
     */
    public static FileConfiguration getPluginConfig() {
        return plugin.getConfig();
    }
}
