package me.yochran.lunarsoup.gui.guis;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.gui.Button;
import me.yochran.lunarsoup.gui.CustomGUI;
import me.yochran.lunarsoup.gui.GUI;
import me.yochran.lunarsoup.kits.Kit;
import me.yochran.lunarsoup.player.SoupPlayer;
import me.yochran.lunarsoup.utils.ItemBuilder;
import me.yochran.lunarsoup.utils.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class KitBuyConfirmationGUI extends CustomGUI {

    private final LunarSoup plugin = LunarSoup.getInstance();

    public KitBuyConfirmationGUI(Player player, int size, String title) {
        super(player, size, title);
    }

    /**
     * Function to set up the GUI.
     * @param kit: The specified kit to buy.
     */
    public void setup(Kit kit) {
        SoupPlayer soupPlayer = new SoupPlayer(getGui().getPlayer());

        getGui().setFiller(9);

        ItemBuilder confirm = new ItemBuilder(XMaterial.LIME_WOOL.parseItem(), 1, "&aConfirm", new ArrayList<>());
        ItemBuilder cancel = new ItemBuilder(XMaterial.RED_WOOL.parseItem(), 1, "&cCancel", new ArrayList<>());

        Button confirmButton = new Button(confirm.getItem(), () -> {
            GUI.close(getGui());
            soupPlayer.giveKit(kit);

            new BukkitRunnable() {
                @Override
                public void run() {
                    KitsGUI kitsGUI = new KitsGUI(player, 27, "&8Kit Selection");
                    kitsGUI.setup();
                    GUI.open(kitsGUI.getGui());
                }
            }.runTaskLater(plugin, 1);
        }, confirm.getName(), confirm.getLore());
        Button cancelButton = new Button(cancel.getItem(), () -> {
            GUI.close(getGui());

            new BukkitRunnable() {
                @Override
                public void run() {
                    KitsGUI kitsGUI = new KitsGUI(player, 27, "&8Kit Selection");
                    kitsGUI.setup();
                    GUI.open(kitsGUI.getGui());
                }
            }.runTaskLater(plugin, 1);
        }, cancel.getName(), cancel.getLore());

        getGui().setButton(3, confirmButton);
        getGui().setButton(5, cancelButton);
    }
}
