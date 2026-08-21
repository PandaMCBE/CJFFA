package fr.pandamcbe.cjffa;

import fr.pandamcbe.cjffa.cjffamanager.GetSpawners;
import fr.pandamcbe.cjffa.cjffamanager.SpawnItems;
import fr.pandamcbe.cjffa.commands.Commands;
import fr.pandamcbe.cjffa.listeners.KillsAndDeathsListeners;
import fr.pandamcbe.cjffa.listeners.ParameterListeners;
import fr.pandamcbe.cjffa.listeners.PlayerJoinListeners;
import fr.pandamcbe.mgapi.serverutilapi.scoreboards.ScoreboardManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import runnables.AutoMessage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class CJFFA extends JavaPlugin {
    public static CJFFA INSTANCE;
    public Map<UUID, HashMap<ItemStack, Long>> itemsRemoveID = new HashMap<>();
    public GetSpawners getSpawners = new GetSpawners();
    public LuckPerms luckPerms;
    public SpawnItems spawnItems;
    public Map<UUID, Map<String, Object>> mainMap;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    private ScoreboardManager sbManager;
    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }
    public Scoreboard board;
    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }


    @Override
    public void onEnable() {
        INSTANCE = this;
        System.out.println("§2[DEBUG] CJFFA chargé !");

        this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
        this.executorMonoThread = Executors.newScheduledThreadPool(1);
        this.sbManager = new ScoreboardManager();
        org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        this.board = scoreboardManager.getMainScoreboard();

        System.out.println("Main ON");
        getCommand("report").setExecutor(new Commands());
        getCommand("rules").setExecutor(new Commands());
        getCommand("howtoplay").setExecutor(new Commands());

//        RedisPbSb.subscription();
        // Les listeners

        this.registerlisteners(
                new PlayerJoinListeners(),
                new ParameterListeners(),
                new KillsAndDeathsListeners()
        );

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getServer().setIdleTimeout(180);
        getServer().setDefaultGameMode(GameMode.ADVENTURE);
        getServer().setSpawnRadius(0);

        mainMap = new HashMap<>();

        createFile("messages");
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
        getSpawners.createAllSpawns();
        spawnItems = new SpawnItems(this, -1, 0);
        spawnItems.addAllItems();

        new AutoMessage().runTaskTimer(this, 0, 15 * 60 * 20L);
        super.onEnable();
    }
    @Override
    public void onDisable(){
        this.sbManager.onDisable();
        super.onDisable();
    }

    private void registerlisteners(Listener... listeners) {
        final PluginManager manager = Bukkit.getPluginManager();
        for (Listener listener : listeners) {
            manager.registerEvents(listener, this);
        }
    }

    public static CJFFA getInstance() {
        return INSTANCE;
    }
    public ScoreboardManager getScoreboardManager() {
        return sbManager;
    }

    private void createFile(String fileName) {
        if (getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), fileName + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();

                if (fileName.equalsIgnoreCase("messages")) {
                    FileConfiguration config = getConfig("messages");
                    config.set("messages", Arrays.asList("§6text"
                    ));
                    save(file, config);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getFile(String filename) {
        return new File(getDataFolder(), filename + ".yml");
    }

    public FileConfiguration getConfig(String filename) {
        return YamlConfiguration.loadConfiguration(getFile(filename));
    }

    public void save(File file, FileConfiguration config) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
