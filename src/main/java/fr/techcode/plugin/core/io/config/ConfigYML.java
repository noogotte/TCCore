package fr.techcode.plugin.core.io.config;

import fr.techcode.plugin.core.io.logging.TCLogger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class ConfigYML implements Config {

    // Instance of config yml
    protected YamlConfiguration config;
    // File (Path) to the config
    protected File path;
    // Instance of logger
    protected TCLogger logger;

    /**
     * Create a config yml
     *
     * @param path | Path to the config directory
     */
    public ConfigYML(File path, TCLogger logger) {
        this.path = path;
        this.logger = logger;
        load();
    }

    /**
     * Load config if exist or create config by default
     */
    public void load() {
        // New config
        config = new YamlConfiguration();

        // Check config
        check();

        // Load default config
        defaults();

        // Call load event
        onLoad();
    }

    /**
     * Unload config
     */
    public void unload() {
        // Call unload event
        onUnload();

        // Attemps to save the config
        try {
            logger.add(getName()).add(" | Save configuration...").info();
            config.save(path.getAbsoluteFile());
        } catch (IOException ignored) {
            logger.add(getName()).add(" | File configuration can't be save.").severe();
        }

        // Help GC
        config = null;
        logger = null;
        path = null;
    }

    /**
     * Add a default value
     *
     * @param path  | Path access
     * @param value | Boolean value
     */
    protected void addDefault(String path, boolean value) {
        if (!config.isBoolean(path)) config.set(path, value);
    }

    /**
     * Add a default value
     *
     * @param path  | Path access
     * @param value | Integer value
     */
    protected void addDefault(String path, int value) {
        if (!config.isInt(path)) config.set(path, value);
    }

    /**
     * Add a default value
     *
     * @param path  | Path access
     * @param value | Double value
     */
    protected void addDefault(String path, double value) {
        if (!config.isDouble(path)) config.set(path, value);
    }

    /**
     * Add a default value
     *
     * @param path  | Path access
     * @param value | Double value
     */
    protected void addDefault(String path, long value) {
        if (!config.isLong(path)) config.set(path, value);
    }

    /**
     * Check & Create if necessary the config file
     */
    private void check() {
        // Check exist
        if (path.exists()) {
            loadConfig();
        } else {
            createConfig();
        }
    }

    /**
     * Create config file
     */
    private void createConfig() {
        // Create file config
        try {
            logger.add(getName()).add(" | Create default configuration...").info();
            config.save(path.getAbsoluteFile());
        } catch (IOException ex) {
            logger.add(getName()).add(" | File configuration can't be create...").severe();
        }
    }

    /**
     * Load config file
     */
    private void loadConfig() {
        // Load all properties
        try {
            logger.add(getName()).add(" | Loading configuration...").info();
            config.load(path.getAbsoluteFile());
        } catch (IOException ignored) {
            logger.add(getName()).add(" | Loading configuration failed...").info();
            createConfig();
        } catch (InvalidConfigurationException ignored) {
            logger.add(getName()).add(" | Loading configuration failed...").info();
            createConfig();
        }
    }

    /**
     * Fired an event config when the config is loaded
     */
    protected void onLoad() {
    }

    /**
     * Fired an event config when the config is unloaded
     */
    protected void onUnload() {
    }

    /**
     * Default values
     */
    protected void defaults() {
    }

    /**
     * Get name of the config
     *
     * @return | Config name
     */
    protected abstract String getName();

}