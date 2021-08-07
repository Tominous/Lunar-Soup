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

public class BalanceCommand implements CommandExecutor {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Function that is ran when the /bal command is executed.
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
            sender.sendMessage(Utils.translate("&cIncorrect Usage! /bal [player]"));
            return true;
        }

        OfflinePlayer player;

        if (args.length == 0) player = (Player) sender;
        else player = Bukkit.getPlayer(args[0]);

        if (!plugin.playerConfig.contains(player.getUniqueId().toString())) {
            sender.sendMessage(Utils.translate("&c" + args[0] + " has never joined before."));
            return true;
        }

        SoupPlayer soupPlayer = new SoupPlayer(player);

        sender.sendMessage(Utils.translate("&e" + player.getName() + "'s Balance: &a" + soupPlayer.getCredits()));

        return true;
    }
}
