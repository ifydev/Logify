package me.ifydev.logify.api.database.handlers;

import me.ifydev.logify.api.LogifyAPI;
import me.ifydev.logify.api.database.AbstractDatabaseHandler;
import me.ifydev.logify.api.database.ConnectionError;
import me.ifydev.logify.api.database.ConnectionInformation;
import me.ifydev.logify.api.log.InteractionType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Innectic
 * @since 02/07/2019
 */
public class SQLHandler extends AbstractDatabaseHandler {

    private String baseConnectionURL;

    public SQLHandler(ConnectionInformation connectionInformation) {
        super(connectionInformation);
        baseConnectionURL = "jdbc:mysql://" + connectionInformation.getUrl() + ":" + connectionInformation.getPort();
    }

    private Optional<Connection> getConnection() {
        try {
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
        String database = connectionInformation.getDatabase();

        try {
            Connection connection;
            connection = DriverManager.getConnection(baseConnectionURL, connectionInformation.getUsername(), connectionInformation.getPassword());
            if (connection == null) {
                System.out.println("Could not connect to database during initialization.");
                return;
            }

            // Ensure that the database exists for MySQL configurations, otherwise set the database to nothing to make
            // sqlite play nice.
            PreparedStatement statement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS " + database);

            statement.execute();
            statement.close();

            // Create the schema
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + database + ".blockChanges (type VARCHAR(100) NOT NULL, player VARCHAR(100), `when` BIGINT NOT NULL, event VARCHAR(100), x INT NOT NULL, y INT NOT NULL, z INT NOT NULL, world VARCHAR(100) NOT NULL, `to` VARCHAR(100) NOT NULL, `from` VARCHAR(100) NOT NULL)");
            statement.execute();
            statement.close();

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + database + ".players (type VARCHAR(100) NOT NULL, player VARCHAR(100), `when` BIGINT NOT NULL)");
            statement.execute();
            statement.close();

            connection.close();
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

    @Override
    public void logBlockInteraction(InteractionType type, UUID player, UUID event, int x, int y, int z, String world, String from, String to) {
        Optional<Connection> connection = getConnection();
        if (!connection.isPresent()) return;

        try {
            PreparedStatement statement = connection.get().prepareStatement("INSERT INTO blockChanges (type,player,event,x,y,z,world,`from`,`to`,`when`) VALUES (?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, type.toString());
            statement.setString(2, player == null ? "" : player.toString());
            statement.setString(3, event == null ? "" : event.toString());
            statement.setInt(4, x);
            statement.setInt(5, y);
            statement.setInt(6, z);
            statement.setString(7, world);
            statement.setString(8, from);
            statement.setString(9, to);
            statement.setLong(10, (System.currentTimeMillis() / 1000));

            statement.execute();
            statement.close();
            connection.get().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logPlayerInteraction(InteractionType type, UUID player) {
        Optional<Connection> connection = getConnection();
        if (!connection.isPresent()) return;

        try {
            PreparedStatement statement = connection.get().prepareStatement("INSERT INTO players (type,player,`when`) VALUES (?,?,?)");

            statement.setString(1, type.toString());
            statement.setString(2, player.toString());
            statement.setLong(3, (System.currentTimeMillis() / 1000));

            statement.execute();
            statement.close();
            connection.get().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
