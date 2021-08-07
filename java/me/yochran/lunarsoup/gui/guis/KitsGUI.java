package me.yochran.lunarsoup.gui.guis;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.gui.Button;
import me.yochran.lunarsoup.gui.CustomGUI;
import me.yochran.lunarsoup.gui.GUI;
import me.yochran.lunarsoup.kits.Kit;
import me.yochran.lunarsoup.kits.KitManagement;
import me.yochran.lunarsoup.player.SoupPlayer;
import me.yochran.lunarsoup.utils.ItemBuilder;
import me.yochran.lunarsoup.utils.Utils;
import me.yochran.lunarsoup.utils.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class KitsGUI extends CustomGUI {

    private final LunarSoup plugin = LunarSoup.getInstance();

    public KitsGUI(Player player, int size, String title) {
        super(player, size, title);
    }

    /**
     * Function to set up the GUI.
     */
    public void setup() {
        SoupPlayer soupPlayer = new SoupPlayer(getGui().getPlayer());

        ItemBuilder pvp = new ItemBuilder(XMaterial.DIAMOND_SWORD.parseItem(), 1, "PvP", ItemBuilder.formatLore(new String[] {
                "&8&m-⬴------------------⤀-",
                "&7&oBasic PvP class.",
                "&8&m-⬴------------------⤀-"
        }));
        ItemBuilder dwarf = new ItemBuilder(XMaterial.GOLDEN_AXE.parseItem(), 1, "Dwarf", ItemBuilder.formatLore(new String[] {
                "&8&m-⬴---------------------------⤀-",
                "&7&oCrouch to charge up a blast",
                "&7&othat knocks players away!",
                "&8&m-⬴---------------------------⤀-",
        }));

        Button pvpButton = new Button(pvp.getItem(), () -> giveKit(KitManagement.getKit("PvP")), pvp.getName(), pvp.getLore());
        Button dwarfButton = new Button(dwarf.getItem(), () -> giveKit(KitManagement.getKit("Dwarf")), dwarf.getName(), dwarf.getLore());

        getGui().setButton(0, pvpButton);
        getGui().setButton(1, dwarfButton);

        for (Map.Entry<Integer, Button> buttons : getButtons().entrySet()) {
            Kit kit = KitManagement.getKit(buttons.getValue().getName());

            if (!soupPlayer.hasKit(kit)) {
                buttons.getValue().setName("&c" + kit.getName());

                buttons.getValue().getLore().add(Utils.translate("&ePrice: &a" + kit.getPrice() + "&e."));
                buttons.getValue().getLore().add(Utils.translate("&aClick here to purchase."));
            } else buttons.getValue().setName("&a" + kit.getName());

            getGui().setButton(buttons.getKey(), buttons.getValue());
        }
    }

    /**
     * Function to give the kit to the player, or purchase the kit (if possible).
     * @param kit: The specified kit to check/give.
     */
    public void giveKit(Kit kit) {
        Player player = getGui().getPlayer();
        SoupPlayer soupPlayer = new SoupPlayer(player);

        if (!soupPlayer.hasKit(kit)) {
            buyKit(kit);
            return;
        }

        GUI.close(getGui());
        player.getInventory().clear();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<Integer, ItemStack> items : kit.getItems().entrySet()) {
                    switch (items.getKey()) {
                        case 100:
                            player.getInventory().setHelmet(items.getValue());
                            break;
                        case 101:
                            player.getInventory().setChestplate(items.getValue());
                            break;
                        case 102:
                            player.getInventory().setLeggings(items.getValue());
                            break;
                        case 103:
                            player.getInventory().setBoots(items.getValue());
                            break;

                        default: player.getInventory().setItem(items.getKey(), items.getValue());
                    }
                }

                kit.getEffects().forEach(player::addPotionEffect);
            }
        }.runTaskLater(plugin, 1);

        player.sendMessage(Utils.translate("&eYou have received the &d" + kit.getName() + " &ekit."));
    }

    /**
     * Function to try to buy a kit.
     * @param kit: The specified kit to buy.
     */
    public void buyKit(Kit kit) {
        Player player = getGui().getPlayer();
        SoupPlayer soupPlayer = new SoupPlayer(player);

        if (soupPlayer.getCredits() < kit.getPrice()) {
            player.sendMessage(Utils.translate("&cNot enough credits!"));
            return;
        }

        GUI.close(getGui());

        new BukkitRunnable() {
            @Override
            public void run() {
                KitBuyConfirmationGUI kitBuyConfirmationGUI = new KitBuyConfirmationGUI(player, 9, "&8Purchase for " + kit.getPrice() + " credits?");
                kitBuyConfirmationGUI.setup(kit);
                GUI.open(kitBuyConfirmationGUI.getGui());
            }
        }.runTaskLater(plugin, 1);
    }
}
