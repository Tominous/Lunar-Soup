package me.yochran.lunarsoup.kits;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kit {

    private final String ID;
    private String name;
    private int price;
    private final Map<Integer, ItemStack> items;
    private final List<PotionEffect> effects;

    public Map<Integer, ItemStack> getItems() { return items; }
    public List<PotionEffect> getEffects() { return effects; }

    public Kit(String name, int price) {
        this.ID = name.toUpperCase();
        this.name = name;
        this.price = price;
        this.items = new HashMap<>();
        this.effects = new ArrayList<>();

        KitManagement.getKits().put(getID(), this);
    }

    public String getID() { return ID; }
    public String getName() { return name; }
    public int getPrice() { return price; }

    public void setName(String name) { this.name = name; }
    public void setPrice(int price) { this.price = price; }

    public void addItem(int slot, ItemStack item) { getItems().put(slot, item); }
    public void addEffect(PotionEffect effect) { getEffects().add(effect); }
}
