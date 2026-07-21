package fr.pandamcbe.cjffa.cjffamanager;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Spawners {

    private final int niveau;
    private final int spawnTime;
    private final int despawnTime;
    private final int killstreak;
    List<Location> spawns;
    public Spawners (int niveau, int spawnTime, int despawnTime, int killstreak){
        this.niveau = niveau;
        this.spawnTime = spawnTime;
        this.despawnTime = despawnTime;
        this.killstreak = killstreak;
        this.spawns = new ArrayList<>();
    }

    public int getNiveau() {
        return niveau;
    }

    public int getDespawnTime() {
        return despawnTime;
    }

    public int getKillstreak() {
        return killstreak;
    }

    public int getSpawnTime() {
        return spawnTime;
    }

    public List<Location> getSpawns() {
        return spawns;
    }
}
