package me.yochran.lunarsoup.commands;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CombatTagCommand implements CommandExecutor {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Function that is run when the /ct command is executed.
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

        if (!plugin.combatTag.containsKey(player.getUniqueId())) {
            player.sendMessage(Utils.translate("&aYou are not in combat."));
            return true;
        }

        int tag = plugin.combatTag.get(player.getUniqueId());

        sender.sendMessage(Utils.translate("&eCombat Tag: &a" + tag));

        return true;
    }
}
