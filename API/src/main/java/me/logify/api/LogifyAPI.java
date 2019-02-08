package me.logify.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.logify.api.database.AbstractDatabaseHandler;
import me.logify.api.database.ConnectionInformation;
import me.logify.api.database.DatabaseType;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author Innectic
 * @since 02/07/2019
 */
@AllArgsConstructor
public class LogifyAPI {
    @Getter private Logger logger;
    @Getter private AbstractDatabaseHandler database;

    private static LogifyAPI api;

    public LogifyAPI(ConnectionInformation information) {
        api = this;

        DatabaseType type = DatabaseType.MYSQL;
    }

    public static Optional<LogifyAPI> get() {
        return Optional.of(api);
    }
}
