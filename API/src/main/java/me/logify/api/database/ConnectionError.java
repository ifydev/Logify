package me.logify.api.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Innectic
 * @since 02/07/2019
 */
@AllArgsConstructor
public enum ConnectionError {
    REJECTED("Connection to database rejected"), DATABASE_EXCEPTION("Exception in a database handler");

    @Getter private String display;
}
