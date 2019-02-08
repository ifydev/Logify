package me.logify.api.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Innectic
 * @since 02/07/2019
 */
@AllArgsConstructor
@Getter
public enum DatabaseType {
    MYSQL("mysql"), SQLITE("sqlite");

    private String name;

    public static Optional<DatabaseType> findType(String type) {
        return Arrays.stream(values()).filter(handler -> handler.getName().equalsIgnoreCase(type)).findFirst();
    }
}
