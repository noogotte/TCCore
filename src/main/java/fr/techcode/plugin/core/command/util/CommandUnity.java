package fr.techcode.plugin.core.command.util;

import fr.techcode.plugin.core.command.handle.CommandArguments;
import fr.techcode.plugin.core.command.handle.CommandSource;
import org.bukkit.command.CommandExecutor;

public interface CommandUnity extends CommandExecutor {

    /**
     * Fired an event when the command occured
     *
     * @param source | Player or Console calling the server
     * @param args   | Arguments for the commands
     */
    public void execute(CommandSource source, CommandArguments args);

    /**
     * Release all resources
     */
    public void dispose();

    /**
     * Get usage of this command
     *
     * @return | Command usage
     */
    public String getUsage();

}
