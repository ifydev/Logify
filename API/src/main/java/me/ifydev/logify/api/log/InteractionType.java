package me.ifydev.logify.api.log;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Innectic
 * @since 02/07/2019
 */
public interface InteractionType {

    enum Block implements InteractionType {
        BREAK, PLACE, BURN, EXPLODE;

        public static Optional<Block> getType(String type) {
            return Arrays.stream(values()).filter(t -> t.name().equalsIgnoreCase(type)).findFirst();
        }
    }

    enum Player implements InteractionType {
        JOIN, LEAVE
    }
}
