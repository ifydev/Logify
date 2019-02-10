package me.ifydev.logify.api.database;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.ifydev.logify.api.log.InteractionType;
import me.ifydev.logify.api.structures.Interaction;
import me.ifydev.logify.api.structures.TimeObject;

import java.util.List;
import java.util.Optional;
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
    public abstract void logPlayerInteraction(InteractionType type, UUID player);
    public abstract List<Interaction> getRecentInteraction(Optional<InteractionType> type, Optional<TimeObject> time, Optional<UUID> player, int x, int y, int z, String world);
}
