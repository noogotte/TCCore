package fr.techcode.plugin.core.command.handle;

import fr.techcode.plugin.core.TCCore;
import fr.techcode.plugin.core.command.util.CommandUnity;
import fr.techcode.plugin.core.command.util.UsageGenerator;
import fr.techcode.plugin.core.io.logging.TCLogger;
import fr.techcode.plugin.core.permission.Permission;
import org.bukkit.command.CommandSender;

public abstract class CommandHandler implements CommandUnity {

    // Id refer for a proper log identification
    private String id;
    // Usage for this command handler
    protected String usage;
    // Logger for this command
    private TCLogger logger;
    // Permission checker
    protected Permission permission;

    /**
     * Build a command handler
     *
     * @param id         | Id command
     * @param permission | Permission for this command
     */
    public CommandHandler(String id, Permission permission, TCLogger logger) {
        this.id = id;
        this.permission = permission;
        this.logger = logger;
    }

    /**
     * Handle the event command
     *
     * @param sender | Player or Console calling the server
     * @param cmd    | Ignored
     * @param label  | Ignored
     * @param args   | Arguments for the commands
     * @return | If the commands is handle or not
     */
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        execute(new CommandSource(sender), new CommandArguments(args));
        return true;
    }

    /**
     * Fired an event when the command occured
     *
     * @param source | Player or Console calling the server
     * @param args   | Arguments for the commands
     */
    @Override
    public void execute(CommandSource source, CommandArguments args) {
        // Start timer & check
        long start = System.currentTimeMillis();

        logger.add(id).add(" - ").add(source.getName()).info();
        if (permission.check(source.getSender())) {
            logger.add(id).add(" | Fail (").add(System.currentTimeMillis() - start).add(" ms)").info();
            return;
        }
        logger.add(id).add(" | ").add(onExecute(source, args) ? "Success" : "Fail").add(" (").add(System.currentTimeMillis() - start).add(" ms)").info();
    }

    /**
     * Release all resources
     */
    public void dispose() {
        onDispose();
        permission = null;
        logger = null;
        usage = null;
        id = null;
    }

    /**
     * Get usage of this command
     *
     * @return | Command usage
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Generate usage from information
     *
     * @param access | Canonical access to this command
     * @param args   | Arguments
     * @param desc   | Description
     */
    public void setUsage(String access, String args, String desc) {
        usage = UsageGenerator.generate(access, args, desc);
    }

    /**
     * Generate usage from information
     *
     * @param access | Canonical access to this command
     * @param args   | Arguments
     * @param desc   | Description
     */
    public void setUsage(String access, String args, String desc, UsageGenerator generator) {
        usage = generator.quickGen(access, args, desc);
    }

    /**
     * Fired on execute event
     *
     * @param source | Sender of the command
     * @param args   | Command arguments
     */
    protected abstract boolean onExecute(CommandSource source, CommandArguments args);


    /**
     * Fired on dispose event
     */
    protected void onDispose() {
    }

}
