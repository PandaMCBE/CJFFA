package fr.pandamcbe.cjffa.Listeners;

import fr.pandamcbe.cjffa.GiveKitsUtils;
import fr.pandamcbe.cjffa.Main;
import fr.pandamcbe.commons.FFAKitsAccount;
import fr.pandamcbe.mgapi.data.management.redis.RedisAccess;
import fr.pandamcbe.mgapi.mysql.Managers.FFAKits.KitsManager;
import fr.pandamcbe.mgapi.utils.Items.ItemBuilder;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.sql.SQLException;

public class KillsAndDeathsListeners implements Listener {

    private Main main;

    public KillsAndDeathsListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player p = event.getPlayer();
        GiveKitsUtils.giveKitsUtils(p);
        Location location = p.getLocation();

//        Location spawn = new Location(location.getWorld(), -48.525, 51, 49.539, -1.0f, -1.0f);
        Location spawn = new Location(location.getWorld(), 82.553, 67, 200.700, 1.0f, 0.0f);
//        p.sendMessage("§7§lVous êtes mort(e), §c§l-4 elos !");
        event.setRespawnLocation(spawn);

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

                    Player victim = e.getEntity();
                    Player Killer = victim.getKiller();

                    Bukkit.getScheduler().runTaskAsynchronously(fr.pandamcbe.mgapi.Main.INSTANCE, ()-> {
                    if (Killer != null) {

//                        setKillStreak(getKillStreak() + 1);
//
//                        if((KillStreak % 5) == 0) {
//                            Killer.sendMessage("tu es gay" + KillStreak );
//                        }

                    Killer.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE, 1).toItemStack());

                            RedisAccess redisAccess = RedisAccess.INSTANCE;
                            RedissonClient redissonClient = redisAccess.getRedissonClient();
                            RBucket<FFAKitsAccount> accountRBucket = redissonClient.getBucket("FFAKitsAccount" + victim.getUniqueId().toString());
                            FFAKitsAccount a = accountRBucket.get();
                            RMap<String, Integer> map = redissonClient.getMap("ProfilAccount:" + Killer.getUniqueId().toString());
                            map.put("Coins", map.get("Coins") + 5);
                            map.put("Kills", map.get("Kills") + 1);

                    Killer.sendMessage("§7§l+ §b§l1 kill !");
                    Killer.sendMessage("§7§l+ §6§l5 coins !");
//                    Killer.sendMessage("§7§lVous avez kill §6" + victim.getName() + " §7§l! §a§l+1 elo !");
                    if (KitsManager.ARCHER.getPlayerKit(Killer.getUniqueId()) == 1 && a.isArcher()) {
                        Killer.getInventory().addItem(new ItemBuilder(Material.ARROW, 4).toItemStack());
                        }
                    }

                        RedisAccess redisAccess = RedisAccess.INSTANCE;
                        RedissonClient redissonClient = redisAccess.getRedissonClient();
                        RMap<String, Integer> map = redissonClient.getMap("ProfilAccount:" + Killer.getUniqueId().toString());
                        map.put("Coins", map.get("Coins") - 1);

                    });

        e.getDrops().clear();
        e.setDeathMessage(null);
        victim.spigot().respawn();
    }



//    @EventHandler
//    public void onPvp(EntityDamageByEntityEvent event) {
//
//        if (!(event.getEntity() instanceof Player)) return;
//
//        Player victim = (Player) event.getEntity();
//        if (!(event.getDamager() instanceof Player)) return;
//
//        Player damager = (Player) event.getDamager();
//
//        if (victim.getHealth() == 0) {
//
//
//            if (damager instanceof Arrow) {
//                Arrow aw = (Arrow) damager;
//                if ((arrow.getShooter() instanceof Player)) {
//                    damager = (Player) arrow.getShooter();
//                }
//            }
//            PlayerInfo playerInfo = PlayerInfo.getInfosPlayer(damager);
//            playerInfo.addElos(6);
//            playerInfo.addCoins(30);
//            playerInfo.addKills(1);
//            damager.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE, 1).toItemStack());
//            damager.sendMessage("§7§l+ §b§l1 kill !");
//            damager.sendMessage("§7§l+ §6§l30 coins !");
//            damager.sendMessage("§7§lVous avez kill §6" + victim.getName() + " §7§l! §a§l+6 elos !");
//            if (KitsManager.ARCHER.getPlayerKit(damager.getUniqueId()) == 1) {
//                damager.getInventory().addItem(new ItemBuilder(Material.ARROW, 4).toItemStack());
//            }
//        }
//    }
}

