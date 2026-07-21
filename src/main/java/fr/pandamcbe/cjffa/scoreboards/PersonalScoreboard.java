package fr.pandamcbe.cjffa.scoreboards;

import fr.pandamcbe.cjffa.CJFFA;
import fr.pandamcbe.commons.StatsAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mgapi.serverutilapi.scoreboards.ObjectiveSign;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PersonalScoreboard extends fr.pandamcbe.mgapi.serverutilapi.scoreboards.PersonalScoreboard {

    public PersonalScoreboard(Player player){
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "§4§lCJFFA");
        reloadData();
        objectiveSign.addReceiver(player);
    }

    public void reloadData() {
    }
    public void setLines(String ip) {
        World world = player.getWorld();
        StatsAccount a = (StatsAccount) Main.getInstance().manipulation.getAccount(CJFFA.getInstance().mainMap, player.getUniqueId(), AllNames.statsPojo(AllNames.CJFFA.getName()));

                Bukkit.getScheduler().runTask(CJFFA.getInstance(), () -> {
                    this.objectiveSign.setLine(1, "§8§m----------------");
                    this.objectiveSign.setLine(2, "§7Connectés : §f" + Bukkit.getOnlinePlayers().size());
                    this.objectiveSign.setLine(3, "§7"+world.getName()+"§7-Connectés : §f" + world.getPlayers().size());
                    this.objectiveSign.setLine(4, "§7MGCoins : §f" + a.getCoins());
                    this.objectiveSign.setLine(5, "§7MGKStreak : §f" + a.getKillstreaks());
//            this.objectiveSign.setLine(4, "§7Elos : §f" + account.getelos());
                    this.objectiveSign.setLine(6, "§7MGKills : §f" + (a.getKills() + (a.getKillstreaks() * 5)));
                    this.objectiveSign.setLine(7, "§7SpawningTimer: §f" + CJFFA.getInstance().spawnItems.getTimer() / 60);
                    this.objectiveSign.setLine(8, "§8§m--------------");
                    this.objectiveSign.setLine(9, ip);
                    this.objectiveSign.updateLines();
                });

    }
}