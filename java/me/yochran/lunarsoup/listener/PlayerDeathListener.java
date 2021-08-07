package me.yochran.lunarsoup.listener;

import me.yochran.lunarsoup.LunarSoup;
import me.yochran.lunarsoup.player.SoupPlayer;
import me.yochran.lunarsoup.utils.Utils;
import me.yochran.lunarsoup.utils.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PlayerDeathListener implements Listener {

    private final LunarSoup plugin = LunarSoup.getInstance();

    /**
     * List of valid causes for the death function to run.
     */
    private final EntityDamageEvent.DamageCause[] causes = new EntityDamageEvent.DamageCause[] {
            EntityDamageEvent.DamageCause.ENTITY_ATTACK,
            EntityDamageEvent.DamageCause.PROJECTILE,
            EntityDamageEvent.DamageCause.FALL,
            EntityDamageEvent.DamageCause.MAGIC,
            EntityDamageEvent.DamageCause.LAVA,
            EntityDamageEvent.DamageCause.FIRE,
            EntityDamageEvent.DamageCause.FIRE_TICK,
            EntityDamageEvent.DamageCause.VOID
    };

    /**
     * Function that runs when a player gets killed.
     * @param event: EntityDeathEvent
     */
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        EntityDamageEvent.DamageCause cause = event.getEntity().getLastDamageCause().getCause();
        if (!Arrays.asList(causes).contains(cause))
            return;

        Player entityVictim = (Player) event.getEntity();

        event.getDrops().clear();
        for (ItemStack item : entityVictim.getInventory().getContents()) {
            if (item != null && item.getType() == XMaterial.MUSHROOM_STEW.parseMaterial())
                event.getDrops().add(item);
        }

        if (event.getEntity().getKiller() == null || event.getEntity().getKiller() == entityVictim)
            return;

        Player entityAttacker = entityVictim.getKiller();

        SoupPlayer attacker = new SoupPlayer(entityAttacker);
        SoupPlayer victim = new SoupPlayer(entityVictim);

        victim.setTag(0);

        victim.addDeath();
        attacker.addKill();
        attacker.addKillToStreak();

        if (attacker.getStreak() % 5 == 0)
            Utils.broadcastMessage("&a" + entityAttacker.getName() + "&e is on a &a" + attacker.getStreak() + "&e streak.");

        int range = (20 - 10) + 1;
        int random = (int) Math.round((Math.random() * range) + 10);

        attacker.setCredits(attacker.getCredits() + random);

        entityAttacker.sendMessage(Utils.translate("&9You have killed &a" + entityVictim.getName() + "&9 for &7" + random + "&9 credits."));
        entityVictim.sendMessage(Utils.translate("&cYou have been killed by &7" + entityAttacker.getName() + "&c."));

        if (victim.getStreak() > 0) {
            Utils.broadcastMessage("&a" + entityVictim.getName() + "&e has been killed on a &a" + victim.getStreak() + "&e streak.");
            victim.endStreak();
        }

        if (victim.isBountied()) {
            Utils.broadcastMessage("&a" + entityAttacker.getName() + "&e has claimed the &a" + victim.getBounty() + "&e credit bounty for &a" + entityVictim.getName() + "&e.");
            attacker.claimBounty(victim);
        }
    }
}
