package me.logify.spigot.util;

import me.ifydev.logify.api.database.ConnectionInformation;
import me.ifydev.logify.api.database.DatabaseType;
import me.ifydev.logify.api.log.ModuleConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Innectic
 * @since 02/07/2019
 */
public class ConfigUtil {

    private static Optional<Integer> isInt(String maybe) {
        try {
            return Optional.of(Integer.valueOf(maybe));
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }

    public static Optional<ConnectionInformation> getConnectionInformation(FileConfiguration configuration) {
        Optional<DatabaseType> handler = DatabaseType.findType(configuration.getString("storage", "sqlite"));
        if (!handler.isPresent()) return Optional.empty();

        if (handler.get().equals(DatabaseType.MYSQL)) {
            String host = configuration.getString("connection.host");
            String database = configuration.getString("connection.database");
            String port = configuration.getString("connection.port");
            String username = configuration.getString("connection.username");
            String password = configuration.getString("connection.password");

            if (host == null || database == null || port == null || username == null || password == null) return Optional.empty();
            Optional<Integer> portNumber = isInt(port);
            return portNumber.map(integer -> new ConnectionInformation(handler.get(), host, database, integer, username, password, new HashMap<>()));
        }
        return Optional.empty();
    }

    public static ModuleConfiguration getModuleConfiguraton(String module, FileConfiguration config) {
        ConfigurationSection section = config.getConfigurationSection("modules." + module);

        boolean enabled = section.getBoolean("enabled", false);
        Map<String, Boolean> subModules = new HashMap<>();

        section.getKeys(false).stream().filter(key -> !key.equalsIgnoreCase("enabled")).forEach(key -> {
            boolean value = section.getBoolean(key, false);
            subModules.put(key, value);
        });
        return new ModuleConfiguration(enabled, subModules);
    }
}
