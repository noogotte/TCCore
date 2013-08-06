package fr.techcode.plugin.core;

import fr.techcode.plugin.core.io.logging.TCLogger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class TCCore extends JavaPlugin {

    //
    // Plugin instance reference
    private static TCCore instance;
    // Logger instance reference
    private static TCLogger logger;
    // Permission instance of Vault
    private static Permission permission = null;
    // Prefix for proper logs & messages
    public final static String PREFIX = "[TCCore] ";

    /**
     * Load the plugin
     */
    @Override
    public void onEnable() {
        // Init some stuff
        instance = this;
        logger = new TCLogger(PREFIX);

        // All setup
        setupPermissions();

        // Good :)
        logger.add("Load & Ready").info();
    }

    /**
     * Unload the plugin
     */
    @Override
    public void onDisable() {
        // Good :)
        logger.add("Unload & Clean").info();

        // Help GC
        logger = null;
        instance = null;
    }

    /**
     * Get an instance of TCCore
     *
     * @return | Instance of TCCore or null
     */
    public static TCCore getInstance() {
        return instance;
    }

    /**
     * Get an instance of TCLogger
     *
     * @return | Instance of TCLogger or null
     */
    public static TCLogger getLogs() {
        return logger;
    }

    /**
     * Get an instance of Permission
     *
     * @return | Instance of Permission or null
     */
    public static Permission getPermission() {
        return permission;
    }

    /**
     * Attempt to setup Permission support
     *
     * @return | If this is a success or not
     */
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

}
