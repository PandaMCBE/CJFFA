package fr.pandamcbe.cjffa;

import fr.pandamcbe.cjffa.Listeners.KillsAndDeathsListeners;
import fr.pandamcbe.cjffa.Listeners.ParameterListeners;
import fr.pandamcbe.cjffa.Listeners.PlayerJoinListeners;
import fr.pandamcbe.cjffa.commands.Commands;
import fr.pandamcbe.cjffa.scoreboards.ScoreboardManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import runnables.AutoMessage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class Main extends JavaPlugin {

    public static Main INSTANCE2;
    public LuckPerms luckPerms;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    private ScoreboardManager scoreboardManager;
    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    @Override
    public void onEnable() {
        INSTANCE2 = this;

        this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
        this.executorMonoThread = Executors.newScheduledThreadPool(1);
        this.scoreboardManager = new ScoreboardManager();

        System.out.println("Main ON");
        getCommand("report").setExecutor(new Commands());
        getCommand("spawn").setExecutor(new Commands());
        getCommand("hub").setExecutor(new Commands());
        getCommand("lobby").setExecutor(new Commands());
        getCommand("rules").setExecutor(new Commands());

        // Les listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListeners(), this);
        getServer().getPluginManager().registerEvents(new ParameterListeners(), this);
        getServer().getPluginManager().registerEvents(new KillsAndDeathsListeners(this), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getServer().setIdleTimeout(180);
        getServer().setDefaultGameMode(GameMode.ADVENTURE);


        createFile("messages");
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();

        }

        new AutoMessage().runTaskTimer(this, 0 * 20L, 15 * 60 * 20L);

        super.onEnable();
    }

    @Override
    public void onDisable(){
        this.scoreboardManager.onDisable();
        super.onDisable();
    }

    public static Main getInstance2() {
        return INSTANCE2;
    }
    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
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
