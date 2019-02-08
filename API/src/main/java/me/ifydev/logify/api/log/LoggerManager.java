package me.ifydev.logify.api.log;

import lombok.Getter;
import me.ifydev.logify.api.database.AbstractDatabaseHandler;

/**
 * @author Innectic
 * @since 02/07/2019
 */
@Getter
public class LoggerManager {
    private BlockLogger blockLogger;

    public LoggerManager(AbstractDatabaseHandler handler) {
        blockLogger = new BlockLogger(handler);
    }
}
