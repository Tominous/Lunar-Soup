package me.yochran.lunarsoup.player;

import me.yochran.lunarsoup.utils.ItemBuilder;
import me.yochran.lunarsoup.utils.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class PlayerManagement {

    /**
     * Give the starting items to a player.
     * @param player: The specified player to give the items to.
     */
    public static void giveItems(Player player) {
        SoupPlayer soupPlayer = new SoupPlayer(player);

        ItemBuilder kitSelector = new ItemBuilder(XMaterial.ENCHANTED_BOOK.parseItem(), 1, "&aKit Selector", new ArrayList<>());
        ItemBuilder previousKit;
        ItemBuilder perks = new ItemBuilder(XMaterial.CHEST.parseItem(), 1, "&aPerks", new ArrayList<>());

        ItemStack head = XMaterial.PLAYER_HEAD.parseItem();
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        skullMeta.setOwner(player.getName());
        head.setItemMeta(skullMeta);
        ItemBuilder stats = new ItemBuilder(head, 1, "&aYour Stats", new ArrayList<>());

        ItemBuilder events = new ItemBuilder(XMaterial.EMERALD.parseItem(), 1, "&aEvents", new ArrayList<>());

        player.getInventory().setItem(0, kitSelector.getItemStack());

        if (soupPlayer.previousKitAvailable()) {
            previousKit = new ItemBuilder(XMaterial.CLOCK.parseItem(), 1, "&aSelect Previous Kit", new ArrayList<>());
            player.getInventory().setItem(1, previousKit.getItemStack());
        }

        player.getInventory().addItem(perks.getItemStack());

        player.getInventory().setItem(4, stats.getItemStack());
        player.getInventory().setItem(8, events.getItemStack());
    }
}
