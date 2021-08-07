package me.yochran.lunarsoup.runnables;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.player.SoupPlayer;
import me.yochran.lunarsoup.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class CombatTagRunnable extends BukkitRunnable {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Function that runs repeatedly for all players who are tagged.
     */
    @Override
    public void run() {
        for (Map.Entry<UUID, Integer> mapData : plugin.combatTag.entrySet()) {
            Player player = Bukkit.getPlayer(mapData.getKey());

            if (player != null) {
                SoupPlayer soupPlayer = new SoupPlayer(player);

                if (soupPlayer.getTag() <= 0) {
                    soupPlayer.setTag(0);
                    plugin.combatTag.remove(player.getUniqueId());

                    player.sendMessage(Utils.translate("&aYou are no longer in combat."));
                } else soupPlayer.setTag(soupPlayer.getTag() - 1);

            } else plugin.combatTag.remove(mapData.getKey());
        }
    }
}
