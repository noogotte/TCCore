package fr.techcode.plugin.core.command.handle;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandSource {

    // Command caller
    private CommandSender sender;
    // Cached all elements of the next message
    private List<String> cached = null;

    /**
     * Build a Command source
     *
     * @param sender | Command caller
     */
    public CommandSource(CommandSender sender) {
        this.sender = sender;
    }

    /**
     * Get name of sender
     *
     * @return | Sender name
     */
    public String getName() {
        return sender.getName();
    }

    /**
     * Ensure that cached is initialized
     *
     * @return | Init cached
     */
    public CommandSource build() {
        if (cached == null) cached = new ArrayList<String>(16);
        return this;
    }

    /**
     * Add an element for message
     *
     * @param el | Message element
     */
    public CommandSource add(Object el) {
        cached.add(String.valueOf(el));
        return this;
    }

    /**
     * Send a message to sender
     */
    public void send() {
        // Get some stuff & append prefix
        StringBuilder builder = new StringBuilder(256);
        Iterator<String> it = cached.iterator();

        // Iterate over all elements
        while (it.hasNext()) {
            builder.append(it.next());
            it.remove();
        }

        // Return next log
        sender.sendMessage(builder.toString());
    }

    /**
     * Send a message to sender
     *
     * @param msg | Message to send
     */
    public void send(String msg) {
        sender.sendMessage(msg);
    }

    /**
     * Get player sender
     *
     * @return | Player
     */
    public Player getPlayer() {
        return (isPlayer()) ? (Player) sender : null;
    }

    /**
     * Get sender
     *
     * @return | Sender
     */
    public CommandSender getSender() {
        return sender;
    }

    /**
     * Check if the sender is a player
     *
     * @return | Is a player
     */
    public boolean isPlayer() {
        return sender instanceof Player;
    }

    /**
     * Check if the sender is the server console
     *
     * @return | Is console server
     */
    public boolean isConsole() {
        return sender instanceof ConsoleCommandSender;
    }

}
