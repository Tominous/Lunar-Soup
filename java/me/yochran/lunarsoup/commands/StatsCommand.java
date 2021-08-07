package me.yochran.lunarsoup.commands;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.player.SoupPlayer;
import me.yochran.lunarsoup.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class StatsCommand implements CommandExecutor {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Function that's ran when the /stats command is executed.
     * @param sender: Command Sender
     * @param command: Command
     * @param label: Command Label
     * @param args: Command Arguments
     * @return boolean
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate("&cYou must be a player to use that command."));
            return true;
        }

        if (args.length > 1) {
            sender.sendMessage(Utils.translate("&cIncorrect Usage! /stats [player]"));
            return true;
        }

        OfflinePlayer player;

        if (args.length == 0) player = (Player) sender;
        else player = Bukkit.getOfflinePlayer(args[0]);

        if (!plugin.playerConfig.contains(player.getUniqueId().toString())) {
            sender.sendMessage(Utils.translate("&c" + args[0] + " has never joined before."));
            return true;
        }

        SoupPlayer soupPlayer = new SoupPlayer(player);

        String[] lines = new String[] {
                "&7&m--------------------------------------------",
                "&a" + player.getName(),
                "&eKills: &a" + soupPlayer.getKills(),
                "&eDeaths: &c" + soupPlayer.getDeaths(),
                "&eKD: &a" + soupPlayer.getKDR(),
                "&eKillstreak: &a" + soupPlayer.getStreak(),
                "&eHighest Killstreak: &a" + soupPlayer.getHighestStreak(),
                "&eEvents Won: &a0",
                "&eCredits: &a" + soupPlayer.getCredits(),
                "&eBounty: &a",
                "&eMost Used Kit: &aPvP",
                "&7&m--------------------------------------------"
        };

        StringBuilder message = new StringBuilder();
        Arrays.asList(lines).forEach(line -> message.append("\n").append(line));

        sender.sendMessage(Utils.translate(message.toString()));

        return true;
    }
}
