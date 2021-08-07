package me.yochran.lunarsoup.player;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.configuration.Config;
import me.yochran.lunarsoup.configuration.ConfigManagement;
import me.yochran.lunarsoup.kits.Kit;
import me.yochran.lunarsoup.kits.KitManagement;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SoupPlayer {

    private final LunarSoup plugin = LunarSoup.getInstance();

    private final OfflinePlayer player;

    public SoupPlayer(OfflinePlayer player) {
        this.player = player;
    }

    /**
     * Get the OfflinePlayer from the SoupPlayer.
     * @return OfflinePlayer
     */
    public OfflinePlayer getPlayer() { return player; }

    /**
     * Check if player has a previous kit.
     * @return boolean
     */
    public boolean previousKitAvailable() {
        return !plugin.playerConfig.getString(this.getPlayer().getUniqueId().toString() + ".PreviousKit").equals("NONE");
    }

    /**
     * Get a player's previous kit.
     * @return Kit
     */
    public Kit getPreviousKit() {
        String kitName = plugin.playerConfig.getString(this.player.getUniqueId().toString() + ".PreviousKit");
        return KitManagement.getKit(kitName);
    }

    /**
     * Get the player's amount of credits.
     * @return double
     */
    public double getCredits() {
        return plugin.playerConfig.getDouble(this.getPlayer().getUniqueId().toString() + ".Credits");
    }

    /**
     * Get all the player's kits.
     * @return HashSet of kits
     */
    public Set<Kit> getKits() {
        List<String> kits = new ArrayList<>(plugin.playerConfig.getStringList(this.getPlayer().getUniqueId().toString() + ".Kits"));
        Set<Kit> kitsFormatted = new HashSet<>();

        kits.forEach(entry -> kitsFormatted.add(KitManagement.getKit(entry)));
        return kitsFormatted;
    }

    /**
     * Check if a player has a specific kit.
     * @return boolean
     * @param kit: The specified kit to check.
     */
    public boolean hasKit(Kit kit) {
        return getKits().contains(kit);
    }

    /**
     * Give a player a kit
     * @param kit: The specified kit to give
     */
    public void giveKit(Kit kit) {
        Set<Kit> kits = new HashSet<>(getKits());
        kits.add(kit);

        List<String> kitsFormatted = new ArrayList<>();
        kits.forEach(entry -> kitsFormatted.add(kit.getID()));

        plugin.playerConfig.set(this.getPlayer().getUniqueId().toString() + ".Kits", kitsFormatted);
        Config.save(ConfigManagement.getConfig("players.yml"));
    }
}
