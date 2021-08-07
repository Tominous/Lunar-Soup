package me.yochran.lunarsoup.listener;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.configuration.Config;
import me.yochran.lunarsoup.configuration.ConfigManagement;
import me.yochran.lunarsoup.kits.Kit;
import me.yochran.lunarsoup.kits.KitManagement;
import me.yochran.lunarsoup.player.PlayerManagement;
import me.yochran.lunarsoup.player.SoupPlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PlayerJoinListener implements Listener {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Function for when a player joins the server.
     * @param event: PlayerJoinEvent
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (!plugin.playerConfig.contains(player.getUniqueId().toString())) {
            SoupPlayer soupPlayer = new SoupPlayer(player);
            soupPlayer.giveKit(KitManagement.getKit("PvP"));

            List<String> kits = new ArrayList<>();
            soupPlayer.getKits().forEach(kit -> kits.add(kit.getID()));

            plugin.playerConfig.set(player.getUniqueId().toString() + ".Name", player.getName());
            plugin.playerConfig.set(player.getUniqueId().toString() + ".Credits", 0.0);
            plugin.playerConfig.set(player.getUniqueId().toString() + ".PreviousKit", "NONE");
            plugin.playerConfig.set(player.getUniqueId().toString() + ".Kits", kits);
            Config.save(ConfigManagement.getConfig("players.yml"));
        }

        if (!world.getName().equals(plugin.pluginConfig.getString("World")))
            return;

        PlayerManagement.giveItems(player);
    }

    /**
     * Function for when a player switches worlds.
     * @param event: PlayerChangedWorldEvent
     */
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (!world.getName().equals(plugin.pluginConfig.getString("World")))
            return;

        PlayerManagement.giveItems(player);
    }
}
