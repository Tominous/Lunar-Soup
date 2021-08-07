package me.yochran.lunarsoup.listener;

import me.yochran.lunarsoup.gui.GUI;
import me.yochran.lunarsoup.gui.guis.KitsGUI;
import me.yochran.lunarsoup.utils.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    /**
     * Function for when a player clicks on a starting item.
     * @param event: PlayerInteractEvent
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (action != Action.RIGHT_CLICK_AIR || item == null || item.getType() == XMaterial.AIR.parseMaterial() || !item.getItemMeta().hasDisplayName())
            return;

        switch (ChatColor.stripColor(item.getItemMeta().getDisplayName()).toLowerCase()) {
            case "kit selector":
                KitsGUI kitsGUI = new KitsGUI(player, 27, "&8Kit Selection");
                kitsGUI.setup();
                GUI.open(kitsGUI.getGui());
                break;
        }
    }
}
