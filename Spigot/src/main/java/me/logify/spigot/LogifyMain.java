package me.logify.spigot;

import me.ifydev.logify.api.LogifyAPI;
import me.ifydev.logify.api.database.ConnectionInformation;
import me.logify.spigot.commands.TestCommand;
import me.logify.spigot.events.BlockEvents;
import me.logify.spigot.events.PlayerEvents;
import me.logify.spigot.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Optional;
import java.util.logging.Level;

/**
 * @author Innectic
 * @since 02/07/2019
 */
public class LogifyMain extends JavaPlugin {

    @Override
    public void onEnable() {
        createConfig();

        Optional<ConnectionInformation> connectionInformation = ConfigUtil.getConnectionInformation(getConfig());
        if (!connectionInformation.isPresent()) {
            getLogger().severe("Could not get connection information from configuration!");
            return;
        }
        long start = System.currentTimeMillis();
        LogifyAPI api = new LogifyAPI(connectionInformation.get(),
                ConfigUtil.getModuleConfiguraton("block", getConfig()),
                ConfigUtil.getModuleConfiguraton("player", getConfig()), getLogger());
        if (!api.initialized()) {
            getLogger().severe("Could not initialize database connection");
            return;
        }
        long timeTaken = System.currentTimeMillis() - start;
        getLogger().info("Logify API initialized in " + ((double) timeTaken / 1000) + " seconds (" + timeTaken + "ms)!");

        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {

    }

    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                boolean created = getDataFolder().mkdirs();
                if (!created) getLogger().log(Level.SEVERE, "Could not create config!");
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockEvents(), this);
        pm.registerEvents(new PlayerEvents(), this);
    }

    private void registerCommands() {
        getCommand("test").setExecutor(new TestCommand());
    }

    public static LogifyMain getInstance() {
        return LogifyMain.getPlugin(LogifyMain.class);
    }
}
