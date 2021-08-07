package me.yochran.lunarsoup.listener;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Function that is run when a player moves.
     * @param event: PlayerMoveEvent
     */
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!plugin.spawnTimer.containsKey(event.getPlayer().getUniqueId()) || !plugin.playerCoords.containsKey(event.getPlayer().getUniqueId()))
            return;

        Player player = event.getPlayer();
        Double[] playerLocation = plugin.playerCoords.get(player.getUniqueId());

        if (player.getLocation().getX() != playerLocation[0] || player.getLocation().getZ() != playerLocation[1]) {
            plugin.spawnTimer.remove(player.getUniqueId());
            plugin.playerCoords.remove(player.getUniqueId());

            player.sendMessage(Utils.translate("&cSpawn teleport cancelled due to movement."));
        }
    }
}
