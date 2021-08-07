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

public class BountyCommand implements CommandExecutor {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Function that is run when the /bounty command is executed.
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

        if (args.length != 2) {
            sender.sendMessage(Utils.translate("&cIncorrect Usage! /bounty <player> <amount>"));
            return true;
        }

        Player player = (Player) sender;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (!plugin.playerConfig.contains(target.getUniqueId().toString())) {
            sender.sendMessage(Utils.translate("&c" + args[0] + " is not online."));
            return true;
        }

        try { Integer.parseInt(args[1]); }
        catch (NumberFormatException ignored) {
            sender.sendMessage(Utils.translate("&cInvalid Amount!"));
            return true;
        }

        SoupPlayer soupPlayer = new SoupPlayer(player);
        int amount = Integer.parseInt(args[1]);

        if (amount > soupPlayer.getCredits()) {
            sender.sendMessage(Utils.translate("&cYou do not have enough credits for that bounty."));
            return true;
        }

        SoupPlayer soupTarget = new SoupPlayer(target);

        soupPlayer.setCredits(soupPlayer.getCredits() - amount);

        if (!soupTarget.isBountied()) {
            soupTarget.setBounty(amount);
            Utils.broadcastMessage("&a" + player.getName() + "&e has placed a &a" + amount + " &ecredit bounty on &a" + target.getName() + "&e.");
        } else {
            soupTarget.increaseBounty(amount);
            Utils.broadcastMessage("&a" + player.getName() + "&e has upped the bounty on &a" + target.getName() + "&e to &a" + soupTarget.getBounty() + " (+" + amount + ") &ecredits.");
        }

        return true;
    }
}
