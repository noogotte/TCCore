package fr.techcode.plugin.core.command.dispatch;

import fr.techcode.plugin.core.command.handle.CommandArguments;
import fr.techcode.plugin.core.command.handle.CommandSource;
import fr.techcode.plugin.core.command.util.CommandArgument;
import fr.techcode.plugin.core.command.util.CommandUnity;

import java.util.EnumMap;

public class CommandDispatchArgs extends CommandDispatch<CommandArgument> {

    /**
     * Build a Command dispatcher based on argument
     */
    public CommandDispatchArgs(String access, String args, String desc) {
        super(access, args, desc);
        commands = new EnumMap<CommandArgument, CommandUnity>(CommandArgument.class);
    }

    /**
     * Fired an event when the dispatch occured
     *
     * @param source | Player or Console calling the server
     * @param args   | Arguments for the commands
     */
    @Override
    public void execute(CommandSource source, CommandArguments args) {
        CommandUnity command = commands.get(CommandArgument.values()[args.size()]);
        if (command != null) {
            command.execute(source, args);
        } else {
            source.send(master);
            for (CommandUnity unity : commands.values()) {
                source.send(unity.getUsage());
            }
        }
    }

}