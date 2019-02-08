package me.ifydev.logify.api.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ifydev.logify.api.database.handlers.SQLHandler;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Innectic
 * @since 02/07/2019
 */
@AllArgsConstructor
@Getter
public enum DatabaseType {
    MYSQL("mysql", SQLHandler.class), SQLITE("sqlite", SQLHandler.class);

    private String name;
    private Class<? extends AbstractDatabaseHandler> clazz;

    public static Optional<DatabaseType> findType(String type) {
        return Arrays.stream(values()).filter(handler -> handler.getName().equalsIgnoreCase(type)).findFirst();
    }
}
