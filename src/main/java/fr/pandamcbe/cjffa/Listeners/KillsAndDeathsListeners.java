package fr.pandamcbe.cjffa.listeners;

import fr.pandamcbe.cjffa.CJFFA;
import fr.pandamcbe.cjffa.cjffamanager.GiveKitsUtils;
import fr.pandamcbe.commons.FFAKitsAccount;
import fr.pandamcbe.commons.ProfilAccount;
import fr.pandamcbe.commons.StatsAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.DirtyManager;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AccountType;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KillsAndDeathsListeners implements Listener {

    MemAccountsManipulation manipulation = Main.getInstance().manipulation;
    String tableName = Main.getInstance().serverName;
    private final DirtyManager dirtyManager = new DirtyManager();
    @EventHandler
    private void onPlayerRespawn(PlayerRespawnEvent event) {
        Player p = event.getPlayer();
        Location location = p.getLocation();
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 0, true, false));
//        Location spawn = new Location(location.getWorld(), -48.525, 51, 49.539, -1.0f, -1.0f);
        Location spawn = new Location(location.getWorld(), -108.500, 24.0, -110.500 -1.0f -1.0f);
        event.setRespawnLocation(spawn);
//        Bukkit.getScheduler().runTaskLater(Main.getInstance(), ()-> new GiveKitsUtils(p), 1L);
        new GiveKitsUtils(p);
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player Killer = victim.getKiller();
        e.getDrops().clear();
        e.setDeathMessage(null);
        if (Killer != null) {

            if (victim.getUniqueId() != Killer.getUniqueId()) {

                StatsAccount statsAccount = (StatsAccount) manipulation.getStatsAccount(CJFFA.getInstance().mainMap, Killer.getUniqueId(), AccountType.statsKey(tableName));
                FFAKitsAccount kitsAccount = (FFAKitsAccount) manipulation.getShopAccounts(CJFFA.getInstance().mainMap, Killer.getUniqueId(), AccountType.CJFFAKITS.getKeyPrefix());
                ProfilAccount profilAccount = (ProfilAccount) manipulation.getProfilAccount(CJFFA.getInstance().mainMap, Killer.getUniqueId(), AccountType.PROFIL.getKeyPrefix());

                statsAccount.setKills(statsAccount.getKills() + 1);
                statsAccount.setElos(statsAccount.getElos() + 1);
                profilAccount.setCoins(profilAccount.getCoins() + 5);

                dirtyManager.markDirty(Killer.getUniqueId(), AccountType.statsKey(AllNames.CJFFA.getName()), statsAccount);
                dirtyManager.markDirty(Killer.getUniqueId(), AccountType.CJFFAKITS.getKeyPrefix(), kitsAccount);
                dirtyManager.markDirty(Killer.getUniqueId(), AccountType.PROFIL.getKeyPrefix(), profilAccount);

                Main.getInstance().gameMechanics.applyKillStreak(statsAccount, Killer, "§8§l[§4§lCJFFA§8§l] §d§l>");

                if (kitsAccount.getArcher() == 2) {
                    Killer.getInventory().addItem(new ItemBuilder(Material.ARROW, 4).toItemStack());
                }
                Killer.setHealth(20.0);
                Killer.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE, 1).toItemStack());
                Killer.sendMessage("§7§l+ §b§l1 kill !");
                Killer.sendMessage("§7§l+ §6§l5 coins !");
                Killer.sendMessage("§7§l+ §d§l1 elo !");

                StatsAccount statsAccount2 = (StatsAccount) manipulation.getStatsAccount(CJFFA.getInstance().mainMap, victim.getUniqueId(), AccountType.statsKey(tableName));
                ProfilAccount profilAccount2 = (ProfilAccount) manipulation.getProfilAccount(CJFFA.getInstance().mainMap, Killer.getUniqueId(), AccountType.PROFIL.getKeyPrefix());

                if (statsAccount2.getKillstreaks() > 1) {
                    victim.sendMessage("§c§lVous avez perdu vos KillStreaks!");
                    statsAccount2.setKillstreaks(0);
                }
                victim.sendMessage("§c§l- §6§l2 coins");
                profilAccount2.setCoins(statsAccount2.getCoins() - 2);
                dirtyManager.markDirty(victim.getUniqueId(), AccountType.statsKey(AllNames.CJFFA.getName()), statsAccount2);
                dirtyManager.markDirty(victim.getUniqueId(), AccountType.PROFIL.getKeyPrefix(), profilAccount2);
            }
        }

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntitiesByClass(Arrow.class)) {
                entity.remove();
            }
        }

        victim.spigot().respawn();

    }
}

