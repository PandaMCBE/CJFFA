package fr.pandamcbe.cjffa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("report")) {
                player.sendMessage("§b§lMG-Land §3§l> §6§lVoulez-vous §e§lreport §d§l§oquelqu'un §6§l?");

                TextComponent component = new TextComponent(TextComponent.fromLegacyText("§a§l§nCliquez ici"));
                // Add a click event to the component.
                component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://docs.google.com/forms/d/e/1FAIpQLSfFTdxrUsv0d9dzn69yCkE6MxgdgMegjUoU21dyY93KmHpQAA/viewform?usp=pp_url"));

                // Send it!
                player.spigot().sendMessage(component);
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("howtoplay")) {
                player.sendMessage("§8§l[§4§lCJFFA§8§l] §d§l> §a§lMini introduction pour débutans (en vrai longue déso) :\n" + "§e§lSympa le ffa mais comment j'y joue ? \n"+ "\n" + "§f§lDans l'ensemble un FFA tout ce\n" + "§f§lqu'il y a de plus normal, à l'exception\n" + "§f§lde plusieurs éléments en particulier.\n"+"§f§lLa map est couverte de cactus de haut en bas.\n" + "§f§lSe place au dessus de ces derniers des plateformes.\n"+"§f§lCes plateformes représentent plusieurs levels §6§lor§7§l|§b§ldiamant§7§l|§a§lémeraude§f§l.\n"+ "§f§lEt là vous vous ditez que ces levels ne.\n"+"§f§lpas très originaux et ensuite à quoi ça sert bon sang ?\n"+"§f§lLes cactus vous permet de sauter très haut.\n"+"§f§lAfin d'atteindre les plateformes en hauteur\n"+"§f§l(ou fuir comme un lâche).\n" + "§f§lElles font spawn des items spéciaux de différents niveaux\n"+"§f§laprès X minutes §6§l3§7§l,§b§l6§7§l,§a§l9§f§l.\n" + "\n" +"§f§lCe n'est pas fini pour récupérez ces items,\n" + "§f§lvous devez faire un nombre de killstreak précis.\n"+ "§f§lPour chaque niveaux : §6§l2§7§l/§b§l3§7§l/§a§l5 §f§l qui se reset après.\n" + "§d§lBON JEU !");

                return true;
            }

        }
        return false;
    }
}