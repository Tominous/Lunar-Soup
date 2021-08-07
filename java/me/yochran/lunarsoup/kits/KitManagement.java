package me.yochran.lunarsoup.kits;

import me.yochran.lunarsoup.kits.kits.Dwarf;
import me.yochran.lunarsoup.kits.kits.PvP;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class KitManagement {

    private static final Map<String, Kit> kits;

    static {
        kits = new HashMap<>();
    }

    /**
     * Register all the kits.
     */
    public static void registerKits() {
        new PvP();
        new Dwarf();
    }

    /**
     * Get all the kits
     * @return String and Kit HashMap
     */
    public static Map<String, Kit> getKits() { return kits; }

    /**
     * Get a kit by its name.
     * @param name: The name of the kit.
     * @return Kit
     */
    public static Kit getKit(String name) { return getKits().get(name.toUpperCase()); }
}
