package fr.techcode.plugin.core.io.config;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

public class ConfigManager<T extends Enum<T>> {

    // All configs currently known
    private EnumMap<T, Config> configs;

    /**
     * Create a Config manager
     *
     * @param type | All config type
     */
    public ConfigManager(Class<T> type) {
        configs = new EnumMap<T, Config>(type);
    }

    /**
     * Register a new config
     *
     * @param type   | All config type
     * @param config | Config to register
     */
    public void register(T type, Config config) {
        configs.put(type, config);
    }

    /**
     * Unregister a config
     *
     * @param type | All config type
     */
    public void unregister(T type) {
        Config config = configs.get(type);
        if (config != null) {
            config.unload();
            configs.remove(type);
        }
    }

    /**
     * Get config
     *
     * @param type | Type of the config
     * @return | Config or null
     */
    public Config get(T type) {
        return configs.get(type);
    }

    /**
     * Release all resources
     */
    public void dispose() {
        Iterator<Map.Entry<T, Config>> it = configs.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().unload();
            it.remove();
        }
    }

}