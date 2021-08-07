package me.yochran.lunarsoup.commands;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.player.SoupPlayer;
import me.yochran.lunarsoup.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class BountiesCommand implements CommandExecutor {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * The function that is run when the /bounties command is executed.
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

        Map<SoupPlayer, Integer> allBounties = new HashMap<>();

        if (plugin.playerConfig.contains("Bounty")) {
            plugin.playerConfig.getConfigurationSection("Bounty").getKeys(false).forEach(bounty -> {
                SoupPlayer soupPlayer = new SoupPlayer(Bukkit.getOfflinePlayer(UUID.fromString(bounty)));
                allBounties.put(soupPlayer, soupPlayer.getBounty());
            });
        }

        Map<SoupPlayer, Integer> sortedBounties = sortBountyList(allBounties);

        List<Object[]> top10 = new ArrayList<>();
        for (Map.Entry<SoupPlayer, Integer> entries : sortedBounties.entrySet()) {
            Object[] data = new Object[] {
                    entries.getKey().getPlayer().getName(),
                    entries.getValue()
            };

            top10.add(data);
        }

        for (int i = 0; i < 10; i++) top10.add(new Object[] { "//", 0 });

        String[] lines = new String[] {
                "&3*** Bounty Top ***",
                "&31 &a" + top10.get(0)[0] + "&7 - &a" + top10.get(0)[1],
                "&32 &a" + top10.get(1)[0] + "&7 - &a" + top10.get(1)[1],
                "&33 &a" + top10.get(2)[0] + "&7 - &a" + top10.get(2)[1],
                "&34 &a" + top10.get(3)[0] + "&7 - &a" + top10.get(3)[1],
                "&35 &a" + top10.get(4)[0] + "&7 - &a" + top10.get(4)[1],
                "&36 &a" + top10.get(5)[0] + "&7 - &a" + top10.get(5)[1],
                "&37 &a" + top10.get(6)[0] + "&7 - &a" + top10.get(6)[1],
                "&38 &a" + top10.get(7)[0] + "&7 - &a" + top10.get(7)[1],
                "&39 &a" + top10.get(8)[0] + "&7 - &a" + top10.get(8)[1],
                "&310 &a" + top10.get(9)[0] + "&7 - &a" + top10.get(9)[1],

        };

        StringBuilder message = new StringBuilder();
        Arrays.asList(lines).forEach(line -> message.append("\n").append(line));

        sender.sendMessage(Utils.translate(message.toString()));

        return true;
    }

    /**
     * Function to sort the list of bounties
     * @param map: The specified map to sort.
     * @return Map of SoupPlayer and Integer
     */
    public Map<SoupPlayer, Integer> sortBountyList(Map<SoupPlayer, Integer> map) {
        List<Map.Entry<SoupPlayer, Integer> > list = new LinkedList<Map.Entry<SoupPlayer, Integer>>(map.entrySet());

        list.sort(new Comparator<Map.Entry<SoupPlayer, Integer>>() {
            public int compare(Map.Entry<SoupPlayer, Integer> value1, Map.Entry<SoupPlayer, Integer> value2) {
                return value1.getValue().compareTo(value2.getValue());
            }
        });

        Map<SoupPlayer, Integer> sortedBounties = new LinkedHashMap<>();
        for (Map.Entry<SoupPlayer, Integer> entry : list) sortedBounties.put(entry.getKey(), entry.getValue());

        return sortedBounties;
    }
}
