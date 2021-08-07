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
     * @return int
     */
    public int getCredits() {
        return plugin.playerConfig.getInt(this.getPlayer().getUniqueId().toString() + ".Credits");
    }

    /**
     * Set a players amount of credits.
     * @param credits: The specified amount to set to.
     */
    public void setCredits(int credits) {
        plugin.playerConfig.set(this.getPlayer().getUniqueId().toString() + ".Credits", credits);
        Config.save(ConfigManagement.getConfig("players.yml"));
    }

    /**
     * Get all the player's kits.
     * @return HashSet of kits
     */
    public List<String> getKits() {
        return new ArrayList<>(plugin.playerConfig.getStringList(this.getPlayer().getUniqueId().toString() + ".Kits"));
    }

    /**
     * Check if a player has a specific kit.
     * @return boolean
     * @param kit: The specified kit to check.
     */
    public boolean hasKit(Kit kit) {
        return getKits().contains(kit.getID());
    }

    /**
     * Give a player a kit
     * @param kit: The specified kit to give
     */
    public void giveKit(Kit kit) {
        List<String> kits = new ArrayList<>(getKits());

        if (!kits.contains(kit.getID()))
            kits.add(kit.getID());

        plugin.playerConfig.set(this.getPlayer().getUniqueId().toString() + ".Kits", kits);
        Config.save(ConfigManagement.getConfig("players.yml"));
    }

    /**
     * Get the amount of kills a player has.
     * @return int
     */
    public int getKills() {
        return plugin.playerConfig.getInt(this.getPlayer().getUniqueId().toString() + ".Kills");
    }

    /**
     * Get the amount of deaths a player has.
     * @return int
     */
    public int getDeaths() {
        return plugin.playerConfig.getInt(this.getPlayer().getUniqueId().toString() + ".Deaths");
    }

    /**
     * Get the amount of deaths a player has.
     * @return int
     */
    public int getStreak() {
        return plugin.playerConfig.getInt(this.getPlayer().getUniqueId().toString() + ".Streak");
    }

    /**
     * Get the amount of deaths a player has.
     * @return int
     */
    public int getHighestStreak() {
        return plugin.playerConfig.getInt(this.getPlayer().getUniqueId().toString() + ".HighestStreak");
    }

    /**
     * Check if a players current streak is higher than their previous highest streak.
     * @return boolean
     */
    public boolean isHighestStreak() {
        return getStreak() > getHighestStreak();
    }

    /**
     * Get a players KDR.
     * @return double
     */
    public double getKDR() {
        int kills = getKills();
        int deaths;

        if (kills == 0) deaths = 1;
        else deaths = getDeaths();

        return (double) kills / (double) deaths;
    }

    /**
     * Add a kill to the player.
     */
    public void addKill() {
        plugin.playerConfig.set(this.getPlayer().getUniqueId().toString() + ".Kills", getKills() + 1);
        Config.save(ConfigManagement.getConfig("players.yml"));
    }

    /**
     * Add a death to the player.
     */
    public void addDeath() {
        plugin.playerConfig.set(this.getPlayer().getUniqueId().toString() + ".Deaths", getDeaths() + 1);
        Config.save(ConfigManagement.getConfig("players.yml"));
    }

    /**
     * End a player's streak.
     */
    public void endStreak() {
        plugin.playerConfig.set(this.getPlayer().getUniqueId().toString() + ".Streak", 0);
        Config.save(ConfigManagement.getConfig("players.yml"));
    }

    /**
     * Add a kill to a players current kill streak.
     */
    public void addKillToStreak() {
        plugin.playerConfig.set(this.getPlayer().getUniqueId().toString() + ".Streak", getStreak() + 1);

        if (isHighestStreak())
            setHighestStreak();

        Config.save(ConfigManagement.getConfig("players.yml"));
    }

    /**
     * Set a players highest streak to their current streak.
     */
    public void setHighestStreak() {
        plugin.playerConfig.set(this.getPlayer().getUniqueId().toString() + ".Streak", getStreak());
        Config.save(ConfigManagement.getConfig("players.yml"));
    }

    /**
     * Checks if a player is bountied.
     * @return boolean
     */
    public boolean isBountied() {
        return plugin.playerConfig.contains("Bounty") && plugin.playerConfig.contains("Bounty." + this.getPlayer().getUniqueId().toString());
    }

    /**
     * Gets a player's bounty.
     * @return int
     */
    public int getBounty() {
        return plugin.playerConfig.getInt("Bounty." + this.getPlayer().getUniqueId().toString() + ".Amount");
    }

    /**
     * Set a player's bounty.
     * @param amount: The specified amount to set.
     */
    public void setBounty(int amount) {
        plugin.playerConfig.set("Bounty." + this.getPlayer().getUniqueId().toString() + ".Amount", amount);
        Config.save(ConfigManagement.getConfig("players.yml"));
    }

    /**
     * Increase a player's bounty.
     * @param amount: The specified amount to increase by.
     */
    public void increaseBounty(int amount) {
        setBounty(getBounty() + amount);
    }

    /**
     * Claim a player's bounty.
     * @param player: The specified player whose bounty is to be claimed.
     */
    public void claimBounty(SoupPlayer player) {
        setCredits(getCredits() + player.getBounty());
        plugin.playerConfig.set("Bounty." + player.getPlayer().getUniqueId().toString(), null);
        Config.save(ConfigManagement.getConfig("players.yml"));
    }

    /**
     * Check if a player is tagged.
     * @return boolean
     */
    public boolean isTagged() {
        return plugin.combatTag.containsKey(this.getPlayer().getUniqueId());
    }

    /**
     * Get a player's combat tag.
     * @return int
     */
    public int getTag() {
        return plugin.combatTag.get(this.getPlayer().getUniqueId());
    }

    /**
     * Set a player's combat tag.
     * @param tag: The specified tag to set.
     */
    public void setTag(int tag) {
        plugin.combatTag.put(this.getPlayer().getUniqueId(), tag);
    }
}
