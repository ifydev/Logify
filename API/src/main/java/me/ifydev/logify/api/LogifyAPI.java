package me.ifydev.logify.api;

import lombok.Getter;
import me.ifydev.logify.api.database.AbstractDatabaseHandler;
import me.ifydev.logify.api.database.ConnectionInformation;
import me.ifydev.logify.api.log.ModuleConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author Innectic
 * @since 02/07/2019
 */
public class LogifyAPI {
    @Getter private Logger logger;
    @Getter private AbstractDatabaseHandler database;

    @Getter private ModuleConfiguration blockModuleConfig;
    @Getter private ModuleConfiguration playerModuleConfig;

    private static LogifyAPI api;

    public LogifyAPI(ConnectionInformation information, ModuleConfiguration blockModule, ModuleConfiguration playerModule, Logger logger) {
        api = this;

        this.blockModuleConfig = blockModule;
        this.playerModuleConfig = playerModule;
        this.logger = logger;

        try {
            database = information.getType().getClazz().getConstructor(ConnectionInformation.class).newInstance(information);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            database = null;
            return;
        }
        database.initialize();
        database.reload();
    }

    public boolean initialized() {
        return database != null;
    }

    public static Optional<LogifyAPI> get() {
        return Optional.of(api);
    }
}
