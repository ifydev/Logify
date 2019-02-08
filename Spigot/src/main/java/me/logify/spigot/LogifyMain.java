package me.logify.spigot;

import me.logify.api.database.ConnectionInformation;
import me.logify.spigot.util.ConfigUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

/**
 * @author Innectic
 * @since 02/07/2019
 */
public class LogifyMain extends JavaPlugin {

    @Override
    public void onEnable() {
        Optional<ConnectionInformation> connectionInformation = ConfigUtil.getConnectionInformation(this.getDataFolder(), this.getConfig());
        if (!connectionInformation.isPresent()) {
            getLogger().severe("Could not get connection information from configuration!");
            return;
        }
    }

    @Override
    public void onDisable() {

    }
}
