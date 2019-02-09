package me.ifydev.logify.api.log.modules;

import lombok.RequiredArgsConstructor;
import me.ifydev.logify.api.database.AbstractDatabaseHandler;
import me.ifydev.logify.api.log.InteractionType;
import me.ifydev.logify.api.structures.Location;

import java.util.List;
import java.util.UUID;

/**
 * @author Innectic
 * @since 02/07/2019
 */
@RequiredArgsConstructor
public class BlockLogger {

    private final AbstractDatabaseHandler handler;

    public void blockBreak(UUID player, int x, int y, int z, String world, String from, String to) {
        handler.logBlockInteraction(InteractionType.Block.BREAK, player, null, x, y, z, world, from, to);
    }

    public void blockPlace(UUID player, int x, int y, int z, String world, String from, String to) {
        handler.logBlockInteraction(InteractionType.Block.PLACE, player, null, x, y, z, world, from, to);
    }

    public void blockBurn(UUID player, int x, int y, int z, String world, String from, String to) {
        handler.logBlockInteraction(InteractionType.Block.BURN, player, null, x, y, z, world, from, to);
    }

    public void regionExplode(UUID player, UUID event, String world, List<Location> locations, String to) {
        locations.forEach(location -> handler.logBlockInteraction(InteractionType.Block.EXPLODE, player, event,
                location.getX(), location.getY(), location.getZ(), world, location.getMaterial(), to));
    }
}
