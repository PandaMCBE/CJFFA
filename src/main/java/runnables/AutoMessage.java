package runnables;

import fr.pandamcbe.cjffa.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AutoMessage extends BukkitRunnable {

    private List<String> messages = Main.getInstance2().getConfig("messages").getStringList("messages");
    private int i = 0;

    @Override
    public void run() {
        if(messages.isEmpty()){
            cancel();
            return;
        }
        if(i >= messages.size()){
            i = 0;
        }

        Bukkit.getOnlinePlayers().forEach(player -> {player.sendMessage(ChatColor.translateAlternateColorCodes('&',messages.get(i)));});

        i++;
    }
}
