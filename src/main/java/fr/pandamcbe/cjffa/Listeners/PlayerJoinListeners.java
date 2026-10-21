package fr.pandamcbe.cjffa.listeners;

import fr.pandamcbe.cjffa.CJFFA;
import fr.pandamcbe.cjffa.cjffamanager.GiveKitsUtils;
import fr.pandamcbe.cjffa.scoreboards.PersonalScoreboard;
import fr.pandamcbe.commons.StatsAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.DirtyManager;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AccountType;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import net.luckperms.api.model.user.User;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoinListeners implements Listener {

    private final MemAccountsManipulation memAccountsManipulation = Main.getInstance().manipulation;
    private final DirtyManager dirtyManager = new DirtyManager();
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        Location location = p.getLocation();
        CJFFA.getInstance().getScoreboardManager().onLogin(new PersonalScoreboard(p), p);
        World world = p.getWorld();
        User user = CJFFA.getInstance().luckPerms.getPlayerAdapter(Player.class).getUser(p);
        if (p.getGameMode() != GameMode.CREATIVE) {
            p.setGameMode(GameMode.ADVENTURE);
        }
        p.getInventory().clear();
        event.setJoinMessage("§8§l[§4§lCJFFA§8§l] " + user.getCachedData().getMetaData().getPrefix() + " §r" + p.getName() + " §8§l[§a§l+§8§l]");
        //PvP Arena Map Location spawn = new Location(location.getWorld(),-48.525, 51, 49.539, -1.0f, -1.0f);
        Location spawn = new Location(location.getWorld(), -108.500, 24.0, -110.500, -1.0f, -1.0f);
        p.playSound(p.getLocation(), Sound.WITHER_IDLE, 1.0f, 1.0f);
        p.teleport(spawn);
        memAccountsManipulation.getShopAccounts(Main.getInstance().localCacheManager, p.getUniqueId(), AccountType.CJFFAKITS.getKeyPrefix(), AccountType.COSMEWORN.getKeyPrefix());
        memAccountsManipulation.getProfilAccount(Main.getInstance().localCacheManager, p.getUniqueId(), AccountType.PROFIL.getKeyPrefix());
        StatsAccount statsAccount = (StatsAccount) memAccountsManipulation.getStatsAccount(Main.getInstance().localCacheManager, p.getUniqueId(), AccountType.statsKey(AllNames.CJFFA.getName()));
        statsAccount.setKills(0);
        statsAccount.setKillstreaks(0);
        //on régéne le poto
        p.setHealth(20.0);

        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 0, true, false));

        new GiveKitsUtils(p);

        if (!p.hasPlayedBefore()) {
            p.sendMessage("§8§l[§4§lCJFFA§8§l] §d§l> §aMini introduction pour débutants (en vrai longue déso) :\n" + "§eSympa le ffa mais comment j'y joue ? \n" + "\n" + "§f§lDans l'ensemble un FFA tout ce\n" + "§f§lqu'il y a de plus normal, à l'exception\n" + "§f§lde plusieurs éléments en particulier.\n" + "§f§lLa map est couverte de cactus de haut en bas." + "§f§lSe placent au dessus de ces derniers des plateformes.\n" + "§f§lCes plateformes représentent plusieurs levels §6or§7|§bdiamant§7|§aémeraude§f§l.\n" + "§f§lEt là vous vous ditez que ces levels ne.\n" + "§f§lpas très originaux et ensuite à quoi ça sert bon sang ?\n" + "§f§lLes cactus vous permettez de sauter très haut.\n" + "§f§lafin d'atteindre les plateformes en hauteur.\n" + "§f§lElles font spawn des items spéciaux de différents niveaux\n" + "§f§laprès X minutes §63§7§b6§a9§f§l.\n" + "\n" + "§f§lCe n'est pas fini, pour récupérer ces items,\n" + "§f§lvous devez faire un nombre de killstreak précis.\n" + "§f§lPour chaque niveau : §62§7/§b3§7/§a5 §f§l qui se reset après.\n" + "§dBON JEU !");
        }
        Bukkit.getScheduler().runTaskLater(CJFFA.getInstance(), ()-> {
            if (world.getPlayers().size() > 1) {
                if (!Bukkit.getScheduler().isCurrentlyRunning(CJFFA.getInstance().spawnItems.getTask())) {
                    CJFFA.getInstance().spawnItems.setTimer(0);
                    CJFFA.getInstance().spawnItems.TaskspawnAll();
                    for (Player pls : world.getPlayers())
                        pls.sendTitle("§aGénérateur d'items", "§bnouveau cycle débuté");
                }
            }
        }, 10 * 20L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        Main main = Main.getInstance();
        main.gameMechanics.clearInventoryEntirely(p);
        CJFFA.getInstance().getScoreboardManager().onLogout(p);
        User user = CJFFA.getInstance().luckPerms.getPlayerAdapter(Player.class).getUser(p);
        for (PotionEffect effect : p.getActivePotionEffects())
            p.removePotionEffect(effect.getType());
        event.setQuitMessage("§8§l[§4§lCJFFA§8§l] " + user.getCachedData().getMetaData().getPrefix() + " §r" + p.getName() + " §8§l[§c§l-§8§l]");
//        dirtyManager.forceSave(Main.getInstance().localCacheManager, p.getUniqueId());
        main.manipulation.deleteAccount(p.getUniqueId(), Main.getInstance().localCacheManager);
        System.out.println(Main.getInstance().localCacheManager.get(p.getUniqueId()));

    }


}