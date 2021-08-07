package me.yochran.lunarsoup.kits.kits;

import me.yochran.lunarsoup.kits.Kit;
import me.yochran.lunarsoup.utils.ItemBuilder;
import me.yochran.lunarsoup.utils.XMaterial;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Dwarf extends Kit {

    public Dwarf() {
        super("Dwarf", 750);
        registerItems();
        registerEffects();
    }

    public void registerItems() {
        ItemBuilder axe = new ItemBuilder(XMaterial.GOLDEN_AXE.parseItem(), 1, "&9Dwarf's Axe", ItemBuilder.formatLore(new String[] {
                "&6Crouch to charge up a blast",
                "&6that knocks players away!"
        }));

        axe.getItem().addEnchantment(Enchantment.DAMAGE_ALL, 4);
        axe.getItem().addUnsafeEnchantment(Enchantment.DURABILITY, 25);

        this.addItem(0, axe.getItemStack());
        for (int i = 1; i < 36; i++) this.addItem(i, XMaterial.MUSHROOM_STEW.parseItem());

        ItemStack helmet = XMaterial.GOLDEN_HELMET.parseItem();
        ItemStack chestplate = XMaterial.GOLDEN_CHESTPLATE.parseItem();
        ItemStack leggings = XMaterial.GOLDEN_LEGGINGS.parseItem();
        ItemStack boots = XMaterial.GOLDEN_BOOTS.parseItem();

        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        chestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leggings.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 3);

        this.addItem(100, helmet);
        this.addItem(101, chestplate);
        this.addItem(102, leggings);
        this.addItem(103, boots);
    }

    public void registerEffects() {
        this.addEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1));
    }
}
