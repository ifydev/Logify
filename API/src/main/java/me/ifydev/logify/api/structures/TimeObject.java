package me.ifydev.logify.api.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @author Innectic
 * @since 02/10/2019
 */
@AllArgsConstructor
@Getter
public class TimeObject {
    // TODO: Maybe we should add days?
    private long hours;
    private long minutes;
    private long seconds;

    /**
     * Convert the time object into seconds.
     *
     * @return the time that is represented as seconds.
     */
    public long toSeconds() {
        return ((hours * 60 * 60) + (minutes * 60) + seconds);
    }

    /**
     * Convert a given amount of seconds to a TimeObject.
     *
     * @param seconds the amount of original seconds.
     * @return        the converted object containing h/m/s.
     */
    public static TimeObject fromSeconds(long seconds) {
        long hours = TimeUnit.SECONDS.toHours(seconds);
        seconds -= TimeUnit.HOURS.toSeconds(hours);

        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        seconds -= TimeUnit.MINUTES.toSeconds(minutes);

        return new TimeObject(hours, minutes, seconds);
    }
}
