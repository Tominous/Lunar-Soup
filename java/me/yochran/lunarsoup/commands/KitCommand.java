package me.yochran.lunarsoup.commands;

import me.yochran.lunarsoup.gui.GUI;
import me.yochran.lunarsoup.gui.guis.KitsGUI;
import me.yochran.lunarsoup.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

    /**
     * The function that's run when the /kit command is executed.
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

        Player player = (Player) sender;

        KitsGUI kitsGUI = new KitsGUI(player, 27, "&8Kit Selection");
        kitsGUI.setup();
        GUI.open(kitsGUI.getGui());

        return true;
    }
}
