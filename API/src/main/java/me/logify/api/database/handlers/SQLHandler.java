package me.logify.api.database.handlers;

import me.logify.api.LogifyAPI;
import me.logify.api.database.AbstractDatabaseHandler;
import me.logify.api.database.ConnectionError;
import me.logify.api.database.ConnectionInformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

/**
 * @author Innectic
 * @since 02/07/2019
 */
public class SQLHandler extends AbstractDatabaseHandler {

    private boolean isUsingSQLite = false;
    private String baseConnectionURL;

    public SQLHandler(ConnectionInformation connectionInformation) {
        super(connectionInformation);

        String type;
        String databaseURL;

        if (connectionInformation.getMeta().containsKey("sqlite")) {
            type = "sqlite";
            Map sqliteData = (Map) connectionInformation.getMeta().get("meta");
            databaseURL = (String) sqliteData.get("file");
            isUsingSQLite = true;
        } else {
            type = "mysql";
            databaseURL = "//" + connectionInformation.getUrl() + connectionInformation.getPort();
        }
        baseConnectionURL = "jdbc:" + type + ":" + databaseURL;
    }

    private Optional<Connection> getConnection() {
        try {
            if (isUsingSQLite) return Optional.ofNullable(DriverManager.getConnection(baseConnectionURL));
            String connectionURL = baseConnectionURL + "/" + connectionInformation.getDatabase();
            return Optional.ofNullable(DriverManager.getConnection(connectionURL, connectionInformation.getUsername(), connectionInformation.getPassword()));
        } catch (SQLException e) {
            LogifyAPI.get().ifPresent(api -> api.getLogger().severe(ConnectionError.REJECTED.getDisplay()));
            LogifyAPI.get().ifPresent(api -> api.getLogger().severe(e.getMessage()));
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void initialize() {
        Optional<Connection> connection = getConnection();
        if (!connection.isPresent()) {
            LogifyAPI.get().ifPresent(api -> api.getLogger().severe("Could not connect to database during initialization"));
            return;
        }
        String database = connectionInformation.getDatabase();

        try {
            // Ensure that the database exists for MySQL configurations, otherwise set the database to nothing to make
            // sqlite play nice.
            if (!isUsingSQLite) {
                PreparedStatement statement = connection.get().prepareStatement("CREATE DATABASE IF NOT EXISTS ?");
                statement.setString(1, database);

                statement.execute();
                statement.close();
                database += ".";
            } else database = "";

            // Create the schema
            // TODO

            connection.get().close();
        } catch (SQLException e) {
            LogifyAPI.get().ifPresent(api -> api.getLogger().severe(ConnectionError.DATABASE_EXCEPTION.getDisplay()));
            e.printStackTrace();
        }
    }

    @Override
    public void reload() {

    }

    @Override
    public void drop() {

    }
}
