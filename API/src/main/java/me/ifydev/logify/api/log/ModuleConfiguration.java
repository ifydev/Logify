package me.ifydev.logify.api.log;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * @author Innectic
 * @since 02/08/2019
 */
@AllArgsConstructor
public class ModuleConfiguration {

    @Getter private boolean moduleEnabled;
    private Map<String, Boolean> moduleStatus;

    public boolean isSubModuleEnabled(String module) {
        return moduleStatus.getOrDefault(module, false);
    }

    public void setModuleStatus(String module, boolean status) {
        moduleStatus.put(module, status);
    }
}
