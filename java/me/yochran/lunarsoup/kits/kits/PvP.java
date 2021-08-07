package me.yochran.lunarsoup.kits.kits;

import me.yochran.lunarsoup.kits.Kit;
import me.yochran.lunarsoup.utils.XMaterial;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class PvP extends Kit {

    public PvP() {
        super("PvP", 0);
        registerItems();
    }

    public void registerItems() {
        ItemStack sword = XMaterial.DIAMOND_SWORD.parseItem();
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        sword.addEnchantment(Enchantment.DURABILITY, 3);

        this.addItem(0, sword);
        for (int i = 1; i < 36; i++) this.addItem(i, XMaterial.MUSHROOM_STEW.parseItem());

        this.addItem(100, XMaterial.IRON_HELMET.parseItem());
        this.addItem(101, XMaterial.IRON_CHESTPLATE.parseItem());
        this.addItem(102, XMaterial.IRON_BOOTS.parseItem());
        this.addItem(103, XMaterial.IRON_LEGGINGS.parseItem());
    }
}
