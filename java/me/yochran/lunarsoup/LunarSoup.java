package me.yochran.lunarsoup;

import me.yochran.lunarsoup.commands.*;
import me.yochran.lunarsoup.configuration.Config;
import me.yochran.lunarsoup.configuration.ConfigManagement;
import me.yochran.lunarsoup.kits.KitManagement;
import me.yochran.lunarsoup.listener.InventoryClickListener;
import me.yochran.lunarsoup.listener.PlayerDeathListener;
import me.yochran.lunarsoup.listener.PlayerInteractListener;
import me.yochran.lunarsoup.listener.PlayerJoinListener;
import me.yochran.lunarsoup.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class LunarSoup extends JavaPlugin {

    private static LunarSoup instance;
    public static LunarSoup getInstance() { return instance; }

    public FileConfiguration pluginConfig;
    public FileConfiguration playerConfig;
    public FileConfiguration kitConfig;

    public final PluginManager pluginManager = getServer().getPluginManager();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage(Utils.translate("LunarSoup v1.0 by Yochran is loading..."));

        instance = this;
        saveDefaultConfig();
        setupConfiguration();
        registerListeners();
        registerCommands();

        KitManagement.registerKits();

        Bukkit.getConsoleSender().sendMessage(Utils.translate("LunarSoup v1.0 by Yochran has successfully loaded."));
    }

    /**
     * Function to set up all file configuration.
     */
    private void setupConfiguration() {
        Arrays.asList(
                new Config("players.yml"),
                new Config("kits.yml")
        ).forEach(config -> {
            Config.setup(config);
            Config.save(config);
            Config.reload(config);
        });

        pluginConfig = getConfig();
        playerConfig = ConfigManagement.getConfig("players.yml").getConfig();
        kitConfig = ConfigManagement.getConfig("kits.yml").getConfig();
    }

    /**
     * Function to register listeners.
     */
    private void registerListeners() {
        Arrays.asList(
                new InventoryClickListener(),
                new PlayerJoinListener(),
                new PlayerInteractListener(),
                new PlayerDeathListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    /**
     * Function to register commands.
     */
    private void registerCommands() {
        getCommand("Kit").setExecutor(new KitCommand());
        getCommand("Stats").setExecutor(new StatsCommand());
        getCommand("Balance").setExecutor(new BalanceCommand());
        getCommand("GiveCredits").setExecutor(new CreditManagementCommands());
        getCommand("TakeCredits").setExecutor(new CreditManagementCommands());
        getCommand("Bounty").setExecutor(new BountyCommand());
        getCommand("Bounties").setExecutor(new BountiesCommand());
    }
}
