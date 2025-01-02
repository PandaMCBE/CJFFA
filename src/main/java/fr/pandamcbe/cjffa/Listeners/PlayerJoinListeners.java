package fr.pandamcbe.cjffa.Listeners;

import fr.pandamcbe.cjffa.GiveKitsUtils;
import fr.pandamcbe.cjffa.Main;

import net.luckperms.api.model.user.User;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;



public class PlayerJoinListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        Location location = p.getLocation();
        Main.getInstance2().getScoreboardManager().onLogin(p);
        User user = Main.getInstance2().luckPerms.getPlayerAdapter(Player.class).getUser(p);
        //updateScoreboard(player);

        //on met le joueur en aventure même si il est sous un autre gamemode
        if (p.getGameMode() != GameMode.CREATIVE) {
            p.setGameMode(GameMode.ADVENTURE);
        }

        //on clear le joueur
        p.getInventory().clear();
        //on lui envoie un message
        event.setJoinMessage("§c§lCJFFA " + user.getCachedData().getMetaData().getPrefix() + " §r" + p.getName() + " §8§l[§a§l+§8§l]");
        //on identifie la location du spawn
        //PvP Arena Map Location spawn = new Location(location.getWorld(),-48.525, 51, 49.539, -1.0f, -1.0f);
        Location spawn = new Location(location.getWorld(),  82.462, 67, 200.700, 0.0f, -1.0f);
        //petit playsound quand on join. *gling gling gling gling
        p.playSound(p.getLocation(), Sound.WITHER_IDLE, 1.0f, 1.0f);
        //et on le téléporte au spawn qu'on a identifié juste au dessus
        p.teleport(spawn);

        //on régéne le poto
        p.setHealth(20.0);



        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1, true));

        GiveKitsUtils.giveKitsUtils(p);

    }

//    private void updateScoreboard(Player player) {
//    }

    //Le message quand le joueur quitte
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
//        p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        for(PotionEffect effect : p.getActivePotionEffects())
        {
            p.removePotionEffect(effect.getType());
        }
        User user = Main.getInstance2().luckPerms.getPlayerAdapter(Player.class).getUser(p);
        Main.getInstance2().getScoreboardManager().onLogout(p);
        event.setQuitMessage("§4§lCJFFA " + user.getCachedData().getMetaData().getPrefix() + " §r" + p.getName() + " §8§l[§c§l-§8§l]");
    }

}