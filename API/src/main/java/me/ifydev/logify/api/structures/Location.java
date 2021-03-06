package me.ifydev.logify.api.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

/**
 * @author Innectic
 * @since 02/08/2019
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Location {
    private int x;
    private int y;
    private int z;
    private String world;
    private Optional<String> material;
}
