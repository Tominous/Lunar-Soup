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

public class CreditManagementCommands implements CommandExecutor {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Function that is run whenever the /givecredits or /takecredits command is executed.
     * @param sender: Command Sender
     * @param command: Command
     * @param label: Command Label
     * @param args: Command Arguments
     * @return boolean
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("lunarsoup.creditmanagement")) {
            sender.sendMessage(Utils.translate("&cYou do not have permission to use that command."));
            return true;
        }

        OfflinePlayer player;
        SoupPlayer soupPlayer;
        int amount;

        switch (command.getName().toLowerCase()) {
            case "givecredits":
                if (args.length != 2) {
                    sender.sendMessage(Utils.translate("&cIncorrect Usage! /givecredits <player> <amount>."));
                    return true;
                }

                player = Bukkit.getOfflinePlayer(args[0]);
                if (!plugin.playerConfig.contains(player.getUniqueId().toString())) {
                    sender.sendMessage(Utils.translate("&c" + args[0] + " has never joined before."));
                    return true;
                }

                soupPlayer = new SoupPlayer(player);

                try { Integer.parseInt(args[1]); }
                catch (NumberFormatException ignored) {
                    sender.sendMessage(Utils.translate("&cInvalid Amount!"));
                    return true;
                }

                amount = Integer.parseInt(args[1]);
                soupPlayer.setCredits(soupPlayer.getCredits() + amount);

                sender.sendMessage(Utils.translate("&eYou have given &a" + player.getName() + " &d" + amount + " &ecredits."));

                break;
            case "takecredits":
                if (args.length != 2) {
                    sender.sendMessage(Utils.translate("&cIncorrect Usage! /takecredits <player> <amount>."));
                    return true;
                }

                player = Bukkit.getOfflinePlayer(args[0]);
                if (!plugin.playerConfig.contains(player.getUniqueId().toString())) {
                    sender.sendMessage(Utils.translate("&c" + args[0] + " has never joined before."));
                    return true;
                }

                soupPlayer = new SoupPlayer(player);

                try { Integer.parseInt(args[1]); }
                catch (NumberFormatException ignored) {
                    sender.sendMessage(Utils.translate("&cInvalid Amount!"));
                    return true;
                }

                amount = Integer.parseInt(args[1]);
                if (amount > soupPlayer.getCredits()) soupPlayer.setCredits(0);
                else soupPlayer.setCredits(soupPlayer.getCredits() - amount);

                sender.sendMessage(Utils.translate("&eYou have taken &d" + amount + " &ecredits from &a" + player.getName() + "&e."));

                break;
        }

        return true;
    }
}
