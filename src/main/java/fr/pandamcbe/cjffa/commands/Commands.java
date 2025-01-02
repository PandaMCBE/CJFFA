package fr.pandamcbe.cjffa.commands;

import fr.pandamcbe.cjffa.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;


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
            if (cmd.getName().equalsIgnoreCase("spawn") || cmd.getName().equalsIgnoreCase("lobby") || cmd.getName().equalsIgnoreCase("hub")) {
                if (args.length >= 1) {
                }
                ByteArrayOutputStream c = new ByteArrayOutputStream();
                DataOutputStream out2 = new DataOutputStream(c);

                try {
                    out2.writeUTF("Connect");
                    out2.writeUTF("lobby");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.sendPluginMessage(Main.getInstance2(), "BungeeCord", c.toByteArray());
                player.sendMessage("§7Vous avez été téléporté au lobby..");
                return true;
            }

            }
        return false;
    }
}