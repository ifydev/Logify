package me.ifydev.logify.api.log;

/**
 * @author Innectic
 * @since 02/07/2019
 */
public interface InteractionType {

    enum Block implements InteractionType {
        BREAK, PLACE, BURN, EXPLODE
    }

    enum Player implements InteractionType {
        JOIN, LEAVE
    }
}
