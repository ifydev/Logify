package me.ifydev.logify.api.database;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Innectic
 * @since 02/07/2019
 */
@RequiredArgsConstructor
public abstract class AbstractDatabaseHandler {

    @Getter protected final ConnectionInformation connectionInformation;

    public abstract void initialize();
    public abstract void reload();
    public abstract void drop();
}
