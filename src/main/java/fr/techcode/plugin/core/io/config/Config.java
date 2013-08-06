package fr.techcode.plugin.core.io.config;

public interface Config {

    /**
     * Load config if exist or create config by default
     */
    public void load();

    /**
     * Unload config
     */
    public void unload();

}
