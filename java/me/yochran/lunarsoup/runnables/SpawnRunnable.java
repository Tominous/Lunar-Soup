package me.yochran.lunarsoup.runnables;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.player.PlayerManagement;
import me.yochran.lunarsoup.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class SpawnRunnable extends BukkitRunnable {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Function that runs repeatedly for all players who are in the spawn timer.
     */
    @Override
    public void run() {
        for (Map.Entry<UUID, Integer> mapData : plugin.spawnTimer.entrySet()) {
            Player player = Bukkit.getPlayer(mapData.getKey());

            if (player != null) {

                if (mapData.getValue() <= 0) {
                    player.sendMessage(Utils.translate("&aTeleporting you to spawn."));
                    plugin.spawnTimer.remove(mapData.getKey());
                    PlayerManagement.sendToSpawn(player);
                } else {
                    player.sendMessage(Utils.translate("&3Spawn Teleport: &b" + mapData.getValue() + "&3..."));
                    plugin.spawnTimer.put(mapData.getKey(), mapData.getValue() - 1);
                }

            } else plugin.spawnTimer.remove(mapData.getKey());
        }
    }
}
