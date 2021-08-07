package me.yochran.lunarsoup.kits;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Kit {

    private final String ID;
    private String name;
    private double price;
    private Map<Integer, ItemStack> items;

    public Kit(String name, int price, Map<Integer, ItemStack> items) {
        this.ID = name.toUpperCase();
        this.name = name;
        this.price = price;
        this.items = items;

        KitManagement.getKits().put(getID(), this);
    }

    public String getID() { return ID; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public Map<Integer, ItemStack> getItems() { return items; }

    public void setName(String name) { this.name = name; }
    public void setPrice(int price) { this.price = price; }
    public void setItems(Map<Integer, ItemStack> items) { this.items = items; }

    public void addItem(int slot, ItemStack item) {
        getItems().put(slot, item);
    }
    public void removeItem(int slot) {
        getItems().remove(slot);
    }
}
