package me.ifydev.logify.api.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Innectic
 * @since 02/08/2019
 */
@AllArgsConstructor
@Getter
@Setter
public class Location {
    private int x;
    private int y;
    private int z;
    private String material;
}