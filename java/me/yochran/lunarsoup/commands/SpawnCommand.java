package me.yochran.lunarsoup.commands;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.player.SoupPlayer;
import me.yochran.lunarsoup.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Function that is run when the /spawn command is executed.
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
        SoupPlayer soupPlayer = new SoupPlayer(player);

        if (soupPlayer.isTagged()) {
            sender.sendMessage(Utils.translate("&cYou cannot use that command while in combat!"));
            return true;
        }

        sender.sendMessage(Utils.translate("&7Started spawn timer..."));

        plugin.spawnTimer.put(player.getUniqueId(), 5);
        plugin.playerCoords.put(player.getUniqueId(), new Double[] { player.getLocation().getX(), player.getLocation().getZ() });

        return true;
    }
}
