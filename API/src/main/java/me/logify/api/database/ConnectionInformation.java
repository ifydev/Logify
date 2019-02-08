package me.logify.api.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * @author Innectic
 * @since 02/07/2019
 */
@AllArgsConstructor
@Getter
public class ConnectionInformation {
    public DatabaseType type;
    private String url;
    private String database;
    private int port;
    private String username;
    private String password;
    private Map<String, Object> meta;
}
