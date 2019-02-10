package me.ifydev.logify.api;

import java.util.regex.Pattern;

/**
 * @author Innectic
 * @since 02/10/2019
 */
public class LogifyConstants {

    public static final Pattern TIME_PATTERN = Pattern.compile("((?<hour>\\d+)h)?((?<minute>\\d+)m)?((?<second>\\d+)s)?");
}
