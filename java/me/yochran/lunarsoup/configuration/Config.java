package me.yochran.lunarsoup.configuration;

import me.yochran.lunarsoup.LunarSoup;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private final LunarSoup plugin = LunarSoup.getInstance();
    private static final LunarSoup splugin = LunarSoup.getInstance();

    private final String name;
    public File file;
    public FileConfiguration config;

    private static final Map<String, Config> configs;

    static {
        configs = new HashMap<>();
    }

    public Config(String name) {
        this.name = name;
        getConfigs().put(name, this);
    }

    /*
     * Getters
     */
    public String getName() { return name; }
    public File getFile() { return file; }
    public FileConfiguration getConfig() { return config; }

    /*
     * Setters
     */
    public void setFile(File file) { this.file = file; }
    public void setConfig(FileConfiguration config) { this.config = config; }

    public static Map<String, Config> getConfigs() { return configs; }

    /**
     * Function to set up a config.
     * @param config: The specified config to set up.
     */
    public static void setup(Config config) {
        if (!splugin.getDataFolder().exists())
            splugin.getDataFolder().mkdir();

        config.setFile(new File(splugin.getDataFolder(), config.getName()));

        if (config.getFile().exists()) {
            config.setConfig(YamlConfiguration.loadConfiguration(config.getFile()));
            return;
        }

        try {
            config.getFile().createNewFile();
            config.setConfig(YamlConfiguration.loadConfiguration(config.getFile()));
        } catch (IOException ignored) {Bukkit.getConsoleSender().sendMessage("[LunarSoup] kits.yml file could not load."); }
    }

    /**
     * Function to save a config.
     * @param config: The specified config to save.
     */
    public static void save(Config config) {
        try { config.getConfig().save(config.getFile()); }
        catch (IOException ignored) { Bukkit.getConsoleSender().sendMessage("[LunarSoup] " + config.getName() +  " file could not load."); }
    }

    /**
     * Function to reload a config.
     * @param config: The specified config to reload.
     */
    public static void reload(Config config) {
        config.setConfig(YamlConfiguration.loadConfiguration(config.getFile()));
    }
}
