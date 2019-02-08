package me.ifydev.logify.api.log;

import lombok.RequiredArgsConstructor;
import me.ifydev.logify.api.database.AbstractDatabaseHandler;

import java.util.UUID;

/**
 * @author Innectic
 * @since 02/07/2019
 */
@RequiredArgsConstructor
public class BlockLogger {

    private final AbstractDatabaseHandler handler;

    public void blockBreak(UUID player, int x, int y, int z, String world, String from, String to) {
        handler.logBlockInteraction(InteractionType.Block.BREAK, player, x, y, z, world, from, to);
    }

    public void blockPlace(UUID player, int x, int y, int z, String world, String from, String to) {
        handler.logBlockInteraction(InteractionType.Block.PLACE, player, x, y, z, world, from, to);
    }

    public void blockBurn(UUID player, int x, int y, int z, String world, String from, String to) {
        handler.logBlockInteraction(InteractionType.Block.BURN, player, x, y, z, world, from, to);
    }
}
