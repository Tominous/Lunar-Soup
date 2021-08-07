package me.yochran.lunarsoup.listener;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.player.SoupPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageListener implements Listener {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * Function that is run when a player is damaged.
     * @param event: EntityDamageByEntityEvent
     */
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
            return;

        Player entityVictim = (Player) event.getEntity();
        Player entityAttacker = (Player) event.getDamager();

        SoupPlayer victim = new SoupPlayer(entityVictim);
        SoupPlayer attacker = new SoupPlayer(entityAttacker);

        victim.setTag(15);
        attacker.setTag(15);
    }
}
