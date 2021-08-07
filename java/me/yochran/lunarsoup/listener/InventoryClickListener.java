package me.yochran.lunarsoup.listener;

import me.yochran.lunarsoup.gui.Button;
import me.yochran.lunarsoup.gui.GUI;
import me.yochran.lunarsoup.utils.XMaterial;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    /**
     * Function for when a player clicks a button in a GUI.
     * @param event: InventoryClickEvent
     */
    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == XMaterial.AIR.parseMaterial())
            return;

        if (!GUI.getOpenGUIs().containsKey(event.getWhoClicked().getUniqueId()))
            return;

        GUI gui = GUI.getOpenGUIs().get(event.getWhoClicked().getUniqueId());

        if (gui == null || event.getClickedInventory() == null || !gui.getInventory().equals(event.getClickedInventory()))
            return;

        event.setCancelled(true);

        Button button = gui.getButton(event.getSlot());

        if (button == null || button.getAction() == null)
            return;

        Runnable action = button.getAction();
        action.run();
    }
}
