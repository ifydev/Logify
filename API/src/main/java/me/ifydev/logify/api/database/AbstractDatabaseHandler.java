package me.ifydev.logify.api.database;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.ifydev.logify.api.log.InteractionType;

import java.util.UUID;

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

    public abstract void logBlockInteraction(InteractionType type, UUID player, UUID event, int x, int y, int z, String world, String from, String to);
}
