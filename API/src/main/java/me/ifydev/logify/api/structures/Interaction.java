package me.ifydev.logify.api.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ifydev.logify.api.log.InteractionType;

/**
 * @author Innectic
 * @since 02/10/2019
 */
@AllArgsConstructor
@Getter
public class Interaction {
    private Location location;
    private InteractionType type;
    private long when;
}
