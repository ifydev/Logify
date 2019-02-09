package me.ifydev.logify.api.log;

import lombok.Getter;
import me.ifydev.logify.api.LogifyAPI;
import me.ifydev.logify.api.database.AbstractDatabaseHandler;
import me.ifydev.logify.api.log.modules.BlockLogger;

import java.util.Optional;

/**
 * @author Innectic
 * @since 02/07/2019
 */
@Getter
public class LoggerManager {
    private Optional<BlockLogger> blockLogger = Optional.empty();

    public LoggerManager(AbstractDatabaseHandler handler) {
        Optional<LogifyAPI> api = LogifyAPI.get();
        if (!api.isPresent()) return;

        if (api.get().getBlockModuleConfig().isModuleEnabled()) blockLogger = Optional.of(new BlockLogger(handler));
    }
}
