package me.ifydev.logify.api;

import java.util.regex.Pattern;

/**
 * @author Innectic
 * @since 02/10/2019
 */
public class LogifyConstants {

    public static final String LOGIFY_PREFIX = "&a&lLogify> ";
    public static final Pattern TIME_PATTERN = Pattern.compile("((?<hour>\\d+)h)?((?<minute>\\d+)m)?((?<second>\\d+)s)?");

    public static final String NOT_ENOUGH_ARGUMENTS_REVERT = LOGIFY_PREFIX + "/logify revert <selection|radius> <args...>";
    public static final String NOT_ENOUGH_ARGUMENTS_REVERT_SELECTION = LOGIFY_PREFIX + "/logify revert selection <time>";
    public static final String NOT_ENOUGH_ARGUMENTS_REVERT_RADIUS = LOGIFY_PREFIX + "/logify revert radius <radius> <time>";
}
