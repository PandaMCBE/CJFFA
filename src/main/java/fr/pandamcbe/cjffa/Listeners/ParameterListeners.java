package fr.pandamcbe.cjffa.Listeners;


import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class ParameterListeners implements Listener {

    @EventHandler
    public void onTakeDamages(EntityDamageEvent event) {

        //Si l'entité est une instance de player
        if (event.getEntity() instanceof Player) {

            //Player = entity
            Player p = (Player) event.getEntity();
            Location spawn = new Location(p.getWorld(), 82.553, 65, 200.700, 1.0f, 0.0f);
            //Si le nom du monde est worldlobby
            if (p.getLocation().distanceSquared(spawn) <= 2 * 2) {
                //on annule l'action des damages sur le player
                event.setCancelled(true);

            }
        }
    }


    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            // Annule le changement de niveau de faim du joueur
            event.setCancelled(true);

            // Définit le niveau de faim du joueur à 20 (maximum)
            player.setFoodLevel(20);
        }
    }

    //on annule le drop des items
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        // Vérifiez si les dégâts sont causés par une chute
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
            // Annulez les dégâts de chute
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();

        // Vérifier si le joueur quitte le sol (en passant d'une position Y à une autre)
        if (to.getY() > player.getLocation().getY()) {
            // Le joueur a quitté le sol, vérifier les blocs adjacents (y compris les côtés du cactus)
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        Block block = player.getLocation().subtract(xOffset, yOffset, zOffset).getBlock();

                        // Vérifier si le bloc est un cactus
                        if (block.getType() == Material.CACTUS) {
                            // Appliquer l'effet de jump_boost
                            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 24, true));
                            return;  // Sortir de la boucle si un cactus est trouvé
                        }
                    }
                }
            }
        }
   }
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event){
        event.setCancelled(true);
    }
}