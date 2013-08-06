package fr.techcode.plugin.core.command.dispatch;

import fr.techcode.plugin.core.command.handle.CommandArguments;
import fr.techcode.plugin.core.command.handle.CommandSource;
import fr.techcode.plugin.core.command.util.CommandUnity;
import fr.techcode.plugin.core.command.util.UsageGenerator;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class CommandDispatch<K> implements CommandUnity {

    // Master usage for this dispatcher
    protected String master;
    // Usage for this dispather
    protected String usage;
    // All usages
    protected List<String> usages;
    // Commands Map
    protected Map<K, CommandUnity> commands;

    /**
     * Build a Command dispatcher
     */
    public CommandDispatch(String access, String args, String desc) {
        this.master = ChatColor.GOLD + "Utilisation: " + access + ' ' + args;
        this.usage = UsageGenerator.generate(access, args, desc);
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
        if (args.length < 1) {
            for (String msg : usages) sender.sendMessage(msg);
            return true;
        }

        execute(new CommandSource(sender), new CommandArguments(args));
        return true;
    }

    /**
     * Register a command handler
     *
     * @param accessor | Command access
     * @param command  | Command handler
     */
    public void register(K accessor, CommandUnity command) {
        commands.put(accessor, command);
    }

    /**
     * Unregister a command handler
     *
     * @param accessor | Command access
     */
    public void unregister(K accessor) {
        commands.get(accessor).dispose();
        commands.remove(accessor);
    }

    /**
     * Release all resources
     */
    public void dispose() {
        onDispose();

        Iterator<Map.Entry<K, CommandUnity>> it = commands.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().dispose();
            it.remove();
        }

        commands = null;
        usages = null;
        usage = null;
    }

    /**
     * Get usage for this dispatcher
     *
     * @return | Usage
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Update usages list
     */
    public void update() {
        usages.clear();
        usages.add(master);
        for (Map.Entry<K, CommandUnity> entry : commands.entrySet()) {
            usages.add(entry.getValue().getUsage());
        }
    }

    /**
     * Fired an event when the dispatch occured
     *
     * @param source | Player or Console calling the server
     * @param args   | Arguments for the commands
     */
    public abstract void execute(CommandSource source, CommandArguments args);

    /**
     * Fired on dispose event
     */
    protected void onDispose() {
    }

}
