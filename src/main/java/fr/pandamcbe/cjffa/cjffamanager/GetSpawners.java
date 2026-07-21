package fr.pandamcbe.cjffa.cjffamanager;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class GetSpawners {
    String world = "worldffa";

    public Spawners createGoldSpawn (){
        Spawners gold = new Spawners(1, 60 * 3, 60 * 5,3);
        gold.spawns.add(new Location(Bukkit.getWorld(world), -109, 59, -29));
        gold.spawns.add(new Location(Bukkit.getWorld(world), -109, 59, -29));
        gold.spawns.add(new Location(Bukkit.getWorld(world), -27, 59, -29));
        gold.spawns.add(new Location(Bukkit.getWorld(world), -27, 59, -111));
        gold.spawns.add(new Location(Bukkit.getWorld(world), -27, 59, -194));
        gold.spawns.add(new Location(Bukkit.getWorld(world), -109, 59, -194));
        gold.spawns.add(new Location(Bukkit.getWorld(world), -192, 59, -194));
        gold.spawns.add(new Location(Bukkit.getWorld(world), -192, 59, -111));
        gold.spawns.add(new Location(Bukkit.getWorld(world), -192, 59, -29));
        return gold;
    }
    public Spawners createDiamondSpawn (){
        Spawners diamond = new Spawners(2, 60 * 6, 60 * 5, 6);
        diamond.spawns.add(new Location(Bukkit.getWorld(world), -109, 91, -69));
        diamond.spawns.add(new Location(Bukkit.getWorld(world), -67, 91, -111));
        diamond.spawns.add(new Location(Bukkit.getWorld(world), -109, 91, -153));
        diamond.spawns.add(new Location(Bukkit.getWorld(world), -151, 91, -111));
        diamond.spawns.add(new Location(Bukkit.getWorld(world), -89, 91, -91));
        diamond.spawns.add(new Location(Bukkit.getWorld(world), -88, 91, -132));
        diamond.spawns.add(new Location(Bukkit.getWorld(world), -130, 91, -132));
        diamond.spawns.add(new Location(Bukkit.getWorld(world), -130, 91, -190));
        return diamond;
    }

    public Spawners createEmeraldSpawn(){
        Spawners emerald = new Spawners(3, 60 * 9, 60 * 5, 9);
        emerald.spawns.add(new Location(Bukkit.getWorld(world), -121, 129, -111));
        emerald.spawns.add(new Location(Bukkit.getWorld(world), -109, 129, -99));
        emerald.spawns.add(new Location(Bukkit.getWorld(world), -97, 129, -111));
        emerald.spawns.add(new Location(Bukkit.getWorld(world), -109, 129, -123));
        return emerald;
    }

    public void createAllSpawns(){
        createDiamondSpawn();
        createEmeraldSpawn();
        createGoldSpawn();

    }

}