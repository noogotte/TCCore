package fr.techcode.plugin.core.command.dispatch;

import fr.techcode.plugin.core.command.handle.CommandArguments;
import fr.techcode.plugin.core.command.handle.CommandSource;
import fr.techcode.plugin.core.command.util.CommandUnity;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandDispatchName extends CommandDispatch<String> {

    /**
     * Build a Command dispatcher based on name
     */
    public CommandDispatchName(String access, String args, String desc) {
        super(access, args, desc);
        commands = new HashMap<String, CommandUnity>(16);
        usages = new ArrayList<String>(16);
    }

    /**
     * Fired an event when the dispatch occured
     *
     * @param source | Player or Console calling the server
     * @param args   | Arguments for the commands
     */
    @Override
    public void execute(CommandSource source, CommandArguments args) {
        CommandUnity command = commands.get(args.getArg((byte) 0));
        if (command != null) {
            command.execute(source, args.incrementAndGet());
        } else {
            for (String msg : usages) source.send(msg);
        }
    }

    /**
     * Fired on dispose event
     */
    @Override
    public void onDispose() {
        usages.clear();
    }

}
